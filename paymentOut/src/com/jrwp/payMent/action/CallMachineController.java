package com.jrwp.payMent.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.utils.LogUtil;
import com.jrwp.payMent.entity.CallMachine;
import com.jrwp.payMent.entity.PoliceClassService;
import com.jrwp.payMent.entity.UserService;
import com.jrwp.payMent.help.CallMachineHelper;
import com.jrwp.payMent.help.PoliceServiceHelper;
import com.jrwp.payMent.service.CallMachineService;
import com.jrwp.payMent.service.IPoliceClassService;

@Description("叫号机设备管理")
@Controller
@RequestMapping("callMachineController")
public class CallMachineController {

	@Resource
	private CallMachineService callMachineService;
	@Resource
	private IPoliceClassService policeClassService;

	private final String code = "81";

	@Description("叫号机设备列表")
	@RequestMapping("machineList")
	public ModelAndView machineList() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("callmachine/List");
		return maView;
	}

	@Description("叫号机设备Json")
	@RequestMapping("machineListJson")
	@ResponseBody
	public CallMachineHelper machineListJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			String queryinfo) {
		CallMachineHelper callMachineHelper = null;
		List<CallMachine> list = new ArrayList<>();
		try {
			PageHelper.startPage(pageNum, pageSize);
			list = callMachineService.list(queryinfo);
			PageInfo<CallMachine> page = new PageInfo<>(list);
			callMachineHelper = new CallMachineHelper(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return callMachineHelper;
	}

	@Description("添加叫号机")
	@RequestMapping("addMachine")
	public ModelAndView addMachine() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("callmachine/Info");
		return maView;
	}

	@Description("编辑叫号机")
	@RequestMapping("editMachine")
	public ModelAndView editMachine(Long id) {
		ModelAndView maView = new ModelAndView();
		try {
			CallMachine machine = callMachineService.getObjectById(id);
			maView.addObject("machine", machine);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		maView.setViewName("callmachine/Info");
		return maView;
	}

	@Description("保存设备")
	@RequestMapping("saveMachine")
	@ResponseBody
	public DoResult saveMachine(CallMachine machine) {
		DoResult result = new DoResult();
		try {
			callMachineService.save(machine);
			result.setStateMsg("保存成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("保存失败");
			result.setStateType(DoResultType.fail);
			LogUtil.error("保存叫号机失败:{}", e.getMessage());
		}
		return result;
	}

	@Description("删除设备")
	@RequestMapping("delete")
	@ResponseBody
	public DoResult delete(Long id) {
		DoResult result = new DoResult();
		try {
			callMachineService.delete(id);
			result.setStateMsg("删除成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("删除失败");
			result.setStateType(DoResultType.fail);
		}
		return result;
	}

	@Description("设备参数配置")
	@RequestMapping("macsettingView")
	public ModelAndView macsettingView(Long id) {
		ModelAndView maView = new ModelAndView();
		CallMachine machine = callMachineService.getObjectById(id);
		maView.addObject("id", id);
		maView.addObject("machine",machine);
		maView.setViewName("callmachine/setConfig");
		return maView;
	}

	@Description("保存设备参数配置")
	@RequestMapping("saveMacSetting")
	@ResponseBody
	public DoResult saveMacSetting(Long id, Long modelId) {
		DoResult result = new DoResult();
		try {
			CallMachine machine = new CallMachine();
			machine.setId(id);
			machine.setModelId(modelId);
			callMachineService.update(machine);
			result.setStateMsg("保存成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("保存失败");
			result.setStateType(DoResultType.fail);
		}
		return result;
	}

	@Description("设备业务配置")
	@RequestMapping("businessConfig")
	public ModelAndView businessConfig(String deviceNumber) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("deviceNumber", deviceNumber);
		mav.setViewName("callmachine/businessconfig");
		return mav;
	}

	@Description("业务配置Json")
	@RequestMapping("businessConfigJson")
	@ResponseBody
	public PoliceServiceHelper businessConfigJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "1") int pageSize,
			Integer uiCode, HttpServletRequest request) {
		PoliceServiceHelper policeServiceHelper = new PoliceServiceHelper();
		List<PoliceClassService> list = new ArrayList<>();
		try {
			policeServiceHelper.setCurrentPage(pageNum);
			policeServiceHelper.setItemsPerPage(pageSize);
			list = policeClassService.getTopServices((pageNum - 1) * pageSize,
					pageNum * pageSize, code);
			policeServiceHelper.setTotalItems(list.size());
			policeServiceHelper.setTotalPages(list.size() / pageSize);
			getChilds(list, uiCode.toString());
			policeServiceHelper.setItems(list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LogUtil.error("业务配置Json接口异常:{}", e.getMessage());
		}
		return policeServiceHelper;
	}

	/**
	 * 获取一级分类的子集
	 * 
	 * @param list
	 */
	public void getChilds(List<PoliceClassService> list, String uiCode) {
		// UserService userService = userServiceBiz.getByUiCode(uiCode, 0);
		// String oldSercode = "";
		// if (userService != null) {
		// oldSercode = userService.getSerCode();
		// }
		// String[] sercodes = null;
		// if (oldSercode != null && !oldSercode.equals("")) {
		// sercodes = userService.getSerCode().split(",");
		// }
		// for (PoliceClassService classService : list) {
		// // 判断是否开通
		// if (sercodes != null) {
		// for (String sercode : sercodes) {
		// if (classService.getSerCode().equals(sercode)) {
		// classService.setIsopen(true);
		// }
		// }
		// }
		// classService.setIsdelay(false);
		// classService.setIsexpand(true);
		// List<PoliceClassService> childs = policeClassService
		// .getLevel2(classService.getSerCode());
		// initChilds(childs, sercodes);
		// for (PoliceClassService child : childs) {
		// List<PoliceClassService> childsleve3 = policeClassService
		// .getLevel2(child.getSerCode());
		// initChilds(childsleve3, sercodes);
		// child.setChildren(childsleve3);
		// }
		// classService.setChildren(childs);
		// }
	}

	/**
	 * 判断子集是否有子集
	 * 
	 * @param childs
	 */
	public void initChilds(List<PoliceClassService> childs, String[] sercodes) {
		// 判断是否开通
		for (PoliceClassService service : childs) {
			service.setIsexpand(true);
			service.setIsdelay(false);
			for (PoliceClassService child : childs) {
				if (sercodes != null) {
					for (String sercode : sercodes) {
						if (child.getSerCode().equals(sercode)) {
							child.setIsopen(true);
						}
					}
				}
			}
			/*
			 * //判断是否有子集 if (policeClassService.countsize(service.getSerCode())
			 * > 0) { service.setIsexpand(false); service.setIsdelay(true);
			 * service.setChildren(new ArrayList<PoliceClassService>()); } else
			 * { service.setIsexpand(false); service.setIsdelay(false); }
			 */
		}
	}
}

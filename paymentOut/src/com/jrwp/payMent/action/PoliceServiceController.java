package com.jrwp.payMent.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

//import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Core_User;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.service.IUserService;
import com.jrwp.payMent.entity.Core_Police;
import com.jrwp.payMent.entity.PoliceClassService;
import com.jrwp.payMent.help.PoliceServiceHelper;
import com.jrwp.payMent.service.IPoliceClassService;
import com.jrwp.payMent.service.IPoliceService;

@Description("业务配置管理")
@Controller
@RequestMapping("policeServiceController")
public class PoliceServiceController {

	@Resource
	private IPoliceClassService policeClassService;
	@Resource
	private IPoliceService pService;
	@Resource
	private IUserService uService;

	@Description("业务列表")
	@RequestMapping("policeserviceList")
	public ModelAndView policeServicelist() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("policeservice/policeservicelist");
		return mav;
	}

	@Description("业务列表Json")
	@RequestMapping("policeServiceJson")
	@ResponseBody
	public PoliceServiceHelper policeServiceJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "1") int pageSize,
			HttpServletRequest request) {
		PoliceServiceHelper policeServiceHelper = new PoliceServiceHelper();
		Core_User user = (Core_User) request.getSession().getAttribute("user");// 从session中获取以后对象
		String username = user.getUserName();
		System.out.println("这是session会话中的用户名：" + username);
		// 获取pc_code
		String pcCode = uService.getObjectByName(username).getPcCode();
		if (pcCode.equals("0")) {
			try {
				Integer size = policeClassService.countsize("0");
				policeServiceHelper.setCurrentPage(pageNum);
				policeServiceHelper.setItemsPerPage(pageSize);
				policeServiceHelper.setTotalItems(size);
				policeServiceHelper.setTotalPages(size / pageSize);
				List<PoliceClassService> list = policeClassService
						.getTopServices((pageNum - 1) * pageSize, pageNum
								* pageSize, null);
				getChilds(list);
				policeServiceHelper.setItems(list);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			// 如果不是超级管理员，则只查本警钟的业务
			Integer size = 1;
			policeServiceHelper.setCurrentPage(pageNum);
			policeServiceHelper.setItemsPerPage(pageSize);
			policeServiceHelper.setTotalItems(size);
			policeServiceHelper.setTotalPages(size / pageSize);
			List<PoliceClassService> list = policeClassService.getTopServices(
					(pageNum - 1) * pageSize, pageNum * pageSize, pcCode);
			getChilds(list);
			policeServiceHelper.setItems(list);
		}
		return policeServiceHelper;
	}

	/**
	 * 获取一级分类的子集
	 * 
	 * @param list
	 */
	public void getChilds(List<PoliceClassService> list) {
		for (PoliceClassService classService : list) {
			classService.setIsdelay(false);
			classService.setIsexpand(true);
			List<PoliceClassService> childs = policeClassService
					.getLevel2(classService.getSerCode());
			initChilds(childs);
			classService.setChildren(childs);
		}

	}

	/**
	 * 判断子集是否有子集
	 * 
	 * @param childs
	 */
	public void initChilds(List<PoliceClassService> childs) {
		for (PoliceClassService service : childs) {
			// 判断是否有子集
			if (policeClassService.countsize(service.getSerCode()) > 0) {
				service.setIsexpand(false);
				service.setIsdelay(true);
				service.setChildren(new ArrayList<PoliceClassService>());
			} else {
				service.setIsexpand(false);
				service.setIsdelay(false);
			}
		}
	}

	@Description("业务树列表Json")
	@RequestMapping("policeServiceTreeJson")
	@ResponseBody
	public PoliceServiceHelper policeServiceTreeJson(
			HttpServletRequest request, String parentCode) {
		PoliceServiceHelper policeServiceHelper = new PoliceServiceHelper();
		List<PoliceClassService> childs;
		try {
			childs = policeClassService.getLevel2(parentCode);
			initChilds(childs);
			policeServiceHelper.setItems(childs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return policeServiceHelper;
	}

	@Description("添加业务")
	@RequestMapping("addPoliceService")
	public ModelAndView addPoliceService(PoliceClassService policeService,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("policeservice/policeserviceinfo");
		Core_User user = (Core_User) request.getSession().getAttribute("user");// 从session中获取以后对象
		String username = user.getUserName();
		System.out.println("这是session会话中的用户名：" + username);
		// 获取pc_code
		String pc_code = uService.getObjectByName(username).getPcCode();
		if (pc_code.equals("0")) {
			List<Core_Police> policelist = pService.getPoliceList();
			mav.addObject("policelist", policelist);// 添加警种列表，以供业务办理选择
		} else {
			mav.addObject("policelist", null);// 添加警种列表，以供业务办理选择
		}
		return mav;
	}

	@Description("保存业务")
	@RequestMapping("savePoliceService")
	@ResponseBody
	public DoResult savePoliceService(PoliceClassService policeService) {
		DoResult result = new DoResult();
		try {
			if (policeService.getSerCode() != null
					&& !policeService.getSerCode().equals("")) {
				policeClassService.updatePoliceService(policeService);
				result.setStateType(DoResultType.success);
				result.setStateMsg("业务修改成功");
			} else {
				policeClassService.savePoliceService(policeService);
				result.setStateType(DoResultType.success);
				result.setStateMsg("业务添加成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("业务保存失败");
		}
		return result;
	}

	@Description("删除业务")
	@RequestMapping("deletePoliceService")
	@ResponseBody
	public DoResult deletePolice(@RequestParam("id") String serCode) {// 把警种编号作为主键
		DoResult result = new DoResult();
		System.out.println("*****" + serCode + "*****");
		try {
			policeClassService.updateServiceIsdel(serCode);
			/* policeClassService.deletePoliceService(serCode); */
			result.setStateType(DoResultType.success);
			result.setStateMsg("业务删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("业务删除失败");
		}
		return result;
	}

	@Description("编辑业务")
	@RequestMapping("editPoliceService")
	@ResponseBody
	public ModelAndView editPolice(@RequestParam("id") String serCode,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("policeservice/policeserviceinfo");
		try {
			Core_User user = (Core_User) request.getSession().getAttribute(
					"user");// 从session中获取以后对象
			String username = user.getUserName();
			System.out.println("这是session会话中的用户名：" + username);
			// 获取pc_code
			String pc_code = uService.getObjectByName(username).getPcCode();
			PoliceClassService pcservice = policeClassService
					.getObjectByCode(serCode);
			List<Core_Police> policelist = null;
			if (!pcservice.getParentCode().equals("0")) {
				PoliceClassService parent = policeClassService
						.getObjectByCode(pcservice.getParentCode());
				pcservice.setParent(parent);
			}
			if (pc_code.equals("0")) {
				policelist = pService.getPoliceList();
			}
			mav.addObject("policeClassService", pcservice);
			mav.addObject("policelist", policelist);// 添加警种列表，以供业务办理选择
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return mav;
	}

	@Description("业务列表弹出层")
	@RequestMapping("policeServiceDialog")
	public ModelAndView deptDialog() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("policeservice/policeservicedialog");
		return mav;
	}

	@Description("模糊查询")
	@RequestMapping("getPoliceService")
	@ResponseBody
	public PoliceServiceHelper getObjectByNames(String serName, String pcName) {
		System.out.println("*****" + serName + "*****");
		System.out.println("*****" + pcName + "*****");
		PoliceServiceHelper pServiceHelper = null;
		if (serName == null) {
			serName = "";
		}
		if (pcName == null) {
			pcName = "";
		}
		List<PoliceClassService> policeserviceList = new ArrayList<PoliceClassService>();
		String serNameSearch = new StringBuffer("%").append(serName)
				.append("%").toString();
		String pcNameSearch = new StringBuffer("%").append(pcName).append("%")
				.toString();
		try {
			policeserviceList = this.policeClassService.getObjectByNames(
					serNameSearch, pcNameSearch);
			PageInfo<PoliceClassService> pageInfo = new PageInfo<PoliceClassService>(
					policeserviceList);
			pServiceHelper = new PoliceServiceHelper(pageInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return pServiceHelper;
	}
}

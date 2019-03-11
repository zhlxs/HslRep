package com.jrwp.payMent.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Core_Dept;
import com.jrwp.core.entity.Core_User;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.service.IDeptService;
import com.jrwp.core.service.IUserService;
import com.jrwp.core.utils.LogUtil;
import com.jrwp.payMent.entity.AccountManager;
import com.jrwp.payMent.entity.Mac_Windows;
import com.jrwp.payMent.help.CommonHelper;
import com.jrwp.payMent.service.IMac_WindowsService;

/**
 * 窗口设备管理
 * 
 * @author hsl
 * 
 */
@Description("窗口设备管理")
@Controller
@RequestMapping("macWindowsController")
public class Mac_WindowsController {

	@Resource
	private IMac_WindowsService macWindowsService;
	@Resource
	private IDeptService deptService;
	@Resource
	private IUserService userService;

	Logger logger = Logger.getLogger(Mac_WindowsController.class);

	@Description("设备列表")
	@RequestMapping("macList")
	public ModelAndView MacList() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("Mac/List");
		return maView;
	}

	@Description("设备列表Json")
	@RequestMapping("macListJson")
	@ResponseBody
	public CommonHelper<Mac_Windows> macListJson(
			HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			Long[] deptids, String deviceNumber, String ckbh) {
		CommonHelper<Mac_Windows> commonHelper = null;
		List<String> startCodes = null;
		List<Mac_Windows> list = new ArrayList<>();
		try {
			if (deviceNumber != null) {
				deviceNumber = "%" + deviceNumber + "%";
			}
			Long deptId = GetDeptId();
			startCodes = getStarCodes(deptids, request, null);
			PageHelper.startPage(pageNum, pageSize);
			// list = macWindowsService.list(queryinfo);
			list = macWindowsService.listForDept(deptId == 0 ? null : deptId,
					deviceNumber, ckbh, startCodes);
			PageInfo<Mac_Windows> pageInfo = new PageInfo<>(list);
			commonHelper = new CommonHelper<Mac_Windows>(pageInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.info("json接口[macListJson]发生异常:", e);
			LogUtil.info("json接口[macListJson]发生异常:{}", e.getMessage());
		}
		return commonHelper;
	}

	@Description("添加设备")
	@RequestMapping("addtion")
	public ModelAndView addition() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("Mac/Info");
		return maView;
	}

	@Description("修改设备")
	@RequestMapping("editMac")
	public ModelAndView editMac(Long id) {
		ModelAndView maView = new ModelAndView();
		try {
			Mac_Windows window = macWindowsService.getObjectById(id);
			Core_Dept dept = deptService.getObjectById(window.getDeptid());
			window.setDept(dept);
			maView.addObject("window", window);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		maView.setViewName("Mac/Info");
		return maView;
	}

	@Description("保存设备")
	@RequestMapping("saveMac")
	@ResponseBody
	public DoResult saveMac(Mac_Windows window) {
		DoResult result = new DoResult();
		try {
			macWindowsService.save(window);
			result.setStateMsg("保存成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("保存失败");
			result.setStateType(DoResultType.fail);
			logger.info("json接口[saveMac]发生异常:", e);
			LogUtil.info("json接口[saveMac]发生异常:{}", e.getMessage());
		}
		return result;
	}

	@Description("删除设备")
	@RequestMapping("delete")
	@ResponseBody
	public DoResult delete(Long id) {
		DoResult result = new DoResult();
		try {
			macWindowsService.delete(id);
			result.setStateMsg("删除成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("删除失败");
			result.setStateType(DoResultType.fail);
			logger.info("json接口[saveMac]发生异常:", e);
			LogUtil.info("json接口[saveMac]发生异常:{}", e.getMessage());
		}
		return result;
	}

	/**
	 * 获取StartCodes
	 * 
	 * @param deptids
	 * @param request
	 * @param parentids
	 * @return
	 */
	public List<String> getStarCodes(Long[] deptids,
			HttpServletRequest request, Long[] parentids) {
		List<String> startCodes = new ArrayList<String>();
		// 如果没有传deptids 查询登录用户所能看到的信息
		if (deptids == null && parentids == null) {
			// 获取session中的用户信息 得出部门信息
			Core_User user = (Core_User) request.getSession().getAttribute(
					"user");
			if (user.getId() == 1) {
				return null;
			} else {
				Long logindeptid = userService.getdeptid(user.getUserName());
				Core_Dept dept = deptService.getObjectById(logindeptid);
				String deptCode = dept.getDeptCode();
				if (deptService.countchild(logindeptid.intValue()) > 0) {
					// 如果存在子类 则将按ordercode截取部门编码
					String orderCode = dept.getOrderCode();
					String startCode = deptCode.substring(0,
							orderCode.length() / 5 * 2) + "%";
					startCodes.add(startCode);
				} else {
					// 如果不存在子类 则直接存入部门编码
					startCodes.add(deptCode);
				}
			}
		}
		List<Core_Dept> alldept = deptService.list("");
		if (deptids != null) {
			for (Long deptid : deptids) {
				for (Core_Dept dept : alldept) {
					if (deptid.equals(dept.getId())) {
						startCodes.add(dept.getDeptCode());
					}
				}
			}
		}
		if (parentids != null) {
			for (Long parentid : parentids) {
				for (Core_Dept dept : alldept) {
					if (parentid.equals(dept.getId())) {
						String orderCode = dept.getOrderCode();
						String deptcode = dept.getDeptCode();
						String startCode = deptcode.substring(0,
								orderCode.length() / 5 * 2)
								+ "%";
						startCodes.add(startCode);
					}
				}
			}
		}
		return startCodes;
	}

	/**
	 * 获取当前登录用户的部门id
	 * 
	 * @return
	 */
	public Long GetDeptId() {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		Core_User user = (Core_User) session.getAttribute("user");
		Long deptId = userService.getdeptid(user.getUserName());
		return deptId;
	}
}

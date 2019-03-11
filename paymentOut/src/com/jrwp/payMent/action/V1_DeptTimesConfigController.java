package com.jrwp.payMent.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.jrwp.payMent.entity.DeptTimesConfig;
import com.jrwp.payMent.entity.DeptTimesConfigJson;
import com.jrwp.payMent.help.CommonHelper;
import com.jrwp.payMent.service.IDeptTimesConfigService;

@Description("部门取号次数配置")
@Controller
@RequestMapping("deptTimesConfigController")
public class V1_DeptTimesConfigController {

	@Resource
	private IDeptTimesConfigService deptTimesConfigService;
	@Resource
	private IDeptService deptService;
	@Resource
	private IUserService userService;

	@Description("部门取号配置列表")
	@RequestMapping("timesConfigList")
	public ModelAndView TimesConfigList() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("dept_timesconfig/list");
		return maView;
	}

	@Description("部门取号配置列表Json")
	@RequestMapping("timesConfigListJson")
	@ResponseBody
	public CommonHelper<DeptTimesConfigJson> TimesConfigListJson(
			HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			Long[] deptids, String deptname) {
		CommonHelper<DeptTimesConfigJson> commonHelper = null;
		List<String> startCodes = null;
		List<DeptTimesConfigJson> list = new ArrayList<>();
		try {
			if (deptname != null) {
				deptname = "%" + deptname + "%";
			}
			Long deptId = GetDeptId();
			startCodes = getStarCodes(deptids, request, null);
			DeptTimesConfig config = deptTimesConfigService.getObjectById(0l);// 默认参数配置
			PageHelper.startPage(pageNum, pageSize);
			list = deptTimesConfigService.list(deptId == 0 ? null : deptId,
					deptname, startCodes);
			for (DeptTimesConfigJson json : list) {
				if (json.getTimeinter() == null) {
					json.setTimeinter(config.getTimeInter());
					json.setTimes(config.getTimes());
				} else {
					json.setIsdefault(-1);
				}
			}
			PageInfo<DeptTimesConfigJson> pageInfo = new PageInfo<>(list);
			commonHelper = new CommonHelper<DeptTimesConfigJson>(pageInfo);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.error("接口[TimesConfigListJson]出现异常:{}", e.getMessage());
		}
		return commonHelper;
	}

	@Description("部门取号配置弹窗")
	@RequestMapping("timesConfig")
	public ModelAndView TimesConfig(Long deptid, String deptname) {
		ModelAndView maView = new ModelAndView();
		DeptTimesConfig config = deptTimesConfigService.getObjectById(deptid);
		if (config != null) {
			maView.addObject("deptTimesConfig", config);
		}
		maView.setViewName("dept_timesconfig/dialog");
		maView.addObject("deptid", deptid);
		maView.addObject("deptname", deptname);
		return maView;
	}

	@Description("保存取号配置结果")
	@RequestMapping("saveTimesConfig")
	@ResponseBody
	public DoResult saveTimesConfig(DeptTimesConfig deptTimesConfig) {
		DoResult result = new DoResult();
		try {
			DeptTimesConfig config = deptTimesConfigService
					.getObjectById(deptTimesConfig.getDeptid());
			deptTimesConfig.setUpdatetime(new Date());
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			Core_User user = (Core_User) session.getAttribute("user");
			deptTimesConfig.setCreator(user.getId());
			if (config == null) {
				deptTimesConfigService.save(deptTimesConfig);
			} else {
				deptTimesConfigService.update(deptTimesConfig);
			}
			result.setStateMsg("保存成功，配置已生效!");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			result.setStateMsg("保存失败");
			result.setStateType(DoResultType.fail);
			LogUtil.error("[saveTimesConfig]接口异常:{}", e.getMessage());
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

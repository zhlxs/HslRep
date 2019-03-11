package com.jrwp.core.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Action;
import com.jrwp.core.entity.AuthHelper;
import com.jrwp.core.entity.Core_ActionForAuth;
import com.jrwp.core.entity.Core_Auth;
import com.jrwp.core.entity.Core_Role;
import com.jrwp.core.entity.RoleHelper;
import com.jrwp.core.help.ActionHelper;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.service.IAuthService;
import com.jrwp.core.service.IRoleService;
import com.jrwp.core.utils.ControlManager;
import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.core.utils.LogUtil;

@Description("权限管理")
@Controller
@RequestMapping("authController")
public class AuthController {
	@Resource
	private IAuthService authService;

	@Resource
	private IRoleService roleService;

	@Description("权限列表")
	@RequestMapping("authList")
	public ModelAndView authList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("auth/authlist");
		return mav;

	}

	@Description("权限分页Json")
	@RequestMapping("authJson")
	@ResponseBody
	public AuthHelper authJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "50") int pageSize,
			String queryinfo) {
		AuthHelper authHelper = null;
		try {
			PageHelper.startPage(pageNum, pageSize);
			List<Core_Auth> list = authService.list(queryinfo);
			PageInfo<Core_Auth> page = new PageInfo<Core_Auth>(list);
			authHelper = new AuthHelper(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return authHelper;
	}

	@Description("添加权限")
	@RequestMapping("addAuth")
	public ModelAndView addAuth(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("auth/authinfo");
		return mav;
	}

	@Description("编辑权限")
	@RequestMapping("editAuth")
	public ModelAndView editAuth(long id) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("auth/authinfo");
		try {
			Core_Auth core_Auth = authService.getObjectById(id);
			AuthHelper authHelper = new AuthHelper(core_Auth);
			mav.addObject("authHelper", authHelper);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@Description("保存权限")
	@RequestMapping("saveAuth")
	@ResponseBody
	public DoResult saveAuth(Core_Auth auth, String actionValues,
			String actionNames) {
		DoResult result = new DoResult();
		try {
			authService.save(auth, actionValues, actionNames);
			result.setStateType(DoResultType.success);
			result.setStateMsg("权限保存成功");
			result.setStateValue(auth.getId());
		} catch (Exception e) {
			LogUtil.error("权限保存失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg("权限保存失败");
			result.setStateValue(auth.getId());
		}
		return result;
	}

	@Description("删除权限")
	@RequestMapping("deleteAuth")
	@ResponseBody
	public DoResult deleteAuth(Long id) {
		DoResult result = new DoResult();
		try {
			authService.delete(id);
			result.setStateType(DoResultType.success);
			result.setStateMsg("删除1条权限数据");
			result.setStateValue(id);
		} catch (Exception e) {
			LogUtil.error("删除权限失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg("权限保存失败");
			result.setStateValue(id);
		}
		return result;
	}

	@Description("动作树")
	@RequestMapping("actionTree")
	public ModelAndView actionTree(HttpServletRequest request, String actions,
			Long id) {
		ModelAndView mav = new ModelAndView();
		try {
			List<Action> controllers = ControlManager.getControllers();
			ActionHelper actionHelper = new ActionHelper(controllers);
			String actionJson = JacksonUtil.toJson(actionHelper);
			mav.addObject("actionJson", actionJson);
			mav.addObject("actions", actions);
			if (id != null) {
				Core_Auth core_Auth = authService.getObjectById(id);
				List<Core_ActionForAuth> actionList = core_Auth.getActions();
				Map<String, List<Core_ActionForAuth>> map = new HashMap<String, List<Core_ActionForAuth>>();
				map.put("items", actionList);
				String action = JacksonUtil.toJson(map);
				mav.addObject("actionJson", action);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.setViewName("auth/actiontree");
		return mav;
	}

	@Description("权限列表弹出层")
	@RequestMapping("authDialog")
	public ModelAndView authDialog(String actiontype, String authids,
			String roleid) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("auth/authlist");
		mav.addObject("actiontype", actiontype);
		if (roleid != null) {
			Core_Role role = roleService.getObjectById(Long.parseLong(roleid));
			RoleHelper roleHelper = new RoleHelper(role);
			authids = roleHelper.getRole().getAuthIDs();
		}
		mav.addObject("authids", authids == null || authids == "" ? "0"
				: authids);
		return mav;
	}

	@Description("权限列表弹出层Json")
	@RequestMapping("authDialogJson")
	@ResponseBody
	public AuthHelper authDialogJson(
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "50") int pageSize,
			String authids, String actiontype, String queryinfo) {
		AuthHelper authHelper = null;
		try {
			List<Core_Auth> list = null;
			PageHelper.startPage(pageNum, pageSize);
			if ("forrole".equals(actiontype)) {
				list = authService.getAuthListByIds(authids);
			} else {
				list = authService.list(queryinfo);
			}
			PageInfo<Core_Auth> page = new PageInfo<Core_Auth>(list);
			authHelper = new AuthHelper(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return authHelper;
	}
}

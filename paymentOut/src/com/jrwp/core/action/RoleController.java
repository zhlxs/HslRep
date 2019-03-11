package com.jrwp.core.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Core_Role;
import com.jrwp.core.entity.Core_User;
import com.jrwp.core.entity.Core_UserHelper;
import com.jrwp.core.entity.RoleHelper;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.service.IRoleService;
import com.jrwp.core.service.IUserService;
import com.jrwp.core.utils.LogUtil;

@Description("角色管理")
@Controller
@RequestMapping("roleController")
public class RoleController {
	@Resource
	private IRoleService roleService;

	@Resource
	private IUserService userService;

	@Description("角色列表")
	@RequestMapping("roleList")
	public ModelAndView roleList() {
		ModelAndView mav = new ModelAndView("role/rolelist");
		return mav;
	}

	@Description("角色列表Json")
	@RequestMapping("roleJson")
	@ResponseBody
	public RoleHelper roleJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
			String queryinfo) {
		RoleHelper roleHelper = null;
		try {
			PageHelper.startPage(pageNum, pageSize);
			List<Core_Role> roleList = roleService.list(queryinfo);
			PageInfo<Core_Role> page = new PageInfo<Core_Role>(roleList);
			roleHelper = new RoleHelper(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleHelper;
	}

	@Description("添加角色")
	@RequestMapping("addRole")
	public ModelAndView addRole() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("role/roleinfo");
		return mav;
	}

	@Description("编辑角色")
	@RequestMapping("editRole")
	@ResponseBody
	public ModelAndView editRole(long id) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("role/roleinfo");
		RoleHelper roleHelper = null;
		try {
			Core_Role role = roleService.getObjectById(id);
			roleHelper = new RoleHelper(role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("roleHelper", roleHelper);
		return mav;
	}

	@Description("保存角色")
	@RequestMapping("saveRole")
	@ResponseBody
	public DoResult saveRole(Core_Role role, String authIDs, String authNames) {
		DoResult result = new DoResult();
		try {
			roleService.save(role, authIDs, authNames);
			result.setStateType(DoResultType.success);
			result.setStateMsg("角色保存成功");
		} catch (Exception e) {
			LogUtil.error("保存角色失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg("角色保存失败");
		}
		return result;
	}

	@Description("删除角色")
	@RequestMapping("deleteRole")
	@ResponseBody
	public DoResult deleteRole(long id) {
		DoResult result = new DoResult();
		try {
			roleService.delete(id);
			result.setStateType(DoResultType.success);
			result.setStateMsg("删除1条角色数据");
		} catch (Exception e) {
			LogUtil.error("删除角色失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg("删除失败");
		}
		return result;
	}

	@Description("角色列表弹出层")
	@RequestMapping("roleDialog")
	public ModelAndView roleDialog(String actiontype, String roleids, String id) {
		ModelAndView mav = new ModelAndView("role/rolelist");

		mav.addObject("actiontype", actiontype);
		if (id != null) {
			Core_User user = userService.getObjectById(Long.parseLong(id));
			Core_UserHelper userHelper = new Core_UserHelper(user);
			roleids = userHelper.getUser().getRoleIDs();
		}
		mav.addObject("roleids", roleids == null || roleids == "" ? "0"
				: roleids);
		return mav;
	}

	@Description("角色列表弹出层Json")
	@RequestMapping("roleDialogJson")
	@ResponseBody
	public RoleHelper roleDialogJson(
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
			String actiontype, String roleids, String queryinfo) {
		RoleHelper roleHelper = null;
		try {
			PageHelper.startPage(pageNum, pageSize);
			List<Core_Role> roleList = null;
			if ("forrole".equals(actiontype)) {
				roleList = roleService.getRoleListByIds(roleids);
			} else {
				roleList = roleService.list(queryinfo);
			}

			PageInfo<Core_Role> page = new PageInfo<Core_Role>(roleList);
			roleHelper = new RoleHelper(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleHelper;
	}
}

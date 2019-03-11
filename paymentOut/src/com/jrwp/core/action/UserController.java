package com.jrwp.core.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jrwp.core.entity.Core_Dept;
import com.jrwp.core.service.IDeptService;
import com.jrwp.payMent.entity.Core_Police;
import com.jrwp.payMent.entity.UserService;
import com.jrwp.payMent.help.UserServiceHelper;
import com.jrwp.payMent.service.IPoliceClassService;
import com.jrwp.payMent.service.IPoliceService;
import com.jrwp.payMent.service.UserServiceBiz;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Core_User;
import com.jrwp.core.entity.Core_UserHelper;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.service.IUserService;
import com.jrwp.core.utils.LogUtil;

@Description("用户管理")
@Controller
@RequestMapping("userController")
public class UserController {

	@Resource
	private IUserService userService;
	@Resource
	private UserServiceBiz userServiceBiz;
	@Resource
	private IDeptService deptService;
	@Resource
	private IPoliceService pService;

	@Description("用户列表")
	@RequestMapping("userList")
	public ModelAndView userList() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/userlist");
		return mav;
	}

	@Description("用户列表Json")
	@RequestMapping("userJson")
	@ResponseBody
	public Core_UserHelper userJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "50") int pageSize,
			String queryinfo, HttpSession session) {
		Core_UserHelper userHelper = null;
		try {
			PageHelper.startPage(pageNum, pageSize);
			Core_User user = (Core_User) session.getAttribute("user");
			Long deptid = userService.getdeptid(user.getUserName());
			String dept2 = "%%";
			if (deptid != 0l) {
				Core_Dept dept = deptService.getObjectById(deptid);
				String id = dept.getOrderCode();
				String dept1 = dept.getDeptCode();
				int length = id.length();
				int a = (int) Math.ceil(length / 5);
				int b = a * 2;
				dept2 = dept1.substring(0, b + 1) + "%";
			}
			List<Core_User> list = new ArrayList<Core_User>();
			if (user.getIsSys().equals(1)) {
				list = userService.list(queryinfo);
			} else {
				list = userService.list1(queryinfo, dept2);
			}
			PageInfo<Core_User> page = new PageInfo<Core_User>(list);
			userHelper = new Core_UserHelper(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userHelper;
	}

	@Description("添加用户")
	@RequestMapping("addUser")
	public ModelAndView addUser(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/userinfo");
		// Core_User user = (Core_User)
		// request.getSession().getAttribute("user");
		// user = userService.getByUserName(user.getUserName());
		// String pcCode = user.getPcCode();
		// List<Core_Police> policelist = new ArrayList<Core_Police>();
		// if (pcCode.equals("0")) {
		// policelist = pService.getPoliceList();
		// } else {
		// Core_Police police = pService.getObjectByCode(pcCode);
		// policelist.add(police);
		// }
		// mav.addObject("policelist", policelist);
		return mav;

	}

	@Description("编辑用户")
	@RequestMapping("editUser")
	@ResponseBody
	public ModelAndView editUser(long id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/userinfo");
		try {
			Core_User user = (Core_User) request.getSession().getAttribute(
					"user");
			user = userService.getByUserName(user.getUserName());
			String pcCode = user.getPcCode();
			List<Core_Police> policelist = new ArrayList<Core_Police>();
			if (pcCode.equals("0")) {
				policelist = pService.getPoliceList();
			} else {
				Core_Police police = pService.getObjectByCode(pcCode);
				policelist.add(police);
			}
			Core_User core_User = userService.getObjectById(id);
			LogUtil.debug("部门ID:{}", core_User.getId());
			Core_UserHelper userhelper = new Core_UserHelper(core_User);
			mav.addObject("policelist", policelist);
			mav.addObject("userhelper", userhelper);
			mav.addObject("core_User", core_User);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@Description("保存用户")
	@RequestMapping("saveUser")
	@ResponseBody
	public DoResult saveUser(Core_User user, String deptName, String roleIDs,
			HttpSession session, String roleNames, Date data) {
		DoResult result = new DoResult();
		try {
			data = new Date();
			// Core_User user1 = (Core_User) session.getAttribute("user");
			// Long deptid = userService.getdeptid(user1.getUserName());
			// user.setDeptId(deptid);
			// SimpleDateFormat df = new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// System.out.println(deptid);
			user.setCreateTime(data);
			userService.save(user, deptName, roleNames);
			result.setStateType(DoResultType.success);
			result.setStateMsg("用户保存成功");
		} catch (Exception e) {
			LogUtil.error("用户保存失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg("用户保存失败");
		}
		return result;
	}

	@Description("设置用户状态")
	@RequestMapping("setUserStart")
	@ResponseBody
	public DoResult setUserState(long id, int isstart) {
		DoResult result = new DoResult();
		try {
			userService.setStart(id, isstart);
			result.setStateType(DoResultType.success);
			result.setStateMsg("状态更新成功");
		} catch (Exception e) {
			LogUtil.error("状态更新失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg("状态更新失败");
		}
		return result;
	}

	@Description("删除用户")
	@RequestMapping("deleteUser")
	@ResponseBody
	public DoResult deleteUser(long id) {
		DoResult result = new DoResult();
		try {
			userService.delete(id);
			result.setStateType(DoResultType.success);
			result.setStateMsg("角色删除成功");
		} catch (Exception e) {
			LogUtil.error("角色删除失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg("角色删除失败");
		}
		return result;
	}

	@Description("用户列表弹出层")
	@RequestMapping("userDialog")
	@ResponseBody
	public List<Core_User> userDialog(String queryinfo) {
		List<Core_User> userList = userService.list(queryinfo);
		return userList;
	}

	@Description("用户列表弹出层Json")
	@RequestMapping("userDialogJson")
	@ResponseBody
	public Core_UserHelper userDialogJson(
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
			String queryinfo, String[] deptid) {
		Core_UserHelper userhelper = null;
		System.out.println("进入到用户列表json");
		try {
			PageHelper.startPage(pageNum, pageSize);
			List<Core_User> deptList = userService.list(queryinfo);
			PageInfo<Core_User> page = new PageInfo<Core_User>(deptList);
			userhelper = new Core_UserHelper(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userhelper;
	}

	@Description("部门人员树Json")
	@RequestMapping("deptUserTreeJson")
	@ResponseBody
	public List<Core_User> deptUserTreeJson(String queryinfo) {
		List<Core_User> userList = userService.tree(queryinfo);
		return userList;
	}

	@Description("部门ID查询用户")
	@RequestMapping("userBydeptId")
	@ResponseBody
	public Core_UserHelper userBydeptId(@RequestParam("id") long deptId) {
		Core_UserHelper userHelper = null;
		try {
			List<Core_User> list = userService.getDeptId(deptId);
			PageInfo<Core_User> page = new PageInfo<Core_User>(list);
			userHelper = new Core_UserHelper(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userHelper;
	}

	@Description("用户业务管理")
	@RequestMapping("userBizInfo")
	public ModelAndView userBizInfo(Long id) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/userBizInfo");
		// mav.addObject("uiCode",id);
		mav.addObject("uiCode", id);
		return mav;
	}

	@Description("用户业务管理json")
	@RequestMapping("userBizInfoJson")
	@ResponseBody
	public UserServiceHelper userBizInfoJson(String uiCode) {
		UserServiceHelper userServiceHelper = new UserServiceHelper();
		List<UserService> userServices = new ArrayList<UserService>();
		try {
			userServices = userServiceBiz.getByUsCode(uiCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		userServiceHelper.setItems(userServices);
		return userServiceHelper;
	}
}

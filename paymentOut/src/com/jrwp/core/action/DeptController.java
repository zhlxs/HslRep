package com.jrwp.core.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Core_Dept;
import com.jrwp.core.entity.Core_User;
import com.jrwp.core.help.DeptHelper;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.service.IDeptService;
import com.jrwp.core.service.IUserService;
import com.jrwp.core.utils.LogUtil;
import com.jrwp.payMent.entity.PoliceClassService;
import com.jrwp.payMent.entity.UserService;
import com.jrwp.payMent.help.PoliceServiceHelper;
import com.jrwp.payMent.service.IPoliceClassService;

@Description("部门管理")
@Controller
@RequestMapping("deptController")
public class DeptController {

	@Resource
	private IDeptService deptService;
	@Resource
	private IUserService userService;
	@Resource
	private IPoliceClassService policeClassService;

	@Description("部门列表")
	@RequestMapping("deptList")
	public ModelAndView deptList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("dept/deptlist");
		return mav;
	}

	/**
	 * 获取子集
	 * 
	 * @param list
	 */
	public void getChilds(List<Core_Dept> list) {
		for (Core_Dept dept : list) {
			dept.setIsdelay(false);
			dept.setIsexpand(true);
			List<Core_Dept> childs = deptService.getNextLevel(dept.getId());
			initChilds(childs);
			dept.setChildren(childs);
		}

	}

	/**
	 * 判断子集是否有子集
	 * 
	 * @param childs
	 */
	public void initChilds(List<Core_Dept> childs) {
		for (Core_Dept dept : childs) {
			// 判断是否有子集
			dept.setIschecked(true);
			if (deptService.getsize(dept.getId()) > 0) {
				dept.setIsexpand(false);
				dept.setIsdelay(true);
				dept.setChildren(new ArrayList<Core_Dept>());
			} else {
				dept.setIsexpand(false);
				dept.setIsdelay(false);
			}
		}
	}

	@Description("部门分页Json")
	@RequestMapping("deptJson")
	@ResponseBody
	public DeptHelper deptJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "1") int pageSize,
			HttpServletRequest request) {
		List<Core_Dept> deptlist = null;

		DeptHelper deptHelper = new DeptHelper();
		Core_User user = (Core_User) request.getSession().getAttribute("user");
		Long deptid = userService.getdeptid(user.getUserName());
		try {
			// 如果是超级管理员
			if (deptid == 0) {
				// 先获取parentid 为0 的有几条
				Integer size = deptService.getsize(0l);
				deptHelper.setCurrentPage(pageNum);
				deptHelper.setItemsPerPage(pageSize);
				deptHelper.setTotalItems(size);
				deptHelper.setTotalPages(size / pageSize);
				deptlist = deptService.getTopServices(0L, (pageNum - 1)
						* pageSize, pageNum * pageSize);
			} else {

				// 如果不是超级管理员 则只有一页 顶级部门就是当前部门
				deptHelper.setCurrentPage(pageNum);
				deptHelper.setItemsPerPage(pageSize);
				deptHelper.setTotalItems(1);
				deptHelper.setTotalPages(1);
				Core_Dept dept = deptService.getObjectById(deptid);
				deptlist = new ArrayList<>();
				deptlist.add(dept);
			}
			getChilds(deptlist);
			deptHelper.setItems(deptlist);
			// deptlist = deptService.getDepts();
			// PageInfo<Core_Dept> page = new PageInfo<Core_Dept>(deptlist);
			// DeptHelper depthelper = new DeptHelper(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deptHelper;
	}

	@Description("部门树Json")
	@RequestMapping("deptTreeJson")
	@ResponseBody
	public DeptHelper deptTreeJson(HttpSession session, Long parentid) {

		DeptHelper deptHelper = new DeptHelper();
		List<Core_Dept> childs;
		try {
			childs = deptService.getNextLevel(parentid);
			initChilds(childs);
			deptHelper.setItems(childs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deptHelper;
	}

	@Description("部门列表树Json")
	@RequestMapping("listTreeJson")
	@ResponseBody
	public List<Core_Dept> deptTreeJson(Long parentid, HttpSession session) {

		List<Core_Dept> deptlist = null;
		try {
			// 首次加载
			if (parentid == 0) {
				Core_User user = (Core_User) session.getAttribute("user");
				Long deptid = userService.getdeptid(user.getUserName());
				// 如果是超级管理员
				if (deptid == 0) {
					deptlist = deptService.getNextLevel(parentid);
				} else {
					// 如果不是超级管理员 则只有一页 顶级部门就是当前部门
					Core_Dept dept = deptService.getObjectById(deptid);
					dept.setIschecked(true);
					deptlist = new ArrayList<>();
					deptlist.add(dept);
				}
				getChilds(deptlist);
			} else {
				// 如果不是空
				deptlist = deptService.getNextLevel(parentid);
				initChilds(deptlist);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deptlist;
	}

	@Description("添加部门")
	@RequestMapping("addDept")
	@ResponseBody
	public ModelAndView addDept(Core_Dept dept) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("dept/deptinfo");
		return mav;
	}

	@Description("编辑部门")
	@RequestMapping("editDept")
	@ResponseBody
	public ModelAndView editDept(long id) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("dept/deptinfo");
		try {
			Core_Dept core_Dept = deptService.getObjectById(id);
			DeptHelper depthelper = new DeptHelper(core_Dept);
			mav.addObject("depthelper", depthelper);
			mav.addObject("core_Dept", core_Dept);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@Description("查找部门")
	@RequestMapping("deptByname")
	@ResponseBody
	public DeptHelper deptByname(String deptname) {

		DeptHelper depthelper = null;
		if ((deptname == null) || (deptname.equals(""))) {
			List<Core_Dept> deptlist = null;

			deptlist = this.deptService.getDepts();
			PageInfo<Core_Dept> page = new PageInfo<Core_Dept>(deptlist);
			depthelper = new DeptHelper(page);
		} else {
			List<Core_Dept> deptlist = null;
			deptname = "%" + deptname + "%";
			try {
				deptlist = this.deptService.getDeptByname(deptname);
				Core_Dept dept = new Core_Dept();
				dept.setId(Long.valueOf(0L));
				dept.setParentId(Long.valueOf(0L));
				dept.setDeptName("部门");
				dept.setChildren(deptlist);
				PageInfo<Core_Dept> page = new PageInfo<Core_Dept>(deptlist);
				depthelper = new DeptHelper(page);
				List<Core_Dept> list = new ArrayList<Core_Dept>();
				list.add(dept);
				depthelper.setItems(list);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return depthelper;
	}

	@Description("保存部门")
	@RequestMapping("saveDept")
	@ResponseBody
	public DoResult saveDept(Core_Dept dept, String parentDeptName) {
		DoResult result = new DoResult();

		try {
			// String dwdm = dept.getUnitid();
			// dept.setDeptCode(dwdm);
			deptService.save(dept, parentDeptName);
			result.setStateType(DoResultType.success);
			result.setStateMsg("部门保存成功");
		} catch (Exception e) {
			LogUtil.error("保存部门失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg("部门保存失败");
			e.printStackTrace();
		}
		return result;
	}

	@Description("部门列表弹出层")
	@RequestMapping("deptDialog")
	public ModelAndView deptDialog() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("dept/deptdialog");
		return mav;
	}

	@Description("数据统计部门列表弹出层")
	@RequestMapping("sjtjdeptDialog")
	public ModelAndView sjtjdeptDialog() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sqxx/deptdialog");
		return mav;
	}

	@Description("部门列表弹出层Json")
	@RequestMapping("deptDialogJson")
	@ResponseBody
	public DeptHelper deptDialogJson(String queryinfo) {
		DeptHelper depthelper = null;
		try {
			PageHelper.startPage(1, 100);
			List<Core_Dept> deptList = deptService.list(queryinfo);
			PageInfo<Core_Dept> page = new PageInfo<Core_Dept>(deptList);
			depthelper = new DeptHelper(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return depthelper;
	}

	@Description("删除部门")
	@RequestMapping("deleteDept")
	@ResponseBody
	public DoResult deleteDept(long id) {
		DoResult result = new DoResult();
		try {
			deptService.delete(id);
			result.setStateType(DoResultType.success);
			result.setStateMsg("部门删除成功");
			result.setStateValue(id);
		} catch (Exception e) {
			LogUtil.error("部门删除失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg("部门删除失败");
			if (e.getMessage().equals("have chlidren")) {
				result.setStateMsg("请先删除子对象！");
			} else {
				result.setStateMsg("删除失败");
			}
		}
		return result;
	}

	@Description("部门显示状态")
	@RequestMapping("showDept")
	@ResponseBody
	public DoResult showDept(Long id, int isShow) {
		DoResult result = new DoResult();
		try {
			deptService.show(id, isShow);
			result.setStateType(DoResultType.success);
			result.setStateMsg("状态更新成功");
		} catch (Exception e) {
			LogUtil.error("部门显示状态更新失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg("状态更新失败");
		}
		return result;
	}

	@Description("移动部门")
	@RequestMapping("moveDept")
	@ResponseBody
	public DoResult moveDept(long id, int movetype) {
		DoResult result = new DoResult();
		try {
			deptService.move(id, movetype);
			result.setStateType(DoResultType.success);
			result.setStateMsg("移动完成");
			result.setStateValue(id);
		} catch (Exception e) {
			LogUtil.error("移动部门失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg("移动失败");
		}
		return result;
	}

	// @Description("部门树Json")
	// @RequestMapping("deptTreeJson")
	// @ResponseBody
	// public List<Core_Dept> deptTreeJson(String queryinfo, HttpSession
	// session) {
	//
	// Core_User user = (Core_User) session.getAttribute("user");
	// Long deptid = userService.getdeptid(user.getUserName());
	//
	// Core_Dept dept = deptService.getObjectById(deptid);
	//
	// List<Core_Dept> list = new ArrayList<Core_Dept>();
	// List<Core_Dept> deptlist = deptService.list(queryinfo);
	// dept.setChildren(DeptHelper.getChildren(deptlist, dept.getOrderCode()));
	// list.add(dept);
	//
	// return list;
	// }

	@RequestMapping(value = "editOtherName", method = RequestMethod.POST, produces = { "application/json;"
			+ "charset=utf-8" })
	@ResponseBody
	public Map<String, String> editOtherName(String deptid, String newName) {
		Map<String, String> result = new HashMap<String, String>();
		System.out.println(deptid);
		System.out.println(newName);
		try {

			deptService.updateOtherName(newName, deptid);
			result.put("result", "ok");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "no");
			// TODO: handle exception
		}
		return result;
	}

	/*
	 * @Description("部门业务开通")
	 * 
	 * @RequestMapping("deptBizInfo") public ModelAndView userBizInfo(Long id) {
	 * ModelAndView mav = new ModelAndView();
	 * mav.setViewName("dept/deptBizInfo"); mav.addObject("uiCode",id); return
	 * mav; }
	 * 
	 * 
	 * @Description("部门业务开通json")
	 * 
	 * @RequestMapping("deptBizInfoJson")
	 * 
	 * @ResponseBody public DeptServiceHelper userBizInfoJson(String uiCode) {
	 * DeptServiceHelper deptServiceHelper = new DeptServiceHelper();
	 * 
	 * List<UserService> userServices = new ArrayList<UserService>();
	 * 
	 * try { userServices = deptServiceBiz.getByUsCode(uiCode); } catch
	 * (Exception e){
	 * 
	 * e.printStackTrace(); } deptServiceHelper.setItems(userServices);
	 * 
	 * return deptServiceHelper; }
	 * 
	 * //给对应的部门添加业务，跳转的页面
	 * 
	 * @Description("部门对应业务添加")
	 * 
	 * @RequestMapping("addDeptService") public ModelAndView
	 * addUserService(String uiCode) { ModelAndView model = new ModelAndView();
	 * model.setViewName("dept/adddeptService"); model.addObject("uiCode",
	 * uiCode); return model; }
	 * 
	 * @Description("部门对应业务添加Json")
	 * 
	 * @RequestMapping("addDeptServiceJson")
	 * 
	 * @ResponseBody public PoliceServiceHelper policeServiceJson(
	 * 
	 * @RequestParam(value = "page", defaultValue = "1") int pageNum,
	 * 
	 * @RequestParam(value = "pageSize", defaultValue = "30") int pageSize,
	 * HttpServletRequest request,String uiCode,HttpSession session){
	 * PoliceServiceHelper policeServiceHelper = null; Core_User user =
	 * (Core_User) session.getAttribute("user"); String pcCode =
	 * user.getPcCode(); if (pcCode.equals("0")) { pcCode=null; } try {
	 * //Integer size = policeClassService.countsize();
	 * PageHelper.startPage(pageNum,pageSize); List<PoliceClassService> list =
	 * new ArrayList<PoliceClassService>(); list=
	 * policeClassService.getServices(uiCode,pcCode);
	 * 
	 * PageInfo<PoliceClassService> pageInfo = new
	 * PageInfo<PoliceClassService>(list); pageInfo.setSize(size);
	 * policeServiceHelper = new PoliceServiceHelper(pageInfo); } catch
	 * (Exception e) { e.printStackTrace(); } return policeServiceHelper; }
	 * 
	 * @Description("部门对应业务保存")
	 * 
	 * @RequestMapping("saveDeptService")
	 * 
	 * @ResponseBody public DoResult saveDeptService(String[] checkid, String
	 * uiCode) { DoResult result = new DoResult(); try { for (String s :
	 * checkid) { UserService userService = new UserService();
	 * userService.setSerCode(s); userService.setUiCode(uiCode);
	 * deptServiceBiz.save(userService); } result.setStateMsg("添加成功！");
	 * result.setStateType(DoResultType.success); } catch (Exception e) {
	 * result.setStateMsg("添加失败！"); result.setStateType(DoResultType.fail);
	 * e.printStackTrace(); } return result; }
	 * 
	 * //将isdel修改为1（默认为0）
	 * 
	 * @Description("部门对应业务删除")
	 * 
	 * @RequestMapping("deleteDeptService")
	 * 
	 * @ResponseBody public DoResult deleteUserService(String usCode) { DoResult
	 * result = new DoResult();
	 * 
	 * try { if (usCode != null && !usCode.equals("")) {
	 * deptServiceBiz.updateisdel(usCode); result.setStateMsg("删除成功！");
	 * result.setStateType(DoResultType.success); } } catch (Exception e) {
	 * result.setStateMsg("删除失败！"); result.setStateType(DoResultType.fail);
	 * e.printStackTrace(); }
	 * 
	 * return result; }
	 */
}

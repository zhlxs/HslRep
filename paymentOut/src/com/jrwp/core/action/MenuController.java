package com.jrwp.core.action;

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
import com.jrwp.core.entity.Core_Menu;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.help.MenuHelper;
import com.jrwp.core.help.SelectHelper;
import com.jrwp.core.service.IMenuService;
import com.jrwp.core.utils.ControlManager;
import com.jrwp.core.utils.LogUtil;

@Description("菜单管理")
@Controller
@RequestMapping("menuController")
public class MenuController {
	@Resource
	private IMenuService menuService;

	@Description("菜单列表")
	@RequestMapping("menuList")
	public ModelAndView menuList() {
		ModelAndView mav = new ModelAndView("menu/menulist");
		return mav;
	}

	@Description("菜单列表Json")
	@RequestMapping("menuJson")
	@ResponseBody
	public MenuHelper menuJson() {
		PageHelper.startPage(1, 100);
		List<Core_Menu> menuList = menuService.list();
		PageInfo<Core_Menu> page = new PageInfo<Core_Menu>(menuList);
		MenuHelper menuHelper = new MenuHelper(page);
		return menuHelper;
	}

	@Description("添加菜单")
	@RequestMapping("addMenu")
	public ModelAndView addMenu(Core_Menu menu) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("menu/menuinfo");
		return mav;
	}

	@Description("编辑菜单")
	@RequestMapping("editMenu")
	public ModelAndView editMenu(long id) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("menu/menuinfo");
		Core_Menu menu = menuService.getObjectById(id);
		MenuHelper menuHelper = new MenuHelper(menu);
		mav.addObject("menuHelper", menuHelper);
		return mav;
	}

	@Description("保存菜单")
	@RequestMapping("saveMenu")
	@ResponseBody
	public DoResult saveMenu(Core_Menu menu, String parentMenuName,String controlValue_txt_val,
			String actionValue_txt_val) {
		DoResult result = new DoResult();
		try {
			if (controlValue_txt_val != null
					&& !controlValue_txt_val.equals("")) {
				String menuUrl = controlValue_txt_val + "/"
						+ actionValue_txt_val;
				menuUrl = menuUrl.substring(0, 1).toLowerCase()
						+ menuUrl.substring(1);
				menu.setMenuUrl(menuUrl);
			}
			menuService.save(menu, parentMenuName);
			result.setStateMsg("菜单保存成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error("保存菜单失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg("菜单保存失败");
		}
		return result;
	}

	@Description("菜单列表弹出层")
	@RequestMapping("menuDialog")
	@ResponseBody
	public ModelAndView menuDialog(Integer parentid, Integer isall) {
		ModelAndView mav = new ModelAndView("menu/menudialog");
		mav.addObject("parentid", parentid);
		mav.addObject("isall", isall);
		return mav;
	}

	@Description("菜单列表弹出层Json")
	@RequestMapping("menuDialogJson")
	@ResponseBody
	public MenuHelper menuDialogJson(
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "50") int pageSize,
			int isall) {
		PageHelper.startPage(pageNum, pageSize);
		List<Core_Menu> menuList = menuService.list();
		PageInfo<Core_Menu> page = new PageInfo<Core_Menu>(menuList);
		MenuHelper menuHelper = new MenuHelper(page);
		return menuHelper;
	}

	@Description("删除菜单")
	@RequestMapping("deleteMenu")
	@ResponseBody
	public DoResult deleteMenu(long id) {
		DoResult result = new DoResult();
		try {
			menuService.delete(id);
			result.setStateMsg("删除完成");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			LogUtil.error("删除菜单失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			if (e.getMessage().equals("have chlidren")) {
				result.setStateMsg("请先删除子对象！");
			} else {
				result.setStateMsg("删除失败");
			}
		}
		return result;
	}

	@Description("菜单显示状态")
	@RequestMapping("showMenu")
	@ResponseBody
	public DoResult showMenu(long id, int isshow) {
		DoResult result = new DoResult();
		try {
			menuService.show(id, isshow);
			result.setStateMsg("更新成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			LogUtil.error("菜单显示状态更新失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg("操作失败");
		}
		return result;
	}

	@Description("移动菜单")
	@RequestMapping("moveMenu")
	@ResponseBody
	public DoResult moveMenu(long id, int movetype) {
		DoResult result = new DoResult();
		try {
			menuService.move(id, movetype);
			result.setStateMsg("移动完成");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			LogUtil.error("移动失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg(e.getMessage());
		}
		return result;
	}

	@Description("区域列表Json")
	@RequestMapping("areaJson")
	@ResponseBody
	public List<SelectHelper> areaJson(HttpServletRequest request) {
		List<SelectHelper> list = new ArrayList<SelectHelper>();
		List<String> area = ControlManager.getArea();
		for (String str : area) {
			SelectHelper select = new SelectHelper();
			select.setSelected(false);
			select.setText(str);
			select.setValue(str);
			list.add(select);
		}
		return list;
	}

	@Description("控制器列表Json")
	@RequestMapping("controlJson")
	@ResponseBody
	public List<SelectHelper> controlJson(String areaValue) {
		List<SelectHelper> selectList = ControlManager
				.getControllerList(areaValue);
		return selectList;
	}

	@Description("动作列表Json")
	@RequestMapping("actionJson")
	@ResponseBody
	public List<SelectHelper> actionJson(String controlValue) {
		List<SelectHelper> actionList = ControlManager
				.getActionList(controlValue);
		return actionList;
	}
}

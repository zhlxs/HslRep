package com.jrwp.core.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jrwp.core.entity.Action;
import com.jrwp.core.help.SelectHelper;

/**
 * 控制器管理器
 * 
 * @author USER
 * 
 */
@Component("controlManager")
public class ControlManager {
	private static volatile List<Action> controllers;
	private static LoadPackageClasses loadPackageClasses;
	private static IAction actionList;

	/**
	 * 获得控制器动作的集合
	 * 
	 * @return
	 */
	public static List<Action> getControllers() {
		if (controllers == null) {
			synchronized (ControlManager.class) {
				if (controllers == null) {
					try {
						Set<Class<?>> classSet = loadPackageClasses
								.getClassSet();
						controllers = actionList.getAction(classSet);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return controllers;
	}

	@Resource
	public void setLoadPackageClasses(LoadPackageClasses loadPackageClasses) {
		ControlManager.loadPackageClasses = loadPackageClasses;
	}

	@Resource
	public void setActionList(IAction actionList) {
		ControlManager.actionList = actionList;
	}

	public static List<SelectHelper> getControllerList(String areaValue) {
		List<SelectHelper> list = new ArrayList<SelectHelper>();
		List<Action> actionList = getControllers();
		for (Action action : actionList) {
			if (action.getArea().equals(areaValue)) {
				SelectHelper select = new SelectHelper();
				select.setSelected(false);
				select.setText(action.getName());
				select.setValue(action.getUrl().get(0));
				list.add(select);
			}
		}
		return list;
	}

	public static List<SelectHelper> getActionList(String controlValue) {
		List<SelectHelper> list = new ArrayList<SelectHelper>();
		List<Action> actionList = getControllers();
		for (Action action : actionList) {
			if (action.getUrl().get(0).equals(controlValue)) {
				List<Action> actions = action.getActions();
				for (Action a : actions) {
					SelectHelper select = new SelectHelper();
					select.setSelected(false);
					select.setText(a.getName());
					select.setValue(a.getUrl().get(0));
					list.add(select);
				}
				break;
			}
		}
		return list;
	}

	public static List<String> getArea() {
		List<String> list = new ArrayList<String>();
		List<Action> actionList = getControllers();
		for (Action action : actionList) {
			String area = action.getArea();
			if (!list.contains(area)) {
				list.add(area);
			}
		}
		return list;
	}

}

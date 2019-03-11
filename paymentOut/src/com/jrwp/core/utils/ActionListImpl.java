package com.jrwp.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Action;

@Component("actionList")
public class ActionListImpl implements IAction {

	@Override
	public List<Action> getAction(Set<Class<?>> classSet) {
		List<Action> actionList = new ArrayList<Action>();
		for (Class<?> clazz : classSet) {
			Action action = getAction(Description.class, clazz);
			if (action != null) {
				actionList.add(action);
			}
		}
		return actionList;
	}

	/**
	 * 获得单个类的动作,包含方法动作
	 * 
	 * @param annotationType
	 * @param clazz
	 * @return
	 */
	private Action getAction(Class<? extends Annotation> annotationType,
			Class<?> clazz) {
		Action action = null;
		if (clazz.isAnnotationPresent(annotationType)) {
			if (AnnotationUtil.getAnnotationValue(clazz, annotationType,
					"state").equals(false)) {
				return null;
			}
			action = new Action();
			action.setName(AnnotationUtil.getAnnotationDefaultValue(clazz,
					annotationType));
			action.getUrl().add(
					((String[]) AnnotationUtil.getAnnotationValue(clazz,
							RequestMapping.class, "value"))[0]);
			// action.getUrl().add(clazz.getSimpleName());
			action.setArea(this.getArea(clazz));
			Method[] methods = clazz.getDeclaredMethods();
			action.setActions(getAction(annotationType, methods));

		}
		return action;
	}

	/**
	 * 获得类中方法的动作集合
	 * 
	 * @param annotationType
	 * @param methods
	 * @return
	 */
	private List<Action> getAction(Class<? extends Annotation> annotationType,
			Method... methods) {
		List<Action> list = new ArrayList<Action>();
		for (Method method : methods) {
			if (method.isAnnotationPresent(annotationType)) {
				if (AnnotationUtil.getAnnotationValue(method, annotationType,
						"state").equals(false)) {
					continue;
				}
				String str = AnnotationUtil.getAnnotationDefaultValue(method,
						annotationType);
				Action action = getAction(list, str);
				action.setName(str);
				action.getUrl().add(
						((String[]) AnnotationUtil.getAnnotationValue(method,
								RequestMapping.class, "value"))[0]);
				// action.getUrl().add(method.getName());
				if (!list.contains(action)) {
					list.add(action);
				}
			}
		}
		return list;
	}

	/**
	 * 检查动作列表中有没有指定动作
	 * 
	 * @param actionList
	 * @param str
	 * @return
	 */
	private Action getAction(List<Action> actionList, String str) {
		for (int i = 0; i < actionList.size(); i++) {
			Action action = actionList.get(i);
			if (action.getName().equals(str)) {
				return action;
			}
		}
		return new Action();
	}

	@SuppressWarnings("unused")
	private List<Action> getAction(Class<? extends Annotation> annotationType,
			Class... clazzs) {
		List<Action> list = new ArrayList<Action>();
		for (Class<?> clazz : clazzs) {
			if (clazz.isAnnotationPresent(annotationType)) {
				Action action = new Action();
				action.setName(AnnotationUtil.getAnnotationDefaultValue(clazz,
						annotationType));
				// action.setUrl(clazz.getSimpleName());
				Method[] methods = clazz.getDeclaredMethods();
				action.setActions(getAction(annotationType, methods));
				list.add(action);
			}
		}
		return list;
	}

	public String getArea(Class clazz) {
		String name = clazz.getName();
		int index = name.indexOf("jrwp");
		int begin = name.indexOf(".", index) + 1;
		int end = name.indexOf(".", begin);
		return name.substring(begin, end);
	}

}

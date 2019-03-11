package com.jrwp.core.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限动作辅助类
 * 
 */
public class Action {
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 路径
	 */
	private List<String> url = new ArrayList<String>();
	/**
	 * 动作列表
	 */
	private List<Action> actions;
	/**
	 * 域
	 */
	private String area;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getUrl() {
		return url;
	}

	public void setUrl(List<String> url) {
		this.url = url;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

}

package com.jrwp.core.utils;

import java.util.List;
import java.util.Set;

import com.jrwp.core.entity.Action;

/**
 * 解析类获取动作列表
 * 
 * @author USER
 * 
 */
public interface IAction {
	List<Action> getAction(Set<Class<?>> classSet);
}

package com.jrwp.core.service;

import java.util.List;

import com.jrwp.core.entity.Core_Log;

public interface ILogService {

	/**
	 * 日志列表
	 * 
	 * @param queryinfo
	 * 
	 * @return
	 */
	List<Core_Log> list(String queryinfo, String begintime, String endtime);

	/**
	 * 获取日志详细信息
	 * 
	 * @param id
	 * @return
	 */
	Core_Log getObjectById(long id);

	/**
	 * 保存日志
	 * 
	 * @param log
	 */
	void save(Core_Log log);

}

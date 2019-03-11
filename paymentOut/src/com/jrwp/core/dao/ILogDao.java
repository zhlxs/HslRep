package com.jrwp.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.core.entity.Core_Log;

public interface ILogDao {
	/**
	 * 日志列表
	 * 
	 * @param whereSql
	 * 
	 * @return
	 */
	List<Core_Log> list(@Param("whereSql") String whereSql,
			@Param("begintime") String begintime,
			@Param("endtime") String endtime);

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

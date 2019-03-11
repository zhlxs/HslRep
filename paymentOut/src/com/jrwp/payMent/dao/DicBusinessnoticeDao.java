package com.jrwp.payMent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.DicBusinessnotice;

public interface DicBusinessnoticeDao {

	/**
	 * 保存
	 */
	void save(DicBusinessnotice businessnotice);
	
	void update(DicBusinessnotice businessnotice);
	
	List<DicBusinessnotice> list(@Param("whereSql")String whereSql);
	
	void delete(long id);
	
	DicBusinessnotice getObjectById(long id);
}

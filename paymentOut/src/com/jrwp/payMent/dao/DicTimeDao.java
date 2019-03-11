package com.jrwp.payMent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.DicTime;

public interface DicTimeDao {

	List<DicTime> list();

	DicTime getObjectById(@Param("id") Long id);
}

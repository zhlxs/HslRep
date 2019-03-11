package com.jrwp.payMent.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.DicTime;

public interface DicTimeService {

	List<DicTime> list();

	DicTime getObjectById(@Param("id") Long id);
}

package com.jrwp.payMent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.BusinessAppraise;

public interface BusinessAppraiseDao {

	List<BusinessAppraise> list(@Param("whereSql") String whereSql);

	void save(BusinessAppraise businessAppraise);
}

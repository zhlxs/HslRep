package com.jrwp.payMent.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.BusinessAppraise;

public interface BusinessAppraiseService {

	List<BusinessAppraise> list(@Param("whereSql") String whereSql);

	void save(BusinessAppraise businessAppraise);
}

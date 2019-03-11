package com.jrwp.payMent.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.CallConfigModel;

public interface CallConfigModelService {

	List<CallConfigModel> list(@Param("whereSql") String whereSql);

	void save(CallConfigModel model) throws Exception;

	void update(CallConfigModel model);

	void delete(@Param("id") long id) throws Exception;

	CallConfigModel getObjectById(@Param("id") Long id);
}

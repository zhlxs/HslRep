package com.jrwp.payMent.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.CallConfigParam;

public interface CallConfigParamService {

	List<CallConfigParam> listByModelId(@Param("modelId") Long modelId);

	List<CallConfigParam> listByModelIdDefault(@Param("modelId") Long modelId);

	void save(CallConfigParam param);

	void saveParam(CallConfigParam param);

	void update(CallConfigParam param);

	void delete(@Param("id") long id);

	CallConfigParam getObjectById(@Param("id") Long id);
}

package com.jrwp.payMent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.CallConfigParam;

public interface CallConfigParamDao {

	List<CallConfigParam> listByModelId(@Param("modelId") Long modelId);

	List<CallConfigParam> listByModelIdDefault(@Param("modelId") Long modelId);

	void save(CallConfigParam param);

	void saveParam(CallConfigParam param);

	void update(CallConfigParam param);

	void delete(@Param("id") long id);
	
	void deleteByModelId(@Param("modelId") long modelId);

	CallConfigParam getObjectById(@Param("id") Long id);

	String getInsertCode(@Param("parentId") Long parentId);
}

package com.jrwp.payMent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.TheBlackList;

public interface TheBlackListDao {

	List<TheBlackList> list(@Param("whereSql") String whereSql);

	void save(TheBlackList blackList);

	void delete(@Param("id") long id);
}

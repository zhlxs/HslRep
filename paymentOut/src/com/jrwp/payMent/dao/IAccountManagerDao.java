package com.jrwp.payMent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.AccountManager;

public interface IAccountManagerDao {

	List<AccountManager> list(@Param("whereSql") String whereSql);

	void save(AccountManager account);

	void update(AccountManager account);

	void delete(@Param("id") long id);

	AccountManager getObjectById(@Param("id") Long id);
}

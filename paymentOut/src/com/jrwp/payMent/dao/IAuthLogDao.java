package com.jrwp.payMent.dao;

import java.util.List;

import com.jrwp.payMent.entity.AuthLog;

public interface IAuthLogDao {

	void saveAuthLog(AuthLog log);

	List<AuthLog> list();

	AuthLog getObjectById(Long id);
}

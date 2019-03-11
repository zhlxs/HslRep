package com.jrwp.payMent.service;

import java.util.List;

import com.jrwp.payMent.entity.AuthLog;

public interface IAuthLogService {

	void save(AuthLog log);

	List<AuthLog> list();

	AuthLog getObjectById(Long id);
}

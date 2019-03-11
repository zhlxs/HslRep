package com.jrwp.payMent.dao;

import com.jrwp.payMent.entity.Businessconfig;

public interface BusinessconfigDao {

	void save(Businessconfig businessconfig);
	
	Businessconfig getObjectByCode(String serCode);
	
	Businessconfig getObjectById(long id);
	
	void update(Businessconfig businessconfig);
}

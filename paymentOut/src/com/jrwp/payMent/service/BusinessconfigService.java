package com.jrwp.payMent.service;

import com.jrwp.payMent.entity.Businessconfig;

public interface BusinessconfigService {

	void save(Businessconfig businessconfig, Long noticeId);
	
	Businessconfig getObjectByCode(String serCode);
	
	Businessconfig getObjectById(long id);
	
	void update(Businessconfig businessconfig);
}

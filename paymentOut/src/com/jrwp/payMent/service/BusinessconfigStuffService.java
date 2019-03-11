package com.jrwp.payMent.service;

import java.util.List;

import com.jrwp.payMent.entity.BusinessconfigStuff;

public interface BusinessconfigStuffService {

	List<BusinessconfigStuff> listChildren(Long id);

	void save(BusinessconfigStuff businessconfigStuff);

	void update(BusinessconfigStuff businessconfigStuff);

	BusinessconfigStuff getObjectById(long id);

	void delete(long id);

	void move(long id, int movetype) throws Exception;
}

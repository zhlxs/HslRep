package com.jrwp.payMent.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.CallMachine;

public interface CallMachineService {

	List<CallMachine> list(@Param("whereSql") String whereSql);

	void save(CallMachine machine);

	void update(CallMachine machine);

	CallMachine getObjectById(@Param("id") Long id);

	void delete(@Param("id") Long id);
}

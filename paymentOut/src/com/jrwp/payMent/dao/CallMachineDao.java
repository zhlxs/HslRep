package com.jrwp.payMent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.CallMachine;

public interface CallMachineDao {

	List<CallMachine> list(@Param("whereSql") String whereSql);

	void save(CallMachine machine);

	void update(CallMachine machine);

	CallMachine getObjectById(@Param("id") Long id);

	void delete(@Param("id") Long id);
}

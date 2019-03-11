package com.jrwp.payMent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.SequenceSync;

public interface ISequenceSyncDao {

	List<SequenceSync> list();

	void update(@Param("id") Long id);
}

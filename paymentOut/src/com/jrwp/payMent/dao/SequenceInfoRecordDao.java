package com.jrwp.payMent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.SequenceInfoRecord;
import com.jrwp.wx.entity.Sequence_Info;

public interface SequenceInfoRecordDao {

	void save(SequenceInfoRecord record);

	List<Sequence_Info> list();

	List<Sequence_Info> list1(@Param("begintime") String begintime,
			@Param("endtime") String endtime);

	void delete(@Param("id") Long id);

	Sequence_Info getObjectById(@Param("id") Long id);
}

package com.jrwp.wx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.wx.entity.SequenceInfoJson;
import com.jrwp.wx.entity.Sequence_Info;

public interface Sequence_InfoDao {

	int peopleCount(@Param("appointtime") String appointtime,
			@Param("cardNumber") String cardNumber);

	int peopleCountPlus(@Param("appointtime") String appointtime,
			@Param("cardNumber") String cardNumber);

	int peopleCountById(@Param("appointtime") String appointtime,
			@Param("id") Long id);

	int peopleCountByIdPlus(@Param("appointtime") String appointtime,
			@Param("id") Long id);

	List<SequenceInfoJson> listforSeqence(@Param("card") String openid);

	Sequence_Info getSequenceByAppointId(
			@Param("appointme_infoid") Long appointme_infoid);

	void updateSend(@Param("issend") int issend, @Param("id") Long id);

	SequenceInfoJson listforSequenceById(@Param("id") Long id);

	void updateStatus(@Param("status") int status,
			@Param("winnumber") String winnumber, @Param("id") Long id);

	Sequence_Info getObjectById(@Param("id") Long id);
}

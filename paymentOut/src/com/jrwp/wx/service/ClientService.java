package com.jrwp.wx.service;

import com.jrwp.wx.entity.AppConfig;
import com.jrwp.wx.entity.BeiAnInfoJson;
import com.jrwp.wx.entity.EvaluateSetting;
import com.jrwp.wx.entity.PersonInfo;

public interface ClientService {

	PersonInfo getJYInfo(String userName, String passWord);

	void insertAppraise(long userid, int isdefault, long evaluateID,
			long squence_infoid);
	int updateStatusByPrimaryKey(int id, Integer winNumber, Integer status);

	BeiAnInfoJson getDeviceNumber(String winNumber);

	void updateStatusByPrimaryKey(Integer id, Integer type);

	AppConfig getWinNumMachineCode(String machineCode);

	EvaluateSetting getEvaluateSetting();

	String getMachineCodeByCode(String machineCode);

	void insertMachineCode(String machineCode);

	void updateRecallTime(Integer id);
}

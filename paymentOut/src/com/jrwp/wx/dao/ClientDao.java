package com.jrwp.wx.dao;

import org.apache.ibatis.annotations.Param;

import com.jrwp.wx.entity.AppConfig;
import com.jrwp.wx.entity.BeiAnInfoJson;
import com.jrwp.wx.entity.EvaluateSetting;
import com.jrwp.wx.entity.PersonInfo;

public interface ClientDao {
	public PersonInfo getJYInfo(@Param("userName")String userName, @Param("passWord")String passWord);
	public void insertAppraise(@Param("userid")long userid, @Param("isdefault")int isdefault, @Param("evaluateID")long evaluateID,
			@Param("squence_infoid")long squence_infoid);
	public int updateStatusByPrimaryKey(@Param("id")int id, @Param("winNumber")Integer winNumber,
			@Param("status")Integer status);
	public BeiAnInfoJson getDeviceNumber(@Param("winNumber")String winNumber);
	public void updateStatusByPrimaryKey(@Param("id")Integer id, @Param("type")Integer type);
	public AppConfig getWinNumMachineCode(@Param("machineCode")String machineCode);
	public EvaluateSetting getEvaluateSetting();
	public String getMachineCodeByCode(@Param("machineCode")String machineCode);
	public void insertMachineCode(@Param("machineCode")String machineCode);
	public void updateRecallTime(@Param("id")Integer id);
}

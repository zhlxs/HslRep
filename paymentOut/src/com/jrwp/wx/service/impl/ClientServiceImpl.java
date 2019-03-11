package com.jrwp.wx.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.wx.dao.ClientDao;
import com.jrwp.wx.entity.AppConfig;
import com.jrwp.wx.entity.BeiAnInfoJson;
import com.jrwp.wx.entity.EvaluateSetting;
import com.jrwp.wx.entity.PersonInfo;
import com.jrwp.wx.service.ClientService;

@Service("clientService")
public class ClientServiceImpl implements ClientService{
	@Resource
	private ClientDao clientDao;

	@Override
	public PersonInfo getJYInfo(String userName, String passWord) {
		// TODO Auto-generated method stub
		return clientDao.getJYInfo(userName, passWord);
	}

	@Override
	public void insertAppraise(long userid, int isdefault, long evaluateID,
			long squence_infoid) {
		// TODO Auto-generated method stub
		clientDao.insertAppraise(userid, isdefault, evaluateID, squence_infoid);
	}

	@Override
	public int updateStatusByPrimaryKey(int id, Integer winNumber,
			Integer status) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BeiAnInfoJson getDeviceNumber(String winNumber) {
		// TODO Auto-generated method stub
		if(winNumber.length() == 1){
			winNumber = "0" + winNumber;
		}
		return clientDao.getDeviceNumber(winNumber);
	}

	@Override
	public void updateStatusByPrimaryKey(Integer id, Integer type) {
		// TODO Auto-generated method stub
		clientDao.updateStatusByPrimaryKey(id, type);
	}

	@Override
	public AppConfig getWinNumMachineCode(String machineCode) {
		// TODO Auto-generated method stub
		return clientDao.getWinNumMachineCode(machineCode);
	}

	@Override
	public EvaluateSetting getEvaluateSetting() {
		// TODO Auto-generated method stub
		return clientDao.getEvaluateSetting();
	}

	@Override
	public String getMachineCodeByCode(String machineCode) {
		// TODO Auto-generated method stub
		return clientDao.getMachineCodeByCode(machineCode);
	}

	@Override
	public void insertMachineCode(String machineCode) {
		// TODO Auto-generated method stub
		clientDao.insertMachineCode(machineCode);
	}

	@Override
	public void updateRecallTime(Integer id) {
		// TODO Auto-generated method stub
		clientDao.updateRecallTime(id);
	}
}

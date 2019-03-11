package com.jrwp.wx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrwp.wx.dao.ServerDao;
import com.jrwp.wx.entity.BeiAnInfoJson;
import com.jrwp.wx.entity.OrderCount;
import com.jrwp.wx.entity.SquenceConfig;
import com.jrwp.wx.entity.SupplementRecord;
import com.jrwp.wx.entity.WXSquenceInfo;
import com.jrwp.wx.service.ServerService;

@Service("serverService")
public class ServerServiceImpl implements ServerService{

	@Resource
    private ServerDao serverDao;
	
	@Override
	public void insertBeiAnInfo(BeiAnInfoJson beiAnInfo) {
		// TODO Auto-generated method stub
		serverDao.insertBeiAnInfo(beiAnInfo);
	}

	@Override
	public BeiAnInfoJson getDeviceNumber(String ywckjsjip) {
		// TODO Auto-generated method stub
		return serverDao.getDeviceNumber(ywckjsjip);
	}

	@Override
	public WXSquenceInfo getCallSquence(Integer nowtimequan, int isappointment,
			Integer isearly, String date, Integer deptid, String ser_code) {
		// TODO Auto-generated method stub
		return serverDao.getCallSquence(nowtimequan, isappointment, isearly, date, deptid, ser_code);
	}
	
	@Override
	public WXSquenceInfo getCallSquenceTwo(Integer nowtimequan, int isappointment,
			Integer isearly, String date, Integer deptid, String ser_code) {
		// TODO Auto-generated method stub
		return serverDao.getCallSquenceTwo(nowtimequan, isappointment, isearly, date, deptid, ser_code);
	}

	@Override
	@Transactional
	public void updateSquenceInfo(String ywckjsjip, String serialnumber,Long id, String ckbh,String pdh,Integer deptid, int status, int isexport) {
		// TODO Auto-generated method stub
		serverDao.insertSquenceOrder(ywckjsjip, serialnumber);
		serverDao.updateSquenceInfo(id, ckbh);
		if(ckbh.startsWith("0")){
			ckbh = ckbh.replace("0", "");
		}
		serverDao.updateLedShow(ckbh,pdh,deptid);
		serverDao.updateSquenceSNYCAndWinNumber(id, status, isexport,ckbh);
	}

	@Override
	public WXSquenceInfo getSquenceInfoBySerialNumber(String qhxxxlh,String dateString) {
		// TODO Auto-generated method stub
		return serverDao.getSquenceInfoBySerialNumber(qhxxxlh,dateString);
	}
	@Override
	public WXSquenceInfo getSquenceInfoBySerialNumberTwo(String qhxxxlh,String dateString) {
		// TODO Auto-generated method stub
		return serverDao.getSquenceInfoBySerialNumberTwo(qhxxxlh,dateString);
	}

	@Override
	@Transactional
	public void updateSquenceOver(Long id, int status, int isexport) {
		// TODO Auto-generated method stub
		serverDao.updateSquenceOver(id);
		serverDao.updateSquenceSNYC(id, status, isexport);
	}

	@Override
	@Transactional
	public void changeWinStatu(int statu ,String ywckjsjip) {
		// TODO Auto-generated method stub
		serverDao.updateWinStatu(statu,ywckjsjip);
		serverDao.insertChangeHistory(statu,ywckjsjip);
	}

	@Override
	public void insertSupplementRecord(SupplementRecord supplementRecord) {
		// TODO Auto-generated method stub
		serverDao.insertSupplementRecord(supplementRecord);
	}

	@Override
	public List<SupplementRecord> getSupplementRecord(String dateString,
			Integer deptid) {
		// TODO Auto-generated method stub
		return serverDao.getSupplementRecord(dateString, deptid);
	}

	@Override
	public List<OrderCount> getAllTime() {
		// TODO Auto-generated method stub
		return serverDao.getAllTime();
	}

	@Override
	@Transactional
	public void updateSquenceAndSupplementRecord(Long supplementRecordid,
			Long squenceid, String serialNumber, String ckbh, int statu) {
		// TODO Auto-generated method stub
		serverDao.updateSupplement(supplementRecordid,squenceid);
		serverDao.updateSquence(squenceid,serialNumber,ckbh,statu);
	}

	@Override
	public WXSquenceInfo getWindowBySerialNumber(String qhxxxlh) {
		// TODO Auto-generated method stub
		return serverDao.getWindowBySerialNumber(qhxxxlh);
	}

	@Override
	@Transactional
	public void insertWaitAppraise(Long id, String wicketnumber,String qhxxxlh) {
		// TODO Auto-generated method stub
		serverDao.updatePassBefore(wicketnumber);
		serverDao.insertWaitAppraise(id, wicketnumber,qhxxxlh);
	}

	@Override
	public void updateSquenceFinish(Long id) {
		// TODO Auto-generated method stub
		serverDao.updateSquenceFinish(id);
	}

	@Override
	public void updateReatCall(String qhxxxlh,String dateString) {
		// TODO Auto-generated method stub
		serverDao.updateReatCall(qhxxxlh,dateString);
	}

	@Override
	public void insertSquenceOrder(String ywckjsjip, String serialnumber) {
		// TODO Auto-generated method stub
		serverDao.insertSquenceOrder(ywckjsjip, serialnumber);
	}

	@Override
	public WXSquenceInfo getLastSerialNumber(String ywckjsjip,String dateString) {
		// TODO Auto-generated method stub
		return serverDao.getLastSerialNumber(ywckjsjip,dateString);
	}

	@Override
	public void updateSquenceSNYC(Long id, int status, int isexport) {
		// TODO Auto-generated method stub
		serverDao.updateSquenceSNYC(id, status, isexport);
	}

	@Override
	public void updateSquenceStatus(Long id, int status) {
		// TODO Auto-generated method stub
		serverDao.updateSquenceStatus(id,status);
	}

	@Override
	public void updateLedShow(Integer deptid, String squence, int status) {
		// TODO Auto-generated method stub
		serverDao.updateLedShowGhOrfinish(deptid, squence, status);
	}

	@Override
	public SquenceConfig getSquenceConfig(String deptid) {
		// TODO Auto-generated method stub
		return serverDao.getSquenceConfig(deptid);
	}

	@Override
	public void updateLedStatus() {
		// TODO Auto-generated method stub
		serverDao.updateLedStatus();
	}

	@Override
	public void updateShowStatu(String winNumber, int status,Integer id) {
		// TODO Auto-generated method stub
		if(winNumber.startsWith("0")){
			winNumber = winNumber.replace("0", "");
		}
		serverDao.updateShowStatu(winNumber, status, id);
	}

	@Override
	public String getwinNumberById(Integer id) {
		// TODO Auto-generated method stub
		return serverDao.getwinNumberById(id);
	}

	@Override
	public void updateRecallTime(String qhxxxlh, String dateString) {
		// TODO Auto-generated method stub
		serverDao.updateRecallTime(qhxxxlh,dateString);
	}


}

package com.jrwp.wx.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.jrwp.core.utils.LogUtil;
import com.jrwp.payMent.task.UpdateSequenceSyncTask;
import com.jrwp.wx.dao.WxAppointmeDao;
import com.jrwp.wx.entity.AppointmeInfoJson;
import com.jrwp.wx.entity.CallSquenceJson;
import com.jrwp.wx.entity.OrderCount;
import com.jrwp.wx.service.WxAppointmeService;

@Service("wxAppointmeService")
public class WxAppointmeServiceImpl implements WxAppointmeService {
	@Resource
	private WxAppointmeDao wxAppointmeDao;

	Logger logger = Logger.getLogger(UpdateSequenceSyncTask.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void cancelAppointme(Long appointmeId) {
		// TODO Auto-generated method stub
		try {
			wxAppointmeDao.cancelAppointme(appointmeId);
			wxAppointmeDao.insertAppointmeSync(appointmeId, 0, 0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			logger.info("方法[cancelAppointme]发生异常:", e);
			LogUtil.info("方法[cancelAppointme]发生异常:{}", e.getMessage());
		}
	}

	@Override
	public List<OrderCount> getOrderCount(int businessType, Long modelId) {
		// TODO Auto-generated method stub
		return wxAppointmeDao.getOrderCount(businessType, modelId);
	}

	@Override
	public List<OrderCount> getAppointmeCount(String day, Long deptid,
			int businessType) {
		// TODO Auto-generated method stub
		return wxAppointmeDao.getAppointmeCount(day, deptid, businessType);
	}

	@Override
	public AppointmeInfoJson getAppointmeByCardnumber(String appointmenttime,
			String cardNumber, Long deptid, int businessType, Long timequantum) {
		// TODO Auto-generated method stub
		return wxAppointmeDao.getAppointmeByCardnumber(appointmenttime,
				cardNumber, deptid, businessType, timequantum);
	}

	@Override
	public void insertAppointmeInfo(AppointmeInfoJson appointmeInfoJson) {
		// TODO Auto-generated method stub
		wxAppointmeDao.insertAppointmeInfo(appointmeInfoJson);
	}

	@Override
	public List<CallSquenceJson> getCallSquenceJson() {
		// TODO Auto-generated method stub
		return wxAppointmeDao.getCallSquenceJson();
	}

	@Override
	public List<AppointmeInfoJson> getAppointmeByOpenid(Date date1,
			String openid, Integer deptid) {
		// TODO Auto-generated method stub
		return wxAppointmeDao.getAppointmeByOpenid(date1, openid, deptid);
	}

	@Override
	public void updateCallStatue(Long id) {
		// TODO Auto-generated method stub
		wxAppointmeDao.updateCallStatue(id);
	}

	@Override
	public AppointmeInfoJson getAppointmeById(Long id) {
		// TODO Auto-generated method stub
		return wxAppointmeDao.getAppointmeById(id);
	}

	@Override
	public void insertAppointmeSync(Long appointmeId, int status, int isexport) {
		// TODO Auto-generated method stub
		wxAppointmeDao.insertAppointmeSync(appointmeId, status, isexport);
	}

}

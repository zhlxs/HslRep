package com.jrwp.payMent.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.jrwp.payMent.dao.SequenceInfoRecordDao;
import com.jrwp.payMent.entity.SequenceInfoRecord;
import com.jrwp.payMent.service.SequenceInfoRecordService;
import com.jrwp.wx.entity.Sequence_Info;

@Service
public class SequenceInfoRecordServiceImpl implements SequenceInfoRecordService {

	@Resource
	private SequenceInfoRecordDao sequenceInfoRecordDao;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void save(SequenceInfoRecord record) {
		// TODO Auto-generated method stub
		List<Sequence_Info> list = new ArrayList<>();
		try {
			list = sequenceInfoRecordDao.list();
			if (list != null) {
				for (Sequence_Info info : list) {
					record.setIsAppointment(info.getIsAppointment());
					record.setIsEarly(info.getIsEarly());
					record.setAppointmenttime(info.getAppointmenttime());
					record.setSquence(info.getSquence());
					record.setDeptId(info.getDeptId());
					record.setCreatetime(info.getCreatetime());
					record.setStatus(info.getStatus());
					record.setOpenid(info.getOpenid());
					record.setCardNumber(info.getCardNumber());
					record.setTimequantum(info.getTimequantum());
					record.setIsExport(info.getIsExport());
					record.setName(info.getName());
					record.setWicketNumber(info.getWicketNumber());
					record.setCallTime(info.getCallTime());
					record.setPassTime(info.getPassTime());
					record.setFinshTime(info.getFinshTime());
					record.setIsProxy(info.getIsProxy());
					record.setProxyName(info.getProxyName());
					record.setProxyCardNumber(info.getProxyCardNumber());
					record.setSerialNumber(info.getSerialNumber());
					record.setGetNumberType(info.getGetNumberType());
					record.setSer_Code(info.getSer_Code());
					record.setAppointme_infoid(info.getAppointme_infoid());
					record.setIsCall(info.getIsCall());
					record.setBusinessType(info.getBusinessType());
					record.setImporttime(new Date());
					sequenceInfoRecordDao.save(record);
					sequenceInfoRecordDao.delete(info.getId());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		sequenceInfoRecordDao.save(record);
	}

	@Override
	public List<Sequence_Info> list() {
		// TODO Auto-generated method stub
		return sequenceInfoRecordDao.list();
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		sequenceInfoRecordDao.delete(id);
	}

	@Override
	public List<Sequence_Info> list1(String begintime, String endtime) {
		// TODO Auto-generated method stub
		return sequenceInfoRecordDao.list1(begintime, endtime);
	}

}

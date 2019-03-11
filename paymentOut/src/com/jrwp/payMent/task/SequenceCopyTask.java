package com.jrwp.payMent.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.jrwp.payMent.entity.SequenceInfoRecord;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jrwp.payMent.help.SequenceCopyTaskHelper;
import com.jrwp.payMent.service.SequenceInfoRecordService;
import com.jrwp.wx.entity.Sequence_Info;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

//@Component("sequenceCopyTask")
public class SequenceCopyTask {

	@Resource
	private SequenceInfoRecordService sequenceInfoRecordService;

	@Scheduled(cron = "0 50 23 * * ?")
	// @Scheduled(cron = "*/10 * * * * ?")
	@Transactional(rollbackFor = { Exception.class })
	public void listCopyTask() {
		System.out.println("=====开始执行胡山林的队列备份任务=====");
		try {
			String begintime = null;
			String endtime = null;
			Date date = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
			String time = simpleDateFormat.format(date);
			begintime = endtime = time;
			List<Sequence_Info> list = new ArrayList<>();
			list = sequenceInfoRecordService.list1(begintime, endtime);
			if (list.size() != 0) {
				for (Sequence_Info info : list) {
					SequenceInfoRecord record = new SequenceInfoRecord();
					record.setId(info.getId());
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
					record.setIssend(info.getIssend());
					record.setImporttime(new Date());
					sequenceInfoRecordService.save(record);
					sequenceInfoRecordService.delete(info.getId());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
	}
}

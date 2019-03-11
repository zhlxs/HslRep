package com.jrwp.payMent.task;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.jrwp.core.utils.LogUtil;
import com.jrwp.payMent.dao.ISequenceSyncDao;
import com.jrwp.payMent.entity.SequenceSync;
import com.jrwp.wx.dao.Sequence_InfoDao;
import com.jrwp.wx.entity.Sequence_Info;

/**
 * 更新队列同步信息任务
 * 
 * @author hsl
 * 
 */
//@Component("updateSequenceSyncTask")
public class UpdateSequenceSyncTask {

	@Resource
	private ISequenceSyncDao sequenceSyncDao;
	@Resource
	private Sequence_InfoDao sequence_InfoDao;

	Logger logger = Logger.getLogger(UpdateSequenceSyncTask.class);

	@Scheduled(cron = "*/5 * 8-18 * * ?")
	@Transactional(rollbackFor = { Exception.class })
	public void updateSequenceInfo() {
		List<SequenceSync> list = new ArrayList<>();
		System.out.println("=====更新队列同步信息任务=====");
		try {
			list = sequenceSyncDao.list();
			for (int i = 0; i < list.size(); i++) {
				SequenceSync sequenceSync = list.get(i);
				Sequence_Info info = sequence_InfoDao
						.getObjectById(sequenceSync.getId());
				if (info != null) {
					sequence_InfoDao.updateStatus(sequenceSync.getStatus(),
							sequenceSync.getWinnumber(), info.getId());
					sequenceSyncDao.update(sequenceSync.getId());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			logger.info("任务[updateSequenceInfo]发生异常:", e);
			LogUtil.info("任务[updateSequenceInfo]发生异常:{}", e.getMessage());
		}
	}
}

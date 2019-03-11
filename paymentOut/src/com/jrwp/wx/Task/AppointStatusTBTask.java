package com.jrwp.wx.Task;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jrwp.wx.entity.Appointme_sync;
import com.jrwp.wx.service.WXSquenceInfoService;

@Component("appointStatusTBTask")
public class AppointStatusTBTask {
	private final Logger logger = Logger.getLogger(AppointStatusTBTask.class);
	@Resource
    private WXSquenceInfoService wXSquenceInfoService;
	
	@Scheduled(cron = "0/5 * * * * ?")
    public void appointStatusTb() {
        try {
        	Appointme_sync appointme_sync = wXSquenceInfoService.getWxAppintStatus();
        	if(appointme_sync != null){
        		wXSquenceInfoService.updateAppointStatus(appointme_sync.getId(),appointme_sync.getStatus());
        	}
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("同步预约状态的任务发生异常：",e);
        }
    }
}

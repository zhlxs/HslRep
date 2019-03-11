package com.jrwp.wx.Task;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jrwp.wx.service.ServerService;

@Component("LedShowTask")
public class LedShowTask {
	private final Logger logger = Logger.getLogger(LedShowTask.class);
	@Resource
    private ServerService serverService;
	
	@Scheduled(cron = "0 0 20 * * ?")
	//每晚八点执行
    public void LedChangeStatus() {
        try {
        	logger.info("任务运行了");
        	serverService.updateLedStatus();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("在八点更新led显示状态的时候发生异常：",e);
        }
    }
}

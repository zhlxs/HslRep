package com.jrwp.wx.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jrwp.wx.entity.PmVisitor;
import com.jrwp.wx.service.WXSquenceInfoService;
import com.jrwp.wx.until.PmSquenceHelper;


@Component("pmSquenceTask")
public class PmSquenceTask {
	private final Logger logger = Logger.getLogger(PmSquenceTask.class);
	@Resource
	private WXSquenceInfoService wXSquenceInfoService;
	
	@Scheduled(cron = "0/1 * * * * ?")
    public void getPmVistor() {
        try {
            //logger.info("============luodirenwu在运行");
            List<PmVisitor> taget = new ArrayList<>();
            List<PmVisitor> gone = wXSquenceInfoService.getVisitor(new Date(), null, 3, 1);
            List<PmVisitor> called = wXSquenceInfoService.getCalledVisitor(new Date(), null);
            List<PmVisitor> recalled = wXSquenceInfoService.getVisitor(new Date(), null, 5, 2);
            List<PmVisitor> wait = wXSquenceInfoService.getVisitor(new Date(), null, 1, 2);
            taget.addAll(gone);
            taget.addAll(called);
            taget.addAll(recalled);
            taget.addAll(wait);
            PmSquenceHelper.getInstance().dateChange(2, new ArrayList<PmVisitor>(taget));

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("维护落地大屏队列数据PmSquenceTask的任务发生异常：",e);
        }
    }
}

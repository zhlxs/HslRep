package com.jrwp.wx.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.jrwp.wx.entity.AppraiseInfoForAndroid;
import com.jrwp.wx.service.AppraiseService;
import com.jrwp.wx.until.PmWaitAppraiseHelper;

//@Component("waitAppraiseTask")
public class WaitAppraiseTask {
	@Resource
	private AppraiseService appraiseService;
	
	//@Scheduled(cron = "0/1 * * * * ?")
    public void getWaitAppraise() {
        try {
            System.out.println("============在运行");
            List<AppraiseInfoForAndroid> taget = new ArrayList<>();
            Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(currentTime);
            taget = appraiseService.getAllWaitAppraise(dateString);
            PmWaitAppraiseHelper.getInstance().dateChange(2, new ArrayList<AppraiseInfoForAndroid>(taget));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

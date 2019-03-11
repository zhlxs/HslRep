package com.jrwp.wx.Task;


import javax.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.jrwp.wx.service.WXSquenceInfoService;

/**
 * 查找外网签到记录
 * @author kth
 *
 */
//@Component("WxSigninTask")
public class WxSigninTask {
	@Resource
	private WXSquenceInfoService wXSquenceInfoService;
	
	//@Scheduled(cron = "0 0 20 * * ?")
	//每晚八点执行
    public void wxSignin() {
        try {
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

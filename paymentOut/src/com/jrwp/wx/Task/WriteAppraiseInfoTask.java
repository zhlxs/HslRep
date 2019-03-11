package com.jrwp.wx.Task;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jrwp.wx.entity.AppraiseJson;
import com.jrwp.wx.entity.AppraiseJsonForSend;
import com.jrwp.wx.service.AppraiseService;
import com.jrwp.wx.until.GetXmlString;
import com.jrwp.wx.until.PostXml;

//@Component("WriteAppraiseInfoTask")
public class WriteAppraiseInfoTask {
	private final Logger logger = Logger.getLogger(WriteAppraiseInfoTask.class);
	@Resource
	private AppraiseService appraiseService;
	
	/**
	 * 写入评价
	 */
	//@Scheduled(cron = "* 0/2 * * * ?")//两分钟一次
	public void writeAppraiseInfo() {
		try {
			AppraiseJson appraiseJson = appraiseService.getAppraiseInfo();
			if(appraiseJson != null){
				String xmlString = GetXmlString.secondXmlString(appraiseJson);
				String url = "";
				String responStr = PostXml.postServer(url, xmlString);
				Document dom = DocumentHelper.parseText(responStr);  
		        Element root = dom.getRootElement();  
		        String bodyRsp = root.element("code").getText();
				//发送请求
				//解析请求结果，如果成功，更改记录状态
				if("1".equals(bodyRsp)){
					appraiseService.updateIsWrite(appraiseJson.getId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

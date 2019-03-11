package com.jrwp.wx.Task;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jrwp.wx.entity.AppointmeInfoJson;
import com.jrwp.wx.entity.ModelJson;
import com.jrwp.wx.entity.WarmingInfoJson;
import com.jrwp.wx.service.WXSquenceInfoService;
import com.jrwp.wx.until.Utils;

/**
 * 对推进来的预约信息进行预警比对
 * @author kth
 *
 */
//@Component("WarmingCheckTask")
public class WarmingCheckTask {
	private final Logger logger = Logger.getLogger(WarmingCheckTask.class);
	@Resource
	private WXSquenceInfoService wXSquenceInfoService;
	/**
	 * 对推进来的预约信息进行预警比对(5分钟运行一次)
	 */
	//@Scheduled(cron = "* 0/5 * * * ?")
	public void warmingcheck() {
		try {
			logger.info("进入预警比对======");
			AppointmeInfoJson appointmeInfoJson = wXSquenceInfoService.getAppointmeInfoJson();
			if(appointmeInfoJson != null){
				logger.info("进入预警比对,查找到记录======");
				ModelJson modelJson = new ModelJson();
				modelJson.setCardNumber(appointmeInfoJson.getCardNumber());
				modelJson.setThanType(1);
				Map<String,String> param = new HashMap<String,String>();
				param.put("apiauthtoken", "E7C5EFFD9806F85141188C47986B6FC5");
				param.put("modeljson", JSONObject.fromObject(modelJson).toString());
			    String backResult = Utils.sendPost1("http://192.168.0.6:9686/KeyPersonnelThan/api/getPersonnelThan", param);
			    JSONObject backResultJson =JSONObject.fromObject(backResult);
			    logger.info("预约预警比对返回的结果："+backResultJson);
				int stateType = backResultJson.getInt("stateType");
				if(stateType == 0){//根据结果进行插库，并更新是否核验
					//对返回的内容进行解析，如果判断为预警信息就插入，不是就修改状态
					String stateValue = backResultJson.getString("stateValue");
					if(stateValue != null && !"".equals(stateValue)){
						//如果预警就插入mac_photomatchid，姓名，身份证
						wXSquenceInfoService.insertAppointWarmInfo(Utils.warmingInfo(stateValue),appointmeInfoJson.getId(),appointmeInfoJson.getName(),appointmeInfoJson.getCardNumber(),appointmeInfoJson.getPhone());
					}
					wXSquenceInfoService.updateIsCheck(appointmeInfoJson.getId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("对比预约预警信息的任务发生异常：",e);
		}
	}
	
	/**
	 * 对现场人像比对信息进行预警比对（5分钟运行一次）
	 */
	@Scheduled(cron = "* 0/5 * * * ?")
	public void xcwarmingcheck() {
		try {
			
			WarmingInfoJson warmingInfoJson = wXSquenceInfoService.getWarmingInfoJson();
			if(warmingInfoJson != null){
				logger.info("进入预警比对,查找到记录======");
				ModelJson modelJson = new ModelJson();
				modelJson.setCardNumber(warmingInfoJson.getCardnumber());
				modelJson.setThanType(1);
				Map<String,String> param = new HashMap<String,String>();
				param.put("apiauthtoken", "E7C5EFFD9806F85141188C47986B6FC5");
				param.put("modeljson", JSONObject.fromObject(modelJson).toString());
			    String backResult = Utils.sendPost1("http://192.168.0.6:9686/KeyPersonnelThan/api/getPersonnelThan", param);
			    JSONObject backResultJson =JSONObject.fromObject(backResult);
			    logger.info("现场预警比对返回的结果："+backResultJson);
				int stateType = backResultJson.getInt("stateType");
				if(stateType == 0){//根据结果进行插库，并更新是否核验
					String stateValue = backResultJson.getString("stateValue");
					if(stateValue != null && !"".equals(stateValue)){
						//如果预警就插入mac_photomatchid，姓名，身份证
						wXSquenceInfoService.insertWarmInfo(Utils.warmingInfo(stateValue),warmingInfoJson.getId(),warmingInfoJson.getName(),warmingInfoJson.getCardnumber());
					}
					wXSquenceInfoService.updateXCIsCheck(warmingInfoJson.getId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("对比现场预警信息的任务发生异常：",e);
		}
	}
}

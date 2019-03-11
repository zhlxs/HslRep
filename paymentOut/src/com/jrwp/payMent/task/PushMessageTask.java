package com.jrwp.payMent.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jrwp.core.utils.LogUtil;
import com.jrwp.core.utils.WeXinUntil;
import com.jrwp.follow.dao.AccessTokenDao;
import com.jrwp.payMent.service.SequenceInfoRecordService;
import com.jrwp.wx.dao.RegisterUserDao;
import com.jrwp.wx.dao.Sequence_InfoDao;
import com.jrwp.wx.entity.RegisterUser;
import com.jrwp.wx.entity.Sequence_Info;
import com.jrwp.wx.until.OpenidUtil;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 消息推送任务
 */
//@Component("pushMessageTask")
public class PushMessageTask {

	@Resource
	private SequenceInfoRecordService sequenceInfoRecordService;
	@Resource
	private Sequence_InfoDao sequenceInfoDao;
	@Resource
	private RegisterUserDao registerUserDao;
	@Resource
	private AccessTokenDao accessTokenDao;

	// private final String appId = "wxee10f55b4da5a870";
	// private final String appSecret = "14a79475156194dbc693f074a6dbd03f";

	Logger log = Logger.getLogger(PushMessageTask.class);

	@Scheduled(cron = "*/5 * 8-18 * * ?")
	@Transactional(rollbackFor = { Exception.class })
	public void pushMessage() {
		System.out.println("====开始执行胡山林的微信消息推送任务=====");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<Sequence_Info> list = new ArrayList<>();
		try {
			list = sequenceInfoRecordService.list();
			if (list.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					Sequence_Info info = list.get(i);
					String card = info.getCardNumber();
					int businessType = info.getBusinessType();
					log.info("任务[pushMessage]中的参数businessType:" + businessType);
					Date appointtime = info.getCreatetime();
					String time = format.format(appointtime);
					/**
					 * 前面人数
					 */
					int number = 0;
					if (businessType == 1 || businessType == 2) {
						number = sequenceInfoDao.peopleCountById(time, null);
						if (number == 0) {
							number += 1;
						}
					} else {
						number = sequenceInfoDao
								.peopleCountByIdPlus(time, null);
						if (number == 0) {
							number += 1;
						}
					}
					log.info("=====排队队列前面人数:" + number);
					RegisterUser user = registerUserDao.checkObjectByCard(card);
					if (user != null) {
						String openid = user.getWxOpenId();
						// String name = user.getUsername();
						/**
						 * 推送消息
						 */
						if (number <= 3) {
							/**
							 * 获取token
							 */
							String token = OpenidUtil.getToken(accessTokenDao);
							if (token != null) {
								String res = WeXinUntil.sendMsg(token, openid,
										null, number);
								LogUtil.info("微信返回的结果:{}", res);
								log.info("微信返回的结果:" + res);
								JSONObject jsonObject = JSON.parseObject(res);
								String errcode = jsonObject
										.getString("errcode");
								LogUtil.info("微信返回的提示代码:{}", errcode);
								log.info("微信返回的提示代码:" + errcode);
								if (errcode.equals("0")) {
									/**
									 * 发送成功，更新状态
									 */
									sequenceInfoDao.updateSend(1, info.getId());
									System.out.println("==========微信返回的提示代码:"
											+ errcode);
								} else {
									System.out.println("消息推送失败啦...");
								}
							}
						}
					}
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

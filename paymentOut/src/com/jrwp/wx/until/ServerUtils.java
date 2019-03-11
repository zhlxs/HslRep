package com.jrwp.wx.until;

//import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.jrwp.wx.entity.BeiAnInfoJson;
import com.jrwp.wx.entity.OrderCount;
import com.jrwp.wx.entity.OverCallJson;
import com.jrwp.wx.entity.OverCallJsonSkip;
import com.jrwp.wx.entity.SquenceConfig;
//import com.jrwp.wx.entity.SupplementRecord;
import com.jrwp.wx.entity.WXSquenceInfo;
import com.jrwp.wx.service.ServerService;
import com.jrwp.wx.service.WXSquenceInfoService;

public class ServerUtils {
	private final Logger logger = Logger.getLogger(ServerUtils.class);

	/**
	 * 是否超过单日可办理上限
	 * 
	 * @param jsonData
	 * @return
	 */
	public int isPassAbleAccept(String cardNumber, String proxyCardNumber,
			WXSquenceInfoService wXSquenceInfoService) throws Exception {
		int flag = 0;// 0没有超过，1超过
		try {
			int ableAcceptCount = wXSquenceInfoService.getAbleAcceptCount(Utils
					.getParamsByKey("deptid"));
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(currentTime);
			int already = wXSquenceInfoService.getAlreadyAcceptCount(
					cardNumber, proxyCardNumber, dateString);
			if (already >= ableAcceptCount) {// 超过了
				flag = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("在判断是否超过单日可办理上限的时候发生异常：", e);
			throw new Exception("在判断是否超过单日可办理上限的时候发生异常");
		}
		return flag;
	}

	/**
	 * 是否存在黑名单
	 * 
	 * @param wXSquenceInfoService2
	 * @param jsonData
	 * @return
	 */
	public int isBlackList(String cardNumber, String proxyCardNumber,
			WXSquenceInfoService wXSquenceInfoService) throws Exception {
		int flag = 0;// 0没有，1有
		String cardNumberBlack = null;
		try {
			cardNumberBlack = wXSquenceInfoService.getBlackCardNumber(
					cardNumber, proxyCardNumber);
			if (cardNumberBlack != null && !"".equals(cardNumberBlack)) {// 存在
				flag = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("在判断是否存在黑名单的时候发生异常：", e);
			throw new Exception("在判断是否存在黑名单的时候发生异常");
		}
		return flag;
	}

	/**
	 * 叫号
	 * 
	 * @param ywckjsjip
	 * @param glbm
	 * @return
	 */
	public OverCallJson callNumber(String jsonData, ServerService serverService)
			throws Exception {
		OverCallJson overCallJson = null;
		try {
			JSONObject json = JSONObject.fromObject(jsonData);
			String ywckjsjip = json.getString("ywckjsjip");
			// String glbm = json.getString("glbm");
			BeiAnInfoJson beiAnInfoJson = serverService
					.getDeviceNumber(ywckjsjip);// 获取计算机编号，用于生成序列号
			List<OrderCount> orderCount = serverService.getAllTime();
			Calendar c = Calendar.getInstance();
			int now = c.get(Calendar.HOUR_OF_DAY);
			// 现在处于什么时间段 这个方法需要更改
			int nowtimequan = Utils.getTimqun(now, orderCount);// 处于哪个时间段，把该时间段的id返回来
			// sql语句需要更改,把业务范围加进去，将#01#02转化为1，2用来查询
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(currentTime);
			WXSquenceInfo wXSquenceInfo = serverService.getCallSquenceTwo(
					nowtimequan, 1, 1, dateString, beiAnInfoJson.getDeptid(),
					beiAnInfoJson.getKbywlb().replace("0", "")
							.replace("#", ","));// 获取有没有早到的
			if (wXSquenceInfo == null) {
				wXSquenceInfo = serverService
						.getCallSquenceTwo(nowtimequan, 1, 0, dateString,
								beiAnInfoJson.getDeptid(),
								beiAnInfoJson.getKbywlb().replace("0", "")
										.replace("#", ","));// 获取预约的
				if (wXSquenceInfo == null) {
					wXSquenceInfo = serverService.getCallSquenceTwo(
							null,
							0,
							null,
							dateString,
							beiAnInfoJson.getDeptid(),
							beiAnInfoJson.getKbywlb().replace("0", "")
									.replace("#", ","));// 获取现场号
					if (wXSquenceInfo == null) {
						wXSquenceInfo = serverService.getCallSquenceTwo(null,
								1, 1, dateString, beiAnInfoJson.getDeptid(),
								beiAnInfoJson.getKbywlb().replace("0", "")
										.replace("#", ","));// 获取有没有早到的
					}
				}
			}
			// 还没有建表
			SquenceConfig squenceConfig = serverService.getSquenceConfig(Utils
					.getParamsByKey("deptid"));
			int overmin = squenceConfig.getMinutes();// 现场号等待超时时间
			if (wXSquenceInfo != null) {
				if (squenceConfig.getIsopen() == 1
						&& wXSquenceInfo.getIsappointment() == 1) {// 开启了并且取出的是预约号
					WXSquenceInfo wXSquenceInfoXc = serverService
							.getCallSquenceTwo(null, 0, null, dateString,
									beiAnInfoJson.getDeptid(), beiAnInfoJson
											.getKbywlb().replace("0", "")
											.replace("#", ","));// 获取现场号
					if (wXSquenceInfoXc != null) {
						if (Utils.isOver30(wXSquenceInfoXc.getCreatetime(),
								overmin)) {// 判断这个现场号是否等待超过30分钟
							if (Utils.isEarly(wXSquenceInfo.getCreatetime(),
									wXSquenceInfoXc.getCreatetime())) {// 现场号更早
								wXSquenceInfo = wXSquenceInfoXc;
							}
						}
					}
				}
			}
			// 把该窗口的上一条叫号搜出来
			WXSquenceInfo lastSquenceInfo = serverService.getLastSerialNumber(
					ywckjsjip, dateString);
			if (lastSquenceInfo != null) {
				// 把内外状态同步表状态修改,squence_info状态修改
				if (lastSquenceInfo.getStatus() != 3
						&& lastSquenceInfo.getStatus() != 6) {
					serverService.updateSquenceSNYC(lastSquenceInfo.getId(), 6,
							0);
					serverService.updateSquenceStatus(lastSquenceInfo.getId(),
							6);
				}
			}
			if (wXSquenceInfo != null) {
				logger.info("号码创建的时间" + wXSquenceInfo.getCreatetime());
				logger.info("找到的号码是=" + wXSquenceInfo.getSquence());
				// 准备好返回的信息
				overCallJson = new OverCallJson();
				overCallJson.setSbkzjsjip(beiAnInfoJson.getControlip());
				overCallJson.setQhxxxlh(wXSquenceInfo.getSerialnumber());
				overCallJson.setPdh(wXSquenceInfo.getSquence() + "00");
				overCallJson.setYwlb("0" + wXSquenceInfo.getBusinessType());
				overCallJson.setSfzmhm(wXSquenceInfo.getCardnumber());
				overCallJson.setQhrxm(wXSquenceInfo.getName());
				overCallJson.setQhsj(wXSquenceInfo.getCreatetime());// 是否是这个格式
				overCallJson.setRylb(String.valueOf(wXSquenceInfo
						.getGetNumberType()));
				if (wXSquenceInfo.getBusinessType() == 2) {
					logger.info("综合平台-代办业务(BusinessType = 2)");
					overCallJson.setDlrsfzmhm(wXSquenceInfo.getCardnumber());
					// overCallJson.setQhrxm(wXSquenceInfo.getName());
					overCallJson.setRylb("2");
				}
				if (wXSquenceInfo.getIsproxy() == 1) {
					logger.info("综合平台-代办业务(isproxy = 1)");
					overCallJson.setDlrsfzmhm(wXSquenceInfo
							.getProxycardnumber());
					// overCallJson.setQhrxm(wXSquenceInfo.getProxyname());
					overCallJson.setRylb("2");
				}
				// 保存叫号记录
				// 更新本条队列信息的状态，序列号，窗口号，叫号时间
				serverService.updateSquenceInfo(ywckjsjip,
						wXSquenceInfo.getSerialnumber(), wXSquenceInfo.getId(),
						beiAnInfoJson.getCkbh(), wXSquenceInfo.getSquence(),
						beiAnInfoJson.getDeptid(), 2, 0);
			} /*
			 * else{//如果是空就记录本次挂起记录 System.out.println("==========保存本次未取到号码信息");
			 * SupplementRecord supplementRecord = new SupplementRecord();
			 * supplementRecord.setYwckjsjip(ywckjsjip);
			 * supplementRecord.setSbkzjsjip(beiAnInfoJson.getControlip());
			 * supplementRecord.setKbywlb(beiAnInfoJson.getKbywlb().replace("0",
			 * "").replace("#", ",")); supplementRecord.setIswrite(0);
			 * supplementRecord.setDeptid(beiAnInfoJson.getDeptid());
			 * serverService.insertSupplementRecord(supplementRecord); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("在叫号的时候发生异常：", e);
			// throw new Exception("在判断是否存在黑名单的时候发生异常");
		}
		return overCallJson;
	}

	/**
	 * 重复叫号
	 * 
	 * @param qhxxxlh
	 * @return
	 */
	public OverCallJsonSkip repeatCall(String jsonData,
			ServerService serverService) {
		OverCallJsonSkip overCallJson = null;
		// int code = 1;
		try {
			JSONObject json = JSONObject.fromObject(jsonData);
			String param = json.getString("qhxxxlh");
			// String qhxxxlh = param.substring(0,param.length()-2);
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(currentTime);
			serverService.updateReatCall(param, dateString);// 更新状态为重复叫号
			WXSquenceInfo wXSquenceInfo1 = serverService
					.getSquenceInfoBySerialNumberTwo(param, dateString);
			overCallJson = new OverCallJsonSkip();
			overCallJson.setYwckjsjip(wXSquenceInfo1.getDeviceip());// 错
			overCallJson.setSbkzjsjip(wXSquenceInfo1.getControlip());
			overCallJson.setQhxxxlh(wXSquenceInfo1.getSerialnumber());
			overCallJson.setPdh(wXSquenceInfo1.getSquence() + "00");
			overCallJson.setYwlb("0" + wXSquenceInfo1.getBusinessType());
			overCallJson.setSfzmhm(wXSquenceInfo1.getCardnumber());
			overCallJson.setQhrxm(wXSquenceInfo1.getName());
			overCallJson.setQhsj(wXSquenceInfo1.getCreatetime());// 是否是这个格式的时间
			overCallJson.setRylb(String.valueOf(wXSquenceInfo1
					.getGetNumberType()));
			if (wXSquenceInfo1.getBusinessType() == 2) {
				logger.info("综合平台-代办业务(BusinessType = 2)");
				overCallJson.setDlrsfzmhm(wXSquenceInfo1.getCardnumber());
				// overCallJson.setQhrxm(wXSquenceInfo1.getName());
				overCallJson.setRylb("2");
			}
			if (wXSquenceInfo1.getIsproxy() == 1) {
				logger.info("综合平台-代办业务(isproxy = 1)");
				overCallJson.setDlrsfzmhm(wXSquenceInfo1.getProxycardnumber());
				// overCallJson.setQhrxm(wXSquenceInfo1.getProxyname());
				overCallJson.setRylb("2");
			}
			// serverService.updateRecallTime(qhxxxlh,dateString);
		} catch (Exception e) {
			e.printStackTrace();
			// code = 2;
			logger.error("在重复叫号的时候发生异常：", e);
		}
		// return code;
		return overCallJson;
	}

	/**
	 * 过号
	 * 
	 * @param qhxxxlh
	 * @return
	 */
	public OverCallJsonSkip overCall(String jsonData,
			ServerService serverService) throws Exception {
		OverCallJsonSkip overCallJson = null;
		try {
			JSONObject json = JSONObject.fromObject(jsonData);
			String qhxxxlh = json.getString("qhxxxlh");
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(currentTime);
			WXSquenceInfo wXSquenceInfo1 = serverService
					.getSquenceInfoBySerialNumberTwo(qhxxxlh, dateString);
			/*
			 * overCallJson = new OverCallJsonSkip();
			 * overCallJson.setYwckjsjip(wXSquenceInfo1.getDeviceip());
			 * overCallJson.setSbkzjsjip(wXSquenceInfo1.getControlip());
			 * overCallJson.setQhxxxlh(wXSquenceInfo1.getSerialnumber());
			 * overCallJson.setPdh(wXSquenceInfo1.getSquence()+"00");
			 * overCallJson.setYwlb("0"+wXSquenceInfo1.getBusinessType());
			 * overCallJson.setSfzmhm(wXSquenceInfo1.getCardnumber());
			 * overCallJson.setQhrxm(wXSquenceInfo1.getName());
			 * overCallJson.setQhsj(wXSquenceInfo1.getCreatetime());//是否是这个格式的时间
			 * overCallJson
			 * .setRylb(String.valueOf(wXSquenceInfo1.getGetNumberType()));
			 */
			serverService.updateSquenceSNYC(wXSquenceInfo1.getId(), 3, 0);
			serverService.updateSquenceOver(wXSquenceInfo1.getId(), 3, 0);
			serverService.updateShowStatu(wXSquenceInfo1.getWicketnumber(), 5,
					wXSquenceInfo1.getId().intValue());
			String ywckjsjip = wXSquenceInfo1.getDeviceip();
			BeiAnInfoJson beiAnInfoJson = serverService
					.getDeviceNumber(ywckjsjip);// 获取计算机编号，用于生成序列号
			List<OrderCount> orderCount = serverService.getAllTime();
			Calendar c = Calendar.getInstance();
			int now = c.get(Calendar.HOUR_OF_DAY);
			// 现在处于什么时间段 这个方法需要更改
			int nowtimequan = Utils.getTimqun(now, orderCount);// 处于哪个时间段，把该时间段的id返回来
			// sql语句需要更改,把业务范围加进去，将#01#02转化为1，2用来查询
			/*
			 * Date currentTime = new Date(); SimpleDateFormat formatter = new
			 * SimpleDateFormat("yyyy-MM-dd"); String dateString =
			 * formatter.format(currentTime);
			 */
			WXSquenceInfo wXSquenceInfo = serverService.getCallSquenceTwo(
					nowtimequan, 1, 1, dateString, beiAnInfoJson.getDeptid(),
					beiAnInfoJson.getKbywlb().replace("0", "")
							.replace("#", ","));// 获取有没有早到的
			if (wXSquenceInfo == null) {
				wXSquenceInfo = serverService
						.getCallSquenceTwo(nowtimequan, 1, 0, dateString,
								beiAnInfoJson.getDeptid(),
								beiAnInfoJson.getKbywlb().replace("0", "")
										.replace("#", ","));// 获取预约的
				if (wXSquenceInfo == null) {
					wXSquenceInfo = serverService.getCallSquenceTwo(
							null,
							0,
							null,
							dateString,
							beiAnInfoJson.getDeptid(),
							beiAnInfoJson.getKbywlb().replace("0", "")
									.replace("#", ","));// 获取现场号
					if (wXSquenceInfo == null) {
						wXSquenceInfo = serverService.getCallSquenceTwo(null,
								1, 1, dateString, beiAnInfoJson.getDeptid(),
								beiAnInfoJson.getKbywlb().replace("0", "")
										.replace("#", ","));// 获取有没有早到的
					}
				}
			}
			// 还没有建表
			SquenceConfig squenceConfig = serverService.getSquenceConfig(Utils
					.getParamsByKey("deptid"));
			int overmin = squenceConfig.getMinutes();// 现场号等待超时时间
			if (wXSquenceInfo != null) {
				if (squenceConfig.getIsopen() == 1
						&& wXSquenceInfo.getIsappointment() == 1) {// 开启了并且取出的是预约号
					WXSquenceInfo wXSquenceInfoXc = serverService
							.getCallSquenceTwo(null, 0, null, dateString,
									beiAnInfoJson.getDeptid(), beiAnInfoJson
											.getKbywlb().replace("0", "")
											.replace("#", ","));// 获取现场号
					if (wXSquenceInfoXc != null) {
						if (Utils.isOver30(wXSquenceInfoXc.getCreatetime(),
								overmin)) {// 判断这个现场号是否等待超过30分钟
							if (Utils.isEarly(wXSquenceInfo.getCreatetime(),
									wXSquenceInfoXc.getCreatetime())) {// 现场号更早
								wXSquenceInfo = wXSquenceInfoXc;
							}
						}
					}
				}
			}
			// 把该窗口的上一条叫号搜出来
			WXSquenceInfo lastSquenceInfo = serverService.getLastSerialNumber(
					ywckjsjip, dateString);
			if (lastSquenceInfo != null) {
				// 把内外状态同步表状态修改,squence_info状态修改
				if (lastSquenceInfo.getStatus() != 3
						&& lastSquenceInfo.getStatus() != 6) {
					serverService.updateSquenceSNYC(lastSquenceInfo.getId(), 6,
							0);
					serverService.updateSquenceStatus(lastSquenceInfo.getId(),
							6);
				}
			}
			if (wXSquenceInfo != null) {
				logger.info("号码创建的时间" + wXSquenceInfo.getCreatetime());
				logger.info("找到的号码是=" + wXSquenceInfo.getSquence());
				// 准备好返回的信息
				overCallJson = new OverCallJsonSkip();
				overCallJson.setYwckjsjip(wXSquenceInfo1.getDeviceip());
				overCallJson.setSbkzjsjip(beiAnInfoJson.getControlip());
				overCallJson.setQhxxxlh(wXSquenceInfo.getSerialnumber());
				overCallJson.setPdh(wXSquenceInfo.getSquence() + "00");
				overCallJson.setYwlb("0" + wXSquenceInfo.getBusinessType());
				overCallJson.setSfzmhm(wXSquenceInfo.getCardnumber());
				overCallJson.setQhrxm(wXSquenceInfo.getName());
				overCallJson.setQhsj(wXSquenceInfo.getCreatetime());// 是否是这个格式
				overCallJson.setRylb(String.valueOf(wXSquenceInfo
						.getGetNumberType()));
				if (wXSquenceInfo.getBusinessType() == 2) {
					logger.info("综合平台-代办业务(BusinessType = 2)");
					overCallJson.setDlrsfzmhm(wXSquenceInfo.getCardnumber());
					// overCallJson.setQhrxm(wXSquenceInfo.getName());
					overCallJson.setRylb("2");
				}
				if (wXSquenceInfo.getIsproxy() == 1) {
					logger.info("综合平台-代办业务(isproxy = 1)");
					overCallJson.setDlrsfzmhm(wXSquenceInfo
							.getProxycardnumber());
					// overCallJson.setQhrxm(wXSquenceInfo.getProxyname());
					overCallJson.setRylb("2");
				}
				// 保存叫号记录
				// 更新本条队列信息的状态，序列号，窗口号，叫号时间
				serverService.updateSquenceInfo(ywckjsjip,
						wXSquenceInfo.getSerialnumber(), wXSquenceInfo.getId(),
						beiAnInfoJson.getCkbh(), wXSquenceInfo.getSquence(),
						beiAnInfoJson.getDeptid(), 2, 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("在过号的时候发生异常：", e);
			// throw new Exception("在在过号的时候的时候发生异常");
		}
		return overCallJson;
	}

	/**
	 * 提请评价
	 * 
	 * @param qhxxxlh
	 * @return
	 */
	public int sponsorappraise(String jsonData, ServerService serverService)
			throws Exception {
		int code = 1;
		try {
			JSONObject json = JSONObject.fromObject(jsonData);
			String qhxxxlh = json.getString("qhxxxlh");
			// String qhxxxlh = " ";//解析传过来的数据获取
			WXSquenceInfo wXSquenceInfo = serverService
					.getWindowBySerialNumber(qhxxxlh);
			serverService.insertWaitAppraise(wXSquenceInfo.getId(),
					wXSquenceInfo.getWicketnumber(), qhxxxlh);// 插入前把该窗口之前没有评价的记录更新为跳过
			serverService.updateSquenceFinish(wXSquenceInfo.getId());
			serverService.updateSquenceSNYC(wXSquenceInfo.getId(), 6, 0);
			serverService.updateShowStatu(wXSquenceInfo.getWicketnumber(), 5,
					wXSquenceInfo.getId().intValue());
		} catch (Exception e) {
			code = 2;
			e.printStackTrace();
			logger.error("在提请评价的时候发生异常：", e);
			// throw new Exception("在判断是否存在黑名单的时候发生异常");
		}
		return code;
	}

	/**
	 * 暂停接口
	 * 
	 * @return
	 */
	public int stop(String jsonData, ServerService serverService)
			throws Exception {
		int code = 1;
		try {
			JSONObject json = JSONObject.fromObject(jsonData);
			String ywckjsjip = json.getString("ywckjsjip");
			// String ywckjsjip = "10.1.4.25";//通过解析获得
			serverService.changeWinStatu(1, ywckjsjip);
			// 控制led屏幕显示信息
		} catch (Exception e) {
			code = 2;
			e.printStackTrace();
			logger.error("在暂停的时候发生异常：", e);
			// throw new Exception("在判断是否存在黑名单的时候发生异常");
		}
		return code;
	}

	public int recover(String jsonData, ServerService serverService)
			throws Exception {
		int code = 1;
		try {
			JSONObject json = JSONObject.fromObject(jsonData);
			String ywckjsjip = json.getString("ywckjsjip");
			// String ywckjsjip = "10.1.4.25";//通过解析获得
			serverService.changeWinStatu(0, ywckjsjip);
			// 控制led屏幕显示信息
		} catch (Exception e) {
			code = 2;
			e.printStackTrace();
			logger.error("在恢复的时候发生异常：", e);
			// throw new Exception("在判断是否存在黑名单的时候发生异常");
		}
		return code;
	}
}

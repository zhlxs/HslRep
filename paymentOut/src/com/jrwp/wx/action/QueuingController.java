package com.jrwp.wx.action;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Encoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.wx.entity.AppointmeInfoJson;
import com.jrwp.wx.entity.AppointmeInfoJsonForMachine;
import com.jrwp.wx.entity.CallBusinessJson;
import com.jrwp.wx.entity.CallMenueJson;
import com.jrwp.wx.entity.DeptGZHPic;
import com.jrwp.wx.entity.GetNumberJson;
import com.jrwp.wx.entity.LedShow;
import com.jrwp.wx.entity.LedShowData;
import com.jrwp.wx.entity.MachineConfigJson;
import com.jrwp.wx.entity.ProxyData;
import com.jrwp.wx.entity.WXSquenceInfo;
import com.jrwp.wx.entity.WaitCountJson;
import com.jrwp.wx.entity.WxAppointInfoData;
import com.jrwp.wx.entity.WxGetWaitNumberData;
import com.jrwp.wx.entity.WxSigninData;
import com.jrwp.wx.entity.WxUpdateConfigData;
import com.jrwp.wx.service.WXSquenceInfoService;
import com.jrwp.wx.service.WxAppointmeService;
import com.jrwp.wx.until.DesUtil;
import com.jrwp.wx.until.ServerUtils;
import com.jrwp.wx.until.Utils;

@Description("取号机")
@Controller
@RequestMapping("queuing/queuingController")
public class QueuingController {

	private final Logger logger = Logger.getLogger(QueuingController.class);

	@Resource
	private WXSquenceInfoService wXSquenceInfoService;

	@Resource
	private WxAppointmeService wxAppointmeService;

	// 要确定是否可以通过身份证号码获取(签到的时候展示)
	@Description("微信预约信息查询")
	@RequestMapping(value = "wxappointInfo", produces = "application/json;charset=utf-8")
	@ResponseBody
	public DoResult wxappointmeInfo(HttpServletRequest request,
			HttpServletResponse response) {
		DoResult result = new DoResult();
		InputStream in = null;
		try {
			in = request.getInputStream();
			String model = Utils.inputStream2String(in, "");
			if (model != null) {
				ObjectMapper mapper = new ObjectMapper();
				WxAppointInfoData getData = mapper.readValue(model,
						WxAppointInfoData.class);
				Integer deptid = getData.getDeptid();
				String cardNumber = getData.getCardNumber();
				Date currentTime = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String dateString = formatter.format(currentTime);
				List<AppointmeInfoJsonForMachine> appointmeInfoJsonList = wXSquenceInfoService
						.getAppointmeByCardNumber(dateString, cardNumber,
								deptid);
				if (appointmeInfoJsonList == null
						|| appointmeInfoJsonList.size() == 0) {
					result.setStateType(DoResultType.validFail);
					result.setStateMsg("没有找到预约信息");
				}
				if (appointmeInfoJsonList != null
						&& appointmeInfoJsonList.size() > 0) {
					for (int i = 0; i < appointmeInfoJsonList.size(); i++) {
						// String name =
						// Utils.getBusinessName(Integer.parseInt(appointmeInfoJsonList.get(i).getBusiness()));
						String name = "综合业务";
						appointmeInfoJsonList.get(i).setBusiness(name);
					}
					if (appointmeInfoJsonList.size() == 1) {
						result.setStateValue(appointmeInfoJsonList);
						result.setStateType(DoResultType.success);
						result.setStateMsg("获取成功");
					} else {
						result.setStateValue(appointmeInfoJsonList);
						result.setStateType(DoResultType.info);
						result.setStateMsg("获取成功");
					}
				}
			} else {
				throw new Exception("参数空值异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("获取预约信息失败");
			logger.error("在取号机获取预约信息的时候发生异常：", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return result;
	}

	// 需要返回队列号和等待人数
	@Description("签到取号和现场取号")
	@RequestMapping(value = "wxsignin", produces = "application/json;charset=utf-8")
	@ResponseBody
	public DoResult wxsignin(HttpServletRequest request,
			HttpServletResponse response) {
		DoResult result = new DoResult();
		InputStream in = null;
		try {
			in = request.getInputStream();
			String model = Utils.inputStream2String(in, "");
			// String model = "mmmmm";
			if (model != null) {
				// ObjectMapper mapper = new ObjectMapper();
				// WxSigninData getData = mapper.readValue(model,
				// WxSigninData.class);
				// 测试用数据，正式部署时注释调================
				Long id = null;
				Integer deptid = 1;
				String name = "熊起";
				String cardNumber = "360122199309154232";
				int businessType = 1;
				String ser_code = "0001";
				int checkResult = 4;
				String xsd = "26.23";
				byte[] xcb = Base64.decodeBase64("wAARCAEsAhUDASIAAhEBAxEB");
				byte[] cardb = Base64.decodeBase64("wAARCAEsAhUDASIAAhEBAxEB");
				// 正式部署时放开注释=======================
				// Long id = getData.getId();
				// Integer deptid = getData.getDeptid();
				// String name = getData.getName();
				// String cardNumber = getData.getCardNumber();
				// int businessType = getData.getBusinessType();
				// String ser_code = getData.getSer_code();
				// int checkResult = getData.getCheckResult();
				// String xsd = getData.getXsd();
				// byte[] xcb = Base64.decodeBase64(getData.getPhotobase64());
				// byte[] cardb = Base64.decodeBase64(getData.getCardbase64());
				// ==============================================//
				wXSquenceInfoService.insertCheckResult(name, cardNumber,
						checkResult, xsd, xcb, cardb);// 保存比对信息
				Long squence_id = null;
				if (new ServerUtils().isBlackList(cardNumber, null,
						wXSquenceInfoService) == 0) {// 判断是否存在黑名单
					if (new ServerUtils().isPassAbleAccept(cardNumber, null,
							wXSquenceInfoService) == 0) {// 判断是否超过取号当天上限
						if (id == null || "".equals(id.toString())) {// 现场号
							WXSquenceInfo squenceInfo = new WXSquenceInfo();
							squenceInfo.setDeptid(deptid);
							squenceInfo.setIsappointment(0);
							squenceInfo.setStatus(1);
							squenceInfo.setCardnumber(cardNumber);
							squenceInfo.setName(name);
							squenceInfo.setIscall(0);
							squenceInfo.setSer_code(ser_code);
							squenceInfo.setBusinessType(businessType);
							Date currentTime = new Date();
							SimpleDateFormat formatter = new SimpleDateFormat(
									"yyyy-MM-dd");
							String dateString = formatter.format(currentTime);
							String squence = wXSquenceInfoService
									.getnextSquence(0, dateString, null,
											squenceInfo.getDeptid(),
											businessType);
							squenceInfo.setSquence(squence);
							squenceInfo.setSerialnumber(Utils
									.getSerialnumber(squence));
							int waitCount = wXSquenceInfoService
									.getWaitCountWXZS(deptid, dateString,
											businessType);// 当前业务类型等待总人数
							int otherType = 0;// 非当前业务等待人数统计
							if (businessType == 1) {// 综合业务
								otherType = wXSquenceInfoService
										.getWaitCountWXZS(deptid, dateString, 2);// 代办业务
							} else if (businessType == 2) {// 代办业务
								otherType = wXSquenceInfoService
										.getWaitCountWXZS(deptid, dateString, 1);// 综合业务
							}
							waitCount = waitCount + otherType;// 排队总人数（疑难的貌似没算）
							squence_id = wXSquenceInfoService
									.insertXcSquenceInfo(squenceInfo);
							GetNumberJson getNumberJson = new GetNumberJson();
							getNumberJson.setSquence(squence);
							getNumberJson.setWaitCount(waitCount);// 当次取号人的等待人数，屏幕显示应该加1
							getNumberJson.setWaitCountOther(waitCount + 1);
							getNumberJson.setBusiness(Utils
									.getBusinessName(businessType));
							result.setStateValue(getNumberJson);
							result.setStateType(DoResultType.success);
							String urlAfter = "?id=" + squence_id + "&sf="
									+ DesUtil.encrypt(cardNumber);
							result.setUrl(Utils.getParamsByKey("url")
									+ urlAfter);
							logger.info("url=" + Utils.getParamsByKey("url"));
							result.setStateMsg("取号成功");
						} else {// 预约号
							AppointmeInfoJson appointmeInfoJson = wxAppointmeService
									.getAppointmeById(id);
							if (appointmeInfoJson.getStatus() == 2) {// 已经签到
								result.setStateType(DoResultType.validFail);
								result.setStateMsg("已经签到了");
							} else if (appointmeInfoJson.getStatus() == 1) {
								// 如果有预约信息 则判断是否早到 和是否迟到
								int timequan = appointmeInfoJson
										.getTimequantum().intValue();
								Calendar c = Calendar.getInstance();
								int now = c.get(Calendar.HOUR_OF_DAY);
								logger.info("========预约时间段是："
										+ appointmeInfoJson.getTime());
								logger.info("========现在时间是：" + now);
								int resultNum = Utils.islaterOrearly(
										appointmeInfoJson.getTime(), now);
								logger.info("判断结果=" + resultNum);
								// resultNum =
								// 0;////////////////////////////////////////////////////////
								if (resultNum == 2) {// 迟到了
									result.setStateType(DoResultType.late);
									result.setStateMsg("您迟到了，请取现场号");
								} else {// 准时或者早到
									WXSquenceInfo squenceInfo = new WXSquenceInfo();
									// squenceInfo.setId(appointmeInfoJson.getDeptid().toString()+Utils.getTime());
									squenceInfo.setDeptid(Integer
											.valueOf(appointmeInfoJson
													.getDeptid().toString()));
									squenceInfo.setIsappointment(1);
									squenceInfo.setStatus(1);
									squenceInfo.setName(appointmeInfoJson
											.getName());
									squenceInfo.setOpenid(appointmeInfoJson
											.getOpenid());
									squenceInfo
											.setAppointmenttime(appointmeInfoJson
													.getAppointmenttime()
													.split(" ")[0]);
									// System.out.println("预约时间="+appointmeInfoJson.getAppointmenttime());
									squenceInfo.setCardnumber(appointmeInfoJson
											.getCardNumber());
									squenceInfo.setIsearly(resultNum);
									squenceInfo.setTimeQuantum(timequan);
									squenceInfo.setAppointme_infoid(id);
									squenceInfo.setIscall(0);
									squenceInfo.setSer_code(appointmeInfoJson
											.getSer_code());
									squenceInfo
											.setBusinessType(appointmeInfoJson
													.getBusinessType());
									Date currentTime = new Date();
									SimpleDateFormat formatter = new SimpleDateFormat(
											"yyyy-MM-dd");
									String dateString = formatter
											.format(currentTime);
									String squence = wXSquenceInfoService
											.getnextSquence(1, dateString,
													resultNum, squenceInfo
															.getDeptid(),
													appointmeInfoJson
															.getBusinessType());
									squenceInfo.setSquence(squence);
									squenceInfo.setSerialnumber(Utils
											.getSerialnumber(squence));
									int waitCount = 0;
									int waitCountOther = 0;
									waitCount = wXSquenceInfoService
											.getWaitCountWXZS(squenceInfo
													.getDeptid(), dateString,
													squenceInfo
															.getBusinessType());
									waitCountOther = waitCount + 1;
									int otherType = 0;
									if (squenceInfo.getBusinessType() == 1) {
										otherType = wXSquenceInfoService
												.getWaitCountWXZS(
														squenceInfo.getDeptid(),
														dateString, 2);
									} else if (squenceInfo.getBusinessType() == 2) {
										otherType = wXSquenceInfoService
												.getWaitCountWXZS(
														squenceInfo.getDeptid(),
														dateString, 1);
									}
									waitCount = waitCount + otherType;
									// System.out.println("取得号是="+squenceInfo.getSquence());
									// 插入和修改状态，开启事务
									squence_id = wXSquenceInfoService
											.insertSquenceInfoAndUpdateStates(squenceInfo);
									GetNumberJson getNumberJson = new GetNumberJson();
									getNumberJson.setSquence(squence);
									getNumberJson.setWaitCount(waitCount);
									getNumberJson
											.setWaitCountOther(waitCountOther);
									getNumberJson.setBusiness(Utils
											.getBusinessName(appointmeInfoJson
													.getBusinessType()));
									result.setStateValue(getNumberJson);
									String urlAfter = "?id=" + squence_id
											+ "&sf="
											+ DesUtil.encrypt(cardNumber);
									result.setUrl(Utils.getParamsByKey("url")
											+ urlAfter);
									result.setStateType(DoResultType.success);
									result.setStateMsg("签到取号成功");
								}
							}
						}
						if (result.getUrl() != null
								&& !"".equals(result.getUrl())) {
							wXSquenceInfoService.insertSquenceSync(squence_id,
									0, 1);
						}
					} else {
						result.setStateType(DoResultType.info);
						result.setStateMsg("当日取号数已达上限");
					}
				} else {
					result.setStateType(DoResultType.warning);
					result.setStateMsg("存在黑名单用户");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("微信预约取号失败");
			logger.error("在微信预约取号的时候发生异常：", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		logger.info("签到或现场取号：" + JacksonUtil.toJson(result));
		return result;
	}

	@Description("代办取号")
	@RequestMapping(value = "proxygetnumber", produces = "application/json;charset=utf-8")
	@ResponseBody
	public DoResult proxygetnumber(HttpServletRequest request,
			HttpServletResponse response) {
		DoResult result = new DoResult();
		InputStream in = null;
		try {
			in = request.getInputStream();
			String model = Utils.inputStream2String(in, "");
			// String model = "mmmm";
			if (model != null) {
				ObjectMapper mapper = new ObjectMapper();
				ProxyData getData = mapper.readValue(model, ProxyData.class);
				// 测试用数据，正式部署时注释调
				// ProxyData getData = new ProxyData();
				// getData.setDeptid(1);
				// getData.setName("张三");
				// getData.setCardNumber("360122199309154232");
				// getData.setProxyname("代理人");
				// getData.setProxyCardNumber("360122199403067225");
				// getData.setBusinessType(2);
				// getData.setSer_code("0006");
				// getData.setCheckResult(4);
				// getData.setXsd("33.36");
				// getData.setPhotobase64("wAARCAEsAhUDASIAAhEBAxEB");
				// getData.setCardbase64("wAARCAEsAhUDASIAAhEBAxEB");
				if (new ServerUtils().isBlackList(getData.getCardNumber(),
						getData.getProxyCardNumber(), wXSquenceInfoService) == 0) {
					if (new ServerUtils().isPassAbleAccept(
							getData.getCardNumber(),
							getData.getProxyCardNumber(), wXSquenceInfoService) == 0) {
						wXSquenceInfoService.insertCheckResult(
								getData.getProxyname(),
								getData.getProxyCardNumber(),
								getData.getCheckResult(), getData.getXsd(),
								Base64.decodeBase64(getData.getPhotobase64()),
								Base64.decodeBase64(getData.getCardbase64()));
						WXSquenceInfo squenceInfo = new WXSquenceInfo();
						squenceInfo.setDeptid(getData.getDeptid());
						squenceInfo.setIsappointment(0);
						squenceInfo.setStatus(1);
						squenceInfo.setCardnumber(getData.getCardNumber());
						squenceInfo.setName(getData.getName());
						squenceInfo.setProxyname(getData.getProxyname());
						squenceInfo.setProxycardnumber(getData
								.getProxyCardNumber());
						squenceInfo.setIscall(0);
						squenceInfo.setIsproxy(1);
						squenceInfo.setSer_code(getData.getSer_code());
						squenceInfo.setBusinessType(getData.getBusinessType());
						squenceInfo.setGetNumberType(2);
						Date currentTime = new Date();
						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyy-MM-dd");
						String dateString = formatter.format(currentTime);
						String squence = wXSquenceInfoService.getnextSquence(0,
								dateString, null, squenceInfo.getDeptid(),
								getData.getBusinessType());
						squenceInfo.setSquence(squence);
						squenceInfo.setSerialnumber(Utils
								.getSerialnumber(squence));
						int waitCount = wXSquenceInfoService.getWaitCountWXZS(
								getData.getDeptid(), dateString,
								getData.getBusinessType());
						int otherType = 0;
						if (getData.getBusinessType() == 1) {
							otherType = wXSquenceInfoService.getWaitCountWXZS(
									getData.getDeptid(), dateString, 2);
						} else if (getData.getBusinessType() == 2) {
							otherType = wXSquenceInfoService.getWaitCountWXZS(
									getData.getDeptid(), dateString, 1);
						}
						waitCount = waitCount + otherType;
						Long squence_id = wXSquenceInfoService
								.insertDlSquenceInfo(squenceInfo);
						wXSquenceInfoService
								.insertSquenceSync(squence_id, 0, 1);
						GetNumberJson getNumberJson = new GetNumberJson();
						getNumberJson.setSquence(squence);
						getNumberJson.setWaitCount(waitCount);// 当次取号人的等待人数，屏幕显示应该加1
						getNumberJson.setWaitCountOther(waitCount + 1);
						logger.info("业务类型是");
						logger.info("业务名称是"
								+ Utils.getBusinessName(getData
										.getBusinessType()));
						getNumberJson.setBusiness(Utils.getBusinessName(getData
								.getBusinessType()));
						result.setStateValue(getNumberJson);
						result.setStateType(DoResultType.success);
						String urlAfter = "?id=" + squence_id + "&sf="
								+ DesUtil.encrypt(getData.getCardNumber());
						result.setUrl(Utils.getParamsByKey("url") + urlAfter);
						result.setStateMsg("取号成功");
					} else {
						result.setStateType(DoResultType.info);
						result.setStateMsg("当日取号数已达上限");
					}
				} else {
					result.setStateType(DoResultType.warning);
					result.setStateMsg("存在黑名单用户");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("代理取号失败");
			logger.error("在代理取号的时候发生异常：", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		logger.info("代理取号：" + JacksonUtil.toJson(result));
		return result;
	}

	/**
	 * 供叫号机获取等待人数
	 * 
	 * @param deptid
	 * @return
	 */
	@Description("获取等待人数")
	@RequestMapping(value = "wxgetwaitnumber", produces = "application/json;charset=utf-8")
	@ResponseBody
	public DoResult getWaitCount(HttpServletRequest request,
			HttpServletResponse response) {
		DoResult result = new DoResult();
		InputStream in = null;
		try {
			in = request.getInputStream();
			String model = Utils.inputStream2String(in, "");
			// String model = "bbbb";
			if (model != null) {
				ObjectMapper mapper = new ObjectMapper();
				WxGetWaitNumberData getData = mapper.readValue(model,
						WxGetWaitNumberData.class);
				Integer deptid = getData.getDeptid();
				Date currentTime = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String dateString = formatter.format(currentTime);
				List<WaitCountJson> waitCountJson = wXSquenceInfoService
						.getWaitCount(deptid, dateString);
				if (waitCountJson != null) {
					String[] busArr = new String[waitCountJson.size()];
					for (int i = 0; i < waitCountJson.size(); i++) {
						busArr[i] = String.valueOf(waitCountJson.get(i)
								.getBusinesstype());
					}
					if (!Arrays.asList(busArr).contains("1")) {
						WaitCountJson waitCountJson1 = new WaitCountJson();
						waitCountJson1.setBusinesstype(1);
						waitCountJson1.setWaitCount(0);
						waitCountJson.add(waitCountJson1);
					}
					if (!Arrays.asList(busArr).contains("2")) {
						WaitCountJson waitCountJson2 = new WaitCountJson();
						waitCountJson2.setBusinesstype(2);
						waitCountJson2.setWaitCount(0);
						waitCountJson.add(waitCountJson2);
					}
					if (!Arrays.asList(busArr).contains("4")) {
						WaitCountJson waitCountJson4 = new WaitCountJson();
						waitCountJson4.setBusinesstype(4);
						waitCountJson4.setWaitCount(0);
						waitCountJson.add(waitCountJson4);
					}
				}
				if (waitCountJson == null) {
					waitCountJson = new ArrayList<WaitCountJson>();
					WaitCountJson waitCountJson1 = new WaitCountJson();
					waitCountJson1.setBusinesstype(1);
					waitCountJson1.setWaitCount(0);
					waitCountJson.add(waitCountJson1);
					WaitCountJson waitCountJson2 = new WaitCountJson();
					waitCountJson2.setBusinesstype(2);
					waitCountJson2.setWaitCount(0);
					waitCountJson.add(waitCountJson2);
					WaitCountJson waitCountJson4 = new WaitCountJson();
					waitCountJson4.setBusinesstype(4);
					waitCountJson4.setWaitCount(0);
					waitCountJson.add(waitCountJson4);
				}
				int a = 0;
				int b = 0;
				for (int i = 0; i < waitCountJson.size(); i++) {
					if (1 == waitCountJson.get(i).getBusinesstype()) {
						a = waitCountJson.get(i).getWaitCount();
					}
					if (2 == waitCountJson.get(i).getBusinesstype()) {
						b = waitCountJson.get(i).getWaitCount();
					}
				}
				int count = a + b;
				for (int i = 0; i < waitCountJson.size(); i++) {
					if (1 == waitCountJson.get(i).getBusinesstype()) {
						waitCountJson.get(i).setWaitCount(count);
						;
					}
					/*
					 * if(2 == waitCountJson.get(i).getBusinesstype()){
					 * waitCountJson.get(i).setWaitCount(count); }
					 */
				}
				result.setStateValue(waitCountJson);
				result.setStateType(DoResultType.success);
				result.setStateMsg("获取成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("取号机获取等待人数失败");
			logger.error("在取消预约的时候发生异常：", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		// logger.info("等待人数返回的结果"+JacksonUtil.toJson(result));
		return result;
	}

	/**
	 * 获取配置接口
	 * 
	 * @param String
	 *            deviceNumber
	 * @return
	 */
	@Description("取号机获取配置参数")
	@RequestMapping(value = "wxgetmachineconfig", produces = "application/json;charset=utf-8")
	@ResponseBody
	public DoResult wxgetmachineconfig(HttpServletRequest request,
			HttpServletResponse response) {
		DoResult result = new DoResult();
		InputStream in = null;
		try {
			in = request.getInputStream();
			String model = Utils.inputStream2String(in, "");
			// String model = "bbbb";
			// logger.info("huoqude"+model);
			if (model != null) {
				ObjectMapper mapper = new ObjectMapper();
				WxUpdateConfigData getData = mapper.readValue(model,
						WxUpdateConfigData.class);
				/*
				 * WxUpdateConfigData getData = new WxUpdateConfigData();
				 * getData.setDeviceNumber("123456789");
				 * getData.setModelid("1"); getData.setUpdatetime("2018-11-19");
				 */
				String deviceNumber = getData.getDeviceNumber();
				MachineConfigJson machineConfigJson = null;
				if (getData.getUpdatetime() != null
						&& !"".equals(getData.getUpdatetime())) {// 查询更新
					MachineConfigJson machineConfigCheckJson = wXSquenceInfoService
							.getMachineConfigJsonByDeviceNumber(deviceNumber);
					// MachineConfigJson machineConfigJson = null;
					if (machineConfigCheckJson.getModelid().toString()
							.equals(getData.getModelid())
							&& getData.getUpdatetime().equals(
									machineConfigCheckJson.getUpdatetime())) {// 如果是空就插入
						result.setStateType(DoResultType.validFail);
						result.setStateMsg("没有更新信息");
					} else {
						machineConfigJson = wXSquenceInfoService
								.getMachineConfig(deviceNumber);
					}
				} else {// 获取配置
						// String deviceNumber = "123456789";
					Integer id = wXSquenceInfoService
							.getConfigIdByDeviceNumber(deviceNumber);
					if (id == null) {// 如果是空就插入
						wXSquenceInfoService.insertDeviceNumber(deviceNumber);
						machineConfigJson = wXSquenceInfoService
								.getMachineConfig(deviceNumber);
					} else {
						machineConfigJson = wXSquenceInfoService
								.getMachineConfig(deviceNumber);
					}
				}
				if (machineConfigJson != null) {
					BASE64Encoder encoder = new BASE64Encoder();
					for (int i = 0; i < machineConfigJson.getCallBusinessJson()
							.size(); i++) {
						machineConfigJson
								.getCallBusinessJson()
								.get(i)
								.setBase64(
										encoder.encode(machineConfigJson
												.getCallBusinessJson().get(i)
												.getIcon()));
						machineConfigJson.getCallBusinessJson().get(i)
								.setIcon(null);
					}
					List<CallMenueJson> callMenueJson = new ArrayList<CallMenueJson>();
					CallMenueJson callMenueParent = null;
					for (int i = 0; i < machineConfigJson.getCallBusinessJson()
							.size(); i++) {
						if (0 == machineConfigJson.getCallBusinessJson().get(i)
								.getParentid()) {
							callMenueParent = new CallMenueJson();
							callMenueParent
									.setParentid(machineConfigJson
											.getCallBusinessJson().get(i)
											.getParentid());
							callMenueParent.setOrdercode(machineConfigJson
									.getCallBusinessJson().get(i)
									.getOrdercode());
							callMenueParent.setBase64(machineConfigJson
									.getCallBusinessJson().get(i).getBase64());
							callMenueParent
									.setTopcoord(machineConfigJson
											.getCallBusinessJson().get(i)
											.getTopcoord());
							callMenueParent.setLeftcoord(machineConfigJson
									.getCallBusinessJson().get(i)
									.getLeftcoord());
							callMenueParent.setBusinesstype(machineConfigJson
									.getCallBusinessJson().get(i)
									.getBusinesstype());
							callMenueParent
									.setSer_code(machineConfigJson
											.getCallBusinessJson().get(i)
											.getSer_code());
							callMenueParent.setWidth(machineConfigJson
									.getCallBusinessJson().get(i).getWidth());
							callMenueParent.setHeight(machineConfigJson
									.getCallBusinessJson().get(i).getHeight());
							callMenueJson.add(callMenueParent);
						}
					}
					List<CallBusinessJson> CallBusinessChild = null;
					for (int i = 0; i < callMenueJson.size(); i++) {
						CallBusinessChild = new ArrayList<CallBusinessJson>();
						String ser_code = callMenueJson.get(i).getSer_code();// 父级的code编码
						for (int n = 0; n < machineConfigJson
								.getCallBusinessJson().size(); n++) {
							if (ser_code.equals(machineConfigJson
									.getCallBusinessJson().get(n).getParentid()
									.toString())) {
								CallBusinessChild.add(machineConfigJson
										.getCallBusinessJson().get(n));
							}
						}
						callMenueJson.get(i).setChildMenue(CallBusinessChild);
					}
					machineConfigJson.setCallBusinessJson(null);
					machineConfigJson.setCallMenueJson(callMenueJson);
					DeptGZHPic deptGZHPic = wXSquenceInfoService
							.getgzhpic(deviceNumber);
					result.setUrl(encoder.encode(deptGZHPic.getPic()));
					result.setStateValue(machineConfigJson);
					result.setStateType(DoResultType.success);
					result.setStateMsg("获取成功");
				}
			} else {
				throw new Exception("参数空值异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("取号机获取配置参数失败");
			logger.error("在取号机获取配置参数的时候发生异常：", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return result;
	}

	/**
	 * LED灯显示数据获取
	 * 
	 * @param deptid
	 * @return
	 */
	@Description("获取led显示")
	@RequestMapping(value = "wxgetshow", produces = "application/json;charset=utf-8")
	@ResponseBody
	public DoResult wxgetshow(HttpServletRequest request,
			HttpServletResponse response) {
		DoResult result = new DoResult();
		InputStream in = null;
		try {
			in = request.getInputStream();
			String model = Utils.inputStream2String(in, "");
			if (model != null) {
				ObjectMapper mapper = new ObjectMapper();
				LedShowData getData = mapper
						.readValue(model, LedShowData.class);
				Long deptid = (Long) Long.parseLong(getData.getDeptid());
				List<LedShow> ledShow = wXSquenceInfoService.getShow(deptid, 2);// 在叫号的
				result.setStateValue(ledShow);
				result.setStateType(DoResultType.success);
				result.setStateMsg("获取成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("获取失败");
			logger.error("在获取led显示的时候发生异常：", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return result;
	}

	/**
	 * 该接口未使用
	 * 
	 * @param request
	 * @param response
	 * @param cardNumber
	 * @return
	 */
	@Description("微信预约信息查询")
	@RequestMapping(value = "wxwebappointInfo")
	@ResponseBody
	public DoResult wxwebappointInfo(HttpServletRequest request,
			HttpServletResponse response, String cardNumber) {
		DoResult result = new DoResult();
		// String cardNumber = "360122199309154232";
		try {
			Integer deptid = Integer.valueOf(Utils.getParamsByKey("deptid"));
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(currentTime);
			List<AppointmeInfoJsonForMachine> appointmeInfoJsonList = wXSquenceInfoService
					.getAppointmeByCardNumber(dateString, cardNumber, deptid);
			if (appointmeInfoJsonList == null
					|| appointmeInfoJsonList.size() == 0) {
				result.setStateType(DoResultType.validFail);
				result.setStateMsg("没有找到预约信息");
			}
			if (appointmeInfoJsonList != null
					&& appointmeInfoJsonList.size() > 0) {
				for (int i = 0; i < appointmeInfoJsonList.size(); i++) {
					// String name =
					// Utils.getBusinessName(Integer.parseInt(appointmeInfoJsonList.get(i).getBusiness()));
					String name = "综合业务";
					appointmeInfoJsonList.get(i).setBusiness(name);
				}
				result.setStateValue(appointmeInfoJsonList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("获取预约信息失败");
			logger.error("在网页获取预约信息的时候发生异常：", e);
		}
		return result;
	}

	/**
	 * 该接口未使用
	 * 
	 * @param request
	 * @param response
	 * @param name
	 * @param cardNumber
	 * @param businessType
	 * @param id
	 * @return
	 */
	@Description("签到取号和现场取号")
	@RequestMapping(value = "wxwebsignin")
	@ResponseBody
	public DoResult wxwebsignin(HttpServletRequest request,
			HttpServletResponse response, String name, String cardNumber,
			int businessType, String id) {
		DoResult result = new DoResult();
		try {
			/*
			 * String name = "熊起"; String cardNumber = "360122199309154232"; int
			 * businessType = 2; String id = "0";
			 */
			logger.info("进入aaaa接口");
			Long squence_id = null;
			Integer deptid = Integer.valueOf(Utils.getParamsByKey("deptid"));
			if (new ServerUtils().isBlackList(cardNumber, null,
					wXSquenceInfoService) == 0) {
				if (new ServerUtils().isPassAbleAccept(cardNumber, null,
						wXSquenceInfoService) == 0) {
					if ("0".equals(id.toString())) {// 现场的
						WXSquenceInfo squenceInfo = new WXSquenceInfo();
						squenceInfo.setDeptid(deptid);
						squenceInfo.setIsappointment(0);
						squenceInfo.setStatus(1);
						squenceInfo.setCardnumber(cardNumber);
						squenceInfo.setName(name);
						squenceInfo.setIscall(0);
						squenceInfo.setSer_code("0000");
						squenceInfo.setBusinessType(businessType);
						Date currentTime = new Date();
						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyy-MM-dd");
						String dateString = formatter.format(currentTime);
						String squence = wXSquenceInfoService.getnextSquence(0,
								dateString, null, squenceInfo.getDeptid(),
								businessType);
						squenceInfo.setSquence(squence);
						squenceInfo.setSerialnumber(Utils
								.getSerialnumber(squence));
						int waitCount = wXSquenceInfoService.getWaitCountWXZS(
								deptid, dateString, businessType);
						int otherType = 0;
						if (businessType == 1) {
							otherType = wXSquenceInfoService.getWaitCountWXZS(
									deptid, dateString, 2);
						} else if (businessType == 2) {
							otherType = wXSquenceInfoService.getWaitCountWXZS(
									deptid, dateString, 1);
						}
						waitCount = waitCount + otherType;
						squence_id = wXSquenceInfoService
								.insertXcSquenceInfo(squenceInfo);
						GetNumberJson getNumberJson = new GetNumberJson();
						getNumberJson.setSquence(squence);
						getNumberJson.setWaitCount(waitCount);// 当次取号人的等待人数，屏幕显示应该加1
						getNumberJson.setWaitCountOther(waitCount + 1);
						getNumberJson.setBusiness(Utils
								.getBusinessName(businessType));
						result.setStateValue(getNumberJson);
						result.setStateType(DoResultType.success);
						String urlAfter = "?id=" + squence_id + "&sf="
								+ DesUtil.encrypt(cardNumber);
						result.setUrl(Utils.getParamsByKey("url") + urlAfter);
						logger.info("url=" + Utils.getParamsByKey("url"));
						result.setStateMsg("取号成功");
					} else {// 预约的
						AppointmeInfoJson appointmeInfoJson = wxAppointmeService
								.getAppointmeById(Long.valueOf(id));
						if (appointmeInfoJson.getStatus() == 2) {// 已经签到
							result.setStateType(DoResultType.validFail);
							result.setStateMsg("已经签到了");
						} else if (appointmeInfoJson.getStatus() == 1) {
							// 如果有预约信息 则判断是否早到 和是否迟到
							int timequan = appointmeInfoJson.getTimequantum()
									.intValue();
							Calendar c = Calendar.getInstance();
							int now = c.get(Calendar.HOUR_OF_DAY);
							logger.info("========时间段是："
									+ appointmeInfoJson.getTime());
							int resultNum = Utils.islaterOrearly(
									appointmeInfoJson.getTime(), now);
							System.out.println("判断结果=" + resultNum);
							// resultNum =
							// 0;////////////////////////////////////////////////////////
							if (resultNum == 2) {// 迟到了
								result.setStateType(DoResultType.late);
								result.setStateMsg("您迟到了，请取现场号");
							} else {// 准时或者早到
								WXSquenceInfo squenceInfo = new WXSquenceInfo();
								squenceInfo.setDeptid(Integer
										.valueOf(appointmeInfoJson.getDeptid()
												.toString()));
								squenceInfo.setIsappointment(1);
								squenceInfo.setStatus(1);
								squenceInfo
										.setName(appointmeInfoJson.getName());
								squenceInfo.setOpenid(appointmeInfoJson
										.getOpenid());
								squenceInfo
										.setAppointmenttime(appointmeInfoJson
												.getAppointmenttime()
												.split(" ")[0]);
								squenceInfo.setCardnumber(appointmeInfoJson
										.getCardNumber());
								squenceInfo.setIsearly(resultNum);
								squenceInfo.setTimeQuantum(timequan);
								squenceInfo.setAppointme_infoid(Long
										.valueOf(id));
								squenceInfo.setIscall(0);
								squenceInfo.setSer_code(appointmeInfoJson
										.getSer_code());
								squenceInfo.setBusinessType(appointmeInfoJson
										.getBusinessType());
								Date currentTime = new Date();
								SimpleDateFormat formatter = new SimpleDateFormat(
										"yyyy-MM-dd");
								String dateString = formatter
										.format(currentTime);
								String squence = wXSquenceInfoService
										.getnextSquence(1, dateString,
												resultNum, squenceInfo
														.getDeptid(),
												appointmeInfoJson
														.getBusinessType());
								squenceInfo.setSquence(squence);
								squenceInfo.setSerialnumber(Utils
										.getSerialnumber(squence));
								int waitCount = 0;
								int waitCountOther = 0;
								waitCount = wXSquenceInfoService
										.getWaitCountWXZS(
												squenceInfo.getDeptid(),
												dateString,
												squenceInfo.getBusinessType());
								waitCountOther = waitCount + 1;
								int otherType = 0;
								if (squenceInfo.getBusinessType() == 1) {
									otherType = wXSquenceInfoService
											.getWaitCountWXZS(
													squenceInfo.getDeptid(),
													dateString, 2);
								} else if (squenceInfo.getBusinessType() == 2) {
									otherType = wXSquenceInfoService
											.getWaitCountWXZS(
													squenceInfo.getDeptid(),
													dateString, 1);
								}
								waitCount = waitCount + otherType;
								// System.out.println("取得号是="+squenceInfo.getSquence());
								// 插入和修改状态，开启事务
								squence_id = wXSquenceInfoService
										.insertSquenceInfoAndUpdateStates(squenceInfo);
								GetNumberJson getNumberJson = new GetNumberJson();
								getNumberJson.setSquence(squence);
								getNumberJson.setWaitCount(waitCount);
								getNumberJson.setWaitCountOther(waitCountOther);
								getNumberJson.setBusiness(Utils
										.getBusinessName(appointmeInfoJson
												.getBusinessType()));
								result.setStateValue(getNumberJson);
								String urlAfter = "?id=" + squence_id + "&sf="
										+ DesUtil.encrypt(cardNumber);
								result.setUrl(Utils.getParamsByKey("url")
										+ urlAfter);
								result.setStateType(DoResultType.success);
								result.setStateMsg("签到取号成功");
							}
						}
					}
					if (result.getUrl() != null && !"".equals(result.getUrl())) {
						wXSquenceInfoService
								.insertSquenceSync(squence_id, 0, 1);
					}
				} else {
					result.setStateType(DoResultType.info);
					result.setStateMsg("当日取号数已达上限");
				}
			} else {
				result.setStateType(DoResultType.warning);
				result.setStateMsg("存在黑名单用户");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("微信预约取号失败");
			logger.error("在微信预约取号的时候发生异常：", e);
		}
		return result;
	}

	/**
	 * 接口测试
	 * 
	 * @param request
	 * @param response
	 * @param hm
	 * @return
	 */
	@Description("从指定号码开始取号")
	@RequestMapping(value = "wxzdhm", produces = "application/json;charset=utf-8")
	@ResponseBody
	public DoResult wxzdhm(HttpServletRequest request,
			HttpServletResponse response, String hm) {
		DoResult result = new DoResult();
		try {
			Integer deptid = 1;
			String name = "王学敏";
			String cardNumber = "360122198305144202";
			int businessType = 1;
			String ser_code = "0001";
			WXSquenceInfo squenceInfo = new WXSquenceInfo();
			squenceInfo.setDeptid(deptid);
			squenceInfo.setIsappointment(0);
			squenceInfo.setStatus(6);
			squenceInfo.setCardnumber(cardNumber);
			squenceInfo.setName(name);
			squenceInfo.setIscall(0);
			squenceInfo.setSer_code(ser_code);
			squenceInfo.setBusinessType(businessType);
			String squence = hm;
			squenceInfo.setSquence(squence);
			squenceInfo.setSerialnumber(Utils.getSerialnumber(squence));
			wXSquenceInfoService.insertXcSquenceInfo(squenceInfo);
			result.setStateType(DoResultType.success);
			result.setStateMsg("获取成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("获取失败");
			logger.error("在从指定号码开始取号的时候发生异常：", e);
		}
		return result;
	}
}

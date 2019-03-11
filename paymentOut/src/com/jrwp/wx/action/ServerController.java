package com.jrwp.wx.action;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.webservice.entity.tmrioutaccess.WriteHead;
//import com.jrwp.webservice.entity.tmrioutaccess.request.Request25C11;
import com.jrwp.webservice.entity.tmrioutaccess.request.Request25C12;
//import com.jrwp.webservice.entity.tmrioutaccess.response.Response25C11;
import com.jrwp.webservice.help.TmriOutAccessJSZApi;
import com.jrwp.wx.entity.BeiAnInfoJson;
import com.jrwp.wx.entity.OrderCount;
import com.jrwp.wx.entity.OverCallJson;
import com.jrwp.wx.entity.OverCallJsonSkip;
import com.jrwp.wx.entity.RepeatCallJson;
import com.jrwp.wx.entity.RespData;
import com.jrwp.wx.entity.SquenceConfig;
import com.jrwp.wx.entity.SupplementRecord;
import com.jrwp.wx.entity.WXSquenceInfo;
import com.jrwp.wx.service.ClientService;
import com.jrwp.wx.service.ServerService;
import com.jrwp.wx.until.GetXmlString;
import com.jrwp.wx.until.PostXml;
import com.jrwp.wx.until.ServerUtils;
import com.jrwp.wx.until.Utils;

@Description("对接综合系统接口")
@Controller
@RequestMapping("server")
public class ServerController {
	private final Logger logger = Logger.getLogger(ServerController.class);

	@Resource
	private ServerService serverService;
	@Resource
	private ClientService clientService;
	@Resource
	private TmriOutAccessJSZApi tmriOutAccessJSZApi;

	@Description("25C11接口测试")
	@RequestMapping("25C11")
	@ResponseBody
	public DoResult tmri25C11() {
		DoResult result = new DoResult();
		try {
			// Request25C11 request25C11 = new Request25C11();
			// request25C11.setYwckjsjip("47.66.34.19");
			// request25C11.setSbkzjsjip("10.137.42.81");
			// request25C11.setQhxxxlh("1902253611000002B00600");
			// request25C11.setPdh("B00600");
			// request25C11.setYwlb("01");
			// request25C11.setSfzmhm("360122199309154232");
			// request25C11.setQhsj("2019-02-25 12:50:35");
			// request25C11.setJbr("AA0048");
			// request25C11.setRylb("1");
			// Response25C11 writeHead =
			// tmriOutAccessJSZApi.writeBCInfo(request25C11);
			// result.setStateValue(writeHead);
			// 测试用例
			// String model =
			// "{\"opType\":\"TMRI_CALLOUT\",\"reqdata\":{\"glbm\":\"360100000400\",\"jbr\":\"AA0048\",\"ywckjsjip\":\"47.66.34.19\"},\"charset\":\"UTF-8\"}";
			// JSONObject json = JSONObject.fromObject(model);
			// String jsonData = json.getString("reqdata");
			// OverCallJson overCallJson = new
			// ServerUtils().callNumber(jsonData,
			// serverService);
			// logger.info("返回的信息=" + JSON.toJSONString(overCallJson));
			//
			// String model2 =
			// "{\"opType\":\"TMRI_RECALL\",\"reqdata\":{\"jsjip\":\"47.66.34.19\",\"jbr\":\"AA0048\",\"qhxxxlh\":\""+overCallJson.getQhxxxlh()+"\"},\"charset\":\"UTF-8\"}";
			// JSONObject json2 = JSONObject.fromObject(model2);
			// String jsonData2 = json2.getString("reqdata");
			// OverCallJsonSkip overCallJson2 = new
			// ServerUtils().repeatCall(jsonData2,
			// serverService);
			// logger.info("返回的信息=" + JSON.toJSONString(overCallJson2));
			//
			// String model3 =
			// "{\"opType\":\"TMRI_SKIP\",\"reqdata\":{\"jsjip\":\"47.66.34.19\",\"jbr\":\"AA0048\",\"qhxxxlh\":\""+overCallJson.getQhxxxlh()+"\"},\"charset\":\"UTF-8\"}";
			// JSONObject json3 = JSONObject.fromObject(model3);
			// String jsonData3 = json3.getString("reqdata");
			// OverCallJsonSkip overCallJson3 = new
			// ServerUtils().overCall(jsonData3,
			// serverService);
			// logger.info("返回的信息=" + JSON.toJSONString(overCallJson3));
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("保存失败");
			logger.error("在测试的时候发生异常：", e);
		}
		return result;
	}

	@Description("25C12接口测试")
	@RequestMapping("25C12")
	@ResponseBody
	public DoResult tmri25C12() {
		DoResult result = new DoResult();
		try {
			Request25C12 request25C12 = new Request25C12();
			request25C12.setQhxxxlh("1902253611000002B02000");
			request25C12.setPjjg("1");
			request25C12.setPjlb("2");
			WriteHead writeHead = tmriOutAccessJSZApi
					.writeApprInfo(request25C12);
			result.setStateValue(writeHead);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("保存失败");
			logger.error("在测试的时候发生异常：", e);
		}
		return result;
	}

	@Description("提供给综合平台的接口")
	@RequestMapping("queue")
	@ResponseBody
	public RespData callSystem(HttpServletRequest request,
			HttpServletResponse response) {
		RespData result = new RespData();
		InputStream in = null;
		try {
			in = request.getInputStream();
			String model = Utils.inputStream2String(in, "");
			logger.info("-->[从综合系统收到的信息]:" + model);
			if (model != null && !"".equals(model)) {
				// ObjectMapper mapper = new ObjectMapper();
				// ReqData reqData = mapper.readValue(model, ReqData.class);
				JSONObject json = JSONObject.fromObject(model);
				String opType = json.getString("opType");// 操作名
				String jsonData = json.getString("reqdata");
				// logger.info("收到的信息22="+Utils.getFromBase64(reqdata));
				// String jsonData =
				// Utils.getFromBase64(reqdata);//先请求参数进行base64解密
				if ("TMRI_CALLOUT".equals(opType)) {// 叫号
					OverCallJson overCallJson = new ServerUtils().callNumber(
							jsonData, serverService);
					if (overCallJson != null) {
						// String jsondata = JacksonUtil.toJson(overCallJson);
						result.setRespCode("200");
						result.setRespData(overCallJson);
						result.setRespMsg("叫号接口响应成功");
					} else {
						RepeatCallJson repeatCallJson = new RepeatCallJson();
						repeatCallJson.setCode(1);
						repeatCallJson.setMessage("叫号接口响应成功，但无申请人取号");
						result.setRespCode("200");
						result.setRespData(repeatCallJson);
						result.setRespMsg("叫号接口响应成功");
					}
				} else if ("TMRI_RECALL".equals(opType)) {// 重复叫号
					// int code = new
					// ServerUtils().repeatCall(jsonData,serverService);
					OverCallJsonSkip overCallJson = new ServerUtils()
							.repeatCall(jsonData, serverService);
					if (overCallJson != null) {
						// repeatCallJson.setMessage("重复叫号成功");
						// String jsondata =
						// Utils.getBase64(JacksonUtil.toJson(repeatCallJson));
						result.setRespCode("200");
						result.setRespData(overCallJson);
						result.setRespMsg("重复叫号接口响应成功");
					} else {
						RepeatCallJson repeatCallJson = new RepeatCallJson();
						repeatCallJson.setCode(1);
						repeatCallJson.setMessage("重复叫号失败");
						result.setRespCode("200");
						result.setRespData(repeatCallJson);
						result.setRespMsg("重复叫号接口响应成功");
					}
				} else if ("TMRI_SKIP".equals(opType)) {// 过号
					OverCallJsonSkip overCallJson = new ServerUtils().overCall(
							jsonData, serverService);
					if (overCallJson != null) {
						// String jsondata =
						// Utils.getBase64(JacksonUtil.toJson(overCallJson));
						result.setRespCode("200");
						result.setRespData(overCallJson);
						result.setRespMsg("过号接口响应成功");
					} else {
						RepeatCallJson repeatCallJson = new RepeatCallJson();
						repeatCallJson.setCode(1);
						repeatCallJson.setMessage("过号接口响应成功，但无申请人取号");
						result.setRespCode("200");
						result.setRespData(repeatCallJson);
						result.setRespMsg("过号接口响应成功");
					}
				} else if ("TMRI_EVALUATION".equals(opType)) {// 提请评价
					int code = new ServerUtils().sponsorappraise(jsonData,
							serverService);
					RepeatCallJson repeatCallJson = new RepeatCallJson();
					repeatCallJson.setCode(code);
					if (code == 1) {
						repeatCallJson.setMessage("提请评价成功");
						// String jsondata =
						// Utils.getBase64(JacksonUtil.toJson(repeatCallJson));
						result.setRespCode("200");
						result.setRespData(repeatCallJson);
						result.setRespMsg("提请评价接口响应成功");
					} else {
						repeatCallJson.setMessage("提请评价失败");
					}
				} else if ("TMRI_SUSPEND".equals(opType)) {// 暂停叫号
					int code = new ServerUtils().stop(jsonData, serverService);
					RepeatCallJson repeatCallJson = new RepeatCallJson();
					repeatCallJson.setCode(code);
					if (code == 1) {
						repeatCallJson.setMessage("暂停成功");
						// String jsondata =
						// Utils.getBase64(JacksonUtil.toJson(repeatCallJson));
						result.setRespCode("200");
						result.setRespData(repeatCallJson);
						result.setRespMsg("暂停接口响应成功");
					} else {
						repeatCallJson.setMessage("暂停失败失败");
					}
				} else if ("TMRI_RECOVER".equals(opType)) {// 恢复叫号
					int code = new ServerUtils().recover(jsonData,
							serverService);
					RepeatCallJson repeatCallJson = new RepeatCallJson();
					repeatCallJson.setCode(code);
					if (code == 1) {
						repeatCallJson.setMessage("暂停成功");
						// String jsondata =
						// Utils.getBase64(JacksonUtil.toJson(repeatCallJson));
						result.setRespCode("200");
						result.setRespData(repeatCallJson);
						result.setRespMsg("暂停接口响应成功");
					} else {
						repeatCallJson.setMessage("暂停失败失败");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("在六合一接口发生异常：", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		logger.info("-->[叫号流程结果处理信息:]" + JSON.toJSONString(result));
		return result;
	}

	/**
	 * 怎么发送，怎么解析问题
	 * 
	 * @param ip
	 * @return
	 */
	@Description("叫号评价备案信息读取接口")
	@RequestMapping("getBeiAnInfo")
	@ResponseBody
	public DoResult getBeiAnInfo(HttpServletRequest request,
			HttpServletResponse response) {
		DoResult result = new DoResult();
		try {
			String ip = "127.0.0.1";
			String xmlString = GetXmlString.firstXmlString(ip);
			String url = "";
			String responStr = PostXml.postServer(url, xmlString);
			Document dom = DocumentHelper.parseText(responStr);
			Element root = dom.getRootElement();
			String bodyRsp = root.element("queue").getText();
			Document dom1 = DocumentHelper.parseText(bodyRsp);
			Element root2 = dom1.getRootElement();
			String sbkzjsjbh = root2.element("sbkzjsjbh").getText();
			String jsjlb = root2.element("jsjlb").getText();
			String jsjip = root2.element("jsjip").getText();
			String ckbh = root2.element("ckbh").getText();
			String kbywlb = root2.element("kbywlb").getText();
			if (kbywlb == null || "".equals(kbywlb)) {
				kbywlb = "0";
			}
			BeiAnInfoJson beiAnInfo = new BeiAnInfoJson();
			beiAnInfo.setSbkzjsjbh(sbkzjsjbh);
			beiAnInfo.setJsjlb(jsjlb);
			beiAnInfo.setJsjip(jsjip);
			beiAnInfo.setCkbh(ckbh);
			beiAnInfo.setKbywlb(kbywlb);
			beiAnInfo.setControlip(ip);
			serverService.insertBeiAnInfo(beiAnInfo);
			/*
			 * BeiAnInfoJson beiAnInfo = new BeiAnInfoJson();
			 * beiAnInfo.setSbkzjsjbh("1234569999"); beiAnInfo.setJsjlb("1");
			 * beiAnInfo.setJsjip("10.1.4.25"); beiAnInfo.setCkbh("01");
			 * beiAnInfo.setKbywlb("01"); beiAnInfo.setControlip(ip);
			 * serverService.insertBeiAnInfo(beiAnInfo);
			 */
			result.setStateType(DoResultType.success);
			result.setStateMsg("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("保存失败");
			logger.error("在叫号评价备案信息读取接口的时候发生异常：", e);
		}
		return result;
	}

	@Description("叫号接口")
	@RequestMapping("callNumber")
	@ResponseBody
	public DoResult callNumber(HttpServletRequest request,
			HttpServletResponse response, String ywckjsjip) {
		DoResult result = new DoResult();
		// String ywckjsjip = "10.1.4.25";
		// String glbm = "555";
		try {
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
			WXSquenceInfo wXSquenceInfo = serverService.getCallSquence(
					nowtimequan, 1, 1, dateString, beiAnInfoJson.getDeptid(),
					beiAnInfoJson.getKbywlb().replace("0", "")
							.replace("#", ","));// 获取有没有早到的
			String type = beiAnInfoJson.getKbywlb().replace("0", "")
					.replace("#", ",");
			if (wXSquenceInfo == null) {
				wXSquenceInfo = serverService.getCallSquence(nowtimequan, 1, 0,
						dateString, beiAnInfoJson.getDeptid(), type);// 获取预约的
				if (wXSquenceInfo == null) {
					wXSquenceInfo = serverService.getCallSquence(null, 0, null,
							dateString, beiAnInfoJson.getDeptid(), type);// 获取现场号
					if (wXSquenceInfo == null) {
						wXSquenceInfo = serverService.getCallSquence(null, 1,
								1, dateString, beiAnInfoJson.getDeptid(), type);// 获取有没有早到的
					}
				}
			}
			SquenceConfig squenceConfig = serverService.getSquenceConfig(Utils
					.getParamsByKey("deptid"));
			int overmin = squenceConfig.getMinutes();// 现场号等待超时时间
			if (wXSquenceInfo != null) {
				if (squenceConfig.getIsopen() == 1
						&& wXSquenceInfo.getIsappointment() == 1) {// 开启了并且取出的是预约号
					WXSquenceInfo wXSquenceInfoXc = serverService
							.getCallSquence(null, 0, null, dateString,
									beiAnInfoJson.getDeptid(), beiAnInfoJson
											.getKbywlb().replace("0", "")
											.replace("#", ","));// 获取现场号
					if (wXSquenceInfoXc != null) {
						if (Utils.isOver30(wXSquenceInfoXc.getCreatetime(),
								overmin)) {// 判断这个现场号是否等待超过30分钟
							if (Utils.isEarly(wXSquenceInfo.getCreatetime(),
									wXSquenceInfoXc.getCreatetime())) {// 现场号更早
								logger.info("====调整得到的号");
								wXSquenceInfo = wXSquenceInfoXc;
							}
						}
					}
				}
			}
			/*
			 * 把该窗口的上一条叫号搜出来，更改叫号状态为“非叫号状态”， 按熊起说的，“我不知道业务是什么时候完成办理的，
			 * 索性在这里修改状态，其实也可以写进任务模式”
			 */
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
				result.setStateMsg("兄台,找到的号码是:" + wXSquenceInfo.getSquence());
				System.out.println("-->[找到的号码是]" + wXSquenceInfo.getSquence());
				// 准备好返回的信息[overCallJson]，这里据说是预备的，不知道用处，先注释
				/*
				 * OverCallJson overCallJson = new OverCallJson();
				 * overCallJson.setSbkzjsjip(beiAnInfoJson.getControlip());
				 * overCallJson.setQhxxxlh(wXSquenceInfo.getSerialnumber());
				 * overCallJson.setPdh(wXSquenceInfo.getSquence());
				 * overCallJson.setYwlb("0" + wXSquenceInfo.getBusinessType());
				 * overCallJson.setSfzmhm(wXSquenceInfo.getCardnumber());
				 * overCallJson.setQhrxm(wXSquenceInfo.getName());
				 * overCallJson.setQhsj(wXSquenceInfo.getCreatetime());//
				 * 是否是这个格式 overCallJson.setRylb(String.valueOf(wXSquenceInfo
				 * .getGetNumberType()));
				 */
				// 保存叫号记录
				// serverService.insertSquenceOrder(ywckjsjip,wXSquenceInfo.getSerialnumber());
				// 更新本条队列信息的状态，序列号，窗口号，叫号时间，led显示
				serverService.updateSquenceInfo(ywckjsjip,
						wXSquenceInfo.getSerialnumber(), wXSquenceInfo.getId(),
						beiAnInfoJson.getCkbh(), wXSquenceInfo.getSquence(),
						beiAnInfoJson.getDeptid(), 2, 0);
				// 更新内外状态同步表
				// serverService.updateSquenceSNYC(wXSquenceInfo.getId(),2,0);
				// led屏幕显示和叫号（调取叫号机的接口）
			} else {// 如果是空就记录本次挂起记录
				System.out.println("==========保存本次未取到号码信息");
				SupplementRecord supplementRecord = new SupplementRecord();
				supplementRecord.setYwckjsjip(ywckjsjip);
				supplementRecord.setSbkzjsjip(beiAnInfoJson.getControlip());
				supplementRecord.setKbywlb(beiAnInfoJson.getKbywlb()
						.replace("0", "").replace("#", ","));
				supplementRecord.setIswrite(0);
				supplementRecord.setDeptid(beiAnInfoJson.getDeptid());
				serverService.insertSupplementRecord(supplementRecord);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("叫号失败");
			logger.error("在叫号的时候发生异常：", e);
		}
		return result;
	}

	/**
	 * 怎么发送，怎么解析问题
	 * 
	 * @param
	 * @return
	 */
	@Description("重复叫号接口")
	@RequestMapping("repeatCall")
	@ResponseBody
	public DoResult repeatCall(String qhxxxlh) {
		DoResult result = new DoResult();
		// String qhxxxlh = "";//解析传过来的数据获取
		System.out.println("序列号是=" + qhxxxlh);
		try {
			// 控制叫号机叫号??
			// serverService.updateReatCall(qhxxxlh);//更新状态为重复叫号
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("保存失败");
			logger.error("在重复叫号的时候发生异常：", e);
		}
		return result;
	}

	/**
	 * 怎么发送，怎么解析问题
	 * 
	 * @param
	 * @return
	 */
	/*
	 * @Description("过号接口")
	 * 
	 * @RequestMapping("overCall")
	 * 
	 * @ResponseBody public DoResult overCall(String qhxxxlh) { DoResult result
	 * = new DoResult(); try { //String qhxxxlh = "";//解析传过来的数据获取 WXSquenceInfo
	 * wXSquenceInfo = serverService.getSquenceInfoBySerialNumber(qhxxxlh);
	 * OverCallJson overCallJson = new OverCallJson();
	 * overCallJson.setYwckjsjip(wXSquenceInfo.getDeviceip());
	 * overCallJson.setSbkzjsjip(wXSquenceInfo.getControlip());
	 * overCallJson.setQhxxxlh(wXSquenceInfo.getSerialnumber());
	 * overCallJson.setPdh(wXSquenceInfo.getSquence());
	 * overCallJson.setYwlb("0"+wXSquenceInfo.getBusinessType());
	 * overCallJson.setSfzmhm(wXSquenceInfo.getCardnumber());
	 * overCallJson.setQhrxm(wXSquenceInfo.getName());
	 * overCallJson.setQhsj(wXSquenceInfo.getCreatetime());//是否是这个格式的时间
	 * overCallJson.setRylb(String.valueOf(wXSquenceInfo.getGetNumberType()));
	 * 
	 * serverService.updateSquenceOver(wXSquenceInfo.getId(),3,0);
	 * //serverService.updateSquenceSNYC(wXSquenceInfo.getId(),3,0);
	 * serverService.updateLedShow(wXSquenceInfo.getDeptid(),wXSquenceInfo.
	 * getSquence(),5); } catch (Exception e) { e.printStackTrace();
	 * result.setStateType(DoResultType.fail); result.setStateMsg("过号失败");
	 * logger.error("在过号的时候发生异常：",e); } return result; }
	 */

	/**
	 * 怎么发送，怎么解析问题，怎么发起评价，评价结果怎么写入
	 * 
	 * @param
	 * @return
	 */
	@Description("提请评价接口")
	@RequestMapping("sponsorappraise")
	@ResponseBody
	public DoResult sponsorappraise(String qhxxxlh) {
		DoResult result = new DoResult();
		try {
			// String qhxxxlh = " ";//解析传过来的数据获取
			WXSquenceInfo wXSquenceInfo = serverService
					.getWindowBySerialNumber(qhxxxlh);
			serverService.insertWaitAppraise(wXSquenceInfo.getId(),
					wXSquenceInfo.getWicketnumber(), qhxxxlh);// 插入前把该窗口之前没有评价的记录更新为跳过
			serverService.updateSquenceFinish(wXSquenceInfo.getId());
			serverService.updateSquenceSNYC(wXSquenceInfo.getId(), 6, 0);
			serverService.updateLedShow(wXSquenceInfo.getDeptid(),
					wXSquenceInfo.getSquence(), 5);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("保存失败");
			logger.error("在重复叫号的时候发生异常：", e);
		}
		return result;
	}

	/*
	 * @Description("提请评价接口")
	 * 
	 * @RequestMapping("check")
	 * 
	 * @ResponseBody public DoResult check() { DoResult result = new DoResult();
	 * try { ModelJson modelJson = new ModelJson();
	 * modelJson.setCardNumber("360122199309154232"); modelJson.setThanType(1);
	 * Map<String,String> param = new HashMap<String,String>();
	 * param.put("apiauthtoken", "E7C5EFFD9806F85141188C47986B6FC5");
	 * param.put("modeljson", JSONObject.fromObject(modelJson).toString());
	 * String backResult = Utils.sendPost1(
	 * "http://192.168.0.6:9686/KeyPersonnelThan/api/getPersonnelThan", param);
	 * JSONObject backResultJson =JSONObject.fromObject(backResult);
	 * logger.info("现场预警比对返回的结果："+backResultJson); } catch (Exception e) {
	 * e.printStackTrace(); result.setStateType(DoResultType.fail);
	 * result.setStateMsg("保存失败"); logger.error("在重复叫号的时候发生异常：",e); } return
	 * result; }
	 */

	/**
	 * 是否是我控制窗口LED显示屏 怎么发送，怎么解析问题
	 * 
	 * @param
	 * @return
	 */
	/*
	 * @Description("暂停叫号/恢复接口")
	 * 
	 * @RequestMapping("stopOrRecover")
	 * 
	 * @ResponseBody public DoResult stopOrRecover() { DoResult result = new
	 * DoResult(); try { String ywckjsjip = "10.1.4.25";//通过解析获得
	 * serverService.changeWinStatu(ywckjsjip); //控制led屏幕显示信息 } catch (Exception
	 * e) { e.printStackTrace(); result.setStateType(DoResultType.fail);
	 * result.setStateMsg("保存失败"); logger.error("在重复叫号的时候发生异常：",e); } return
	 * result; }
	 */

	/**
	 * 写入补充取号信息
	 */
	public void writeSupplementRecord() {
		try {
			Integer deptid = 47;// 从配置文件读取
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(currentTime);
			List<SupplementRecord> supplementRecord = serverService
					.getSupplementRecord(dateString, deptid);
			if (supplementRecord != null) {// 语句需要改
				WXSquenceInfo wXSquenceInfo = null;
				String ywckjsjip = null;
				Long supplementRecordid = null;
				List<OrderCount> orderCount = serverService.getAllTime();
				Calendar c = Calendar.getInstance();
				int now = c.get(Calendar.HOUR_OF_DAY);
				int nowtimequan = Utils.getTimqun(now, orderCount);// 处于哪个时间段，把该时间段的id返回来
				for (int i = 0; i < supplementRecord.size(); i++) {// 找到一条就跳出，一次只补写一条
					wXSquenceInfo = serverService.getCallSquence(nowtimequan,
							1, 0, dateString, deptid, supplementRecord.get(i)
									.getKbywlb());// 获取预约的
					if (wXSquenceInfo == null) {
						wXSquenceInfo = serverService.getCallSquence(null, 0,
								null, dateString, deptid,
								supplementRecord.get(i).getKbywlb());// 获取现场号
					}
					if (wXSquenceInfo != null) {
						ywckjsjip = supplementRecord.get(i).getYwckjsjip();
						supplementRecordid = supplementRecord.get(i).getId();
						break;
					}
				}
				// 将这条信息写入综合系统
				if (wXSquenceInfo != null) {
					BeiAnInfoJson beiAnInfoJson = serverService
							.getDeviceNumber(ywckjsjip);// 获取计算机编号，用于生成序列号
					String serialNumber = "6位时间" + beiAnInfoJson.getSbkzjsjbh()
							+ wXSquenceInfo.getSquence();
					// 准备好返回的信息
					OverCallJson overCallJson = new OverCallJson();
					// overCallJson.setYwckjsjip(ywckjsjip);
					overCallJson.setSbkzjsjip(beiAnInfoJson.getControlip());
					overCallJson.setQhxxxlh(serialNumber);
					overCallJson.setPdh(wXSquenceInfo.getSquence());
					overCallJson.setYwlb("0" + wXSquenceInfo.getBusinessType());
					overCallJson.setSfzmhm(wXSquenceInfo.getCardnumber());
					overCallJson.setQhrxm(wXSquenceInfo.getName());
					overCallJson.setQhsj(wXSquenceInfo.getCreatetime());// 是否是这个格式
					overCallJson.setRylb(String.valueOf(wXSquenceInfo
							.getGetNumberType()));
					if ("1".equals("code")) {// 如果成功
						// 更新本条队列信息的状态，序列号，窗口号，叫号时间,这条记录的状态改为几?????????
						serverService.updateSquenceAndSupplementRecord(
								supplementRecordid, wXSquenceInfo.getId(),
								serialNumber, beiAnInfoJson.getCkbh(), 3);// 返回写入成功，修改相应状态
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("在重复叫号的时候发生异常：", e);
		}
	}

}

package com.jrwp.wx.action;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Encoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.wx.entity.AppConfig;
import com.jrwp.wx.entity.BeiAnInfoJson;
import com.jrwp.wx.entity.EvaluateSetting;
import com.jrwp.wx.entity.OrderCount;
import com.jrwp.wx.entity.Parm;
import com.jrwp.wx.entity.ParmLoginModelJson;
import com.jrwp.wx.entity.PersonInfo;
import com.jrwp.wx.entity.SquenceConfig;
import com.jrwp.wx.entity.SquenceHelper;
import com.jrwp.wx.entity.UserInfo;
import com.jrwp.wx.entity.WXSquenceInfo;
import com.jrwp.wx.service.AppointmeInfoService;
import com.jrwp.wx.service.ClientService;
import com.jrwp.wx.service.ServerService;
import com.jrwp.wx.service.SquenceInfoService;
import com.jrwp.wx.until.Utils;

@Description("客户端接口")
@Controller
@RequestMapping("client/clientController")
public class ClientController {
	private final Logger logger = Logger.getLogger(ClientController.class);

	@Resource
	private ClientService clientService;

	@Resource
	private ServerService serverService;

	@Resource
	private SquenceInfoService squenceInfoService;

	@Resource
	private AppointmeInfoService appointmeInfoService;

	@Description("客户端登录")
	@RequestMapping(value = "userlogin", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String userlogin(HttpServletRequest request,
			HttpServletResponse response) {
		DoResult result = new DoResult();
		InputStream in = null;
		try {
			in = request.getInputStream();
			String model = Utils.inputStream2String(in, "");
			if (model != null && !"".equals(model)) {
				BASE64Encoder encoder = new BASE64Encoder();
				ObjectMapper mapper = new ObjectMapper();
				Parm parmString = mapper.readValue(model, Parm.class);
				ParmLoginModelJson loginJson = JacksonUtil.readValue(
						parmString.getModeljson(), ParmLoginModelJson.class);
				if (loginJson.getUserName() != null
						&& !"".equals(loginJson.getUserName())
						&& loginJson.getPassWord() != null
						&& !"".equals(loginJson.getPassWord())) {
					PersonInfo personInfo = clientService.getJYInfo(
							loginJson.getUserName(), loginJson.getPassWord());
					if (personInfo != null) {
						AppConfig appConfig = clientService
								.getWinNumMachineCode(loginJson.getMachineIP());
						UserInfo userInfo = new UserInfo();
						userInfo.setID(personInfo.getId());
						userInfo.setDeptid(personInfo.getDeptID());
						userInfo.setUserName(personInfo.getAccount());
						userInfo.setPassWord(personInfo.getPassword());
						userInfo.setPoliceName(personInfo.getName());
						userInfo.setCharge("0");
						userInfo.setIsStart("1");
						userInfo.setPoliceManCode(personInfo.getJynumber());
						userInfo.setPoliceManPhoto(encoder.encode(personInfo
								.getPhoto()));
						userInfo.setIsappr(1);
						userInfo.setIstakephoto(2);
						userInfo.setWindowNum(appConfig.getWinNum());
						userInfo.setPayType(1 + "");
						userInfo.setOrderDescribe("南昌车管所");
						userInfo.setIsInterface(2 + "");
						userInfo.setIsAppointme(1);
						userInfo.setIsTianJian(0);
						userInfo.setAppointmeUrl(Utils
								.getParamsByKey("AppointmeUrl"));
						userInfo.setFiring(2);
						userInfo.setSystitle(appConfig.getSystitle());
						userInfo.setSysunit(appConfig.getSysunit());
						result.setStateValue(JacksonUtil.toJson(userInfo));
						result.setStateMsg("登陆成功");
						result.setStateType(DoResultType.success);
					} else {
						result.setStateMsg("账号或密码错误");
						result.setStateType(DoResultType.validFail);
					}
				} else {
					result.setStateMsg("账号或密码为空");
					result.setStateType(DoResultType.warning);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("登陆失败");
			logger.error("在客户端登陆的时候发生异常：", e);
		}
		return JacksonUtil.toJson(result);
	}

	@Description("客户端评价")
	@RequestMapping(value = "saveAppraise", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String saveAppraise(HttpServletRequest request,
			HttpServletResponse response) {
		DoResult result = new DoResult();
		InputStream in = null;
		try {
			in = request.getInputStream();
			String model = Utils.inputStream2String(in, "");
			if (model != null) {
				ObjectMapper mapper = new ObjectMapper();
				Parm parmString = mapper.readValue(model, Parm.class);
				JSONObject json = JSONObject.fromObject(parmString
						.getModeljson());
				int isdefault = 1;
				long userid = json.getLong("ID");
				boolean autoEvaluate = json.getBoolean("autoEvaluate");
				if (autoEvaluate) {
					isdefault = 0;
				}
				long evaluateID = json.getLong("evaluateID");
				long squence_infoid = json.getLong("orderNumber");
				clientService.insertAppraise(userid, isdefault, evaluateID,
						squence_infoid);
				result.setStateType(DoResultType.success);
				result.setStateMsg("保存成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("保存失败");
			logger.error("在保存客户端评价信息时候发生异常：", e);
		}
		return JacksonUtil.toJson(result);
	}

	@Description("客户端获取评价参数")
	@RequestMapping(value = "setEvaluate", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String setEvaluate(HttpServletRequest request,
			HttpServletResponse response) {
		DoResult result = new DoResult();
		InputStream in = null;
		try {
			in = request.getInputStream();
			String model = Utils.inputStream2String(in, "");
			if (model != null) {
				// ObjectMapper mapper = new ObjectMapper();
				// Parm parmString = mapper.readValue(model, Parm.class);
				// ParmLoginModelJson loginJson = JacksonUtil.readValue(
				// parmString.getModeljson(), ParmLoginModelJson.class);
				EvaluateSetting evaluateSetting = clientService
						.getEvaluateSetting();// 要和部门关联
				if (evaluateSetting != null) {
					if (evaluateSetting.getEvaluateList() != null
							&& evaluateSetting.getEvaluateList().size() > 0) {
						for (int i = 0; i < evaluateSetting.getEvaluateList()
								.size(); i++) {
							int isdefault = evaluateSetting.getEvaluateList()
									.get(i).getIsdefault();
							if (isdefault == 1) {
								evaluateSetting.setAutoEvaluateID(String
										.valueOf(evaluateSetting
												.getEvaluateList().get(i)
												.getEvaluateID()));
							}
						}
					}
				}
				result.setStateValue(JacksonUtil.toJson(evaluateSetting));
				result.setStateType(DoResultType.success);
				result.setStateMsg("获取成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("获取失败");
			logger.error("在客户端获取评价参数的候发生异常：", e);
		}
		logger.info("返回的参数：" + result);
		return JacksonUtil.toJson(result);
	}

	/**
	 * 获取下一个排队号
	 * 
	 * @param request
	 * @param apiauthtoken
	 * @param deptid
	 * @param response
	 * @param winNumber
	 * @param id
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "getNextSquence", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getNextSquence(HttpServletRequest request,
			String apiauthtoken, Integer deptid, HttpServletResponse response,
			Integer winNumber, Integer id, Integer type) {
		// woshi DB848A594A30BB02BD80E20240D479EB
		/*
		 * String apiauthtoken = "DB848A594A30BB02BD80E20240D479EB"; Integer
		 * deptid = (Integer)1; Integer winNumber = (Integer)2; Integer id =
		 * (Integer)1232; Integer type = 4;
		 */
		String code = DigestUtils.md5Hex("KTHWXAPPOINTME").toUpperCase();
		SquenceHelper squence = new SquenceHelper();
		DoResult result = new DoResult();
		if (winNumber == null) {
			winNumber = 1;
		}
		if (apiauthtoken != null && apiauthtoken.equals(code)) {
			try {
				// 先把传过来的ID，对应数据状态改为4 或者 3
				// id为null就是叫号，不为null是过号
				if (id != null) {
					serverService.updateSquenceOver((long) id, 3, 0);
				}
				BeiAnInfoJson beiAnInfoJson = clientService
						.getDeviceNumber(winNumber + "");// 获取计算机编号，用于生成序列号
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
						nowtimequan, 1, 1, dateString,
						beiAnInfoJson.getDeptid(), beiAnInfoJson.getKbywlb()
								.replace("0", "").replace("#", ","));// 获取有没有早到的
				if (wXSquenceInfo == null) {
					wXSquenceInfo = serverService.getCallSquence(
							nowtimequan,
							1,
							0,
							dateString,
							beiAnInfoJson.getDeptid(),
							beiAnInfoJson.getKbywlb().replace("0", "")
									.replace("#", ","));// 获取预约的
					if (wXSquenceInfo == null) {
						wXSquenceInfo = serverService.getCallSquence(null, 0,
								null, dateString, beiAnInfoJson.getDeptid(),
								beiAnInfoJson.getKbywlb().replace("0", "")
										.replace("#", ","));// 获取现场号
						if (wXSquenceInfo == null) {
							wXSquenceInfo = serverService.getCallSquence(null,
									1, 1, dateString,
									beiAnInfoJson.getDeptid(), beiAnInfoJson
											.getKbywlb().replace("0", "")
											.replace("#", ","));// 获取有没有早到的
						}
					}
				}
				// logger.info("原本取出的现场号是="+wXSquenceInfo.getSquence());
				// 还没有建表
				SquenceConfig squenceConfig = serverService
						.getSquenceConfig(Utils.getParamsByKey("deptid"));
				int overmin = squenceConfig.getMinutes();// 现场号等待超时时间
				if (wXSquenceInfo != null) {
					if (squenceConfig.getIsopen() == 1
							&& wXSquenceInfo.getIsappointment() == 1) {// 开启了并且取出的是预约号
						WXSquenceInfo wXSquenceInfoXc = serverService
								.getCallSquence(
										null,
										0,
										null,
										dateString,
										beiAnInfoJson.getDeptid(),
										beiAnInfoJson.getKbywlb()
												.replace("0", "")
												.replace("#", ","));// 获取现场号
						// logger.info("取出的现场号是="+wXSquenceInfoXc.getSquence());
						if (wXSquenceInfoXc != null) {
							if (Utils.isOver30(wXSquenceInfoXc.getCreatetime(),
									overmin)) {// 判断这个现场号是否等待超过30分钟
								if (Utils.isEarly(
										wXSquenceInfo.getCreatetime(),
										wXSquenceInfoXc.getCreatetime())) {// 现场号更早
									wXSquenceInfo = wXSquenceInfoXc;
								}
							}
						}
					}
				}
				// 把该窗口的上一条叫号搜出来
				WXSquenceInfo lastSquenceInfo = serverService
						.getLastSerialNumber(beiAnInfoJson.getJsjip(),
								dateString);
				if (lastSquenceInfo != null) {
					// 把内外状态同步表状态修改,squence_info状态修改
					if (lastSquenceInfo.getStatus() != 3
							&& lastSquenceInfo.getStatus() != 6) {
						serverService.updateSquenceSNYC(
								lastSquenceInfo.getId(), 6, 0);
						serverService.updateSquenceStatus(
								lastSquenceInfo.getId(), 6);
					}
				}
				if (wXSquenceInfo != null) {
					int localCount = squenceInfoService.getLocalCount(
							new Date(), deptid, beiAnInfoJson.getKbywlb()
									.replace("0", "").replace("#", ","));
					squence.setLocalCount(localCount - 1);
					squence.setId(new Long(wXSquenceInfo.getId()).intValue());
					squence.setSquence(wXSquenceInfo.getSquence());
					squence.setName(wXSquenceInfo.getName());
					result.setStateMsg("-->[找到的号码是]:"
							+ wXSquenceInfo.getSquence());
					// 保存叫号记录
					// serverService.insertSquenceOrder(beiAnInfoJson.getJsjip(),wXSquenceInfo.getSerialnumber());
					// 保存叫号记录,更新内外状态同步表,更新本条队列信息的状态，序列号，窗口号，叫号时间，led显示
					serverService.updateSquenceInfo(beiAnInfoJson.getJsjip(),
							wXSquenceInfo.getSerialnumber(),
							wXSquenceInfo.getId(), beiAnInfoJson.getCkbh(),
							wXSquenceInfo.getSquence(),
							beiAnInfoJson.getDeptid(), 2, 0);
					// 更新内外状态同步表
					// serverService.updateSquenceSNYC(wXSquenceInfo.getId(),2,0);
				} else {
					squence.setId(0);
					squence.setSquence("0");
					squence.setLocalCount(0);
					squence.setName("");
				}
				result.setStateType(DoResultType.success);
				result.setStateValue(JacksonUtil.toJson(squence));
				result.setStateMsg("获取成功");
				System.out.println(JacksonUtil.toJson(result));
			} catch (Exception e) {
				e.printStackTrace();
				result.setStateType(DoResultType.fail);
				result.setStateMsg("接口出现异常");
				logger.error("在客户端取号的时候发生异常：", e);
			}
		} else {
			result.setStateType(DoResultType.fail);
			result.setStateMsg("令牌错误");
		}
		return JacksonUtil.toJson(result);
	}

	/**
	 * 重复、到号以及过号接口
	 * 
	 * @param request
	 * @param apiauthtoken
	 * @param id
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "recall", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String recall(HttpServletRequest request, String apiauthtoken,
			Integer id, Integer type) {
		// type 1 重复 2 到号 3过号
		String code = DigestUtils.md5Hex("KTHWXAPPOINTME").toUpperCase();
		DoResult result = new DoResult();
		if (apiauthtoken != null && apiauthtoken.equals(code)) {
			try {
				if (id != null) {
					if (type == 1) {
						type = 5;
					} else if (type == 2) {
						type = 4;
					} else if (type == 3) {
						type = 3;
					}
					// SquenceInfo squenceInfo = squenceInfoService.g
					clientService.updateStatusByPrimaryKey(id, type);
					if (type == 5) {
						clientService.updateRecallTime(id);
					}
					if (type == 3) {
						serverService.updateSquenceSNYC((long) id, 3, 0);
						String winNumber = serverService.getwinNumberById(id);
						serverService.updateShowStatu(winNumber, 5, id);
						// serverService.updateLedShow(id,wXSquenceInfo.getSquence(),5);
					}
					/*
					 * Integer st= squenceInfoService.getseqSync(id); if (tyep
					 * == 5) { String t = st+""; type =
					 * Integer.valueOf(type+"0"+t.split("0")[1]);
					 * squenceInfoService.updateseqSync(id, type); } else {
					 * squenceInfoService.seqSync(id, type); }
					 */
				}
				result.setStateType(DoResultType.success);
				// result.setStateValue(JacksonUtil.toJson(squence));
				result.setStateMsg("获取成功");
				System.out.println(JacksonUtil.toJson(result));
			} catch (Exception e) {
				e.printStackTrace();
				result.setStateType(DoResultType.fail);
				result.setStateMsg("接口出现异常");
				logger.error("到号接口出现异常", e);
			}
		} else {
			result.setStateType(DoResultType.fail);
			result.setStateMsg("令牌错误");
		}
		System.out.println(JacksonUtil.toJson(result));
		return JacksonUtil.toJson(result);
	}

	/**
	 * 预约等待人数
	 * 
	 * @param request
	 * @param apiauthtoken
	 * @param deptid
	 * @return
	 */
	@RequestMapping(value = "getAppCount", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	@ResponseBody
	public String getAppCount(HttpServletRequest request, String apiauthtoken,
			Integer deptid) {
		String code = DigestUtils.md5Hex("KTHWXAPPOINTME").toUpperCase();
		DoResult result = new DoResult();
		// deptid = Integer.valueOf(Utils.getParamsByKey("deptid"));
		if (apiauthtoken != null && apiauthtoken.equals(code)) {
			try {
				int count = appointmeInfoService
						.getAppCount(new Date(), deptid);
				result.setStateType(DoResultType.success);
				result.setStateValue(count);
				result.setStateMsg("获取成功");
			} catch (Exception e) {
				result.setStateType(DoResultType.fail);
				result.setStateMsg("接口出现异常");
				e.printStackTrace();
			}
		} else {
			result.setStateType(DoResultType.fail);
			result.setStateMsg("令牌错误");
		}
		System.out.println(result);
		return JacksonUtil.toJson(result);
	}

	/**
	 * 现场排队人数
	 * 
	 * @param request
	 * @param apiauthtoken
	 * @param deptid
	 * @param winNumber
	 * @return
	 */
	@RequestMapping(value = "getLocalCount", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	@ResponseBody
	public String getLocalCount(HttpServletRequest request,
			String apiauthtoken, Integer deptid, String winNumber) {
		String code = DigestUtils.md5Hex("KTHWXAPPOINTME").toUpperCase();
		// SquenceHelper squence = null;
		DoResult result = new DoResult();
		// deptid = Integer.valueOf(Utils.getParamsByKey("deptid"));
		if (apiauthtoken != null && apiauthtoken.equals(code)) {
			try {
				BeiAnInfoJson beiAnInfoJson = clientService
						.getDeviceNumber(winNumber);
				int count = squenceInfoService.getLocalCount(new Date(),
						deptid, beiAnInfoJson.getKbywlb().replace("0", "")
								.replace("#", ","));
				result.setStateType(DoResultType.success);
				result.setStateValue(count);
				result.setStateMsg("获取成功");
			} catch (Exception e) {
				result.setStateType(DoResultType.fail);
				result.setStateMsg("接口出现异常");
				e.printStackTrace();
			}
		} else {
			result.setStateType(DoResultType.fail);
			result.setStateMsg("令牌错误");
		}
		// System.out.println(result);
		return JacksonUtil.toJson(result);
	}
}

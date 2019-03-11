package com.jrwp.wx.action;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jrwp.payMent.dao.SequenceInfoRecordDao;
import com.jrwp.payMent.entity.SequenceInfoRecord;
import com.jrwp.wx.entity.Sequence_Info;
import com.jrwp.wx.until.DesUtil;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Core_Dept;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.service.IDeptService;
import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.core.utils.LogUtil;
import com.jrwp.core.utils.WXUtil;
import com.jrwp.payMent.entity.Businessconfig;
import com.jrwp.payMent.entity.DicBusinessnotice;
import com.jrwp.payMent.entity.PoliceClassService;
import com.jrwp.payMent.service.BusinessconfigService;
import com.jrwp.payMent.service.DicBusinessnoticeService;
import com.jrwp.payMent.service.IPoliceClassService;
import com.jrwp.wx.dao.Sequence_InfoDao;
import com.jrwp.wx.entity.RegisterUser;
import com.jrwp.wx.entity.SequenceInfoJson;
import com.jrwp.wx.service.RegisterUserService;

/**
 * 微信用户中心
 * 
 * @author hsl
 * 
 */
@Description(value = "微信用户接口", state = false)
@Controller
@RequestMapping("wxUserAction")
public class WxUserAction {

	@Resource
	private RegisterUserService registerUserService;
	@Resource
	private IDeptService deptService;
	@Resource
	private IPoliceClassService policeClassService;
	@Resource
	private DicBusinessnoticeService businessnoticeService;
	@Resource
	private BusinessconfigService businessconfigService;
	@Resource
	private Sequence_InfoDao sequence_InfoDao;
	@Resource
	private SequenceInfoRecordDao sequenceInfoRecordDao;

	// 乐合测试
	// private final String appId = "wx6d4d594ef75edec4";
	// private final String appSecret = "5827020e5fd8d8a037afb09232a29813";
	// 正式
	private final String appId = "wxee10f55b4da5a870";
	private final String appSecret = "14a79475156194dbc693f074a6dbd03f";

	@Description("获取微信code")
	@RequestMapping("getWxCode")
	public void getWxCode(HttpServletRequest request,
			HttpServletResponse response, Integer type, Long id, String sf) {
		Logger logger = Logger.getLogger(this.getClass());
		try {
			request.getSession().setAttribute("type", type);
			request.getSession().setAttribute("id", id);
			request.getSession().setAttribute("sf", sf);
			String redirectURL = URLEncoder.encode(
					"http://" + "traffic.happy791.com"
							+ "/payOut/wxUserAction/getopenId", "UTF-8");
			String OauthURL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
					+ appId
					+ "&redirect_uri="
					+ redirectURL
					+ "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
			response.sendRedirect(OauthURL);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("获取微信code时出现异常", e);
		}
	}

	@Description("获取openId")
	@RequestMapping("getopenId")
	public void GetOpenidAndAccessToken(HttpServletRequest request,
			HttpServletResponse response) {
		String openId = null;
		Logger logger = Logger.getLogger(this.getClass());
		try {
			String code = request.getParameter("code");
			String access_tokenToOpenIDUrils = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
					+ appId
					+ "&secret="
					+ appSecret
					+ "&code="
					+ code
					+ "&grant_type=authorization_code";
			String result1 = WXUtil.sendPost(access_tokenToOpenIDUrils, "");
			JSONObject json = JSONObject.fromObject(result1);
			openId = json.get("openid").toString();
			// openId = "oJJFXs_By9h93a4qfG2HebGUngLs";
			request.getSession().setAttribute("openId", openId);
			RegisterUser user = registerUserService.getObjectByOpenId(openId);
			if (user != null) {
				// Long id = (Long) request.getSession().getAttribute("id");
				// id = 282l;
				// if (id != null) {
				// user.setIsappoint(0);
				// user.setSeqid(id);
				// logger.info("接口[GetOpenidAndAccessToken]中获取到的序列id:" + id);
				// } else {
				// user.setIsappoint(1);
				// }
				registerUserService.updateUser(user);
				request.getSession().setAttribute("userInfo", user);
			} else {
				// Long id = (Long) request.getSession().getAttribute("id");
				RegisterUser registerUser = new RegisterUser();
				registerUser.setCreatetime(new Date());
				registerUser.setWxOpenId(openId);
				registerUser.setStatus(0);
				// if (id != null) {
				// registerUser.setIsappoint(0);
				// registerUser.setSeqid(id);
				// } else {
				// registerUser.setIsappoint(1);
				// }
				registerUserService.save(registerUser);
				request.getSession().setAttribute("userInfo", registerUser);
			}
			/**
			 * 判断是否绑定身份证号码
			 */
			Long id = (Long) request.getSession().getAttribute("id");
			// id = 282l;
			// String sf = DesUtil.decrypt((String)
			// request.getSession().getAttribute("sf"));
			if (id != null) {
				String sf = (String) request.getSession().getAttribute("sf");
				if (sf != null) {
					sf = DesUtil.decrypt(sf);
				}
				String card = registerUserService.getCardByOpenId(openId);
				if (card != null && !("").equals(card)) {
					if (card != sf) {
						// 查询结果显示页面
						response.setContentType("text/html;charset=gb2312");
						response.sendRedirect("sequenceInfofromOut?seqid=" + id);
					} else {
						// 查询结果显示页面
						response.setContentType("text/html;charset=gb2312");
						response.sendRedirect("sequenceInfofromOut?seqid=" + id);
					}
				} else {
					// 跳转至绑定页面
					response.setContentType("text/html;charset=gb2312");
					response.sendRedirect("goRegister?isappoint=false");
				}
			} else {
				response.setContentType("text/html;charset=gb2312");
				Integer type = (Integer) request.getSession().getAttribute(
						"type");
				if (type != null) {
					if (type == 1) {// 非扫码方式
						response.sendRedirect("goLayout");
					}
				} else {// 非扫码方式
					response.sendRedirect("goLayout");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("获取微信openid时出现异常", e);
		}
	}

	@Description("进入布局页")
	@RequestMapping("goLayout")
	public ModelAndView goLayout() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("index/layout");
		return maView;
	}

	@Description("进入主页")
	@RequestMapping("toIndex")
	public ModelAndView getIndex() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("index/index");
		return maView;
	}

	@Description("注册绑定页面")
	@RequestMapping("goRegister")
	public ModelAndView goRegister(Boolean isappoint) {
		ModelAndView maView = new ModelAndView();
		maView.addObject("isappoint", isappoint);
		maView.setViewName("wxuser/register");
		return maView;
	}

	@Description("保存预约用户信息")
	@RequestMapping("saveUserInfo")
	@ResponseBody
	public DoResult saveUserInfo(Long userid, String name, String card,
			String phone, HttpServletRequest request) {
		Logger logger = Logger.getLogger(this.getClass());
		DoResult result = new DoResult();
		RegisterUser user = new RegisterUser();
		try {
			user.setId(userid);
			user.setIdCardNumber(card);
			user.setUsername(name);
			user.setStatus(1);
			user.setPhoneNumber(phone);
			RegisterUser registerUser = new RegisterUser();
			if (card != null) {
				registerUser = registerUserService.checkObjectByCard(card);
			}
			if (registerUser != null) {
				result.setStateMsg("该身份证号码已经绑定，无法注册！");
				result.setStateType(DoResultType.fail);
			} else {
				registerUserService.updateUser(user);
				// 更新session用户信息
				RegisterUser sessionuser = (RegisterUser) request.getSession()
						.getAttribute("userInfo");
				sessionuser.setIdCardNumber(card);
				sessionuser.setUsername(name);
				sessionuser.setStatus(1);
				sessionuser.setPhoneNumber(phone);
				request.getSession().setAttribute("userInfo", sessionuser);
				result.setStateMsg("注册成功");
				result.setStateType(DoResultType.success);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("注册失败");
			result.setStateType(DoResultType.fail);
			logger.error("用户信息绑定失败:{}", e);
		}
		return result;
	}

	@Description("个人中心")
	@RequestMapping("usercenter")
	public ModelAndView usercenter() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("wxuser/usercenter");
		return maView;
	}

	@Description("个人信息")
	@RequestMapping("userInfo")
	public ModelAndView userInfo() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("wxuser/userinfo");
		return maView;
	}

	@Description("个人信息Json")
	@RequestMapping("userInfoJson")
	@ResponseBody
	public String userInfoJson(Long userid) {
		Logger logger = Logger.getLogger(this.getClass());
		DoResult result = new DoResult();
		try {
			RegisterUser user = registerUserService.getObjectById(userid);
			result.setStateMsg("获取信息成功");
			result.setStateType(DoResultType.success);
			result.setStateValue(user);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("接口异常");
			result.setStateType(DoResultType.fail);
			logger.error("个人信息接口异常", e);
		}
		return JacksonUtil.toJson(result);
	}

	@Description("预约成功页面")
	@RequestMapping("successView")
	public ModelAndView successView(Long deptId, String day, String time) {
		ModelAndView maView = new ModelAndView();
		try {
			Core_Dept dept = new Core_Dept();
			if (deptId != null) {
				dept = deptService.getObjectById(deptId);
			}
			maView.addObject("deptName", dept.getDeptName());
			maView.addObject("day", day.replaceAll("/", "-"));
			maView.addObject("time", time);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		maView.setViewName("wxuser/success");
		return maView;
	}

	@Description("办理须知")
	@RequestMapping("businessnotice")
	public ModelAndView businessnotice(String serCode, Integer businessType) {
		ModelAndView maView = new ModelAndView();
		DicBusinessnotice notice = new DicBusinessnotice();
		Businessconfig businessconfig = new Businessconfig();
		try {
			PoliceClassService classService = policeClassService
					.getObjectByCode(serCode);
			Long businessnoticeId = classService.getBusinessnoticeId();
			if (businessnoticeId == null) {
				businessnoticeId = 0l;
			}
			notice = businessnoticeService.getObjectById(businessnoticeId);
			businessconfig = businessconfigService.getObjectByCode(serCode);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		maView.addObject("notice", notice);
		maView.addObject("businessconfig", businessconfig);
		maView.addObject("serCode", serCode);
		maView.addObject("businessType", businessType);
		maView.setViewName("wxuser/notice");
		return maView;
	}

	@Description("当前排队信息查询")
	@RequestMapping("sequenceInfo")
	public ModelAndView SequenceInfo(String openid, Long seqid) {
		Logger logger = Logger.getLogger(this.getClass());
		logger.info("==========用户序列id:" + seqid);
		// LogUtil.info("用户序列id:{}", seqid);
		ModelAndView maView = new ModelAndView();
		maView.addObject("openid", openid);
		maView.addObject("seqid", seqid);
		maView.setViewName("wxuser/sequence");
		return maView;
	}

	@Description("外部链接排队信息查询")
	@RequestMapping("sequenceInfofromOut")
	public ModelAndView sequenceInfofromOut(String openid, Long seqid) {
		Logger logger = Logger.getLogger(this.getClass());
		logger.info("==========用户序列id:" + seqid);
		// LogUtil.info("用户序列id:{}", seqid);
		ModelAndView maView = new ModelAndView();
		maView.addObject("openid", openid);
		maView.addObject("seqid", seqid);
		maView.setViewName("wxuser/sequence_out");
		return maView;
	}

	/**
	 * 根据身份证查询排队信息
	 * 
	 * @param card
	 * @return
	 */
	@Description("排队信息Json")
	@RequestMapping("SequenceInfoJson")
	@ResponseBody
	public List<SequenceInfoJson> SequenceInfoJson(String card) {
		List<SequenceInfoJson> list = new ArrayList<>();
		try {
			if (card != null || !("").equals(card)) {
				list = sequence_InfoDao.listforSeqence(card);
				for (SequenceInfoJson json : list) {
					Integer status = json.getStatus();
					if (status != null) {
						if (status == 1) {// 等待叫号
							int peopleNumber = 0;
							if (json.getBusinessType() == 1) {
								json.setBusiness("综合业务");
								peopleNumber = sequence_InfoDao.peopleCount(
										json.getCreatetime(), card);
								if (peopleNumber == 0) {
									peopleNumber += 1;
								}
							} else if (json.getBusinessType() == 2) {
								json.setBusiness("代办业务");
								peopleNumber = sequence_InfoDao.peopleCount(
										json.getCreatetime(), card);
								if (peopleNumber == 0) {
									peopleNumber += 1;
								}
							} else if (json.getBusinessType() == 4) {
								json.setBusiness("疑难业务");
								peopleNumber = sequence_InfoDao
										.peopleCountPlus(json.getCreatetime(),
												card);
								if (peopleNumber == 0) {
									peopleNumber += 1;
								}
							}
							json.setPeopleNumber(peopleNumber);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LogUtil.info("接口[SequenceInfoJson]出现异常:{}", e.getMessage());
		}
		return list;
	}

	/**
	 * 根据队列主键获取排队信息
	 * 
	 * @param id
	 * @return
	 */
	@Description("排队信息Json1")
	@RequestMapping("sequenceInfoJsonById")
	@ResponseBody
	public List<SequenceInfoJson> sequenceInfoJsonById(Long id) {
		List<SequenceInfoJson> list = new ArrayList<>();
		System.out.println("==========接口[sequenceInfoJsonById]中获取到的队列id:" + id);
		try {
			if (id != null) {
				SequenceInfoJson infoJson = sequence_InfoDao
						.listforSequenceById(id);
				infoJson.setDay(infoJson.getCreatetime().substring(0, 10));
				int peopleCount = 0;
				infoJson.setPeopleNumber(peopleCount);
				if (infoJson.getBusinessType() == 1) {
					infoJson.setBusiness("综合业务");
					peopleCount = sequence_InfoDao.peopleCountById(
							infoJson.getCreatetime(), id);
					if (peopleCount == 0) {
						peopleCount += 1;
					}
				} else if (infoJson.getBusinessType() == 2) {
					infoJson.setBusiness("代办业务");
					peopleCount = sequence_InfoDao.peopleCountById(
							infoJson.getCreatetime(), id);
					if (peopleCount == 0) {
						peopleCount += 1;
					}
				} else if (infoJson.getBusinessType() == 4) {
					infoJson.setBusiness("疑难业务");
					peopleCount = sequence_InfoDao.peopleCountByIdPlus(
							infoJson.getCreatetime(), id);
					if (peopleCount == 0) {
						peopleCount += 1;
					}
				}
				list.add(infoJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.info("接口[sequenceInfoJsonById]出现异常:{}", e.getMessage());
		}
		return list;
	}

	@Description("网点列表")
	@RequestMapping("addressList")
	public ModelAndView addressList() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("wxuser/address");
		return maView;
	}

	public static void main(String[] args) {
		// try {
		// String str = null;
		// System.out.println(DesUtil.decrypt(str));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		String time = "2018-07-13 14:00:34.0";
		System.out.println(time.substring(0, 10));
	}
}

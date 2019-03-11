package com.jrwp.wx.action;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Core_Dept;
import com.jrwp.core.service.IDeptService;
import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.core.utils.LogUtil;
import com.jrwp.core.utils.MsgManager;
import com.jrwp.follow.Until.ComUntil;
import com.jrwp.follow.dao.DeptPayConfigDao;
import com.jrwp.follow.entity.PayConfig;
import com.jrwp.wx.entity.AppointmeInfo;
import com.jrwp.wx.entity.SquenceInfo;
import com.jrwp.wx.service.AppointmeInfoService;
import com.jrwp.wx.service.SquenceInfoService;
import com.jrwp.wx.until.OpenidUtil;
import com.jrwp.wx.until.TimequantumUntil;

@Description("队列信息管理")
@Controller
@RequestMapping("squenceInfoAction")
public class SquenceInfoAction {

	@Resource
	private AppointmeInfoService appointmeInfoService;

	@Resource
	private SquenceInfoService squenceInfoService;

	@Resource
	private DeptPayConfigDao deptPayConfigDao;

	@Resource
	private IDeptService deptService;

	@RequestMapping("gzSigninIndex")
	public ModelAndView gzSigninIndex(Integer deptid, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		request.getSession().setAttribute("deptid", deptid);
		// 赣州国投的中转页面
		String url = "http://gzgawjw.jxgzga.gov.cn/GZWJW/Module/HZ/HZID.aspx";
		String redirect = "http://" + ComUntil.COM
				+ "/paymentOut/squenceInfoAction/gzGetBack";
		modelAndView.setViewName("redirect:" + url);
		modelAndView.addObject("redirect", URLEncoder.encode(redirect));
		return modelAndView;
	}

	@RequestMapping("gzGetBack")
	public ModelAndView gzGetBack(String userinfo, HttpServletRequest request) {
		System.out.println("进入gzGetBack");
		ModelAndView modelAndView = new ModelAndView();
		try {
			System.out.println("==========================" + userinfo
					+ "======================================");
			Core_Dept dept = deptService.getObjectById(543);
			request.getSession().setAttribute("deptname", dept.getDeptName());
			if (userinfo != null) {
				JSONObject jsonObject = JSONObject.fromObject(userinfo);
				String openid = jsonObject.get("openid").toString();
				System.out.println("别人给的OPENID=-=====" + openid);
				System.out.println("====================================");
				System.out.println("进入index,别人提供了openid，存入Session");
				request.getSession().setAttribute("openid", openid);
				modelAndView.setViewName("wx/signin");
			}
		} catch (Exception e) {
			e.printStackTrace();
			MsgManager.writeError(request, e);
			LogUtil.getLogger().error(e.getMessage(), e);
		}

		return modelAndView;
	}

	@RequestMapping("signinIndex")
	public ModelAndView wxIndex(Integer deptid, HttpServletRequest request,
			Integer type) {
		ModelAndView modelAndView = new ModelAndView();

		request.getSession().setAttribute("deptid", deptid);
		Core_Dept dept = deptService.getObjectById(deptid);
		PayConfig config = deptPayConfigDao
				.getdeptConfig(dept.getDeptCode(), 1);
		if (config == null) {
			dept = deptService.getObjectById(dept.getParentId());
			config = deptPayConfigDao.getdeptConfig(dept.getDeptCode(), 1);
			if (config == null) {
				dept = deptService.getObjectById(dept.getParentId());
				config = deptPayConfigDao.getdeptConfig(dept.getDeptCode(), 1);
			}
		}
		System.out.println(JacksonUtil.toJson(config));
		request.getSession().setAttribute("appid", config.getAppid());
		request.getSession().setAttribute("deptname", dept.getDeptName());
		request.getSession().setAttribute("secret", config.getAppsecret());
		// request.getSession().setAttribute("appid", "wx6d4d594ef75edec4");
		// request.getSession().setAttribute("secret",
		// "5827020e5fd8d8a037afb09232a29813");
		// request.getSession().setAttribute("openid", "test");
		modelAndView.setViewName("wx/signin");
		return modelAndView;
	}

	@RequestMapping("hadAppointme")
	public ModelAndView hadAppointme(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		// request.getSession().setAttribute("deptid", deptid);
		// request.getSession().setAttribute("openid", "test");
		modelAndView.setViewName("wx/appointmeType");
		return modelAndView;
	}

	// 代约取号
	@RequestMapping("otherAppointme")
	public ModelAndView otherAppointme(HttpServletRequest request, String code) {
		ModelAndView modelAndView = new ModelAndView();
		// request.getSession().setAttribute("deptid", deptid);
		// request.getSession().setAttribute("openid", "test");
		modelAndView.addObject("code", code);
		modelAndView.setViewName("wx/otherSignin");
		return modelAndView;
	}

	// 叫号机预约扫码取号主页
	@RequestMapping("JhjAppSquenceIndex")
	public ModelAndView JhjAppSquenceIndex(HttpServletRequest request,
			Integer deptid) {
		ModelAndView modelAndView = new ModelAndView();
		// request.getSession().setAttribute("deptid", deptid);
		// request.getSession().setAttribute("openid", "test");
		modelAndView.addObject("deptid", deptid);
		modelAndView.setViewName("wx/jhjSignin");
		return modelAndView;
	}

	// 叫号机预约扫码取号
	@RequestMapping("getJhjAppSquence")
	public ModelAndView getJhjAppSquence(HttpServletRequest request,
			HttpSession session, String cardnumber, Integer deptid) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			SquenceInfo oldsquenceInfo = null;
			// 判断是否预约
			AppointmeInfo appointmeInfo = null;
			if (cardnumber != null) {
				appointmeInfo = appointmeInfoService
						.getAppInfoByopenidOrCardnumber(null, cardnumber,
								new Date(), deptid);
			}
			if (appointmeInfo == null) {
				modelAndView.setViewName("wx/siginfail");
				modelAndView.addObject("msg", "未查询到您今日的预约信息或你已经签过到了！");
				return modelAndView;
			} else {
				// 如果有预约信息 则判断是否早到 和是否迟到
				int timequan = appointmeInfo.getTimeQuantum();
				Calendar c = Calendar.getInstance();
				int now = c.get(Calendar.HOUR_OF_DAY);
				int result = TimequantumUntil.islaterorearly(timequan, now);
				if (result == 2) {// 迟到了
					modelAndView.setViewName("wx/siginfail");
					modelAndView.addObject("msg", "抱歉！你迟到了！请在取号机上领取现场号码！");
					return modelAndView;
				} else {// 准时
					SquenceInfo squenceInfo = new SquenceInfo();
					squenceInfo.setDeptid(deptid);
					squenceInfo.setIsappointment(1);
					squenceInfo.setStatus(6);// 6代表要去调用叫号机接口
					squenceInfo.setOpenid("jhj");
					squenceInfo.setAppointmenttime(squenceInfo
							.getAppointmenttime());
					squenceInfo.setCardnumber(cardnumber);
					squenceInfo.setIsearly(result);
					squenceInfo.setTimeQuantum(timequan);
					String squence = "000";
					squenceInfo.setSquence(squence);
					squenceInfoService.insertSelective(squenceInfo);
					modelAndView.setViewName("wx/siginsuccess");
					modelAndView
							.addObject("msg",
									"你已签到成功！请在叫号机下方领取你的凭条！ 请勿离开现场，耐心等待窗口叫号，呼叫超时将需重新取号！");
					// appointmeInfoService.cancleApp(appointmeInfo.getId());
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}

	// 预约取号
	@RequestMapping("getAppSquence")
	public ModelAndView getAppSquence(String code, HttpServletRequest request,
			HttpSession session, String cardnumber) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			System.out.println("进入signin====");
			Integer deptid = (Integer) session.getAttribute("deptid");
			// deptid = 47;
			// String openid = "oJJFXsxcPZAdTiWLuucLAHibZ0KM";
			String openid = (String) request.getSession()
					.getAttribute("openid");
			if (openid == null) {
				openid = OpenidUtil.getOpenid(code, session);
				request.getSession().setAttribute("openid", openid);
			}
			SquenceInfo oldsquenceInfo = null;
			// 判断是否预约
			AppointmeInfo appointmeInfo = null;
			if (cardnumber != null) {
				appointmeInfo = appointmeInfoService
						.getAppInfoByopenidOrCardnumber(null, cardnumber,
								new Date(), deptid);
			} else {
				appointmeInfo = appointmeInfoService
						.getAppInfoByopenidOrCardnumber(openid, null,
								new Date(), deptid);
			}
			if (appointmeInfo == null) {
				modelAndView.setViewName("wx/siginfail");
				modelAndView.addObject("msg", "未查询到您今日的预约信息！");
				return modelAndView;
			} else {
				// 先判断是否已经签到 避免重复签到
				if (cardnumber != null) {
					oldsquenceInfo = squenceInfoService.isSignin(null,
							cardnumber, new Date());
				} else {
					oldsquenceInfo = squenceInfoService.isSignin(openid, null,
							new Date());
				}
				if (oldsquenceInfo != null) {
					squenceInfoService.updateStatusByPrimaryKey(
							oldsquenceInfo.getId(), null, 3);
					// modelAndView.setViewName("wx/siginrepeat");
					// modelAndView.addObject("msg", "你已经签过到了！不能重复签到取号！");
					// return modelAndView;
				}
				// 如果有预约信息 则判断是否早到 和是否迟到
				int timequan = appointmeInfo.getTimeQuantum();
				Calendar c = Calendar.getInstance();
				int now = c.get(Calendar.HOUR_OF_DAY);
				int result = TimequantumUntil.islaterorearly(timequan, now);
				if (result == 2) {// 迟到了
					modelAndView.setViewName("wx/siginfail");
					modelAndView.addObject("msg", "抱歉！你迟到了！");
					return modelAndView;
				} else {// 准时
					SquenceInfo squenceInfo = new SquenceInfo();
					squenceInfo.setDeptid(deptid);
					squenceInfo.setIsappointment(1);
					squenceInfo.setStatus(1);
					squenceInfo.setOpenid(openid);
					squenceInfo.setAppointmenttime(squenceInfo
							.getAppointmenttime());
					squenceInfo.setCardnumber(appointmeInfo.getName());
					squenceInfo.setIsearly(result);
					squenceInfo.setTimeQuantum(timequan);
					String squence = squenceInfoService.getnextSquence(1,
							new Date(), result, deptid);
					squenceInfo.setSquence(squence);

					squenceInfoService.insertSelective(squenceInfo);
					modelAndView.setViewName("wx/siginsuccess");
					modelAndView.addObject("squence", squence);
					if (result == 1) {
						modelAndView.addObject("msg",
								"你好！你提前到达了现场，已为你签到成功，到达预约时间将优先为你办理业务！");
					} else {
						modelAndView.addObject("msg",
								"你已签到成功！请耐心等待叫号~ 请勿离开现场，耐心等待窗口叫号，呼叫超时将需重新取号！");
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}

	@RequestMapping("getcommSquenceJsp")
	public ModelAndView getcommSquenceJsp(HttpSession session, String code) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			Integer deptid = (Integer) session.getAttribute("deptid");
			// String openid = "test"+Math.random();
			String openid = (String) session.getAttribute("openid");
			if (openid == null) {
				openid = OpenidUtil.getOpenid(code, session);
				session.setAttribute("openid", openid);
			}
			modelAndView.setViewName("wx/comSignin");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;

	}

	// 普通签到取号
	@RequestMapping("getcommSquence")
	public ModelAndView getcommSquence(HttpServletRequest request,
			HttpSession session, String name) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			Integer deptid = (Integer) session.getAttribute("deptid");
			// deptid = 47;
			System.out.println("进入getcommSquence====");
			String openid = (String) session.getAttribute("openid");
			// String openid = "test" + Math.random();
			SquenceInfo oldsquenceInfo = squenceInfoService.isSignin(openid,
					null, new Date());
			if (oldsquenceInfo != null) {
				// 已经签到 前面号码作废
				squenceInfoService.updateStatusByPrimaryKey(
						oldsquenceInfo.getId(), null, 3);
				/*
				 * modelAndView.setViewName("wx/siginrepeat");
				 * modelAndView.addObject("msg", "你已经签过到了！不能重复签到取号！"); return
				 * modelAndView;
				 */
			}
			SquenceInfo squenceInfo = new SquenceInfo();
			squenceInfo.setDeptid(deptid);
			squenceInfo.setIsappointment(0);
			squenceInfo.setStatus(1);
			squenceInfo.setOpenid(openid);
			squenceInfo.setCardnumber(name);
			// 未预约
			String squence = squenceInfoService.getnextSquence(0, new Date(),
					null, deptid);
			squenceInfo.setSquence(squence);
			squenceInfoService.insertSelective(squenceInfo);
			modelAndView.setViewName("wx/siginsuccess");
			modelAndView.addObject("squence", squence);
			modelAndView.addObject("msg",
					"你已签到成功！请耐心等待叫号~ 请勿离开现场，耐心等待窗口叫号，呼叫超时将需重新取号！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}

	// 查询排队信息
	@RequestMapping("querysquenceInfo")
	public ModelAndView querysquenceInfo(String code,
			HttpServletRequest request, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		String openid;
		try {
			if (session.getAttribute("openid") == null) {
				// session中没有openid 则去获取
				openid = OpenidUtil.getOpenid(code, session);
				// openid="oJJFXsy2j_chu3NveepUrQLctqoM";
				request.getSession().setAttribute("openid", openid);
			} else {
				openid = (String) session.getAttribute("openid");
			}
			SquenceInfo squenceInfo = squenceInfoService.isSignin(openid, null,
					new Date());
			if (squenceInfo == null) {
				modelAndView.addObject("sta", 0);
			} else {
				Map<String, String> map = squenceInfoService.getfrontCount(
						new Date(), squenceInfo);
				modelAndView.addObject("sta", 1);
				modelAndView.addObject("squence", squenceInfo.getSquence());
				modelAndView.addObject("count", map.get("count"));
				modelAndView.addObject("time", map.get("time"));
			}
			// modelAndView.addObject("squence", squence);
			modelAndView.setViewName("wx/lineup");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
}

package com.jrwp.wx.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Core_Dept;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.service.IDeptService;
import com.jrwp.follow.dao.AccessTokenDao;
import com.jrwp.follow.dao.DeptPayConfigDao;
import com.jrwp.follow.entity.PayConfig;
import com.jrwp.wx.dao.FrequentContactsMapper;
import com.jrwp.wx.entity.AppointmeInfo;
import com.jrwp.wx.entity.DeptAddress;
import com.jrwp.wx.entity.FrequentContacts;
import com.jrwp.wx.entity.TimeQuan;
import com.jrwp.wx.service.AppointmeInfoService;
import com.jrwp.wx.service.DeptAddressService;
import com.jrwp.wx.until.OpenidUtil;

@Description("预约信息管理")
@Controller
@RequestMapping("appointmeAction")
public class AppointmeController {

	@Resource
	private AppointmeInfoService appointmeInfoService;

	@Resource
	private IDeptService deptService;

	@Resource
	private DeptAddressService deptAddressService;

	@Resource
	private FrequentContactsMapper frequentContactsMapper;

	@Resource
	private DeptPayConfigDao deptPayConfigDao;

	@Resource
	private AccessTokenDao accessTokenDao;

	@RequestMapping("appointmeIndex")
	public ModelAndView wxIndex(Integer deptid, HttpServletRequest request,
			String userinfo) {
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("deptid" + deptid);
		System.out.println("userinfo:===========" + userinfo);
		request.getSession().setAttribute("deptid", deptid);
		Core_Dept dept = deptService.getObjectById(deptid);
		System.out.println("deptname" + dept.getDeptName());
		request.getSession().setAttribute("deptname", dept.getDeptName());
		if (userinfo == null) {
			System.out.println("====================================");
			System.out.println("进入index,别人没有提供openid");
			PayConfig config = deptPayConfigDao.getdeptConfig(
					dept.getDeptCode(), 1);
			request.getSession().setAttribute("appid", config.getAppid());
			request.getSession().setAttribute("secret", config.getAppsecret());
		} else {
			JSONObject jsonObject = JSONObject.fromObject(userinfo);
			String openid = jsonObject.get("openid").toString();
			System.out.println("别人给的OPENID=-=====" + openid);
			System.out.println("====================================");
			System.out.println("进入index,别人提供了openid，存入Session");
			request.getSession().setAttribute("openid", openid);
		}
		modelAndView.setViewName("wx/enter");
		return modelAndView;
	}

	@RequestMapping("startappointme")
	public ModelAndView startappointme(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		// Integer deptid = (Integer)
		// request.getSession().getAttribute("deptid");
		modelAndView.setViewName("wx/transit");
		return modelAndView;
	}

	@RequestMapping("getOpenId")
	public ModelAndView getOpenID(String code, HttpServletRequest request,
			HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			System.out.println("进入getOpenid方法");
			String openid;
			if (session.getAttribute("openid") == null) {
				// session中没有openid 则去获取
				// openid = "test" + Math.random();
				openid = OpenidUtil.getOpenid(code, session);
				request.getSession().setAttribute("openid", openid);
			} else {
				openid = (String) session.getAttribute("openid");
			}
			Integer deptid = (Integer) request.getSession().getAttribute(
					"deptid");
			System.out.println("=============openid:" + openid);
			System.out.println("=============deptid:" + deptid);
			// Integer deptid = (Integer)
			// request.getSession().getAttribute("deptid");
			if (deptid == null) {
				deptid = 44;
			}
			Core_Dept dept = deptService.getObjectById(deptid);
			String deptcode = dept.getDeptCode();
			String orderCode = dept.getOrderCode();
			String starcode = deptcode.substring(0, orderCode.length() / 5 * 2)
					+ "%";
			List<DeptAddress> list = deptAddressService
					.selectByDeptid(starcode);
			modelAndView.addObject("deptlist", list);
			modelAndView.addObject("deptid", deptid);
			modelAndView.setViewName("wx/order");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}

	@RequestMapping("gotoorder")
	public ModelAndView gotoorder(Integer deptid, HttpServletRequest request,
			HttpSession session, String deptname) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.addObject("deptid", deptid);
			modelAndView.addObject("deptname", deptname);
			List<FrequentContacts> frequentContacts = frequentContactsMapper
					.getByOpenid((String) request.getSession().getAttribute(
							"openid"));
			modelAndView.addObject("frequentContacts", frequentContacts);
			modelAndView.setViewName("wx/order");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}

	@Description("保存预约信息")
	@RequestMapping("saveAppointmeInfo")
	public ModelAndView save(AppointmeInfo appointmeInfo,
			HttpServletRequest request, String appointmenttimeStart,
			String appointmenttimeEnd, Integer service) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			// int deptid = (int) request.getSession().getAttribute("deptid");
			if (appointmeInfo.getOpenid() == null) {
				appointmeInfo.setOpenid("test");
				System.out.println("openid是空");
			} else {
				System.out.println(appointmeInfo.getOpenid());
			}
			appointmeInfo.setStatus(1);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sf.parse(appointmeInfo.getAppointmenttime());
			// 判断是否重复预约
			AppointmeInfo old = appointmeInfoService
					.getAppInfoByopenidOrCardnumber(null,
							appointmeInfo.getCardnumber(), date,
							appointmeInfo.getDeptid());

			if (old != null) {
				modelAndView.setViewName("wx/appointmerepeat");
				modelAndView.addObject("msg", "当天已经有预约信息！不能重复预约！");
				return modelAndView;
			}
			int id = appointmeInfoService.insert(appointmeInfo);
			modelAndView.setViewName("wx/success");
			TimeQuan timeQuan = appointmeInfoService.getTimequn(appointmeInfo
					.getTimeQuantum());
			appointmeInfo.setTimeQuan(timeQuan);
			Core_Dept dept = deptService.getObjectById(appointmeInfo
					.getDeptid());
			// 公众号推送一条消息
			// String token = OpenidUtil.getToken(request.getSession(),
			// accessTokenDao);
			// String groupUrl =
			// "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="
			// + token;
			//
			// String content = "你好！你预约在" + appointmeInfo.getAppointmenttime() +
			// "到" + dept.getDeptName() +
			// "办理业务的信息已经受理，请您准时到达现场并扫描现场的二维码进行签到！";
			// String openid1data = "{\"touser\":\"" + appointmeInfo.getOpenid()
			// + "\",\"msgtype\": \"text\",\"text\": {\"content\": "
			// + "\"" + content + "\"}}";
			// String sr2 = OpenidUtil.sendPost(groupUrl, openid1data);
			// System.out.println(sr2);
			modelAndView.addObject("appointmeInfo", appointmeInfo);
			modelAndView.addObject("dept", dept);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}

	@Description("查询预约信息")
	@RequestMapping("queryAppointmeInfo")
	public ModelAndView queryAppointmeInfo(String cardnumber,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("wx/search");
		List<AppointmeInfo> list = null;
		try {
			if (cardnumber == null || cardnumber.equals("")) {
				String openid = (String) request.getSession().getAttribute(
						"openid");
				if (openid == null) {
					return modelAndView;
				}
				list = appointmeInfoService.selectByopenidOrCardnumber(openid,
						null,
						new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			} else {
				list = appointmeInfoService.selectByopenidOrCardnumber(null,
						cardnumber,
						new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			}

			modelAndView.addObject("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}

	// 获取当前预约人数 可预约人数
	@RequestMapping("getAppointmeCount")
	@ResponseBody
	public DoResult getAppointmeCount(String day, int time, int deptid) {
		DoResult result = new DoResult();
		try {
			// 记录 应该查询时 假如部门判断
			int count = appointmeInfoService.getTimequnCount(day, time, deptid);
			result.setStateValue(count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*@Description("取消预约信息")
	@RequestMapping("cancleAppointme")
	@ResponseBody
	public DoResult cancleAppointme(int id, HttpServletRequest request) {
		DoResult result = new DoResult();
		try {
			AppointmeInfo appointmeInfo = appointmeInfoService.findByid(id);
			appointmeInfoService.cancleApp(id);
			String token = OpenidUtil.getToken(request.getSession(),
					accessTokenDao);
			String groupUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="
					+ token;
			String content = "你好！你预约在" + appointmeInfo.getAppointmenttime()
					+ "办理业务的信息已经取消！";
			String openid1data = "{\"touser\":\"" + appointmeInfo.getOpenid()
					+ "\",\"msgtype\": \"text\",\"text\": {\"content\": "
					+ "\"" + content + "\"}}";
			String sr2 = OpenidUtil.sendPost(groupUrl, openid1data);
			System.out.println(sr2);
			result.setStateMsg("取消预约成功！");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			result.setStateMsg("取消预约失败！");
			result.setStateType(DoResultType.fail);
			e.printStackTrace();
		}
		return result;
	}*/

}

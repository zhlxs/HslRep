package com.jrwp.wx.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jrwp.core.annotation.Description;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.wx.entity.AppointInfo;
import com.jrwp.wx.entity.PmVisitor;
import com.jrwp.wx.entity.RegisterUser;
import com.jrwp.wx.entity.WxPmVistor;
import com.jrwp.wx.entity.WxSquenceList;
import com.jrwp.wx.service.AppointInfoService;
import com.jrwp.wx.service.WxSquenceService;

@Description("微信查看队列状态接口")
@Controller
@RequestMapping("wx/wxSquenceController")
public class WxSquenceController {
	private final Logger logger = Logger.getLogger(WxSquenceController.class);

	@Resource
	private WxSquenceService wxSquenceService;
	
	@Resource
	private AppointInfoService appointInfoService;

	@Description("获取列表")
	@RequestMapping("getList")
	@ResponseBody
	public DoResult getList(HttpServletRequest request, Long deptid) {
		DoResult result = new DoResult();
		try {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(currentTime);

			List<WxSquenceList> wxSquenceList = new ArrayList<WxSquenceList>();
			WxSquenceList zh = new WxSquenceList();
			zh.setBusinessType(1);
			zh.setSquenceName("综合业务");
			int zhWaitCount = wxSquenceService.getZhWaitCount(dateString,
					deptid);
			zh.setWaitCount(zhWaitCount);
			List<WxPmVistor> squenceList = wxSquenceService.getSquenceNumber(
					dateString, deptid);
			zh.setWinNumber(squenceList);
			wxSquenceList.add(zh);

			WxSquenceList db = new WxSquenceList();
			db.setBusinessType(4);
			// db.setSquenceName("疑难窗口");
			db.setSquenceName("疑难业务");
			int dbWaitCount = wxSquenceService.getDbWaitCount(dateString,
					deptid);
			db.setWaitCount(dbWaitCount);
			List<WxPmVistor> dbsquenceList = wxSquenceService
					.getDbSquenceNumber(dateString, deptid);
			db.setWinNumber(dbsquenceList);
			wxSquenceList.add(db);
			result.setStateType(DoResultType.success);
			result.setStateValue(wxSquenceList);
			result.setStateMsg("获取成功");
		} catch (Exception e) {
			result.setStateType(DoResultType.fail);
			result.setStateMsg("接口出现异常");
			logger.error("微信获取列表出现异常", e);
			e.printStackTrace();
		}

		// logger.info("返回给落地屏的数据="+JacksonUtil.toJson(result));
		return result;
	}

	@Description("获取列表")
	@RequestMapping("getSquenceList")
	@ResponseBody
	public DoResult getSquenceList(HttpServletRequest request, Long deptid,
			int businessType) {
		DoResult result = new DoResult();
		List<AppointInfo> list = new ArrayList<AppointInfo>();
		try {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(currentTime);
			RegisterUser registerUser = (RegisterUser)request.getSession().getAttribute("userInfo");
			if(registerUser != null){
				list = appointInfoService.getInfo(registerUser.getWxOpenId());
			}
			List<WxPmVistor> taget = new ArrayList<>();
			List<WxPmVistor> all = wxSquenceService.getAllSquenceInfo(deptid,
					dateString);
			List<WxPmVistor> gone = new ArrayList<>();
			List<WxPmVistor> called = new ArrayList<>();
			List<WxPmVistor> recalled = new ArrayList<>();
			List<WxPmVistor> wait = new ArrayList<>();
			if (businessType == 1) {
				for (WxPmVistor visitor : all) {
					if (visitor.getState().equals("1")
							&& (visitor.getBusinessType() == 1 || visitor
									.getBusinessType() == 2)) {
						if (wait.size() <= all.size()) {
							wait.add(visitor);
						}
					} else if (visitor.getState().equals("2")
							&& (visitor.getBusinessType() == 1 || visitor
									.getBusinessType() == 2)) {
						if (called.size() <= all.size()) {
							called.add(visitor);
						}
					} else if (visitor.getState().equals("3")
							&& (visitor.getBusinessType() == 1 || visitor
									.getBusinessType() == 2)) {
						if (gone.size() <= all.size()) {
							gone.add(visitor);
						}
					} else if (visitor.getState().equals("5")
							&& (visitor.getBusinessType() == 1 || visitor
									.getBusinessType() == 2)) {
						if (recalled.size() <= all.size()) {
							recalled.add(visitor);
						}
					}
				}
				for (WxPmVistor visitor : called) {
					visitor.setState("1");
					taget.add(visitor);
				}
				for (WxPmVistor visitor : wait) {
					visitor.setState("0");
					taget.add(visitor);
				}
				for (WxPmVistor visitor : gone) {
					visitor.setState("3");
					taget.add(visitor);
				}
				for (WxPmVistor visitor : recalled) {
					visitor.setState("5");
					taget.add(visitor);
				}
			} else if (businessType == 4) {
				for (WxPmVistor visitor : all) {
					if (visitor.getState().equals("1")
							&& visitor.getBusinessType() == 4) {
						if (wait.size() <= all.size()) {
							wait.add(visitor);
						}
					} else if (visitor.getState().equals("2")
							&& visitor.getBusinessType() == 4) {
						if (called.size() <= all.size()) {
							called.add(visitor);
						}
					} else if (visitor.getState().equals("3")
							&& visitor.getBusinessType() == 4) {
						if (gone.size() <= all.size()) {
							gone.add(visitor);
						}
					} else if (visitor.getState().equals("5")
							&& visitor.getBusinessType() == 4) {
						if (recalled.size() <= all.size()) {
							recalled.add(visitor);
						}
					}
				}
				for (WxPmVistor visitor : called) {
					visitor.setState("1");
					taget.add(visitor);
				}
				for (WxPmVistor visitor : wait) {
					visitor.setState("0");
					taget.add(visitor);
				}
				for (WxPmVistor visitor : gone) {
					visitor.setState("3");
					taget.add(visitor);
				}
				for (WxPmVistor visitor : recalled) {
					visitor.setState("5");
					taget.add(visitor);
				}
			}
			if(list != null && list.size()>0){
				for(int i=0;i<list.size();i++){
					Long appointme_infoid = list.get(i).getId();
					for(int z=0;z<taget.size();z++){
						if(appointme_infoid == taget.get(z).getAppointme_infoid()){
							taget.get(z).setIsred(1);
						}
					}
				}
			}
			result.setStateValue(taget);
			result.setStateType(DoResultType.success);
			// result.setStateValue(wxSquenceList);
			result.setStateMsg("获取成功");
		} catch (Exception e) {
			result.setStateType(DoResultType.fail);
			result.setStateMsg("接口出现异常");
			logger.error("微信获取列表出现异常", e);
			e.printStackTrace();
		}

		// logger.info("返回给落地屏的数据="+JacksonUtil.toJson(result));
		return result;
	}

}

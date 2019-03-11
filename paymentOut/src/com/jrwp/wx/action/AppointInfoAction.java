package com.jrwp.wx.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jrwp.core.annotation.Description;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.payMent.dao.Time_TableDao;
import com.jrwp.payMent.entity.DicTime;
import com.jrwp.payMent.service.DicTimeService;
import com.jrwp.payMent.service.IPoliceClassService;
import com.jrwp.wx.entity.AppointInfo;
import com.jrwp.wx.entity.DeptAddress;
import com.jrwp.wx.entity.WXSquenceInfo;
import com.jrwp.wx.service.AppointInfoService;
import com.jrwp.wx.service.DeptAddressService;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Description(value = "预约信息查询接口", state = false)
@Controller
@RequestMapping("wx/appointInfoAction")
public class AppointInfoAction {

	@Resource
	private AppointInfoService appointInfoService;
	@Resource
	private DeptAddressService deptAddressService;
	@Resource
	private Time_TableDao timetabDao;
	@Resource
	private IPoliceClassService policeClassService;
	@Resource
	private DicTimeService dicTimeService;

	@Description("预约信息视图")
	@RequestMapping("appointInfoView")
	public ModelAndView appointInfoView() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("appointInfo/mymatter");
		return maView;
	}

	@Description("预约信息Json")
	@RequestMapping("appointInfoJson")
	@ResponseBody
	public String appointInfoJson(String openId) {
		Logger logger = Logger.getLogger(this.getClass());
		DoResult result = new DoResult();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			List<AppointInfo> list = appointInfoService.getInfo(openId);
			if (list.size() != 0) {
				for (AppointInfo info : list) {
					// Long id = info.getId();
					Long deptId = info.getDeptId();
					Long timeId = info.getTimeQuantum();
					String serCode = info.getSerCode();
					info.setAppTime(df.format(info.getAppointmenttime()));
					// WXSquenceInfo squenceInfo =
					// appointInfoService.getSequenceByAppointId(id);
					// if(squenceInfo != null){
					// info.setIsAttend(1);//已经签到
					// }else{
					// info.setIsAttend(-1);//还未签到
					// }
					// DeptAddress address =
					// deptAddressService.getObjectById(deptId);
					// System.out.println(address.getAddress());
					info.setDeptName(deptAddressService.getObjectById(deptId)
							.getDeptname());
					info.setTimeInter(dicTimeService.getObjectById(timeId)
							.getTime());
					info.setSerName(policeClassService.getObjectByCode(serCode)
							.getSerName());
				}
				result.setStateValue(list);
				result.setStateMsg("信息获取成功");
				result.setStateType(DoResultType.success);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("接口异常");
			result.setStateType(DoResultType.fail);
			logger.error("预约信息获取失败", e);
		}
		return JacksonUtil.toJson(result);
	}

	// public static void main(String[] args) {
	// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	// System.out.println(df.format(new Date()));
	// }
}

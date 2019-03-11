package com.jrwp.payMent.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Core_User;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.service.IDeptService;
import com.jrwp.core.service.IUserService;
import com.jrwp.core.utils.LogUtil;
import com.jrwp.payMent.entity.DeptTimesAuth;
import com.jrwp.payMent.help.CommonHelper;
import com.jrwp.payMent.service.IDeptTimesAuthService;

/**
 * 取号次数授权控制器
 * 
 * @author hsl
 * 
 */
@Description("取号次数授权控制器")
@Controller
@RequestMapping("deptTimesAuthController")
public class V1_DeptTimesAuthController {

	@Resource
	private IDeptTimesAuthService deptTimesAuthService;
	@Resource
	private IUserService userService;
	@Resource
	private IDeptService deptService;

	@Description("待授权列表")
	@RequestMapping("waitAuthList")
	public ModelAndView WaitAuthList() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("dept_timesauth/list");
		return mav;
	}

	@Description("待授权列表Json")
	@RequestMapping("waitAuthListJson")
	@ResponseBody
	public CommonHelper<DeptTimesAuth> WaitAuthListJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		CommonHelper<DeptTimesAuth> commonHelper = null;
		List<DeptTimesAuth> list = new ArrayList<>();
		try {
			PageHelper.startPage(pageNum, pageSize);
			list = deptTimesAuthService.list();
			PageInfo<DeptTimesAuth> pageInfo = new PageInfo<DeptTimesAuth>(list);
			commonHelper = new CommonHelper<DeptTimesAuth>(pageInfo);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.error("[waitAuthListJson]出现异常:{}", e.getMessage());
		}
		return commonHelper;
	}

	@Description("次数授权视图")
	@RequestMapping("timesAuthView")
	public ModelAndView TimesAuthView(Long id) {
		ModelAndView mav = new ModelAndView();
		try {
			DeptTimesAuth deptTimesAuth = deptTimesAuthService
					.getObjectById(id);
			mav.addObject("deptTimesAuth", deptTimesAuth);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mav.setViewName("dept_timesauth/dialog");
		return mav;
	}

	@Description("保存次数授权配置")
	@RequestMapping("saveTimesAuth")
	@ResponseBody
	public DoResult saveTimesAuthRes(Long id, Long deptid, Integer times,
			String starttime, String endtime, String idCard, String userName) {
		DoResult result = new DoResult();
		DeptTimesAuth deptTimesAuth = new DeptTimesAuth();
		try {
			Date date = new Date();
			String sec = String.valueOf(date.getSeconds());
			if (sec.length() == 1) {
				sec = "0" + sec;
			}
			starttime = starttime + ":" + sec;
			endtime = endtime + ":" + sec;
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			Core_User user = (Core_User) session.getAttribute("user");
			deptTimesAuth.setId(id);
			deptTimesAuth.setDeptid(deptid);
			deptTimesAuth.setTimes(times);
			deptTimesAuth.setOperator(user.getId());
			deptTimesAuth.setAuthtime(new Date());
			deptTimesAuth.setStarttime(format.parse(starttime));
			deptTimesAuth.setEndtime(format.parse(endtime));
			deptTimesAuthService.updateById(deptTimesAuth, idCard, userName);
			result.setStateMsg("授权成功，配置已生效");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			result.setStateMsg("保存失败");
			result.setStateType(DoResultType.fail);
			LogUtil.error("[saveTimesAuthRes]出现异常:{}", e.getMessage());
		}
		return result;
	}

}

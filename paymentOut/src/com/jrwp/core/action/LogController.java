package com.jrwp.core.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Core_Log;
import com.jrwp.core.entity.LogHelper;
import com.jrwp.core.service.ILogService;

@Description("日志管理")
@Controller
@RequestMapping("logController")
public class LogController {

	// @Description("日志列表")
	// @RequestMapping("JKRZListJsp")
	// public ModelAndView JKRZListJsp() {
	// ModelAndView mav = new ModelAndView("corelog/coreloglist");
	// return mav;
	// }
	@Resource
	private ILogService logService;

	@Description("操作日志列表")
	@RequestMapping("logList")
	public ModelAndView logList() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/corelog/coreloglist");
		return mav;
	}

	@Description("操作日志列表Json")
	@RequestMapping("logJson")
	@ResponseBody
	public LogHelper logJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			String queryinfo, String begintime, String endtime) {
		if (begintime == null && endtime == null) {
			Date date = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
			begintime = endtime = simpleDateFormat.format(date);
		}
		PageHelper.startPage(pageNum, pageSize);
		LogHelper logHelper = null;
		List<Core_Log> logList = new ArrayList<>();
		try {
			if (queryinfo == null) {
				logList = logService.list(queryinfo, begintime, endtime);
			} else {
				logList = logService.list(queryinfo, null, null);
			}
			PageInfo<Core_Log> page = new PageInfo<Core_Log>(logList);
			logHelper = new LogHelper(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logHelper;
	}

	@Description("操作日志详情")
	@RequestMapping("logDetial")
	public ModelAndView logDetial(long id) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("corelog/corelogdetial");
		try {
			Core_Log log = logService.getObjectById(id);
			LogHelper logHelper = new LogHelper(log);
			mav.addObject("log", log);
			mav.addObject("logHelper", logHelper);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
}

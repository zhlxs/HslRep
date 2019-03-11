package com.jrwp.core.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Config;
import com.jrwp.core.entity.Core_Config;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.service.IConfigService;
import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.core.utils.LogUtil;

@Description("参数设置")
@Controller
@RequestMapping("configController")
public class ConfigController {
	@Resource
	private IConfigService configService;

	@Description("参数布局")
	@RequestMapping("configLayout")
	public ModelAndView configLayout() {
		// configService.configLayout();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("coreconfig/coreconfiglayout");
		return mav;
	}

	@Description("系统基本信息")
	@RequestMapping("sysBaseInfo")
	public ModelAndView sysBaseInfo() {
		// 在系统初始化的时候已经加载了系统配置信息,see:MainController.init();
		// List<Core_Config> config = configService.getSysBaseInfo();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("coreconfig/sysbaseinfo");
		return mav;
	}

	@Description("保存系统基本信息")
	@RequestMapping("saveSysBaseInfo")
	@ResponseBody
	public DoResult saveSysBaseInfo(Config config, HttpServletRequest request) {
		DoResult result = new DoResult();
		Core_Config core_Config = new Core_Config();
		if (config != null) {
			String json = JacksonUtil.toJson(config);
			core_Config.setConfigName("sysBaseInfo");
			core_Config.setConfigJson(json);
			try {
				configService.save(core_Config);
				// 保存系统基本信息到上下文
				request.getServletContext().setAttribute("sysBaseInfo", config);
				result.setStateType(DoResultType.success);
				result.setStateMsg("保存成功,页面1秒后自动刷新，如果未刷新，请手动刷新");
			} catch (Exception e) {
				LogUtil.error("保存系统基本信息失败:{}", e.getMessage());
				result.setStateType(DoResultType.fail);
				result.setStateMsg("保存失败");
			}
		}
		return result;
	}

}

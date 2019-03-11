package com.jrwp.core.interceptor;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Core_Log;
import com.jrwp.core.entity.Core_User;
import com.jrwp.core.service.ILogService;
import com.jrwp.core.utils.ActionListImpl;
import com.jrwp.core.utils.AnnotationUtil;
import com.jrwp.core.utils.LogUtil;

/**
 * 日志拦截器
 * 
 * @author USER
 * 
 */
public class LogInterceptor implements HandlerInterceptor {

	@Resource
	private ILogService logService;
	@Resource
	private ActionListImpl actionList;

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception e)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView mav)
			throws Exception {
		try {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Class<? extends Object> clazz = handlerMethod.getBean().getClass();
			String controllerName = ((String[]) AnnotationUtil
					.getAnnotationValue(clazz, RequestMapping.class, "value"))[0];
			String controllerValue = AnnotationUtil.getAnnotationDefaultValue(
					clazz, Description.class);
			String actionName = ((String[]) AnnotationUtil.getAnnotationValue(
					method, RequestMapping.class, "value"))[0];
			String actionValue = AnnotationUtil.getAnnotationDefaultValue(
					method, Description.class);
			if (actionValue == null) {
				return;
			}
			String ip = request.getRemoteAddr();
			Map<String, String[]> parameterMap = request.getParameterMap();
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(parameterMap);
			String area = actionList.getArea(clazz);
			LogUtil.debug("控制器:{};作用域:{}", clazz, area);
			HttpSession session = request.getSession();
			Core_User user = (Core_User) session.getAttribute("user");
			// System.out.println("Controller-->" + controllerName);
			// System.out.println("ControllerA--->" + controllerValue);
			// System.out.println("Action-->" + actionName);
			// System.out.println("ActionA-->" + actionValue);
			// System.out.println("ip--->" + ip);
			// System.out.println(json);
			Core_Log log = new Core_Log();
			log.setAreaName(area);
			log.setUser(user);
			log.setControlDisplay(controllerValue);
			log.setControlName(controllerName);
			log.setActionDisplay(actionValue);
			log.setActionName(actionName);
			log.setRecordIP(ip);
			log.setRecordTime(new Date());
			log.setParameterJson(json);
			logService.save(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) throws Exception {
		return true;
	}

}

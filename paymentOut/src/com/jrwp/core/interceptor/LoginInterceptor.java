package com.jrwp.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {
	private static final String[] IGNORE_URI = { "/mainController/userLogin",
			"/mainController/checkUserLogin", "/mainController/validateCode" };

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object handler, Exception arg3)
			throws Exception {
		System.out.println("afterCompletion--");
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			String name = handlerMethod.getMethod().getName();
			System.out.println(name);
		}
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object handler, ModelAndView arg3) throws Exception {
		System.out.println("postHandle--");
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			String name = handlerMethod.getMethod().getName();
			System.out.println(name);
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		boolean flag = false;
		String url = request.getRequestURL().toString();
		System.out.println(">>>: " + url);
		if (request.getSession().getAttribute("user") == null) {
			for (String s : IGNORE_URI) {
				if (url.contains(s)) {
					flag = true;
					break;
				}
			}
			// 当未登录时,请求其他页面重定向到登录页面
			// if (flag == false) {
			// response.sendRedirect(request.getContextPath()
			// + "/userlogin.jsp");
			// }
		} else {
			flag = true;
		}
		return flag;
	}

}

package com.jrwp.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jrwp.core.entity.Core_User;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.service.IUserService;
import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.core.utils.LogUtil;

public class URLInterceptor implements HandlerInterceptor {
	private static final String[] IGNORE_URI = { "/login", "/index",
			"ConfigPV", "businessImgs", "wxUserAction", "/validateCode",
			"logout", "images", "qrCode", "Down", "wx", "/appointmeAction",
			"img", "squenceInfoAction", "wxAction", ".txt", "getSquenceAction",
			"deptAddressAction", "/appraiseDetailAction", "js", "photos", "/",
			"" };

	private IUserService userService;

	@Override
	public void afterCompletion(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse, Object paramObject,
			Exception paramException) throws Exception {
		System.out.println("afterCompletion");
	}

	@Override
	public void postHandle(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse, Object paramObject,
			ModelAndView paramModelAndView) throws Exception {
		System.out.println("postHandle");

	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		String context = request.getContextPath();
		Subject currentUser = SecurityUtils.getSubject();
		for (String s : IGNORE_URI) {
			if (uri.contains(s)) {
				System.out.println("直接跳转");
				return true;
			}
		}
		String checkAuth = getCheckAuth(uri, context);
		LogUtil.debug("权限:{}", checkAuth);
		Core_User user = (Core_User) currentUser.getSession().getAttribute(
				"user");
		if (user == null) {
			LogUtil.debug("isRemembered:{}", currentUser.isRemembered());
			LogUtil.debug("isAuthenticated:{}", currentUser.isAuthenticated());
			if (currentUser.getPrincipal() != null) {
				LogUtil.debug("Principal:{}", currentUser.getPrincipal()
						.toString());
				user = userService.getByUserName(currentUser.getPrincipal()
						.toString());
				currentUser.getSession().setAttribute("user", user);
			}
			LogUtil.debug("重新获取用户:{}", user);
		}
		LogUtil.debug("部门:{};编号:{}", user.getDept().getDeptName(), user
				.getDept().getDeptCode());
		if (user != null) {
			if (user.getIsSys()) {
				LogUtil.debug("系统级用户");
				return true;
			} else if (currentUser.isPermitted(checkAuth)) {
				LogUtil.debug("拥有权限");
				return true;
			} else {
				LogUtil.debug("没有权限");
			}
		}
		String requestType = request.getHeader("X-Requested-With");
		if ("XMLHttpRequest".equals(requestType)) {
			response.setCharacterEncoding("utf-8");
			DoResult result = new DoResult();
			result.setStateType(DoResultType.warning);
			result.setStateMsg("没有访问权限！");
			String resultJson = JacksonUtil.toJson(result);
			response.getWriter().print(resultJson);
		} else {
			request.getRequestDispatcher("/WEB-INF/jsp/Shared/error.jsp")
					.forward(request, response);
		}
		return false;
	}

	public String getCheckAuth(String uri, String context) {
		String str = uri;
		int beginIndex = str.indexOf("/",
				str.indexOf(context) + context.length()) + 1;
		int endIndex = str.indexOf("?", beginIndex);
		String auth = null;
		if (endIndex != -1) {
			auth = str.substring(beginIndex, endIndex);
		} else {
			auth = str.substring(beginIndex);
		}
		return auth.replace("/", ":");
	}
}

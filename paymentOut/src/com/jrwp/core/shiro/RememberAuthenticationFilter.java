package com.jrwp.core.shiro;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.jrwp.core.entity.Core_User;
import com.jrwp.core.service.IUserService;

public class RememberAuthenticationFilter extends FormAuthenticationFilter {
	@Resource
	private IUserService userService;

	/**
	 * 这个方法决定了是否能让用户登录
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) {
		Subject subject = getSubject(request, response);
		HttpServletRequest req = (HttpServletRequest) request;
		System.out.println("进入isAccessAllowed方法" + req.getRequestURI());
		// 如果 isAuthenticated 为 false 证明不是登录过的，同时 isRememberd 为true
		// 证明是没登陆直接通过记住我功能进来的
		if (subject.isRemembered()) {
			System.out.println("用户登录");
			// 获取session看看是不是空的
			Session session = subject.getSession();
			if (session.getAttribute("user") == null) {
				// 这边根据前面的前提假设，拿到的是username
				String userame = subject.getPrincipal().toString();
				Core_User user = userService.getByUserName(userame);
				System.out.println("重新登录--->" + user);
				if (user != null) {
					session.setAttribute("user", user);
				}
			}
		}

		// 这个方法本来只返回 subject.isAuthenticated() 现在我们加上 subject.isRemembered()
		// 让它同时也兼容remember这种情况
		return subject.isAuthenticated() || subject.isRemembered();
	}
}
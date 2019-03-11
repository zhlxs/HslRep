package com.jrwp.core.action;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Config;
import com.jrwp.core.entity.Core_Config;
import com.jrwp.core.entity.Core_Menu;
import com.jrwp.core.entity.Core_User;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.help.MenuHelper;
import com.jrwp.core.help.ValidationResult;
import com.jrwp.core.service.IConfigService;
import com.jrwp.core.service.IDeptService;
import com.jrwp.core.service.IMenuService;
import com.jrwp.core.service.IUserService;
import com.jrwp.core.utils.CheckImage;
import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.core.utils.LogUtil;
import com.jrwp.core.utils.SecurityCode;
import com.jrwp.core.utils.SecurityCode.SecurityCodeLevel;
import com.jrwp.core.utils.SecurityImage;

@Description(value = "公用部分", state = false)
@Controller
public class MainController {

	@Resource
	private CheckImage checkImage;

	@Resource
	private IConfigService configService;

	@Resource
	private IMenuService menuService;

	@Resource
	private IUserService userService;

	@Resource
	private IDeptService deptService;

	@Description("后台登录")
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		Subject currentUser = SecurityUtils.getSubject();

		mav.setViewName("main/errorIe");
		ServletContext context = request.getServletContext();
		init(context);
		if (currentUser.isAuthenticated()) {
			// 已经登录,跳转到后台页面
			mav.setViewName("redirect:/index");
		} else {
			if (currentUser.isRemembered()) {
				if (currentUser.getSession().getAttribute("user") != null) {
					mav.setViewName("redirect:/index");
				} else {
					mav.setViewName("main/userlogin");
				}
			} else {
				// 未登录,跳转到登录页面
				mav.setViewName("main/userlogin");
				// 后台表单验证使用对象
				// mav.addObject(new Core_User());
			}

		}
		return mav;
	}

	@Description("提交登录")
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public DoResult checkUserLogin(Core_User user, String checkCode,
			String remeberMe, HttpServletRequest request,
			HttpServletResponse response) {
		DoResult result = new DoResult();
		List<ValidationResult> results = new ArrayList<ValidationResult>();
		// 获取当前的用户
		Subject currentUser = SecurityUtils.getSubject();
		// 判断用户是否以认证登录
		HttpSession session = request.getSession();
		String code = (String) session.getAttribute("SESSION_SECURITY_CODE");
		if (!currentUser.isAuthenticated()) {
			if (!checkCode.equalsIgnoreCase(code)) {
				ValidationResult vr = new ValidationResult();
				vr.getMemberNames().add("checkCode");
				vr.setErrorMessage("验证码错误");
				results.add(vr);
			} else {
				System.out.println(DigestUtils.md5Hex(user.getPassword())
						.toUpperCase());
				UsernamePasswordToken token = new UsernamePasswordToken(
						user.getUserName(), DigestUtils
								.md5Hex(user.getPassword()).toUpperCase()
								.toCharArray());
				if (remeberMe != null) {
					token.setRememberMe(true);
				}
				try {
					currentUser.login(token);
					String ip = request.getRemoteAddr();
					user = userService.getByUserName(user.getUserName());
					user.setLastLoginIP(ip);
					user.setLastLoginTime(new Date());
					userService.updateForLogin(user);
					// Cookie userNameCookie = new Cookie("JRWP_USER_NAME",
					// user.getUserName());
					// Cookie userPasswardCookie = new Cookie("JRWP_USER_PWD",
					// DigestUtils.md5Hex(user.getPassword()));
					// response.addCookie(userNam
					// eCookie);
					// response.addCookie(userPasswardCookie);
				} catch (UnknownAccountException e) {
					ValidationResult vr = new ValidationResult();
					vr.getMemberNames().add("userName");
					vr.setErrorMessage("账号不存在");
					results.add(vr);
				} catch (DisabledAccountException e) {
					ValidationResult vr = new ValidationResult();
					vr.getMemberNames().add("userName");
					vr.setErrorMessage("账号未启用,请联系管理员");
					results.add(vr);
				} catch (IncorrectCredentialsException e) {
					ValidationResult vr = new ValidationResult();
					vr.getMemberNames().add("password");
					vr.setErrorMessage("密码输入不正确");
					results.add(vr);
				} catch (AuthenticationException e) {
					e.printStackTrace();
					LogUtil.error("登录失败:{}", e.getMessage());
					ValidationResult vr = new ValidationResult();
					vr.getMemberNames().add("userName");
					vr.setErrorMessage("登录失败");
					results.add(vr);
				}
			}
		}
		result.setValidationResults(results);
		if (results.size() != 0) {
			result.setStateType(DoResultType.validFail);
		} else {
			result.setStateType(DoResultType.success);
			result.setUrl(request.getContextPath() + "/login");
		}
		// if (results.size() != 0) {
		// model.put("stateType", 2);
		// model.put("stateValue", null);
		// model.put("stateMsg", null);
		// model.put("url", null);
		// } else {
		// model.put("stateType", 0);
		// model.put("stateValue", 2);
		// model.put("stateMsg", "登录成功");
		// model.put("url", "/Core/login");
		// }
		// view.setAttributesMap(model);
		System.out.println();
		return result;
	}

	@Description("退出登录")
	@RequestMapping("logout")
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		// 跳转到登录页面
		mav.setViewName("redirect:/login");
		return mav;
	}

	@Description("获取验证码")
	@RequestMapping("validateCode")
	public void validateCode(HttpServletRequest request,
			HttpServletResponse response) {
		// try {
		// checkImage.getCheckIMG(request, response);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// 如果开启Hard模式，可以不区分大小写
		String securityCode = SecurityCode.getSecurityCode(4,
				SecurityCodeLevel.Medium, false).toLowerCase();
		// 获取默认难度和长度的验证码
		// String securityCode = SecurityCode.getSecurityCode();
		// ByteArrayInputStream imageStream = SecurityImage
		// .getImageAsInputStream(securityCode);
		BufferedImage bi = SecurityImage.createImage(securityCode);
		// 放入session中
		HttpSession session = request.getSession();
		session.setAttribute("SESSION_SECURITY_CODE", securityCode);
		// 设置图像缓存为no-cache。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		// 将最终生产的验证码图片输出到Servlet的输出流中
		ServletOutputStream sos = null;
		try {
			sos = response.getOutputStream();
			ImageIO.write(bi, "jpeg", sos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				sos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Description("错误提示页")
	@RequestMapping("errPage")
	public String errPage() {
		// error
		return "errPage";
	}

	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/")
	public String index() {
		return "redirect:/index";
	}

	/**
	 * 首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "index")
	public ModelAndView index(Model model, HttpServletRequest request) {
		// 在客户端保存用户名密码
		// Subject currentUser = SecurityUtils.getSubject();
		// Core_User user = (Core_User)
		// request.getSession().getAttribute("user");
		// if (user == null) {
		// String userName = null;
		// String pwd = null;
		// Cookie[] cookies = request.getCookies();
		// for (Cookie cookie : cookies) {
		// if (cookie.getName().equals("JRWP_USER_NAME")) {
		// userName = cookie.getValue();
		// } else if (cookie.getName().equals("JRWP_USER_PWD")) {
		// pwd = cookie.getValue();
		// }
		// }
		// UsernamePasswordToken token = new UsernamePasswordToken(userName,
		// pwd.toCharArray());
		// token.setRememberMe(true);
		// currentUser.login(token);
		// }
		ModelAndView mav = new ModelAndView();
		mav.setViewName("main/index");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		Core_User user = (Core_User) session.getAttribute("user");
		try {
			if (user == null) {
				user = userService.getByUserName(currentUser.getPrincipal()
						.toString());
				session.setAttribute("user", user);
			}
			List<Core_Menu> menuList = null;

			if (user.getIsSys()) {
				menuList = menuService.syslist();
			} else {
				menuList = menuService.getListByUserId(user.getId());
				System.out.println(user.getId());
			}
			List<Core_Menu> group = MenuHelper.group(menuList);
			String str = JacksonUtil.toJson(group);
			session.setAttribute("menuList", str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServletContext context = request.getServletContext();
		init(context);
		return mav;
	}

	/**
	 * 未授权
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "unauth")
	public ModelAndView unauth(Model model) {
		ModelAndView mav = new ModelAndView();
		if (SecurityUtils.getSubject().isAuthenticated() == false) {
			mav.setViewName("redirect:/login");
		} else {
			mav.setViewName("Shared/error");
		}
		return mav;
	}

	/**
	 * 初始化全局配置
	 */
	private void init(ServletContext context) {
		// 获得系统信息
		List<Core_Config> sysBaseInfo = configService.getSysBaseInfo();
		// 将系统信息放入上下文
		for (int i = 0; i < sysBaseInfo.size(); i++) {
			Core_Config core_Config = sysBaseInfo.get(i);
			if (core_Config.getConfigName().equals("sysBaseInfo")) {
				Config config = JacksonUtil.readValue(
						core_Config.getConfigJson(), Config.class);
				context.setAttribute("sysBaseInfo", config);
			}
		}
	}
}

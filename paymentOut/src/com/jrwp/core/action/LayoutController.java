package com.jrwp.core.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Core_User;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.help.ValidationResult;
import com.jrwp.core.service.IUserService;
import com.jrwp.core.utils.LogUtil;

@Description("管理后台")
@Controller
@RequestMapping("layoutController")
public class LayoutController {

	// private ILayoutService layoutService;
	@Resource
	private IUserService userService;

	@Description("框架")
	@RequestMapping("init")
	public String init() {
		return "init";
	}

	@Description("密码修改")
	@RequestMapping("changePwd")
	@ResponseBody
	public ModelAndView changePwd(ModelAndView mav) {
		mav.setViewName("Layout/changePwd");
		return mav;
	}

	@SuppressWarnings("unused")
	@Description("确认密码修改")
	@RequestMapping("modifPassword")
	@ResponseBody
	public DoResult modifPassWord(String oldPassWord, String newPassWord,
			HttpSession session) {
		DoResult result = new DoResult();
		if (!StringUtils.isEmpty(oldPassWord)) {
			oldPassWord = DigestUtils.md5Hex(oldPassWord);
			Subject currentUser = SecurityUtils.getSubject();
			Core_User user = (Core_User) currentUser.getSession().getAttribute(
					"user");
			Long deptid = userService.getdeptid(user.getUserName());
			user.setDeptId(deptid);
			if (user == null) {
				String userName = currentUser.getPrincipal().toString();
				user = userService.getByUserName(userName);
			} else {
				try {
					if (user.getPassword().equals(oldPassWord)) {
						user.setPassword(DigestUtils.md5Hex(newPassWord));
						userService.update(user);
						result.setStateType(DoResultType.success);
						result.setStateMsg("密码修改成功");
					} else {
						result.setStateType(DoResultType.validFail);
						result.setStateMsg("密码修改失败");
						List<ValidationResult> results = new ArrayList<ValidationResult>();
						ValidationResult vr = new ValidationResult();
						vr.getMemberNames().add("oldPassWord");
						vr.setErrorMessage("原始密码不正确");
						results.add(vr);
						result.setValidationResults(results);
					}
				} catch (Exception e) {
					LogUtil.error("密码修改失败:{}", e.getMessage());
					result.setStateType(DoResultType.fail);
					result.setStateMsg("密码修改失败");
				}
			}
		}
		return result;
	}
}

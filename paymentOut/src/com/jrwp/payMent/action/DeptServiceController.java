package com.jrwp.payMent.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jrwp.core.annotation.Description;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.payMent.entity.UserService;
import com.jrwp.payMent.help.DeptServiceHelper;
import com.jrwp.payMent.service.DeptServiceBiz;
import com.jrwp.payMent.service.IPoliceClassService;

@Description("部门开通业务")
@Controller
@RequestMapping("deptServiceController")
public class DeptServiceController {

	@Resource
	private DeptServiceBiz deptServiceBiz;
	@Resource
	private IPoliceClassService policeClassService;

	@Description("部门业务开通")
	@RequestMapping("deptBizInfo")
	public ModelAndView userBizInfo(Long id) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("deptService/deptBizInfo");
		mav.addObject("uiCode", id);
		return mav;
	}

	@Description("部门业务开通json")
	@RequestMapping("deptBizInfoJson")
	@ResponseBody
	public DeptServiceHelper userBizInfoJson(String uiCode) {
		DeptServiceHelper deptServiceHelper = new DeptServiceHelper();

		List<UserService> userServices = new ArrayList<UserService>();

		try {
			userServices = deptServiceBiz.getByUsCode(uiCode);
		} catch (Exception e) {

			e.printStackTrace();
		}
		deptServiceHelper.setItems(userServices);

		return deptServiceHelper;
	}

	// 给对应的部门添加业务，跳转的页面
	@Description("部门对应业务添加")
	@RequestMapping("addDeptService")
	public ModelAndView addUserService(String uiCode) {
		ModelAndView model = new ModelAndView();
		model.setViewName("deptService/deptserviceinfo");
		model.addObject("uiCode", uiCode);
		return model;
	}

	// 给对应的部门添加业务，跳转的页面
	@Description("部门对应业务添加")
	@RequestMapping("policeServiceDialog")
	public ModelAndView addUserService1(String uiCode) {
		ModelAndView model = new ModelAndView();
		model.setViewName("deptService/adddeptService");
		model.addObject("uiCode", uiCode);
		return model;
	}

	@Description("部门对应业务保存")
	@RequestMapping("saveDeptService")
	@ResponseBody
	public DoResult saveDeptService(String[] checkid, String uiCode,String writeBackUrl,String payCode,String orderDescribe) {
		DoResult result = new DoResult();
		try {
			for (String s : checkid) {
				UserService userService = new UserService();
				userService.setSerCode(s);
				userService.setOrderDescribe(orderDescribe);
				userService.setPayCode(payCode);
				userService.setWriteBackUrl(writeBackUrl);
				userService.setUiCode(uiCode);
				deptServiceBiz.save(userService);
			}
			/* result.setStateMsg("添加成功！"); */
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			result.setStateMsg("添加失败！");
			result.setStateType(DoResultType.fail);
			e.printStackTrace();
		}
		return result;
	}

	// 将isdel修改为1（默认为0）
	@Description("部门对应业务删除")
	@RequestMapping("deleteDeptService")
	@ResponseBody
	public DoResult deleteUserService(String usCode) {
		DoResult result = new DoResult();

		try {
			if (usCode != null && !usCode.equals("")) {
				deptServiceBiz.updateisdel(usCode);
				result.setStateMsg("删除成功！");
				result.setStateType(DoResultType.success);
			}
		} catch (Exception e) {
			result.setStateMsg("删除失败！");
			result.setStateType(DoResultType.fail);
			e.printStackTrace();
		}

		return result;
	}
}

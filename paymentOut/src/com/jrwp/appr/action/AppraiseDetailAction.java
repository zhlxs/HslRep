package com.jrwp.appr.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jrwp.appr.entity.AppraiseDetail;
import com.jrwp.appr.entity.AppraiseModel;
import com.jrwp.appr.entity.Question;
import com.jrwp.appr.service.AppraiseDetailService;
import com.jrwp.appr.service.AppraiseModelService;
import com.jrwp.appr.service.QuestionService;
import com.jrwp.follow.entity.QrCodeParms;
import com.jrwp.payMent.entity.DicDeptaddress;
import com.jrwp.payMent.service.DeptaddressService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("wx/appraiseDetailAction")
@Controller
public class AppraiseDetailAction {

	@Resource
	private AppraiseDetailService detailService;
	@Resource
	private AppraiseModelService appraiseModelService;
	@Resource
	private QuestionService questionService;
	@Resource
	private DeptaddressService deptaddressService;

	// @RequestMapping(value = "appraiseDetailJson", produces =
	// "application/json;charset=utf-8", method = RequestMethod.POST)
	// @ResponseBody
	// public DoResult AppraiseDetailJson(Long id) {
	// DoResult result = new DoResult();
	// try {
	// AppraiseDetail appraiseDetail = detailService.getObjectById(id);
	// result.setStateValue(JacksonUtil.toJson(appraiseDetail));
	// result.setStateType(DoResultType.success);
	// result.setStateMsg("获取信息成功");
	// } catch (Exception e) {
	// e.printStackTrace();
	// result.setStateType(DoResultType.fail);
	// result.setStateMsg("接口异常");
	// }
	// return result;
	// }

	@RequestMapping("apprList")
	public ModelAndView ApprView(Long id, Long appointmeId, Long deptId,
			HttpServletRequest request) {
		id = 1l;
		ModelAndView maView = new ModelAndView();
		try {
			// QrCodeParms parms = (QrCodeParms) request.getSession()
			// .getAttribute("QrParms");
			// id = parms.getEvluateID();
			DicDeptaddress deptaddress = deptaddressService
					.getObjectByDeptId(deptId);
			Long modelId = deptaddress.getModelId();
			if (modelId != null) {
				id = modelId;
			}
			AppraiseModel model = appraiseModelService.getObjectById(id);
			List<AppraiseDetail> list = detailService.list(id);
			for (AppraiseDetail appraiseDetail : list) {
				String ApprName = appraiseDetail.getAppraisename();
				String[] names = ApprName.split(",");
				appraiseDetail.getHotwords().addAll(Arrays.asList(names));
			}
			maView.setViewName("wxAppr/evaluate");
			maView.addObject("appointmeId", appointmeId);
			maView.addObject("list", list);
			maView.addObject("id", id);
			maView.addObject("model", model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maView;
	}

	@RequestMapping("apprListJson")
	@ResponseBody
	public List<AppraiseDetail> apprListJson(Long id, Long appointmeId,
			HttpServletRequest request) {
		List<AppraiseDetail> list = new ArrayList<>();
		try {
			id = 1l;
			list = detailService.list(id);
			for (AppraiseDetail appraiseDetail : list) {
				String ApprName = appraiseDetail.getAppraisename();
				String[] names = ApprName.split(",");
				appraiseDetail.getHotwords().addAll(Arrays.asList(names));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping("finish")
	public ModelAndView finishView(Long id, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			System.out.println("=============进入finish");
			QrCodeParms parms = (QrCodeParms) request.getSession()
					.getAttribute("QrParms");
			modelAndView.addObject("id", id);
			modelAndView.addObject("apprseq", parms.getApprseq());
			modelAndView.setViewName("wxAppr/finish");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}

	@RequestMapping("unfinish")
	public ModelAndView unfinishView(Long id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("id", id);
		modelAndView.setViewName("wxAppr/unfinish");
		return modelAndView;
	}

	/**
	 * 调查问卷
	 */
	@RequestMapping("GotoTest")
	public ModelAndView Test(Long id) {
		ModelAndView mav = new ModelAndView();
		List<Question> list = questionService.getQuestions(id);
		for (Question question : list) {
			String answer = question.getOptions().replace(" ", "");
			String[] option = answer.split("\\|");
			question.getAnswers().addAll(Arrays.asList(option));
		}
		mav.setViewName("wxAppr/after_alert");
		mav.addObject("list", list);
		return mav;
	}
}

package com.jrwp.appr.action;

import com.jrwp.appr.entity.AppraiseDetail;
import com.jrwp.appr.entity.TestCount;
import com.jrwp.appr.entity.UserAppraise;
import com.jrwp.appr.service.AppraiseDetailService;
import com.jrwp.appr.service.TestCountService;
import com.jrwp.appr.service.UserAppraiseService;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.follow.entity.QrCodeParms;
import com.jrwp.wx.dao.Sequence_InfoDao;
import com.jrwp.wx.entity.Sequence_Info;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

@RequestMapping("wx/userAppraiseAction")
@Controller
public class UserAppraiseAction {

	@Resource
	private UserAppraiseService userAppraiseService;
	@Resource
	private AppraiseDetailService ApprdetailService;
	@Resource
	private TestCountService countService;
	@Resource
	private Sequence_InfoDao sequence_InfoDao;

	@RequestMapping(value = "saveUserAppr", produces = "application/json;charset=utf-8")
	@ResponseBody
	public DoResult SaveUserAppr(Long modelId, String description,
			String otheradvice, int point, HttpServletRequest request,
			Long userid, Long orderid, Integer apprseq, Long appointmeId) {
		// id = 1l;
		if (modelId == null) {
			modelId = 1l;
		}
		DoResult result = new DoResult();
		try {
			// 获取session中的参数
			// QrCodeParms parms = (QrCodeParms) request.getSession()
			// .getAttribute("QrParms");
			// userid = parms.getUserID();
			// orderid = parms.getOrderid();
			// apprseq = parms.getApprseq();// 评价顺序
			UserAppraise userAppraise = new UserAppraise();
			List<AppraiseDetail> list = ApprdetailService.list(modelId);
			for (int i = 0; i < list.size(); i++) {
				AppraiseDetail detail = list.get(i);
				int apprValue = detail.getAppraisevalue();
				if (point == apprValue) {// 五颗星处理方式:直接插库
					userAppraise.setAppraisedetailid(detail.getId());
				} else {// 三颗星处理方式:对应匹配
					if (point == 2) {
						if (apprValue == 3) {
							userAppraise.setAppraisedetailid(detail.getId());
						}
					} else {
						if (apprValue == 5) {
							userAppraise.setAppraisedetailid(detail.getId());
						}
					}
				}
			}
			// QrCodeParms parms = (QrCodeParms)
			// request.getSession().getAttribute("QrParms");
			// userid = parms.getUserID();
			// orderid = parms.getOrderid();
			/**
			 * 获取对应队列
			 */
			Sequence_Info sequenceInfo = sequence_InfoDao
					.getSequenceByAppointId(appointmeId);
			// 保存队列id
			if (sequenceInfo != null) {
				userAppraise.setSequence_infoid(sequenceInfo.getId());
			}
			userAppraise.setIswrite(0);
			userAppraise.setHotwords(description);
			userAppraise.setUserid(userid);// 测试用
			userAppraise.setOrderid(orderid);// 测试用
			userAppraise.setOtheradvice(otheradvice);
			userAppraise.setIsexport(0);// 默认值0
			userAppraise.setIsdefault(1);// 默认主动评价
			userAppraise.setIswechat(1);// 微信评价
			userAppraiseService.saveAppr(userAppraise, appointmeId);
			result.setStateValue(apprseq);
			result.setStateType(DoResultType.success);
			result.setStateMsg("评价成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("评价失败");
		}
		return result;
	}

	/**
	 * 保存问卷调查结果
	 * 
	 * @return
	 */
	@RequestMapping("saveAnswer")
	@ResponseBody
	public DoResult SaveUserAnswer(Long[] testid, String[] answers,
			HttpServletRequest request) {
		DoResult result = new DoResult();
		try {
			QrCodeParms parms = (QrCodeParms) request.getSession()
					.getAttribute("QrParms");
			long userid = parms.getUserID();
			long orderid = parms.getOrderid();
			if (testid.length == answers.length) {
				for (int i = 0; i < testid.length; i++) {
					TestCount count = new TestCount();
					count.setUserid(userid);
					count.setTestid(testid[i]);
					count.setAnswer(answers[i]);
					count.setOrderid(orderid);
					countService.insertSelective(count);
				}
				result.setStateType(DoResultType.success);
				result.setStateMsg("保存成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("保存失败");
		}
		return result;
	}
}

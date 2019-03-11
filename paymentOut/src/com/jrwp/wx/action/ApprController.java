package com.jrwp.wx.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jrwp.core.annotation.Description;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.wx.entity.AppraiseInfoForAndroid;
import com.jrwp.wx.service.AppraiseService;
import com.jrwp.wx.until.PmListUntil;
import com.jrwp.wx.until.PmWaitAppraiseHelper;

@Description("平板评价接口")
@Controller
@RequestMapping("appraise/appraiseController")
public class ApprController {
	private final Logger logger=Logger.getLogger(ApprController.class);
	
	@Resource
	private AppraiseService appraiseService;
	/*@Description("获取待评价信息")
	@RequestMapping("getappraiseInfo")
	@ResponseBody
	public DoResult getappraiseInfo(HttpServletRequest request,HttpServletResponse response,String jqm) {
		DoResult result = new DoResult();
		try {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(currentTime);
			String squence_infoid = appraiseService.getSquenceidByCkbh(jqm,dateString);
			result.setStateValue(squence_infoid);
			result.setStateMsg("信息获取成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("获取失败");
			logger.error("在获取待评价信息时候发生异常：",e);
		}
		return result;
	}*/
	
	
	@Description("提交评价信息")
	@RequestMapping("submitappraiseInfo")
	@ResponseBody
	public DoResult submitAppraiseInfo(HttpServletRequest request,HttpServletResponse response,/*Long appraisedetailid*/int value,int isdefault,String squence_infoid) {
		DoResult result = new DoResult();
		try {
			appraiseService.saveAndUpdateStatu(/*appraisedetailid*/(long)1,isdefault,squence_infoid);
			result.setStateMsg("提交成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("提交失败");
			logger.error("在提交评价信息的时候发生异常：",e);
		}
		return result;
	}
	
	@Description("获取待评价信息")
	@RequestMapping("getappraiseInfo")
	@ResponseBody
	public DoResult getappraiseInfo(HttpServletRequest request,HttpServletResponse response,String jqm) {
		DoResult result = new DoResult();
		List<AppraiseInfoForAndroid> all = new ArrayList<>();
		String squence_infoid  = null;
		try {
			List<AppraiseInfoForAndroid> forAndroid = PmWaitAppraiseHelper.getInstance().dateChange(1, null);
			all = PmListUntil.listCopy2(forAndroid);
			if(all != null && all.size()>0){
				for(int i=0; i < all.size();i++){
					System.out.println("=========="+all.get(i).getSquence_infoid());
					if(all.get(i).getJqm().equals(jqm)){
						squence_infoid = all.get(i).getSquence_infoid();
						break;
					}
				}
			}

			result.setStateValue(squence_infoid);
			result.setStateMsg("信息获取成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("获取失败");
			logger.error("在获取待评价信息时候发生异常：",e);
		}
		return result;
	}
}

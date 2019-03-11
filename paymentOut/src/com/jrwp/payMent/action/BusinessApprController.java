package com.jrwp.payMent.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrwp.core.annotation.Description;
import com.jrwp.payMent.entity.BusinessAppraise;
import com.jrwp.payMent.help.BusinessAppraiseHelper;
import com.jrwp.payMent.service.BusinessAppraiseService;

@Description("业务评价考核管理")
@Controller
@RequestMapping("businessApprController")
public class BusinessApprController {

	@Resource
	private BusinessAppraiseService businessAppraiseService;

	@Description("业务评价列表")
	@RequestMapping("businessApprList")
	public ModelAndView businessApprList() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("appraise/List");
		return maView;
	}

	@Description("业务评价列表Json")
	@RequestMapping("businessApprListJson")
	@ResponseBody
	public BusinessAppraiseHelper businessApprListJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			String queryinfo) {
		BusinessAppraiseHelper businessAppraiseHelper = null;
		List<BusinessAppraise> list = new ArrayList<>();
		try {
			PageHelper.startPage(pageNum, pageSize);
			list = businessAppraiseService.list(queryinfo);
			PageInfo<BusinessAppraise> pageInfo = new PageInfo<>(list);
			businessAppraiseHelper = new BusinessAppraiseHelper(pageInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return businessAppraiseHelper;
	}
}

package com.jrwp.payMent.action;

import java.util.ArrayList;
import java.util.Date;
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
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.utils.LogUtil;
import com.jrwp.payMent.entity.TheBlackList;
import com.jrwp.payMent.help.BlackListHelper;
import com.jrwp.payMent.service.TheBlackListService;

/**
 * 黑名单管理
 * 
 * @author hsl
 * 
 */
@Description("黑名单管理")
@Controller
@RequestMapping("blackListController")
public class TheBlackListController {

	@Resource
	private TheBlackListService blackListService;

	@Description("黑名单列表")
	@RequestMapping("blackList")
	public ModelAndView blackList() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("blacklist/List");
		return maView;
	}

	@Description("黑名单列表Json")
	@RequestMapping("blackListJson")
	@ResponseBody
	public BlackListHelper blackListJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
			String queryinfo) {
		BlackListHelper blackListHelper = null;
		List<TheBlackList> list = new ArrayList<>();
		try {
			PageHelper.startPage(pageNum, pageSize);
			list = blackListService.list(queryinfo);
			PageInfo<TheBlackList> pageInfo = new PageInfo<>(list);
			blackListHelper = new BlackListHelper(pageInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return blackListHelper;
	}

	@Description("添加黑名单用户")
	@RequestMapping("addBlack")
	public ModelAndView addBlack() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("blacklist/Info");
		return maView;
	}

	@Description("保存黑名单用户")
	@RequestMapping("saveBlack")
	@ResponseBody
	public DoResult saveBlack(TheBlackList blackList) {
		DoResult result = new DoResult();
		try {
			blackList.setCreatetime(new Date());
			blackListService.save(blackList);
			result.setStateMsg("保存成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("保存失败");
			result.setStateType(DoResultType.fail);
			LogUtil.error("保存黑名单异常:{}", e.getMessage());
		}
		return result;
	}

	@Description("删除黑名单用户")
	@RequestMapping("deleteBlack")
	@ResponseBody
	public DoResult deleteBlack(Long id) {
		DoResult result = new DoResult();
		try {
			blackListService.delete(id);
			result.setStateMsg("删除成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("删除失败");
			result.setStateType(DoResultType.fail);
			LogUtil.error("删除黑名单异常:{}", e.getMessage());
		}
		return result;
	}
}

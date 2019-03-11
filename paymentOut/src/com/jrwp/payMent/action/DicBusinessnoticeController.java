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
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.utils.LogUtil;
import com.jrwp.payMent.entity.DicBusinessnotice;
import com.jrwp.payMent.help.DicBusinessnoticeHelper;
import com.jrwp.payMent.service.DicBusinessnoticeService;

@Description("流程、办事配置管理")
@Controller
@RequestMapping("dicBusinessnoticeController")
public class DicBusinessnoticeController {

	@Resource
	private DicBusinessnoticeService businessnoticeService;

	@Description("流程、办事列表")
	@RequestMapping("noticeList")
	public ModelAndView noticeList() {
		ModelAndView maAndView = new ModelAndView();
		maAndView.setViewName("businessnotice/List");
		return maAndView;
	}

	@Description("流程、办事列表Json")
	@RequestMapping("noticeListJson")
	@ResponseBody
	public DicBusinessnoticeHelper noticeListJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			String queryinfo) {
		DicBusinessnoticeHelper dicBusinessnoticeHelper = null;
		List<DicBusinessnotice> list = new ArrayList<>();
		try {
			PageHelper.startPage(pageNum, pageSize);
			list = businessnoticeService.list(queryinfo);
			PageInfo<DicBusinessnotice> pageInfo = new PageInfo<>(list);
			dicBusinessnoticeHelper = new DicBusinessnoticeHelper(pageInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dicBusinessnoticeHelper;
	}

	@Description("添加流程、依据")
	@RequestMapping("addBusinessnotice")
	public ModelAndView addBusinessnotice() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("businessnotice/Info");
		return maView;
	}

	@Description("编辑流程、依据")
	@RequestMapping("editBusinessnotice")
	public ModelAndView editBusinessnotice(long id) {
		ModelAndView maView = new ModelAndView();
		DicBusinessnotice businessnotice = businessnoticeService
				.getObjectById(id);
		maView.addObject("businessnotice", businessnotice);
		maView.setViewName("businessnotice/Info");
		return maView;
	}

	@Description("保存流程、依据")
	@RequestMapping("saveBusinessnotice")
	@ResponseBody
	public DoResult saveBusinessnotice(DicBusinessnotice businessnotice) {
		DoResult result = new DoResult();
		try {
			businessnoticeService.save(businessnotice);
			result.setStateMsg("保存成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.error("流程、依据保存失败:{}", e.getMessage());
			result.setStateMsg("保存失败");
			result.setStateType(DoResultType.fail);
		}
		return result;
	}

	@Description("删除流程、依据")
	@RequestMapping("delete")
	@ResponseBody
	public DoResult delete(long id) {
		DoResult result = new DoResult();
		try {
			businessnoticeService.delete(id);
			result.setStateMsg("删除成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.error("流程、依据删除失败:{}", e.getMessage());
			result.setStateMsg("删除失败");
			result.setStateType(DoResultType.fail);
		}
		return result;
	}

	@Description("流程、依据详情")
	@RequestMapping("businessnoticeDetail")
	public ModelAndView businessnoticeDetail(long id) {
		ModelAndView maView = new ModelAndView();
		DicBusinessnotice businessnotice = businessnoticeService
				.getObjectById(id);
		maView.addObject("businessnotice", businessnotice);
		maView.setViewName("businessnotice/detail");
		return maView;
	}

	@Description("流程、依据列表弹出层")
	@RequestMapping("noticeDialog")
	public ModelAndView noticeDialog(String actiontype, String modelId) {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("businessnotice/dialog");
		maView.addObject("actiontype", actiontype);
		maView.addObject("modelId",
				modelId == null || modelId == "" ? "0" : modelId);
		return maView;
	}
}

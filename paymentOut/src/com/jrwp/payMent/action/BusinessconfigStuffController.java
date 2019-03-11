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
import com.jrwp.payMent.entity.BusinessconfigStuff;
import com.jrwp.payMent.entity.BusinessconfigType;
import com.jrwp.payMent.help.BusinessconfigStuffHelper;
import com.jrwp.payMent.service.BusinessconfigStuffService;
import com.jrwp.payMent.service.BusinessconfigTypeService;

@Description("业务申请材料管理")
@Controller
@RequestMapping("businessconfigStuController")
public class BusinessconfigStuffController {

	@Resource
	private BusinessconfigStuffService businessconfigStuffService;
	@Resource
	private BusinessconfigTypeService businessconfigTypeService;

	@Description("申请材料列表")
	@RequestMapping("businessSruffList")
	public ModelAndView businessStuffList(Long applytypeid, String serCode) {
		ModelAndView maView = new ModelAndView();
		maView.addObject("id", applytypeid);
		maView.addObject("serCode", serCode);
		maView.setViewName("businessStuff/List");
		return maView;
	}

	@Description("申请材料列表Json")
	@RequestMapping("businessStuffJson")
	@ResponseBody
	public BusinessconfigStuffHelper businessStuffJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			Long id) {
		BusinessconfigStuffHelper businessconfigStuffHelper = null;
		List<BusinessconfigStuff> list = new ArrayList<>();
		try {
			PageHelper.startPage(pageNum, pageSize);
			list = businessconfigStuffService.listChildren(id);
			PageInfo<BusinessconfigStuff> page = new PageInfo<>(list);
			businessconfigStuffHelper = new BusinessconfigStuffHelper(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return businessconfigStuffHelper;
	}

	@Description("添加申请材料")
	@RequestMapping("addBusinessStuff")
	public ModelAndView addBusinessStuff(Long applytypeid) {
		ModelAndView maView = new ModelAndView();
		BusinessconfigType businessconfigType = businessconfigTypeService
				.getObjectById(applytypeid);
		maView.addObject("weApplytype", businessconfigType);
		maView.setViewName("businessStuff/Info");
		return maView;
	}

	@Description("编辑申请材料")
	@RequestMapping("editBusinessStuff")
	public ModelAndView editBusinessStuff(Long id, Long applytypeid) {
		ModelAndView maView = new ModelAndView();
		try {
			BusinessconfigType businessconfigType = businessconfigTypeService
					.getObjectById(applytypeid);
			BusinessconfigStuff businessconfigStuff = businessconfigStuffService
					.getObjectById(id);
			maView.addObject("weApplytype", businessconfigType);
			maView.addObject("weBusinessconfigstr", businessconfigStuff);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		maView.setViewName("businessStuff/Info");
		return maView;
	}

	@Description("保存申请材料")
	@RequestMapping("saveBusinessStuff")
	@ResponseBody
	public DoResult saveBusinessStuff(BusinessconfigStuff businessconfigStuff) {
		DoResult result = new DoResult();
		try {
			businessconfigStuffService.save(businessconfigStuff);
			result.setStateMsg("保存成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("保存失败");
			result.setStateType(DoResultType.fail);
		}
		return result;
	}

	@Description("材料启用状态")
	@RequestMapping("showBusinessconfigstr")
	@ResponseBody
	public DoResult showBusinessconfigstr(long id, Boolean ismustfill) {
		DoResult result = new DoResult();
		try {
			BusinessconfigStuff businessconfigStuff = new BusinessconfigStuff();
			businessconfigStuff.setId(id);
			businessconfigStuff.setIsmustfill(ismustfill);
			businessconfigStuffService.update(businessconfigStuff);
			result.setStateMsg("更新成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			LogUtil.error("材料启用状态更新失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg("操作失败");
		}
		return result;
	}

	@Description("移动菜单")
	@RequestMapping("moveBusinessconfigstr")
	@ResponseBody
	public DoResult moveBusinessconfigstr(long id, int movetype) {
		DoResult result = new DoResult();
		try {
			businessconfigStuffService.move(id, movetype);
			result.setStateMsg("移动完成");
			result.setStateType(DoResultType.success);
			result.setStateValue(id);
		} catch (Exception e) {
			if (("已经排在此类型分类第一了！").equals(e.getMessage())) {
				result.setStateMsg("已经排在此类型分类第一了！");
			} else if (("已经排在此类型分类末尾了！").equals(e.getMessage())) {
				result.setStateMsg("已经排在此类型分类末尾了！");
			} else {
				LogUtil.error("移动失败:{}", e.getMessage());
				result.setStateType(DoResultType.fail);
				// result.setStateMsg(e.getMessage());
				result.setStateMsg("移动失败");
			}
		}
		return result;
	}

	@Description("删除当前申请材料")
	@RequestMapping("delete")
	@ResponseBody
	public DoResult delete(long id) {
		DoResult result = new DoResult();
		try {
			businessconfigStuffService.delete(id);
			result.setStateMsg("删除成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LogUtil.error("申请材料删除失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg("申请材料删除失败");
		}
		return result;
	}
}

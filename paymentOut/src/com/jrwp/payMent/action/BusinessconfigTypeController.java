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
import com.jrwp.payMent.entity.BusinessconfigType;
import com.jrwp.payMent.entity.PoliceClassService;
import com.jrwp.payMent.help.BusinessconfigTypeHelper;
import com.jrwp.payMent.service.BusinessconfigTypeService;
import com.jrwp.payMent.service.IPoliceClassService;

@Description("业务申请类型管理")
@Controller
@RequestMapping("businessConfigTypeController")
public class BusinessconfigTypeController {

	@Resource
	private BusinessconfigTypeService businessconfigTypeService;
	@Resource
	private IPoliceClassService iPoliceClassService;

	@Description("业务类型列表")
	@RequestMapping("typeList")
	public ModelAndView typeList(String serCode) {
		ModelAndView maView = new ModelAndView();
		maView.addObject("serCode", serCode);
		maView.setViewName("businessType/List");
		return maView;
	}

	@Description("业务类型列表Json")
	@RequestMapping("businessTypeJson")
	@ResponseBody
	public BusinessconfigTypeHelper businessTypeJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
			String serCode) {
		BusinessconfigTypeHelper businessconfigTypeHelper = null;
		List<BusinessconfigType> list = new ArrayList<>();
		try {
			PageHelper.startPage(pageNum, pageSize);
			list = businessconfigTypeService.listChild(serCode);
			PageInfo<BusinessconfigType> pageInfo = new PageInfo<>(list);
			businessconfigTypeHelper = new BusinessconfigTypeHelper(pageInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LogUtil.error("业务申请类型列表异常:{}", e.getMessage());
		}
		return businessconfigTypeHelper;
	}

	@Description("添加业务申请类型")
	@RequestMapping("addBusinessType")
	public ModelAndView addBusinessType(String serCode) {
		ModelAndView maView = new ModelAndView();
		PoliceClassService policeClassService = iPoliceClassService
				.getObjectByCode(serCode);
		maView.addObject("serCode", serCode);
		maView.addObject("policeClassService", policeClassService);
		maView.setViewName("businessType/Info");
		return maView;
	}

	@Description("编辑业务申请类型")
	@RequestMapping("editBusinessType")
	public ModelAndView enitBusinessType(Long id, String serCode) {
		ModelAndView maView = new ModelAndView();
		maView.addObject("serCode", serCode);
		try {
			PoliceClassService policeClassService = iPoliceClassService
					.getObjectByCode(serCode);
			BusinessconfigType businessconfigType = businessconfigTypeService
					.getObjectById(id);
			maView.addObject("policeClassService", policeClassService);
			maView.addObject("weApplytype", businessconfigType);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		maView.setViewName("businessType/Info");
		return maView;
	}

	@Description("保存业务申请类型")
	@RequestMapping("saveBusinessType")
	@ResponseBody
	public DoResult saveBusinessType(BusinessconfigType businessconfigType) {
		DoResult result = new DoResult();
		try {
			businessconfigTypeService.save(businessconfigType);
			result.setStateMsg("保存成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.error("保存业务申请类型失败:{}", e.getMessage());
			result.setStateMsg("保存失败");
			result.setStateType(DoResultType.fail);
		}
		return result;
	}

	@Description("是否有效")
	@RequestMapping("isValidType")
	@ResponseBody
	public DoResult isValidType(Long id, Boolean isValid) {
		DoResult result = new DoResult();
		try {
			BusinessconfigType businessconfigType = new BusinessconfigType();
			businessconfigType.setId(id);
			businessconfigType.setIsValid(isValid);
			businessconfigTypeService.update(businessconfigType);
			result.setStateType(DoResultType.success);
			result.setStateMsg("业务类型有效状态更改成功");
		} catch (Exception e) {
			LogUtil.error("业务类型有效状态更改失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg("状态更新失败");
		}
		return result;
	}
	
	@Description("删除业务申请类型")
	@RequestMapping("delete")
	@ResponseBody
	public DoResult deleteBusinessType(long id){
		DoResult result = new DoResult();
		try {
			businessconfigTypeService.delete(id);
			result.setStateMsg("删除失败");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LogUtil.error("业务申请类型删除失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg("业务申请类型删除失败");
		}
		return result;
	}
}

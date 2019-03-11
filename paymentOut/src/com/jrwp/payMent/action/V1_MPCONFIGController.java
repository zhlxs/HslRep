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
import com.jrwp.payMent.entity.MAC_PHOTO_CONFIG;
import com.jrwp.payMent.entity.Mac_Windows;
import com.jrwp.payMent.help.CommonHelper;
import com.jrwp.payMent.service.IMAC_PHOTO_CONFIGService;
import com.jrwp.payMent.service.IMac_WindowsService;

@Description("设备样图配置管理")
@Controller
@RequestMapping("mpcConfigController")
public class V1_MPCONFIGController {

	@Resource
	private IMAC_PHOTO_CONFIGService macConfigService;
	@Resource
	private IMac_WindowsService macWinService;

	@Description("设备照片列表")
	@RequestMapping("photoListOfMac")
	public ModelAndView PhotoListOfMac(Long deptId, Long winId) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("deptId", deptId);
		mav.addObject("winId", winId);
		mav.setViewName("mac_photo/list");
		return mav;
	}

	@Description("设备照片列表Json")
	@RequestMapping("photoListOfMacJson")
	@ResponseBody
	public CommonHelper<MAC_PHOTO_CONFIG> PhotoListOfMacJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			Long winId) {
		CommonHelper<MAC_PHOTO_CONFIG> commonHelper = null;
		List<MAC_PHOTO_CONFIG> list = new ArrayList<>();
		try {
			PageHelper.startPage(pageNum, pageSize);
			list = macConfigService.list(winId);
			PageInfo<MAC_PHOTO_CONFIG> pageInfo = new PageInfo<>(list);
			commonHelper = new CommonHelper<MAC_PHOTO_CONFIG>(pageInfo);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.error("设备照片列表Json接口异常:{}", e.getMessage());
		}
		return commonHelper;
	}

	@Description("添加设备样图配置")
	@RequestMapping("addMacPhoto")
	public ModelAndView addMacPhoto(Long winId) {
		ModelAndView maView = new ModelAndView();
		try {
			Mac_Windows macWindows = macWinService.getObjectById(winId);
			maView.addObject("macWindows", macWindows);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		maView.addObject("winId", winId);
		maView.setViewName("mac_photo/info");
		return maView;
	}

	@Description("编辑设备样图配置")
	@RequestMapping("editMacPhoto")
	public ModelAndView editMacPhoto(Long id) {
		ModelAndView mav = new ModelAndView();
		try {
			MAC_PHOTO_CONFIG maConfig = macConfigService.getObjectById(id);
			Mac_Windows macWindows = macWinService.getObjectById(maConfig
					.getRelationId());
			mav.addObject("maConfig", maConfig);
			mav.addObject("macWindows", macWindows);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mav.setViewName("mac_photo/info");
		return mav;
	}

	@Description("保存设备样图配置")
	@RequestMapping("saveMacPhoto")
	@ResponseBody
	public DoResult saveMacPhoto(MAC_PHOTO_CONFIG maConfig) {
		DoResult result = new DoResult();
		try {
			if (maConfig.getId() == null) {
				MAC_PHOTO_CONFIG config = macConfigService.getObjectByOtherId(
						maConfig.getRelationId(), maConfig.getPhotoId());
				if (config == null) {
					maConfig.setRelationType(3);
					macConfigService.save(maConfig);
					result.setStateMsg("保存成功");
					result.setStateType(DoResultType.success);
				} else {
					result.setStateMsg("该图片已配置,请重新选择图片!");
					result.setStateType(DoResultType.warning);
				}
			} else {
				macConfigService.update(maConfig.getRelationId(),
						maConfig.getId());
				result.setStateMsg("保存成功");
				result.setStateType(DoResultType.success);
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setStateMsg("保存失败");
			result.setStateType(DoResultType.fail);
			LogUtil.error("[saveMacPhoto]出现异常:{}", e.getMessage());
		}
		return result;
	}

	@Description("删除设备样图配置")
	@RequestMapping("deleteById")
	@ResponseBody
	public DoResult deleteById(Long id) {
		DoResult result = new DoResult();
		try {
			macConfigService.deleteById(id);
			result.setStateMsg("删除成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			result.setStateMsg("删除失败");
			result.setStateType(DoResultType.fail);
			LogUtil.error("[saveMacPhoto]出现异常:{}", e.getMessage());
		}
		return result;
	}
}

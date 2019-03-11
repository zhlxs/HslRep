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
import com.jrwp.payMent.entity.DicDeptaddress;
import com.jrwp.payMent.help.DicDeptaddressHelper;
import com.jrwp.payMent.service.DeptaddressService;

@Description("队列维护管理")
@Controller
@RequestMapping("queueDefendController")
public class QueueDefendController {

	@Resource
	private DeptaddressService deptaddressService;

	@Description("配置对象列表")
	@RequestMapping("deptList")
	public ModelAndView deptList() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("queueDefend/List");
		return maView;
	}

	@Description("配置对象列表Json")
	@RequestMapping("deptListJson")
	@ResponseBody
	public DicDeptaddressHelper deptListJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			String queryinfo) {
		DicDeptaddressHelper deptaddressHelper = null;
		List<DicDeptaddress> list = new ArrayList<>();
		try {
			PageHelper.startPage(pageNum, pageSize);
			list = deptaddressService.list(queryinfo);
			PageInfo<DicDeptaddress> pageInfo = new PageInfo<>(list);
			deptaddressHelper = new DicDeptaddressHelper(pageInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return deptaddressHelper;
	}

	@Description("队列维护配置")
	@RequestMapping("deptConfig")
	public ModelAndView deptConfig(Long id) {
		ModelAndView maView = new ModelAndView();
		DicDeptaddress dept = deptaddressService.getObjectById(id);
		maView.addObject("dept", dept);
		maView.addObject("id", id);
		maView.setViewName("queueDefend/Config");
		return maView;
	}

	@Description("保存队列维护配置")
	@RequestMapping("saveDeptConfig")
	@ResponseBody
	public DoResult saveDeptConfig(Long id, Integer isopen, Integer minutes,
			Integer personcount) {
		DoResult result = new DoResult();
		try {
			DicDeptaddress deptaddress = new DicDeptaddress();
			deptaddress.setId(id);
			deptaddress.setIsopen(isopen);
			deptaddress.setMinutes(minutes);
			deptaddress.setPersoncount(personcount);
			deptaddressService.update(deptaddress);
			result.setStateMsg("保存成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("保存失败");
			result.setStateType(DoResultType.fail);
			LogUtil.error("保存队列维护配置异常:{}", e.getMessage());
		}
		return result;
	}
}

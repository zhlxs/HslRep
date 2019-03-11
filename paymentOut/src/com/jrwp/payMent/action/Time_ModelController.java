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
import com.jrwp.payMent.dao.Time_TableDao;
import com.jrwp.payMent.entity.DicTime;
import com.jrwp.payMent.entity.TimeInter;
import com.jrwp.payMent.entity.Time_Model;
import com.jrwp.payMent.entity.Time_Table;
import com.jrwp.payMent.help.DicTimeHelper;
import com.jrwp.payMent.help.TimeModelHelper;
import com.jrwp.payMent.service.DicTimeService;
import com.jrwp.payMent.service.Time_ModelService;

@Description("预约时间管理")
@Controller
@RequestMapping("timeModelController")
public class Time_ModelController {

	@Resource
	private Time_ModelService timeModelService;
	@Resource
	private Time_TableDao timeTableDao;
	@Resource
	private DicTimeService dicTimeService;

	@Description("模板列表")
	@RequestMapping("modelList")
	public ModelAndView modelList() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("time/List");
		return maView;
	}

	@Description("模板列表Json")
	@RequestMapping("modelListJson")
	@ResponseBody
	public TimeModelHelper modelListJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			String queryinfo) {
		TimeModelHelper timeModelHelper = null;
		List<Time_Model> list = new ArrayList<>();
		try {
			PageHelper.startPage(pageNum, pageSize);
			list = timeModelService.list(queryinfo);
			PageInfo<Time_Model> pageInfo = new PageInfo<>(list);
			timeModelHelper = new TimeModelHelper(pageInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return timeModelHelper;
	}

	@Description("添加模板")
	@RequestMapping("addTimeModel")
	public ModelAndView addTimeModel() {
		ModelAndView maView = new ModelAndView();
		// maView.setViewName("time/Info");
		List<DicTime> list = dicTimeService.list();
		maView.addObject("list", list);
		maView.setViewName("time/timeInfo");
		return maView;
	}

	@Description("编辑模板")
	@RequestMapping("editTimeModel")
	public ModelAndView editTimeModel(long id) {
		ModelAndView maView = new ModelAndView();
		try {
			Time_Model model = timeModelService.getObjectById(id);
			List<Time_Table> list = timeTableDao.listforModel(id);
			List<TimeInter> tiInters = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				Time_Table table = list.get(i);
				String time = table.getTime();// 09:00-10:00
				TimeInter inter = new TimeInter();
				inter.setPreHour(time.substring(0, time.indexOf(":")));
				inter.setPreMin(time.substring(time.indexOf(":") + 1,
						time.indexOf("-")));
				inter.setSufHour(time.substring(time.indexOf("-") + 1,
						time.lastIndexOf(":")));
				inter.setSufMin(time.substring(time.lastIndexOf(":") + 1,
						time.length()));
				inter.setId(table.getId());
				inter.setOrder(Integer.valueOf(table.getOrdercode()));
				inter.setOrderCount(table.getOrderCount());
				tiInters.add(inter);
			}
			maView.addObject("model", model);
			maView.addObject("list", tiInters);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		maView.setViewName("time/Info1");
		return maView;
	}

	@Description("时间模板详情")
	@RequestMapping("timeModelDetail")
	public ModelAndView timeModelDetail(long modelId) {
		ModelAndView maAndView = new ModelAndView();
		try {
			Time_Model model = timeModelService.getObjectById(modelId);
			List<Time_Table> list = timeTableDao.listforModel(modelId);
			maAndView.addObject("model", model);
			maAndView.addObject("list", list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		maAndView.setViewName("time/detail");
		return maAndView;
	}

	@Description("保存模板")
	@RequestMapping("savetimeModel")
	@ResponseBody
	public DoResult savetimeModel(Long modelId, String modelName,
			Integer businessType, Integer isValid, Integer[] orderCount,
			Integer[] prehour, Integer[] premin, Integer[] sufhour,
			Integer[] sufmin, String[] order) {
		DoResult result = new DoResult();
		Time_Model model = new Time_Model();
		List<Time_Table> list = new ArrayList<>();
		model.setId(modelId);
		model.setModelName(modelName);
		model.setIsValid(isValid);
		model.setCreatetime(new Date());
		model.setBusinessType(businessType);
		try {
			if (prehour != null && premin != null && sufhour != null
					&& sufmin != null && orderCount != null) {
				if (prehour.length == premin.length
						&& sufhour.length == sufmin.length
						&& prehour.length == sufhour.length) {
					for (int i = 0; i < prehour.length; i++) {
						Time_Table table = new Time_Table();
						String prehourstr = null;
						String preminstr = null;
						String sufhourstr = null;
						String sufminstr = null;
						if (String.valueOf(prehour[i]).length() == 1) {
							prehourstr = "0" + String.valueOf(prehour[i]);
						} else {
							prehourstr = String.valueOf(prehour[i]);
						}
						if (String.valueOf(premin[i]).length() == 1) {
							preminstr = "0" + String.valueOf(premin[i]);
						} else {
							preminstr = String.valueOf(premin[i]);
						}
						if (String.valueOf(sufhour[i]).length() == 1) {
							sufhourstr = "0" + String.valueOf(sufhour[i]);
						} else {
							sufhourstr = String.valueOf(sufhour[i]);
						}
						if (String.valueOf(sufmin[i]).length() == 1) {
							sufminstr = "0" + String.valueOf(sufmin[i]);
						} else {
							sufminstr = String.valueOf(sufmin[i]);
						}
						String time = prehourstr + ":" + preminstr + "-"
								+ sufhourstr + ":" + sufminstr;
						table.setTime(time);
						table.setOrdercode("0000" + order[i]);
						table.setOrderCount(orderCount[i]);
						System.out.println("===================="
								+ table.getOrdercode());
						list.add(table);
					}
					timeModelService.save(model, list);
					result.setStateMsg("保存成功");
					result.setStateType(DoResultType.success);
				}
			} else {
				result.setStateMsg("保存失败:存在空值");
				result.setStateType(DoResultType.fail);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("保存失败");
			result.setStateType(DoResultType.fail);
			LogUtil.error("预约时间段模板保存失败:{}", e.getMessage());
		}
		return result;
	}

	@Description("删除模板")
	@RequestMapping("delete")
	@ResponseBody
	public DoResult delete(long id) {
		DoResult result = new DoResult();
		try {
			timeModelService.delete(id);
			result.setStateMsg("删除成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("删除失败");
			result.setStateType(DoResultType.fail);
			LogUtil.error("时间模板删除失败:{}", e.getMessage());
		}
		return result;
	}

	@Description("时间模板选择列表弹窗")
	@RequestMapping("timeModelDialog")
	public ModelAndView timeModelDialog(String actiontype, String modelId) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("actiontype", actiontype);
		mav.addObject("modelId", modelId == null || modelId == "" ? "0"
				: modelId);
		mav.setViewName("time/timeModelDialog");
		return mav;
	}

//	@Description("时间选择列表弹窗Json")
//	@RequestMapping("timeDialogJson")
//	@ResponseBody
//	public DicTimeHelper timeDialogJson(
//			@RequestParam(value = "page", defaultValue = "1") int pageNum,
//			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
//			String queryinfo) {
//		DicTimeHelper dicTimeHelper = null;
//		List<DicTime> list = new ArrayList<>();
//		try {
//			PageHelper.startPage(pageNum, pageSize);
//			list = dicTimeService.list();
//			PageInfo<DicTime> pageInfo = new PageInfo<>(list);
//			dicTimeHelper = new DicTimeHelper(pageInfo);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return dicTimeHelper;
//	}

	@Description("时间选择Json")
	@RequestMapping("timeJson")
	@ResponseBody
	public List<DicTime> timeJson() {
		List<DicTime> list = new ArrayList<>();
		try {
			list = dicTimeService.list();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Description("保存时间模板")
	@RequestMapping("saveTimeMod")
	@ResponseBody
	public DoResult saveTimeMod(Long modelId, String modelName,
			Integer businessType, Integer isValid, String[] order,
			String[] time, Integer[] orderCount) {
		DoResult result = new DoResult();
		Time_Model model = new Time_Model();
		List<Time_Table> list = new ArrayList<>();
		model.setId(modelId);
		model.setModelName(modelName);
		model.setIsValid(isValid);
		model.setCreatetime(new Date());
		model.setBusinessType(businessType);
		try {
			if (time != null && orderCount != null) {
				if (time.length == orderCount.length) {
					for (int i = 0; i < time.length; i++) {
						Time_Table table = new Time_Table();
						table.setTime(time[i]);
						table.setOrdercode("0000" + order[i]);
						table.setOrderCount(orderCount[i]);
						list.add(table);
					}
					timeModelService.save(model, list);
					result.setStateMsg("保存成功");
					result.setStateType(DoResultType.success);
				} else {
					result.setStateMsg("保存失败:存在空值");
					result.setStateType(DoResultType.fail);
				}
			} else {
				result.setStateMsg("保存失败:存在空值");
				result.setStateType(DoResultType.fail);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("保存失败:出现异常");
			result.setStateType(DoResultType.fail);
		}
		return result;
	}

	public static void main(String[] args) {
		String time = "09:00-10:00";
		System.out.println(time.substring(time.lastIndexOf(":") + 1,
				time.length()));
	}
}

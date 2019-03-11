package com.jrwp.payMent.action;

import java.util.List;

import javax.annotation.Resource;

import com.jrwp.payMent.help.PoliceHelper;
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
import com.jrwp.payMent.entity.Core_Police;
import com.jrwp.payMent.service.IPoliceService;

@Description("警种管理")
@Controller
@RequestMapping("policeController")
public class PoliceController {

	@Resource
	private IPoliceService policeService;
	
	@Description("警种列表")
	@RequestMapping("policeList")
	public ModelAndView policelist(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("police/policelist");
		return mav;
	}
	@Description("警种列表Json")
	@RequestMapping("PoliceJson")
	@ResponseBody
	public PoliceHelper policeJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "20") int pageSize){
		PoliceHelper policeHelper = null;
		try {
			PageHelper.startPage(pageNum,pageSize);
			List<Core_Police> list = policeService.getPoliceList();
			PageInfo<Core_Police> page = new PageInfo<Core_Police>(list);
			policeHelper = new PoliceHelper(page);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return policeHelper;		
	}
	@Description("添加警种")
	@RequestMapping("addPolice")
	public ModelAndView addPolice(Core_Police police){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("police/policeinfo");
		return mav;
	}
	@Description("保存警种")
	@RequestMapping("savePolice")
	@ResponseBody
	public DoResult savePolice(Core_Police police,String id){
		DoResult result = new DoResult();
		try {
			if (id!=null && !id.equals("")) {
				policeService.updatePolice(police,id);
				result.setStateType(DoResultType.success);
				result.setStateMsg("警种修改成功");
			}else {
				
				policeService.savePolice(police);
				result.setStateType(DoResultType.success);
				result.setStateMsg("警种添加成功");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("警种保存失败");
		}
		return result;
	}
	@Description("删除警种")
	@RequestMapping("deletePolice")
	@ResponseBody
	public DoResult deletePolice(@RequestParam("id")String pcCode){//把警种编号作为主键
		DoResult result = new DoResult();
		try {
			policeService.updatePoliceIsdel(pcCode);
			/*policeService.deletePolice(pcCode);*/
			result.setStateType(DoResultType.success);
			result.setStateMsg("警种删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("警种删除失败");
		}
		return result;
	}
	@Description("编辑警种")
	@RequestMapping("editPolice")
	@ResponseBody
	public ModelAndView editPolice(@RequestParam("id")String pcCode){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("police/policeinfo");
		try {
			Core_Police core_Police = policeService.getObjectByCode(pcCode);
			mav.addObject("core_Police",core_Police);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return mav;
	}
	
	@Description("模糊查询")
	@RequestMapping("getPolice")
	@ResponseBody
	public PoliceHelper getObjectByName(String pcName){
		System.out.println("***" + pcName + "***");
		PoliceHelper pHelper = null;
		if ((pcName == null) || (pcName.equals(""))){
			List<Core_Police> list = this.policeService.getPoliceList();
			PageInfo<Core_Police> pageInfo = new PageInfo<Core_Police>(list);
			pHelper = new PoliceHelper(pageInfo);
		} else {
			List<Core_Police> policelist = null;
			String pcNameSearch = new StringBuffer("%").append(pcName).append("%").toString();
			try {
				policelist = this.policeService.getObjectByName(pcNameSearch);
				PageInfo<Core_Police> page = new PageInfo<Core_Police>(policelist);
				pHelper = new PoliceHelper(page);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return pHelper;
	}
}

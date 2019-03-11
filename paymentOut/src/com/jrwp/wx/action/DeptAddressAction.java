package com.jrwp.wx.action;

import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Core_Dept;
import com.jrwp.core.service.IDeptService;
import com.jrwp.wx.entity.ChoseDept;
import com.jrwp.wx.entity.DeptAddress;
import com.jrwp.wx.service.DeptAddressService;
import com.jrwp.wx.until.QRCodeUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 办理网点接口
 * 
 * @author hsl
 * 
 */
@Description(value = "办理网点接口", state = false)
@Controller
@RequestMapping("deptAddressAction")
public class DeptAddressAction {
	@Resource
	private DeptAddressService deptAddressService;
	@Resource
	private IDeptService deptService;

	@RequestMapping("getDeptAddress")
	@ResponseBody
	public List<DeptAddress> getDeptAddress(HttpServletRequest request) {
		List<DeptAddress> list = null;
		try {
			Integer deptid = (Integer) request.getSession().getAttribute(
					"deptid");
			System.out.println("session中的部门id:" + deptid);
			if (deptid == null) {
				deptid = 1;
			}
			Core_Dept dept = deptService.getObjectById(deptid);
			String deptcode = dept.getDeptCode();
			String orderCode = dept.getOrderCode();
			String code = deptcode.substring(0, orderCode.length() / 5 * 2)
					+ "%";
			list = deptAddressService.selectByDeptid(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping("wx/getdept")
	public ModelAndView choserdept() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("wx/navbar1");
		return modelAndView;
	}

	@RequestMapping("wx/testdept")
	@ResponseBody
	public List<ChoseDept> getdept(Long deptid, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List<ChoseDept> list = new ArrayList<>();
		try {
			List<Core_Dept> all = deptService.findAlldept(deptid);
			Core_Dept top = deptService.getObjectById(deptid);
			ChoseDept grand = new ChoseDept();
			grand.setId(top.getId());
			grand.setValue(top.getDeptName());
			List<ChoseDept> sons = new ArrayList<>();
			for (Core_Dept core_dept : all) {
				// 第一层 给爷爷找儿子
				System.out.println(core_dept.getParentId());
				System.out.println(top.getId());
				if (core_dept.getParentId().longValue() == top.getId()
						.longValue()) {
					List<ChoseDept> grandsons = new ArrayList<>();
					ChoseDept son = new ChoseDept();
					son.setValue(core_dept.getDeptName());
					son.setId(core_dept.getId());
					for (Core_Dept coreDept : all) {
						// 第二层 给儿子找孙子
						if (coreDept.getParentId().longValue() == core_dept
								.getId().longValue()) {
							ChoseDept grandson = new ChoseDept();
							grandson.setValue(coreDept.getDeptName());
							grandson.setId(coreDept.getId());
							grandsons.add(grandson);
						}
					}
					if (grandsons.size() > 0) {
						son.setChilds(grandsons);
					}
					sons.add(son);
				}
			}
			if (sons.size() > 0) {
				grand.setChilds(sons);
			}
			list.add(grand);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping("wx/createQrcode")
	@ResponseBody
	public String createQrcode() {
		String text = "http://pay.jxjd626.com/paymentOut/squenceInfoAction/gzSigninIndex?deptid=";
		try {
			List<Core_Dept> all = deptService.findAlldept(543);
			for (Core_Dept dept : all) {
				String text1 = text + dept.getId();
				System.out.println(text1);
				QRCodeUtil.encodeByName(text1,
						dept.getId() + dept.getDeptName(), "d:/MyWorkDoc/gz",
						true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "OK";
	}
}
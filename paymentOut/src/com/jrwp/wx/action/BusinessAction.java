package com.jrwp.wx.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jrwp.core.annotation.Description;
import com.jrwp.core.utils.LogUtil;
import com.jrwp.payMent.entity.Businessconfig;
import com.jrwp.payMent.entity.BusinessconfigStuff;
import com.jrwp.payMent.entity.BusinessconfigType;
import com.jrwp.payMent.service.BusinessconfigTypeService;
import com.jrwp.wx.entity.WxBusiness;
import com.jrwp.wx.service.WxBusinessService;

/**
 * 业务信息接口
 * 
 * @author hsl
 * 
 */
@Description(value = "业务信息接口", state = false)
@Controller
@RequestMapping("wx/businessAction")
public class BusinessAction {

	@Resource
	private WxBusinessService businessService;
	@Resource
	private BusinessconfigTypeService businessconfigTypeService;

	private final String code = "00001";
	private final int length = 10;

	@Description("业务列表布局页")
	@RequestMapping("businessList")
	public ModelAndView businessList() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("wxbusiness/businessList");
		return maView;
	}

	@Description("业务列表Json")
	@RequestMapping("businessListJson")
	@ResponseBody
	public List<WxBusiness> businessListJson(HttpServletRequest request,
			HttpServletResponse response) {
		Logger logger = Logger.getLogger(this.getClass());
		List<WxBusiness> list = new ArrayList<>();
		List<WxBusiness> parents = new ArrayList<>();
		List<WxBusiness> children = new ArrayList<>();
		// BufferedOutputStream bos = null;
		FileImageOutputStream fos = null;
		File file = null;
		try {
			list = businessService.list(code + "%");
			for (WxBusiness business : list) {
				Businessconfig config = business.getConfig();
				byte[] icon = null;
				String iconpath = null;
				if (config != null) {
					icon = config.getIcon();
					iconpath = business.getConfig().getIconpathstr();
				}
				if (icon != null) {
					if (iconpath != null) {
						String[] pathName = iconpath.split("/");
						String fileName = pathName[pathName.length - 1];
						String path = request.getSession().getServletContext()
								.getRealPath("");
						file = new File(path + "//businessImgs//" + fileName);
						if (!file.exists()) {
							fos = new FileImageOutputStream(file);
							fos.write(icon, 0, icon.length);
							fos.flush();
							fos.close();
						}
						business.getConfig().setIcon(null);
					}
				}
				if (business.getOrderCode().length() == length) {
					parents.add(business);
				} else {
					children.add(business);
				}
				for (WxBusiness wxBusiness : parents) {
					List<WxBusiness> forchild = new ArrayList<WxBusiness>();
					for (int i = 0; i < children.size(); i++) {
						if (children.get(i).getOrderCode()
								.startsWith(wxBusiness.getOrderCode())) {
							forchild.add(children.get(i));
						}
					}
					wxBusiness.setChildren(forchild);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("业务数据出现异常", e);
		}
		return parents;
	}

	@Description("业务直接办理列表Json")
	@RequestMapping("businessDirListJson")
	@ResponseBody
	public List<WxBusiness> businessDirListJson(HttpServletRequest request,
			HttpServletResponse response) {
		Logger logger = Logger.getLogger(this.getClass());
		List<WxBusiness> list = new ArrayList<>();
		List<WxBusiness> parents = new ArrayList<>();
		try {
			list = businessService.list(code + "%");
			for (WxBusiness business : list) {
				if (business.getOrderCode().length() == length + 5) {
					parents.add(business);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("业务数据出现异常", e);
		}
		return parents;
	}

	@Description("进入业务办理页")
	@RequestMapping("toApplybusiness")
	public ModelAndView toApplybusiness(HttpServletRequest request,
			HttpServletResponse response, String serCode, Integer businessType) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("serCode", serCode);
		mav.addObject("businessType", businessType);
		mav.setViewName("work/appointbusiness");
		return mav;
	}

	@Description("业务办理类型Json")
	@RequestMapping("businessconfigTypes")
	@ResponseBody
	public List<BusinessconfigType> businessconfigTypes(
			HttpServletRequest request, HttpServletResponse response,
			String serCode) {
		FileImageOutputStream fos = null;
		File file = null;
		List<BusinessconfigType> list = new ArrayList<>();
		try {
			String path = request.getSession().getServletContext()
					.getRealPath("/upload/wxhz");
			System.out.println("样图地址:" + path);
			list = businessconfigTypeService.listChildForWx(serCode);
			for (BusinessconfigType type : list) {
				List<BusinessconfigStuff> stuffs = type.getStuffs();
				System.out.println("业务类型数:" + stuffs.size());
				for (int i = 0; i < stuffs.size(); i++) {
					byte[] sampleByte = stuffs.get(i).getPhotoSample()
							.getPhotosample();
					String[] filePath = stuffs.get(i).getPhotoSample()
							.getPhotosamplepath().split("/");
					if (filePath.length == 3) {
						String picturename = filePath[filePath.length - 1]; // 图片名
						file = new File(path + "//" + picturename);
						if (sampleByte != null) {// 防止控制异常
							if (!file.exists()) {
								fos = new FileImageOutputStream(file);
								fos.write(sampleByte, 0, sampleByte.length);
								fos.flush();
								fos.close();
							}
							stuffs.get(i).getPhotoSample().setPhotosample(null);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LogUtil.error("业务办理类型接口异常:{}", e.getMessage());
		}
		return list;
	}

	@Description("直接进入业务办理")
	@RequestMapping("directAccess")
	public ModelAndView directAccess(Long deptid, String deptname) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("deptid", deptid);
		mav.addObject("deptname", deptname);
		mav.setViewName("work/straightappointbusiness");
		return mav;
	}
}

package com.jrwp.payMent.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.net.aso.a;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.utils.LogUtil;
import com.jrwp.core.utils.ToBytes;
import com.jrwp.payMent.entity.Businessconfig;
import com.jrwp.payMent.entity.DicDeptaddress;
import com.jrwp.payMent.help.DicDeptaddressHelper;
import com.jrwp.payMent.service.DeptaddressService;

@Description("办证网点管理")
@Controller
@RequestMapping("deptaddressController")
public class DeptaddressController {

	@Resource
	private DeptaddressService deptaddressService;

	@Description("办证网点列表")
	@RequestMapping("addressList")
	public ModelAndView addressList() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("deptaddress/List");
		return maView;
	}

	@Description("办证网点列表Json")
	@RequestMapping("addressListJson")
	@ResponseBody
	public DicDeptaddressHelper addressListJson(
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

	@Description("添加办证网点")
	@RequestMapping("addDeptaddress")
	public ModelAndView addDeptaddress() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("deptaddress/Info");
		return maView;
	}

	@Description("编辑办证网点")
	@RequestMapping("editDeptaddress")
	public ModelAndView editDeptaddress(Long id) {
		ModelAndView maView = new ModelAndView();
		DicDeptaddress deptaddress = deptaddressService.getObjectById(id);
		maView.addObject("address", deptaddress);
		maView.setViewName("deptaddress/Info");
		return maView;
	}

	@Description("保存办证网点")
	@RequestMapping("saveAddress")
	@ResponseBody
	public DoResult saveAddress(
			DicDeptaddress deptaddress,
			@RequestParam(value = "myImage", required = false) MultipartFile file) {
		DoResult result = new DoResult();
		try {
			if(deptaddress.getId() == null){
				if(file != null){
					// 将CommonsMultipartFile转化为file类
					CommonsMultipartFile cf = (CommonsMultipartFile) file;
					DiskFileItem fi = (DiskFileItem) cf.getFileItem();
					File f = fi.getStoreLocation();
					// 保存到数据库
					deptaddress.setQrCode(ToBytes.getBytes(f));
					deptaddress.setCreatetime(new Date());
					deptaddressService.save(deptaddress);
					result.setStateMsg("保存成功");
					result.setStateType(DoResultType.success);
				}else{
					result.setStateMsg("保存失败:图片为空");
					result.setStateType(DoResultType.success);
				}
			}else {
				if(file != null){
					// 将CommonsMultipartFile转化为file类
					CommonsMultipartFile cf = (CommonsMultipartFile) file;
					DiskFileItem fi = (DiskFileItem) cf.getFileItem();
					File f = fi.getStoreLocation();
					// 保存到数据库
					deptaddress.setQrCode(ToBytes.getBytes(f));
					deptaddressService.save(deptaddress);
				}else{
					deptaddressService.save(deptaddress);
				}
				result.setStateMsg("保存成功");
				result.setStateType(DoResultType.success);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("保存失败:程序异常");
			result.setStateType(DoResultType.fail);
			LogUtil.error("办证网点保存失败:{}", e.getMessage());
		}
		return result;
	}

	@Description("删除办证网点")
	@RequestMapping("delete")
	@ResponseBody
	public DoResult delete(Long id) {
		DoResult result = new DoResult();
		try {
			deptaddressService.delete(id);
			result.setStateMsg("删除成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("保存失败");
			result.setStateType(DoResultType.fail);
		}
		return result;
	}

	@Description("设置模板")
	@RequestMapping("setModel")
	public ModelAndView setModel(Long id) {
		ModelAndView maView = new ModelAndView();
		maView.addObject("id", id);
		maView.setViewName("deptaddress/setModel");
		return maView;
	}

	@Description("保存模板设置")
	@RequestMapping("saveModelsetting")
	@ResponseBody
	public DoResult saveModelsetting(Long id, Long modelId) {
		DoResult result = new DoResult();
		try {
			if (modelId == null) {
				modelId = 1l;// 默认
			} else {
				if (modelId == 0) {
					modelId = 1l;// 默认
				}
			}
			DicDeptaddress deptaddress = new DicDeptaddress();
			deptaddress.setId(id);
			deptaddress.setModelId(modelId);
			deptaddressService.update(deptaddress);
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

	@Description("设置时间模板")
	@RequestMapping("setTimeModel")
	public ModelAndView setTimeModel(Long id) {
		ModelAndView maView = new ModelAndView();
		maView.addObject("id", id);
		maView.setViewName("deptaddress/setTimeModel");
		return maView;
	}

	@Description("保存时间模板设置")
	@RequestMapping("saveTimeModelsetting")
	@ResponseBody
	public DoResult saveTimeModelsetting(Long id, Long modelId) {
		DoResult result = new DoResult();
		try {
			if (modelId == null) {
				modelId = 0l;// 默认
			}
			DicDeptaddress deptaddress = new DicDeptaddress();
			deptaddress.setId(id);
			deptaddress.setTmodelId(modelId);
			deptaddressService.update(deptaddress);
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
	
	@Description("读取图标")
	@RequestMapping("getIcon")
	@ResponseBody
	public void getphoto(long id, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("img/jpeg");
		response.setCharacterEncoding("utf-8");
		try {
			DicDeptaddress deptaddress = deptaddressService.getObjectByDeptId(id);
			byte[] icon = deptaddress.getQrCode();
			InputStream in = null;
			FileImageInputStream input = null;
			ByteArrayOutputStream output = null;
			if (icon == null) {
				String imgFile = request.getSession().getServletContext()
						.getRealPath("/photos/noIcon.bmp");
				// 读取图片字节数组
				input = new FileImageInputStream(new File(imgFile));
				output = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				int numBytesRead = 0;
				while ((numBytesRead = input.read(buf)) != -1) {
					output.write(buf, 0, numBytesRead);
				}
				icon = output.toByteArray();
			}
			OutputStream outputStream = response.getOutputStream();
			in = new ByteArrayInputStream(icon);
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = in.read(buf, 0, 1024)) != -1) {
				outputStream.write(buf, 0, len);
			}
			outputStream.close();
			output.close();
			input.close();
		} catch (Exception e) {
			LogUtil.error("图标读取失败:{}", e.getMessage());
		}
	}
}

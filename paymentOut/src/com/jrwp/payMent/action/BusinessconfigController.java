package com.jrwp.payMent.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.annotation.Resource;
import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.jrwp.core.annotation.Description;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.utils.LogUtil;
import com.jrwp.core.utils.ToBytes;
import com.jrwp.payMent.entity.Businessconfig;
import com.jrwp.payMent.entity.DicBusinessnotice;
import com.jrwp.payMent.entity.PoliceClassService;
import com.jrwp.payMent.service.BusinessconfigService;
import com.jrwp.payMent.service.DicBusinessnoticeService;
import com.jrwp.payMent.service.IPoliceClassService;

@Description("业务配置")
@Controller
@RequestMapping("businessconfigController")
public class BusinessconfigController {

	@Resource
	private BusinessconfigService businessconfigService;
	@Resource
	private IPoliceClassService policeClassService;
	@Resource
	private DicBusinessnoticeService noticeService;

	@Description("添加业务配置")
	@RequestMapping("addConfig")
	public ModelAndView configList(String id) {
		ModelAndView maView = new ModelAndView();
		try {
			DicBusinessnotice notice = new DicBusinessnotice();
			PoliceClassService classService = policeClassService.getObjectByCode(id);
			if(classService.getBusinessnoticeId() != null){
				notice = noticeService.getObjectById(classService.getBusinessnoticeId());
			}
			Businessconfig businessconfig = businessconfigService
					.getObjectByCode(id);
			maView.addObject("serCode", id);
			maView.addObject("noticeId",classService.getBusinessnoticeId());
			maView.addObject("noticename",notice.getName());
			maView.addObject("businessConfig", businessconfig);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		maView.setViewName("businessconfig/configInfo");
		return maView;
	}

	@Description("保存业务配置")
	@RequestMapping("saveBusinessconfig")
	@ResponseBody
	public DoResult saveBusinessconfig(
			Long id,
			String serCode,
			String sqtj,
			String sqcl,
			@RequestParam(value = "myImage", required = false) MultipartFile file,
			Long noticeId, HttpServletResponse response,
			HttpServletRequest request) {
		DoResult result = new DoResult();
		Businessconfig businessconfig = new Businessconfig();
		try {
			response.setContentType("text/html; charset=utf-8");
			businessconfig.setId(id);
			businessconfig.setCreatetime(new Date());
			businessconfig.setSerCode(serCode);
			businessconfig.setSqtj(sqtj);
			businessconfig.setSqcl(sqcl);
			if (file != null) {
				if (!file.isEmpty()) {
					// 获取文件名
					String fileName = file.getOriginalFilename();
					// 获取上传文件扩展名
					String fileExt = fileName.substring(
							fileName.lastIndexOf(".") + 1, fileName.length());
					// 对扩展名进行小写转换
					fileExt = fileExt.toLowerCase();
					// 图标类型限定
					if (!"jpg".equals(fileExt) && !"jpeg".equals(fileExt)
							&& !"png".equals(fileExt) && !"bmp".equals(fileExt)
							&& !"gif".equals(fileExt)) {
						LogUtil.error("上传照片失败:{}无效图片文件类型");
						result.setStateType(DoResultType.fail);
						result.setStateMsg("上传失败:无效图片文件类型");
					}
					long fileSize = file.getSize();
					if (fileSize > (2 * 1024 * 1024)) {
						LogUtil.error("上传照片失败:{}文件大小不能超过2M");
						result.setStateType(DoResultType.fail);
						result.setStateMsg("保存失败:文件大小不能超过2M");
					}
					// 将CommonsMultipartFile转化为file类
					CommonsMultipartFile cf = (CommonsMultipartFile) file;
					DiskFileItem fi = (DiskFileItem) cf.getFileItem();
					File f = fi.getStoreLocation();
					// 保存到数据库
					businessconfig.setIcon(ToBytes.getBytes(f));
					// 保存路径
					businessconfig.setIconpathstr("businessImgs/" + fileName);
					/**
					 * 保存服务器businessImgs文件夹下
					 */
					String path = request.getServletContext().getRealPath(
							"/businessImgs/");
					File uploadFile = new File(path + File.separator + fileName);
					file.transferTo(uploadFile);
				}
				businessconfigService.save(businessconfig,noticeId);
			}else {
				if(id != null){
					businessconfigService.save(businessconfig,noticeId);
				}
			}
			result.setStateMsg("保存成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LogUtil.error("业务配置保存异常:{}", e.getMessage());
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
			Businessconfig businessConfig = businessconfigService
					.getObjectById(id);
			byte[] icon = businessConfig.getIcon();
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

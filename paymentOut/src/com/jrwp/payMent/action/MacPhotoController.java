package com.jrwp.payMent.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.utils.LogUtil;
import com.jrwp.core.utils.ToBytes;
import com.jrwp.payMent.entity.MAC_PHOTO;
import com.jrwp.payMent.entity.PhotoSample;
import com.jrwp.payMent.help.CommonHelper;
import com.jrwp.payMent.help.PhotoSampleHelper;
import com.jrwp.payMent.service.IMAC_PHOTOService;
import com.jrwp.payMent.service.PhotoSampleService;
import com.jrwp.wx.until.Utils;

@Description("设备样图管理")
@Controller
@RequestMapping("macPhotoController")
public class MacPhotoController {

	@Resource
	private IMAC_PHOTOService maPhotoService;

	@Description("设备-获取样图地址")
	@RequestMapping("getSample")
	@ResponseBody
	public String getSample(Long samplepathid, HttpServletResponse response) {
		/** 此处还需优化 **/
		MAC_PHOTO sample = maPhotoService.getPhotoSample(samplepathid);
		String samplePhoto = sample.getPhotosamplepath();
		String path = Utils.getParamsByKey("path");
		File testFile = new File(path + samplePhoto);
		if (!testFile.exists()) {
			testFile.mkdirs();
			try {
				testFile.createNewFile();
				System.out.println(testFile.createNewFile());
				byte[] icon = sample.getPhotosample();
				InputStream in = null;
				FileOutputStream outputStream = new FileOutputStream(testFile);
				in = new ByteArrayInputStream(icon);
				int len = 0;
				byte[] buf = new byte[1024];
				while ((len = in.read(buf, 0, 1024)) != -1) {
					outputStream.write(buf, 0, len);
				}
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return samplePhoto;
	}

	@Description("设备-样图列表")
	@RequestMapping("sampleList")
	public ModelAndView sampleList(Long applytypeid, String serCode) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("applytypeid", applytypeid);
		mav.addObject("serCode", serCode);
		mav.setViewName("macphoto/sampleList");
		return mav;
	}

	@Description("设备-样图列表Json")
	@RequestMapping("sampleJson")
	@ResponseBody
	public CommonHelper<MAC_PHOTO> sampleJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
			String queryinfo) {
		CommonHelper<MAC_PHOTO> photoSampleHelper = null;
		try {
			PageHelper.startPage(pageNum, pageSize);
			List<MAC_PHOTO> list = maPhotoService.list(queryinfo);
			PageInfo<MAC_PHOTO> page = new PageInfo<MAC_PHOTO>(list);
			photoSampleHelper = new CommonHelper<MAC_PHOTO>(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return photoSampleHelper;
	}

	@Description("设备-类型下样图列表")
	@RequestMapping("sampleListofType")
	public ModelAndView sampleListofType(Long applytypeid,
			Long businessconfigid, Long id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("applytypeid", applytypeid);
		mav.addObject("businessconfigid", businessconfigid);
		mav.addObject("id", id);
		mav.setViewName("macphoto/sampleListofType");
		return mav;
	}

	@Description("设备-类型下样图列表Json")
	@RequestMapping("sampleJsonofType")
	@ResponseBody
	public CommonHelper<MAC_PHOTO> sampleJsonofType(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
			Long id, String queryinfo) {
		CommonHelper<MAC_PHOTO> photoSampleHelper = null;
		try {
			PageHelper.startPage(pageNum, pageSize);
			List<MAC_PHOTO> list = maPhotoService.listofType(id);
			PageInfo<MAC_PHOTO> page = new PageInfo<MAC_PHOTO>(list);
			photoSampleHelper = new CommonHelper<MAC_PHOTO>(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return photoSampleHelper;
	}

	@Description("设备-样图弹出层")
	@RequestMapping("sampleDialog")
	public ModelAndView sampleDialog(int type) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("type", type);
		mav.setViewName("macphoto/sampleDialog");
		return mav;
	}

	@Description("设备-样图列表弹出层Json")
	@RequestMapping("sampleDialogJson")
	@ResponseBody
	public CommonHelper<MAC_PHOTO> sampleDialogJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
			String queryinfo, int type) {
		CommonHelper<MAC_PHOTO> sampleHelper = null;
		try {
			PageHelper.startPage(pageNum, pageSize);
			List<MAC_PHOTO> list = maPhotoService.listValid(queryinfo, type);
			PageInfo<MAC_PHOTO> page = new PageInfo<MAC_PHOTO>(list);
			sampleHelper = new CommonHelper<MAC_PHOTO>(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sampleHelper;
	}

	@Description("设备-添加样图")
	@RequestMapping("addSample")
	public ModelAndView addSample() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("macphoto/sampleinfo");
		return mav;
	}

	@Description("设备-编辑样图")
	@RequestMapping("editSample")
	@ResponseBody
	public ModelAndView editSample(Long id) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("macphoto/sampleinfo");
		try {
			MAC_PHOTO photoSample = maPhotoService.getPhotoSample(id);
			CommonHelper<MAC_PHOTO> photoSampleHelper = new CommonHelper<MAC_PHOTO>(
					photoSample);
			mav.addObject("photoSampleHelper", photoSampleHelper);
			mav.addObject("photoSample", photoSample);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@Description("设备-删除样图")
	@RequestMapping("deleteSample")
	@ResponseBody
	public DoResult deleteSample(long id) {
		DoResult result = new DoResult();
		try {
			maPhotoService.delete(id);
			result.setStateType(DoResultType.success);
			result.setStateMsg("样图删除成功");
			result.setStateValue(id);
		} catch (Exception e) {
			LogUtil.error("样图删除失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg("样图删除失败");
		}
		return result;
	}

	@Description("设备-样图有效状态")
	@RequestMapping("showSample")
	@ResponseBody
	public DoResult showSample(Long id, int isvalid) {
		DoResult result = new DoResult();
		try {
			maPhotoService.updateValid(id, isvalid);
			result.setStateType(DoResultType.success);
			result.setStateMsg("样图有效状态更改成功");
		} catch (Exception e) {
			LogUtil.error("样图状态更新失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg("样图状态更新失败");
		}
		return result;
	}

	@Description("设备-读取样图")
	@RequestMapping("getphoto")
	@ResponseBody
	public void getphoto(long id, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("img/jpeg");
		response.setCharacterEncoding("utf-8");
		try {
			MAC_PHOTO sample = maPhotoService.getPhotoSample(id);
			byte[] icon = sample.getPhotosample();
			InputStream in = null;
			FileImageInputStream input = null;
			ByteArrayOutputStream output = null;
			if (icon == null) {
				String imgFile = request.getSession().getServletContext()
						.getRealPath("/photos/imgHdPic.jpg");
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
			if (outputStream != null) {
				outputStream.close();
			}
			if (output != null) {
				output.close();
			}
			if (input != null) {
				input.close();
			}
		} catch (Exception e) {
			LogUtil.error("样图读取失败:{}", e.getMessage());
			e.printStackTrace();
		}
	}

	@Description("设备-保存样图")
	@RequestMapping("saveSample")
	@ResponseBody
	public DoResult saveSample(MAC_PHOTO photoSample, HttpServletRequest request) {
		DoResult result = new DoResult();
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartHttpServletRequest.getFile("picfile");
			if (file != null) {
				if (!file.isEmpty()) {
					String fileName = file.getOriginalFilename();
					// 获取上传文件扩展名
					String fileExt = fileName.substring(
							fileName.lastIndexOf(".") + 1, fileName.length());
					// 对扩展名进行小写转换
					fileExt = fileExt.toLowerCase();
					String suffix = fileName.substring(fileName.indexOf("."),
							fileName.length());
					// 图片文件大小过滤
					if (!"jpg".equals(fileExt) && !"jpeg".equals(fileExt)
							&& !"png".equals(fileExt) && !"bmp".equals(fileExt)
							&& !"gif".equals(fileExt)) {
						LogUtil.error("上传样图失败:{}无效图片文件类型");
						result.setStateType(DoResultType.fail);
						result.setStateMsg("上传样图失败:无效图片文件类型");
						return result;
					}
					long fileSize = file.getSize();
					if (fileSize >= (2 * 1024 * 1024)) {
						LogUtil.error("上传样图失败:{}文件大小不能超过2M");
						result.setStateType(DoResultType.fail);
						result.setStateMsg("上传失败:文件大小不能超过2M");
						return result;
					} else if (fileSize < (10 * 1024)) {
						LogUtil.error("上传样图失败:{}文件大小不能小于10KB");
						result.setStateType(DoResultType.fail);
						result.setStateMsg("上传失败:文件大小不能小于10KB");
						return result;
					}
					// 将CommonsMultipartFile转化为file类
					CommonsMultipartFile cf = (CommonsMultipartFile) file;
					DiskFileItem fi = (DiskFileItem) cf.getFileItem();
					File f = fi.getStoreLocation();
					photoSample.setPhotosample(ToBytes.getBytes(f));
					String fileRealPath = Utils.getParamsByKey("fileRealPath");
					String webPath = Utils.getParamsByKey("webPath");
					String randomName = new Date().getTime() + suffix;
					// 判断文件夹是否存在
					File targerFile = new File(fileRealPath);
					// 判断是否存在目录
					if (!targerFile.exists()) {
						targerFile.mkdirs();
					}
					// 保存文件
					File uploadFile = new File(fileRealPath + randomName);
					try {
						file.transferTo(uploadFile);
						photoSample.setPhotosamplepath(webPath + randomName);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				photoSample.setPhotosamplepath("not need photo");
			}
		}
		try {
			if (photoSample.getDescribe() == null
					|| photoSample.getDescribe() == "") {
				photoSample.setDescribe(photoSample.getPhotoname());
			}
			maPhotoService.save(photoSample);
			result.setStateType(DoResultType.success);
			result.setStateMsg("保存成功");
		} catch (Exception e) {
			LogUtil.error("保存失败:{}", e.getMessage());
			result.setStateType(DoResultType.fail);
			result.setStateMsg("保存失败");
			return result;
		}
		return result;
	}

	@Description("设备-样图查看")
	@RequestMapping(value = "viewPhoto", method = RequestMethod.GET)
	public void carNumberPhoto(Long id, HttpServletRequest request,
			HttpServletResponse response) {
		if (id != null) {
			MAC_PHOTO zpb = maPhotoService.getObjectById(id);
			// 获取照片
			OutputStream out = null;
			InputStream in = null;
			byte[] buf = null;
			if (zpb != null) {
				buf = zpb.getPhotosample();
			}
			if (buf != null) {
				try {
					out = response.getOutputStream();
					in = new ByteArrayInputStream(buf);
					int len = 0;
					byte[] b = new byte[1024];
					while ((len = in.read(b, 0, 1024)) != -1) {
						out.write(b, 0, len);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (out != null) {
							out.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Description("判断样图名称是否存在")
	@RequestMapping("photoNameExist")
	@ResponseBody
	public Boolean photoNameExist(String photoname) {
		try {
			MAC_PHOTO photo = maPhotoService.getPhotoByName(photoname);
			if (photo != null) {
				return new Boolean(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Boolean(true);
	}

}

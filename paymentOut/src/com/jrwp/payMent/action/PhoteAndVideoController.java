package com.jrwp.payMent.action;

import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Core_User;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.service.IDeptService;
import com.jrwp.core.service.IUserService;
import com.jrwp.core.utils.ErrorManager;
import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.core.utils.ToBytes;
//import com.jrwp.payMent.entity.PVHelper;
import com.jrwp.payMent.entity.PhotosAndVideos;
import com.jrwp.payMent.service.PvService;
import com.jrwp.payMent.until.FileUntil;
//import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
//import sun.misc.BASE64Decoder;

//import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
//import java.util.ArrayList;
import java.util.List;

@Description("宣传图片配置")
@Controller
@RequestMapping("photeAndVideoController")
public class PhoteAndVideoController {
	@Resource
	private IDeptService deptService;
	@Resource
	private IUserService userService;
	@Resource
	private PvService pvService;

	@Description("配置图片页面")
	@RequestMapping("configPhotes")
	public ModelAndView configPhotes(Integer deptid, Integer userid) {
		ModelAndView md = new ModelAndView();
		try {
			if (deptid != null) {
				List<PhotosAndVideos> photosAndVideos = pvService
						.getBydeptid(deptid);
				md.addObject("photosAndVideos", photosAndVideos);
			}
			md.addObject("deptid", deptid);
			md.setViewName("PhotosAndVideos/pv");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md;
	}

	@Description("保存图片配置")
	@RequestMapping(value = "savePvConfig", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String savePvConfig(
			@RequestParam(value = "sequence", required = false) Integer[] sequences,
			HttpServletRequest request,
			Integer deptid,
			@RequestParam(value = "imgfile", required = false) MultipartFile[] files) {
		DoResult result = new DoResult();
		if (sequences == null && files.length == 0) {
			result.setStateValue(1);
		}
		try {
			Core_User user = (Core_User) request.getSession().getAttribute(
					"user");
			user = userService.getByUserName(user.getUserName());
			if (deptid == null) {
				deptid = user.getDeptId().intValue();
			}
			if (sequences.length == files.length) {
				for (int i = 0; i < sequences.length; i++) {
					// 先将图片存入本地
					String filename = "";
					String path = "";
					if (!files[i].isEmpty()) {
						CommonsMultipartFile cf = (CommonsMultipartFile) files[i];
						DiskFileItem fi = (DiskFileItem) cf.getFileItem();
						File f = fi.getStoreLocation();
						// 要判断文件类型 是图片还是视频
						// String typpename = fi.getName();
						// 调用工具类将file转化为byte数组
						byte[] imgdata = ToBytes.getBytes(f);
						// 上传文件路径
						path = request.getServletContext().getRealPath(
								"/ConfigPV/");
						// 上传文件名
						filename = files[i].getOriginalFilename();
						String localurl = "http://" + request.getLocalAddr()
								+ ":" + request.getLocalPort()
								+ "/payment/ConfigPV/" + filename;
						File filepath = new File(path, filename);
						// 判断路径是否存在，如果不存在就创建一个
						if (!filepath.getParentFile().exists()) {
							filepath.getParentFile().mkdirs();
						}
						// 将上传文件保存到一个目标文件当中
						files[i].transferTo(new File(path + File.separator
								+ filename));
						// 开始存库
						PhotosAndVideos photosAndVideos = new PhotosAndVideos();
						if (!FileUntil.isVedioFile(filename)) {
							// 上传的是图片
							photosAndVideos.setPhoto(imgdata);
							photosAndVideos.setIsvideoorphoto(1);
						} else {
							// 上传的是视频
							photosAndVideos.setVideo(localurl);
							photosAndVideos.setIsvideoorphoto(2);
						}
						photosAndVideos.setSequence(sequences[i]);
						photosAndVideos.setDeptid(deptid);
						photosAndVideos.setUserid(user.getId().intValue());
						photosAndVideos.setTitle("ads" + sequences[i] + ".jpg");
						photosAndVideos.setLocalURL(localurl);
						pvService.save(photosAndVideos);
					}
				}
			}
			result.setStateMsg("编辑成功！");
			result.setStateValue(0);
			result.setStateType(DoResultType.success);
		} catch (IOException e) {
			e.printStackTrace();
			result.setStateMsg("编辑失敗！");
			result.setStateType(DoResultType.fail);
			result.setStateValue(0);
		}
		;
		return JacksonUtil.toJson(result);
	}

	@Description("删除图片")
	@RequestMapping("deleteImg")
	@ResponseBody
	public DoResult deleteImg(int id, HttpServletRequest request) {
		DoResult result = new DoResult();
		try {
			// 后面的顺序号-1操作
			pvService.NextAlleqReduce(id);
			pvService.delete(id);
			result.setStateType(DoResultType.success);
			result.setStateValue(1);
		} catch (Exception e) {
			result.setStateType(DoResultType.fail);
			result.setStateValue(0);
			e.printStackTrace();
			ErrorManager.writeError(request, e);
		}
		return result;
	}

	@Description("移动图片")
	@RequestMapping("moveImg")
	@ResponseBody
	public DoResult moveImg(int id, HttpServletRequest request, int type) {
		DoResult result = new DoResult();
		try {
			if (type == 1) {
				// 上移
				pvService.prevSeqAdd(id);
				pvService.seqReduce(id);
			} else if (type == 2) {
				// 下移
				pvService.nextseqReduce(id);
				pvService.seqAdd(id);
			} else if (type == 11) {
				pvService.seqAdd(id);
			} else if (type == 21) {
				pvService.seqReduce(id);
			} else if (type == 31) {
				pvService.seqReduce(id);
				pvService.NextAlleqReduce(id);
			}
			result.setStateType(DoResultType.success);
			result.setStateValue(1);
		} catch (Exception e) {
			result.setStateType(DoResultType.fail);
			result.setStateValue(0);
			e.printStackTrace();
			ErrorManager.writeError(request, e);
		}
		return result;
	}

	@Description(value = "查看配置图片")
	@RequestMapping(value = "congImg", method = RequestMethod.GET)
	public void yhzp(@RequestParam("id") int id, HttpServletRequest request,
			HttpServletResponse response) {
		PhotosAndVideos photosAndVideos = pvService.getByid(id);
		byte[] userImg = photosAndVideos.getPhoto();
		response.setContentType("img/jpeg");
		response.setCharacterEncoding("utf-8");
		OutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			InputStream in = new ByteArrayInputStream(userImg);
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = in.read(buf, 0, 1024)) != -1) {
				outputStream.write(buf, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

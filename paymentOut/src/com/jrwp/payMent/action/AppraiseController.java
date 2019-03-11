package com.jrwp.payMent.action;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Core_User;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.service.IDeptService;
import com.jrwp.core.service.IUserService;
import com.jrwp.core.utils.*;
import com.jrwp.payMent.entity.AppraiseDetail;
import com.jrwp.payMent.entity.AppraiseModel;
import com.jrwp.payMent.help.ApprModelHelper;
import com.jrwp.payMent.service.ApprDetailService;
import com.jrwp.payMent.service.ApprModelService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * 这是评价模块的控制器
 */

@Description("评价模板管理")
@Controller
@RequestMapping("appraiseContrller")
public class AppraiseController {
	@Resource
	private ApprModelService apprModelService;
	@Resource
	private ApprDetailService detailService;
	@Resource
	private IUserService userService;
	@Resource
	private IDeptService deptService;

	@Description("评价模板窗口列表页面")
	@RequestMapping("appraiseList")
	public ModelAndView appraiseList() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("appraise/appraiseModelList");
		return modelAndView;
	}

	@Description("评价模板微信列表页面")
	@RequestMapping("appraiseList1")
	public ModelAndView appraiseList1() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("appraise/appraiseModelList1");
		return mav;
	}

	@Description("评价模板窗口列表Json")
	@RequestMapping("apprModeleJson")
	@ResponseBody
	public ApprModelHelper apprModelJson(String modelName,
			HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {
		ApprModelHelper apprModelHelper = null;
		try {
			PageHelper.startPage(pageNum, pageSize);
			List<AppraiseModel> list = apprModelService.list(modelName);
			PageInfo<AppraiseModel> pageInfo = new PageInfo<>(list);
			apprModelHelper = new ApprModelHelper(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.writeError(request, e);
		}
		return apprModelHelper;
	}

	@Description("评价模板微信列表Json")
	@RequestMapping("apprModeleJson1")
	@ResponseBody
	public ApprModelHelper apprModelJson1(String modelName,
			HttpServletRequest request) {
		ApprModelHelper apprModelHelper = null;
		try {
			List<AppraiseModel> list = apprModelService.list1(modelName);
			PageInfo<AppraiseModel> pageInfo = new PageInfo<>(list);
			apprModelHelper = new ApprModelHelper(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.writeError(request, e);
		}
		return apprModelHelper;
	}

	@Description("添加窗口模板页面")
	@RequestMapping("addApprModel")
	public ModelAndView add(AppraiseModel appraiseModel) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("appraise/appraiseDetailInfo");
		return modelAndView;

	}

	@Description("添加微信模板页面")
	@RequestMapping("addApprModel1")
	public ModelAndView addWechat(AppraiseModel appraiseModel) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("appraise/appraiseDetailInfo1");
		return modelAndView;

	}

	@Description("删除模板")
	@RequestMapping("deleteApprModel")
	@ResponseBody
	public DoResult deleteApprModel(int id) {
		DoResult result = new DoResult();
		try {
			apprModelService.delete(id);
			detailService.deleteByModelId(id);
			result.setStateType(DoResultType.success);
			result.setStateMsg("删除成功");
			result.setStateValue(id);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("删除失败");
		}
		return result;
	}

	@Description("窗口模板详情页面")
	@RequestMapping("modelDetails")
	public ModelAndView modelDetails(HttpServletRequest request, Integer id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			AppraiseModel appraiseModel = apprModelService.getById(id);
			List<AppraiseDetail> details = detailService.getByModelId(id);
			List<AppraiseDetail> list = detailService.getDetailByModelId(id);
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					AppraiseDetail detail = list.get(i);
					if (detail.getIswechat() == true) {
						detail.setIswewarning(detail.getIswarning());
					}
					if (detail.getIswechat() == true
							&& detail.getIswarning() == true) {
						appraiseModel.setWewarn(true);
						break;
					}
				}
				modelAndView.addObject("list", list);
			}
			if (details.size() > 0) {
				for (int i = 0; i < details.size(); i++) {
					AppraiseDetail detail = details.get(i);
					if (detail.getIswarning() != null
							&& detail.getIswarning() == true) {
						appraiseModel.setHaswarn(true);
						break;
					}
				}
				modelAndView.addObject("details", details);
			}
			modelAndView.addObject("model", appraiseModel);
			modelAndView.setViewName("appraise/modelDetail");
		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.writeError(request, e);
		}
		return modelAndView;

	}

	@Description("微信模板详情页面")
	@RequestMapping("modelDetails1")
	public ModelAndView modelDetails1(HttpServletRequest request, Integer id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			AppraiseModel appraiseModel = apprModelService.getById(id);
			List<AppraiseDetail> details = detailService.getByModelId(id);
			modelAndView.addObject("model", appraiseModel);
			modelAndView.addObject("details", details);
			modelAndView.setViewName("appraise/modelDetail1");
		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.writeError(request, e);
		}
		return modelAndView;

	}

	@Description("修改窗口模板页面")
	@RequestMapping("editApprModel")
	public ModelAndView editApprModel(HttpServletRequest request, Integer id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("appraise/appraiseDetailInfo");
		try {
			AppraiseModel appraiseModel = apprModelService.getById(id);
			List<AppraiseDetail> details = detailService.getByModelId(id);
			List<AppraiseDetail> list = detailService.getDetailByModelId(id);
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					AppraiseDetail detail = list.get(i);
					if (detail.getIswechat() == true) {
						detail.setIswewarning(detail.getIswarning());
					}
					if (detail.getIswechat() == true
							&& detail.getIswarning() == true) {
						appraiseModel.setWewarn(true);
						break;
					}
				}
				modelAndView.addObject("list", list);
			}
			if (details.size() > 0) {
				for (int i = 0; i < details.size(); i++) {
					AppraiseDetail detail = details.get(i);
					if (detail.getIswarning() != null
							&& detail.getIswarning() == true) {
						appraiseModel.setHaswarn(true);
						break;
					}
				}
				modelAndView.addObject("details", details);
			}
			modelAndView.addObject("model", appraiseModel);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.writeError(request, e);
		}
		return modelAndView;
	}

	@Description("修改微信模板页面")
	@RequestMapping("editApprModel1")
	public ModelAndView editApprModel1(HttpServletRequest request, Integer id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			AppraiseModel appraiseModel = apprModelService.getById(id);
			List<AppraiseDetail> details = detailService.getByModelId(id);
			modelAndView.setViewName("appraise/appraiseDetailInfo1");
			modelAndView.addObject("model", appraiseModel);
			modelAndView.addObject("details", details);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.writeError(request, e);
		}
		return modelAndView;
	}

	@Description("保存模板")
	@RequestMapping("saveAppraModel")
	@ResponseBody
	public DoResult saveAppraModel(AppraiseModel appraiseModel,
			HttpServletRequest request) {
		DoResult result = new DoResult();
		String msg;
		try {
			Core_User user = (Core_User) request.getSession().getAttribute(
					"user");
			user = userService.getByUserName(user.getUserName());
			appraiseModel.setCreateuser(user.getId().intValue());
			apprModelService.save(appraiseModel);
			msg = "保存成功";
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.writeError(request, e);
			msg = "保存失败";
			result.setStateType(DoResultType.fail);
		}
		result.setStateMsg(msg);
		return result;
	}

	@Description("节点管理页面")
	@RequestMapping("apprDetail")
	public ModelAndView detail(HttpServletRequest request, Integer id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			if (id != null) {
				AppraiseModel appraiseModel = apprModelService.getById(id);
				List<AppraiseDetail> details = detailService.getByModelId(id);
				modelAndView.addObject("model", appraiseModel);
				modelAndView.addObject("details", details);
			}
			modelAndView.setViewName("appraise/appraiseDetailInfo");

		} catch (Exception e) {
			e.printStackTrace();
			ErrorManager.writeError(request, e);
		}
		return modelAndView;
	}

	@Description("评价照片")
	@RequestMapping(value = "showImg", method = RequestMethod.GET)
	public void showImg(@RequestParam("id") Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		OutputStream out = null;
		try {
			AppraiseDetail detail = detailService.selectByPrimaryKey(id);
			byte[] ImgByte = detail.getIcon();
			response.setContentType("img/jpeg");
			response.setCharacterEncoding("utf-8");
			out = response.getOutputStream();
			InputStream in = null;
			if (ImgByte != null) {
				in = new ByteArrayInputStream(ImgByte);
				int len = 0;
				byte[] buf = new byte[1024];
				while ((len = in.read(buf, 0, 1024)) != -1) {
					out.write(buf, 0, len);
				}
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

	@Description("删除节点")
	@RequestMapping("deleteDetail")
	@ResponseBody
	public DoResult deleteDetail(int id) {
		DoResult result = new DoResult();
		System.out.println("=======这是删除节点的id:" + id);
		try {
			detailService.deleteByPrimaryKey(id);
			result.setStateType(DoResultType.success);
			result.setStateMsg("删除成功！");
		} catch (Exception e) {
			result.setStateType(DoResultType.fail);
			result.setStateMsg("删除失败！");
			e.printStackTrace();
		}
		return result;
	}

	@Description("保存微信模板明细")
	@RequestMapping("saveAppraDetail1")
	@ResponseBody
	public DoResult saveAppraDetail1(
			HttpServletRequest request,
			@RequestParam(value = "modelId", required = false) String modelId,
			@RequestParam(value = "modelname", required = false) String modelname,
			@RequestParam(value = "appraisename", required = false) String[] appraisename) {
		DoResult result = new DoResult();
		try {
			if (modelId == null || modelId.equals("")) {
				Core_User user = (Core_User) request.getSession().getAttribute(
						"user");
				user = userService.getByUserName(user.getUserName());
				AppraiseModel model = new AppraiseModel();
				model.setModelname(modelname);
				model.setCreateuser(user.getId().intValue());
				apprModelService.insert1(model);
				int mid = model.getId();// 返回的模版id
				for (int i = 0; i < appraisename.length; i++) {
					AppraiseDetail detail = new AppraiseDetail();
					detail.setAppraisemodelid(mid);
					detail.setAppraisename(appraisename[i]);
					detailService.insert1(detail);
				}
				result.setStateType(DoResultType.success);
				result.setStateMsg("保存成功！");
			} else {
				int mid = Integer.parseInt(modelId);
				AppraiseModel model = new AppraiseModel();
				model.setId(mid);
				model.setModelname(modelname);
				apprModelService.updateByPrimaryKeySelective(model);
				if (appraisename.length > 0) {
					detailService.deleteByModelId(mid);
					for (int i = 0; i < appraisename.length; i++) {
						AppraiseDetail detail = new AppraiseDetail();
						detail.setAppraisemodelid(mid);
						detail.setAppraisename(appraisename[i]);
						detailService.insert1(detail);
					}
					result.setStateType(DoResultType.success);
					result.setStateMsg("保存成功！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStateType(DoResultType.fail);
			result.setStateMsg("保存失败！");
		}
		return result;
	}

	@Description("保存窗口模板明细")
	@RequestMapping(value = "saveAppraDetail", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveAppraDetail(
			@RequestParam(value = "myImage", required = false) MultipartFile[] file,
			@RequestParam(value = "modelId", required = false) String modelId,
			@RequestParam(value = "modelname", required = false) String modelname,
			HttpServletRequest request,
			@RequestParam(value = "appraisename", required = false) String[] appraisename,
			@RequestParam(value = "appraisevalue", required = false) int[] appraisevalue,
			@RequestParam(value = "outtime", required = false) Integer outtime,
			@RequestParam(value = "isautoappraise", required = false) Boolean isautoappraise,
			@RequestParam(value = "isdefault", required = false) Boolean[] isdefault,
			@RequestParam(value = "did", required = false) int[] did,
			@RequestParam(value = "weid", required = false) int[] weid,
			@RequestParam(value = "isFile", required = false) int[] isFile,
			@RequestParam(value = "weappraisename", required = false) String[] weappraisename,
			@RequestParam(value = "weappraisevalue", required = false) int[] weappraisevalue,
			@RequestParam(value = "iswarning", required = false) boolean iswarning,
			@RequestParam(value = "isWewarning", required = false) boolean isWewarning,
			@RequestParam(value = "iswarn", required = false) Boolean[] iswarn,
			@RequestParam(value = "isWewarn", required = false) Boolean[] isWewarn,
			int isQuestionnaire) {
		// MultipartHttpServletRequest multipartHttpServletRequest =
		// (MultipartHttpServletRequest)request;
		// List<MultipartFile> files =
		// multipartHttpServletRequest.getFiles("myImage");
		// System.out.println("uploadFiles:"+files.size());
		DoResult result = new DoResult();
		// InputStream in = null;
		// FileImageOutputStream out = null;
		// OutputStream outStream = null;
		// ByteArrayOutputStream bos = null;
		try {
			// 执行添加操作
			if (modelId == null || modelId.equals("")) {
				Core_User user = (Core_User) request.getSession().getAttribute(
						"user");
				user = userService.getByUserName(user.getUserName());
				AppraiseModel model = new AppraiseModel();
				model.setModelname(modelname);
				if (!"".equals(outtime)) {
					model.setOuttime(outtime);
				}
				if (isautoappraise != null || !("").equals(isautoappraise)) {
					model.setIsautoappraise(isautoappraise);
				}
				if (iswarning == true || isWewarning == true) {
					model.setIswarn(true);
				} else {
					model.setIswarn(false);
				}
				model.setIsQuestionnaire(isQuestionnaire);
				model.setCreateuser(user.getId().intValue());
				apprModelService.insertSelective(model);
				int mid = model.getId();// 返回的模版id
				if (appraisename != null && appraisename.length > 0
						&& appraisename.length == appraisevalue.length) {
					// detailService.deleteByModelId(mid);
					for (int i = 0; i < appraisename.length; i++) {
						AppraiseDetail detail = new AppraiseDetail();
						detail.setAppraisemodelid(mid);
						detail.setAppraisename(appraisename[i]);
						detail.setAppraisevalue(appraisevalue[i]);
						detail.setIsdefault(isdefault[i]);
						detail.setIswarning(iswarn[i]);
						detail.setIswechat(false);
						// 图片处理
						String path = request.getServletContext().getRealPath(
								"/Icon/");
						String filename = System.currentTimeMillis() + ".jpg";
						CommonsMultipartFile cf = (CommonsMultipartFile) file[i];
						DiskFileItem df = (DiskFileItem) cf.getFileItem();
						File f = df.getStoreLocation();
						BufferedImage BI = ImageIO.read(f);
						int w = BI.getWidth();
						int h = BI.getHeight();
						System.out.println("W:" + w + "&&" + "H:" + h);
						if (w * h <= 160 * 160) {
							byte[] ImgBytes = ToBytes.getBytes(f);
							detail.setIcon(ImgBytes);
						} else {
							byte[] ImgBytes = resize(f, 160, 160, path,
									filename);
							detail.setIcon(ImgBytes);
						}
						// 保存图片路径
						detail.setIconpath("/Icon/" + filename);
						detailService.insertSelective(detail);
						// detailService.insert(detail);
					}
				}
				if (weappraisename != null && weappraisename.length > 0
						&& isWewarn.length == weappraisename.length
						&& weappraisevalue.length == weappraisename.length) {
					for (int k = 0; k < weappraisename.length; k++) {
						AppraiseDetail detail = new AppraiseDetail();
						detail.setAppraisemodelid(mid);
						detail.setAppraisename(weappraisename[k]);
						detail.setAppraisevalue(weappraisevalue[k]);
						detail.setIswarning(isWewarn[k]);
						detail.setIswechat(true);
						detailService.insertSelective(detail);
					}
				}
				result.setStateType(DoResultType.success);
				result.setStateMsg("保存成功！");
				// 执行更新操作
			} else {
				int mid = Integer.parseInt(modelId);
				AppraiseModel model = new AppraiseModel();
				model.setId(mid);
				model.setModelname(modelname);
				if (!"".equals(outtime)) {
					model.setOuttime(outtime);
				}
				if (isautoappraise != null || !("").equals(isautoappraise)) {
					model.setIsautoappraise(isautoappraise);
				}
				if (iswarning == true || isWewarning == true) {
					model.setIswarn(true);
				} else {
					model.setIswarn(false);
				}
				model.setIsQuestionnaire(isQuestionnaire);
				apprModelService.updateByPrimaryKeySelective(model);
				if (appraisename != null && appraisename.length > 0
						&& appraisename.length == appraisevalue.length) {
					int flag = 0;
					// detailService.deleteByModelId(mid);
					for (int i = 0; i < appraisename.length; i++) {
						if (did[i] != 0) {
							if (isFile[i] == 0) {
								// 没文件，不需要更新图片字段 只需要更新名字和val
								AppraiseDetail detail = new AppraiseDetail();
								detail.setId(did[i]);
								detail.setAppraisemodelid(mid);
								detail.setAppraisename(appraisename[i]);
								detail.setAppraisevalue(appraisevalue[i]);
								detail.setIsdefault(isdefault[i]);
								detailService
										.updateByPrimaryKeySelective(detail);
							} else {
								// 同时更新文件 文件是file[flag]
								AppraiseDetail detail = new AppraiseDetail();
								detail.setId(did[i]);
								detail.setAppraisemodelid(mid);
								detail.setAppraisename(appraisename[i]);
								detail.setAppraisevalue(appraisevalue[i]);
								detail.setIsdefault(isdefault[i]);
								// 图片处理
								String path = request.getServletContext()
										.getRealPath("/Icon/");
								String filename = System.currentTimeMillis()
										+ ".jpg";
								for (int j = flag; j < file.length; j++) {
									if (file[j].getSize() != 0) {
										flag = j;
									}
								}
								CommonsMultipartFile cf = (CommonsMultipartFile) file[flag];
								DiskFileItem df = (DiskFileItem) cf
										.getFileItem();
								File f = df.getStoreLocation();
								BufferedImage BI = ImageIO.read(f);
								int w = BI.getWidth();
								int h = BI.getHeight();
								System.out.println("W:" + w + "&&" + "H:" + h);
								if (w * h <= 160 * 160) {
									byte[] ImgBytes = ToBytes.getBytes(f);
									detail.setIcon(ImgBytes);
								} else {
									byte[] ImgBytes = resize(f, 160, 160, path,
											filename);
									detail.setIcon(ImgBytes);
								}
								// 保存图片路径
								detail.setIconpath("/Icon/" + filename);
								detailService
										.updateByPrimaryKeySelective(detail);
								flag++;
							}
						} else {
							// insert 所有字段 file[flag] flag++
							AppraiseDetail detail = new AppraiseDetail();
							// detail.setId(did[i]);
							detail.setAppraisemodelid(mid);
							detail.setAppraisename(appraisename[i]);
							detail.setAppraisevalue(appraisevalue[i]);
							detail.setIsdefault(isdefault[i]);
							// 图片处理
							String path = request.getServletContext()
									.getRealPath("/Icon/");
							String filename = System.currentTimeMillis()
									+ ".jpg";
							CommonsMultipartFile cf = (CommonsMultipartFile) file[flag];
							DiskFileItem df = (DiskFileItem) cf.getFileItem();
							File f = df.getStoreLocation();
							BufferedImage BI = ImageIO.read(f);
							int w = BI.getWidth();
							int h = BI.getHeight();
							System.out.println("W:" + w + "&&" + "H:" + h);
							if (w * h <= 160 * 160) {
								byte[] ImgBytes = ToBytes.getBytes(f);
								detail.setIcon(ImgBytes);
							} else {
								byte[] ImgBytes = resize(f, 160, 160, path,
										filename);
								detail.setIcon(ImgBytes);
							}
							// 保存图片路径
							detail.setIconpath("/Icon/" + filename);
							detailService.insert(detail);
							flag++;
						}
					}
				}
				// 保存微信模板信息
				if (weappraisename != null && weappraisename.length > 0
						&& isWewarn.length == weappraisename.length
						&& weappraisevalue.length == weappraisename.length) {
					for (int k = 0; k < weappraisename.length; k++) {
						AppraiseDetail detail = new AppraiseDetail();
						detail.setId(weid[k]);
						detail.setAppraisemodelid(mid);
						detail.setAppraisename(weappraisename[k]);
						detail.setAppraisevalue(weappraisevalue[k]);
						detail.setIswarning(isWewarn[k]);
						detail.setIswechat(true);
						detailService.updateByPrimaryKeySelective(detail);
					}
				}
				result.setStateType(DoResultType.success);
				result.setStateMsg("保存成功！");
			}
		} catch (Exception e) {
			result.setStateType(DoResultType.fail);
			result.setStateMsg("保存失败！");
			e.printStackTrace();
			ErrorManager.writeError(request, e);
		}
		return JacksonUtil.toJson(result);
	}
	
	@Description("模板弹窗列表")
	@RequestMapping("modelDialog")
	public ModelAndView modelDialogList(String actiontype, String modelId){
		ModelAndView maView = new ModelAndView();
		maView.addObject("actiontype", actiontype);
		maView.addObject("modelId", modelId == null || modelId == "" ? "0"
				: modelId);
		maView.setViewName("appraise/modelDialog");
		return maView;
	}
	
	/**
	 * 强制压缩/放大图片到固定的大小
	 * 
	 * @param w
	 *            int 新宽度
	 * @param h
	 *            int 新高度
	 */
	public byte[] resize(File f, int w, int h, String path, String filename)
			throws IOException {
		// SCALE_SMOOTH的缩略算法生成缩略图片的平滑度的优先级比速度高生成的图片质量比较好但速度慢
		FileOutputStream out = null;
		byte[] imgdate = null;
		try {
			Image img = ImageIO.read(f);
			BufferedImage image = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
			File destFile = new File(path, filename);
			// 判断路径是否存在，如果不存在就创建一个
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdirs();
			}
			out = new FileOutputStream(destFile); // 输出到文件流
			// 可以正常实现bmp、png、gif转jpg
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image); // JPEG编码
			imgdate = ToBytes.getBytes(destFile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return imgdate;
	}
}
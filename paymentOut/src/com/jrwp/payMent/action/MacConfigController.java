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
import com.jrwp.payMent.entity.CallConfigModel;
import com.jrwp.payMent.entity.CallConfigParam;
import com.jrwp.payMent.help.CallConfigModelHelper;
import com.jrwp.payMent.help.CallConfigParamHelper;
import com.jrwp.payMent.service.CallConfigModelService;
import com.jrwp.payMent.service.CallConfigParamService;

@Description("叫号机参数配置管理")
@Controller
@RequestMapping("macConfigController")
public class MacConfigController {

	@Resource
	private CallConfigModelService configModelService;
	@Resource
	private CallConfigParamService configParamService;

	@Description("配置模板列表")
	@RequestMapping("configModelList")
	public ModelAndView configModelList() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("callconfig/modelList");
		return maView;
	}

	@Description("配置模板列表Json")
	@RequestMapping("configModelListJson")
	@ResponseBody
	public CallConfigModelHelper configModelListJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			String queryinfo) {
		CallConfigModelHelper callConfigModelHelper = null;
		List<CallConfigModel> list = new ArrayList<>();
		try {
			PageHelper.startPage(pageNum, pageSize);
			list = configModelService.list(queryinfo);
			PageInfo<CallConfigModel> pageInfo = new PageInfo<>(list);
			callConfigModelHelper = new CallConfigModelHelper(pageInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LogUtil.error("配置模板列表Json接口异常:{}", e.getMessage());
		}
		return callConfigModelHelper;
	}

	@Description("状态更新")
	@RequestMapping("setModelStart")
	@ResponseBody
	public DoResult setModelStart(Long id, int isstart) {
		DoResult result = new DoResult();
		try {
			CallConfigModel model = new CallConfigModel();
			model.setId(id);
			model.setIsStart(isstart);
			configModelService.update(model);
			result.setStateMsg("更新成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("更新失败");
			result.setStateType(DoResultType.fail);
			LogUtil.error("模板状态更新异常:{}", e.getMessage());
		}
		return result;
	}

	@Description("添加配置模板")
	@RequestMapping("addConfigModel")
	public ModelAndView addConfigModel() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("callconfig/modelInfo");
		return maView;
	}

	@Description("配置模板参数页")
	@RequestMapping("configParamsList")
	public ModelAndView configParams(Long modelId) {
		ModelAndView maView = new ModelAndView();
		if (modelId == null) {
			modelId = 0l;
		}
		maView.addObject("modelId", modelId);
		maView.setViewName("callconfig/modelParams");
		return maView;
	}

	@Description("配置模板参数页Json")
	@RequestMapping("configParamsJson")
	@ResponseBody
	public CallConfigParamHelper configParamsJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			Long modelId) {
		CallConfigParamHelper callConfigParamHelper = null;
		List<CallConfigParam> list = new ArrayList<>();
		try {
			PageHelper.startPage(pageNum, pageSize);
			list = configParamService.listByModelIdDefault(modelId);
			PageInfo<CallConfigParam> pageInfo = new PageInfo<>(list);
			callConfigParamHelper = new CallConfigParamHelper(pageInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LogUtil.error("配置模板参数页Json异常:{}", e.getMessage());
		}
		return callConfigParamHelper;
	}

	@Description("添加参数")
	@RequestMapping("addParam")
	public ModelAndView addParam(Long modelId) {
		ModelAndView maView = new ModelAndView();
		maView.addObject("modelId", modelId);
		maView.setViewName("callconfig/addparam");
		return maView;
	}

	@Description("参数节点弹窗列表")
	@RequestMapping("listDialog")
	public ModelAndView listDialog(String actiontype, String parentId,
			Long modelId) {
		ModelAndView maView = new ModelAndView();
		maView.addObject("actiontype", actiontype);
		maView.addObject("modelId", modelId);
		maView.addObject("roleids", parentId == null || parentId == "" ? "0"
				: parentId);
		maView.setViewName("callconfig/listDialog");
		return maView;
	}

	@Description("参数弹窗列表Json")
	@RequestMapping("listDialogJson")
	@ResponseBody
	public CallConfigParamHelper listDialogJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			Long modelId) {
		CallConfigParamHelper callConfigParamHelper = null;
		List<CallConfigParam> list = new ArrayList<>();
		try {
			PageHelper.startPage(pageNum, pageSize);
			list = configParamService.listByModelIdDefault(modelId);
			if (list.size() == 0) {
				CallConfigParam param = new CallConfigParam();
				param.setId(0l);
				param.setTitle("默认菜单");
				param.setUpdatetime(new Date());
				list.add(param);
			}
			PageInfo<CallConfigParam> pageInfo = new PageInfo<>(list);
			callConfigParamHelper = new CallConfigParamHelper(pageInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LogUtil.error("配置模板参数页Json异常:{}", e.getMessage());
		}
		return callConfigParamHelper;
	}

	@Description("保存配置参数")
	@RequestMapping("saveParam")
	@ResponseBody
	public DoResult saveParam(
			Long id,
			Long modelId,
			Long parentId,
			String title,
			String topCoord,
			String leftCoord,
			Integer businessType,
			String remark,
			@RequestParam(value = "myImage", required = false) MultipartFile file,
			HttpServletResponse response, HttpServletRequest request) {
		DoResult result = new DoResult();
		CallConfigParam param = new CallConfigParam();
		try {
			if(id == null){
				param.setId(id);
				param.setModelId(modelId);
				param.setParentId(parentId);
				param.setTitle(title);
				param.setTopCoord(topCoord);
				param.setLeftCoord(leftCoord);
				param.setBusinessType(businessType);
				param.setRemark(remark);
				param.setUpdatetime(new Date());
				if (file != null) {
					if (!file.isEmpty()) {
						long fileSize = file.getSize();
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
						} else if (fileSize > (2 * 1024 * 1024)) {
							LogUtil.error("上传照片失败:{}文件大小不能超过2M");
							result.setStateType(DoResultType.fail);
							result.setStateMsg("保存失败:文件大小不能超过2M");
						} else {
							// 将CommonsMultipartFile转化为file类
							CommonsMultipartFile cf = (CommonsMultipartFile) file;
							DiskFileItem fi = (DiskFileItem) cf.getFileItem();
							File f = fi.getStoreLocation();
							// 保存到数据库
							param.setIcon(ToBytes.getBytes(f));
							configParamService.save(param);
							result.setStateMsg("保存成功");
							result.setStateType(DoResultType.success);
						}
					}
				}
			}else {
				if(id == parentId){
					result.setStateMsg("保存失败:父级选择错误!");
					result.setStateType(DoResultType.fail);
				}else {
					param.setId(id);
					param.setModelId(modelId);
					param.setParentId(parentId);
					param.setTitle(title);
					param.setTopCoord(topCoord);
					param.setLeftCoord(leftCoord);
					param.setBusinessType(businessType);
					param.setRemark(remark);
					param.setUpdatetime(new Date());
					if (file != null) {
						if (!file.isEmpty()) {
							long fileSize = file.getSize();
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
							} else if (fileSize > (2 * 1024 * 1024)) {
								LogUtil.error("上传照片失败:{}文件大小不能超过2M");
								result.setStateType(DoResultType.fail);
								result.setStateMsg("保存失败:文件大小不能超过2M");
							} else {
								// 将CommonsMultipartFile转化为file类
								CommonsMultipartFile cf = (CommonsMultipartFile) file;
								DiskFileItem fi = (DiskFileItem) cf.getFileItem();
								File f = fi.getStoreLocation();
								// 保存到数据库
								param.setIcon(ToBytes.getBytes(f));
								configParamService.update(param);
								result.setStateMsg("保存成功");
								result.setStateType(DoResultType.success);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("保存失败");
			result.setStateType(DoResultType.fail);
			LogUtil.error("保存叫号机参数配置异常:{}", e.getMessage());
		}
		return result;
	}

	@Description("保存参数配置模板")
	@RequestMapping("saveModel")
	@ResponseBody
	public DoResult saveModel(Long modelId, String modelName, Integer isStart) {
		DoResult result = new DoResult();
		try {
			CallConfigModel model = new CallConfigModel();
			model.setId(modelId);
			model.setIsStart(isStart);
			model.setModelName(modelName);
			model.setCreatetime(new Date());
			configModelService.save(model);
			result.setStateMsg("保存成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("保存失败");
			result.setStateType(DoResultType.fail);
			LogUtil.error("保存参数配置模板异常:{}", e.getMessage());
		}
		return result;
	}

	@Description("编辑模板参数")
	@RequestMapping("alterModelParams")
	public ModelAndView alterModelParams(Long id, Long modelId) {
		ModelAndView maView = new ModelAndView();
		try {
			CallConfigParam param = configParamService.getObjectById(id);
			CallConfigParam parent = configParamService.getObjectById(param
					.getParentId());
			if (parent != null) {
				param.setParentName(parent.getTitle());
			} else {
				param.setParentName("默认");
			}
			maView.addObject("id", id);
			maView.addObject("modelId", modelId);
			maView.addObject("paramObject", param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		maView.setViewName("callconfig/addparam");
		return maView;
	}

	@Description("读取图标")
	@RequestMapping("getIcon")
	@ResponseBody
	public void getphoto(long id, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("img/jpeg");
		response.setCharacterEncoding("utf-8");
		try {
			CallConfigParam param = configParamService.getObjectById(id);
			byte[] icon = param.getIcon();
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
				output.close();
				input.close();
			} else {
				OutputStream outputStream = response.getOutputStream();
				in = new ByteArrayInputStream(icon);
				int len = 0;
				byte[] buf = new byte[1024];
				while ((len = in.read(buf, 0, 1024)) != -1) {
					outputStream.write(buf, 0, len);
				}
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error("图标读取失败:{}", e.getMessage());
		}
	}

	@Description("删除参数模板")
	@RequestMapping("delete")
	@ResponseBody
	public DoResult delete(Long id) {
		DoResult result = new DoResult();
		try {
			configModelService.delete(id);
			result.setStateMsg("删除成功");
			result.setStateType(DoResultType.success);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("删除失败");
			result.setStateType(DoResultType.fail);
		}
		return result;
	}

	@Description("参数模板弹窗列表")
	@RequestMapping("configModelDialog")
	public ModelAndView configModelDialog(String actiontype, String modelId) {
		ModelAndView maView = new ModelAndView();
		maView.addObject("actiontype", actiontype);
		maView.addObject("modelId", modelId == null || modelId == "" ? "0"
				: modelId);
		maView.setViewName("callconfig/modelDialog");
		return maView;
	}
}

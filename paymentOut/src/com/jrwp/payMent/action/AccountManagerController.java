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
import org.apache.log4j.Logger;
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
import com.jrwp.core.entity.Core_Dept;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.service.IDeptService;
import com.jrwp.core.utils.LogUtil;
import com.jrwp.core.utils.ToBytes;
import com.jrwp.payMent.entity.AccountManager;
import com.jrwp.payMent.help.CommonHelper;
import com.jrwp.payMent.service.IAccountManagerService;

/**
 * 账号管理
 * 
 * @author hsl
 * 
 */
@Description("账号管理")
@Controller
@RequestMapping("accountManagerController")
public class AccountManagerController {

	@Resource
	private IAccountManagerService accountManagerService;
	@Resource
	private IDeptService deptService;

	Logger logger = Logger.getLogger(AccountManagerController.class);

	@Description("账号列表")
	@RequestMapping("accountList")
	public ModelAndView AccountList() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("Account/List");
		return maView;
	}

	@Description("账号列表Json")
	@RequestMapping("accountListJson")
	@ResponseBody
	public CommonHelper<AccountManager> AccountListJson(
			@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			String queryinfo) {
		CommonHelper<AccountManager> commonHelper = null;
		List<AccountManager> list = new ArrayList<>();
		try {
			PageHelper.startPage(pageNum, pageSize);
			list = accountManagerService.list(queryinfo);
			PageInfo<AccountManager> pageInfo = new PageInfo<>(list);
			commonHelper = new CommonHelper<AccountManager>(pageInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.info("json接口[AccountListJson]发生异常:", e);
			LogUtil.info("json接口[AccountListJson]发生异常:{}", e.getMessage());
		}
		return commonHelper;
	}

	@Description("添加账号")
	@RequestMapping("addAccount")
	public ModelAndView addAccount() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("Account/Info");
		return maView;
	}

	@Description("编辑账号")
	@RequestMapping("editAccount")
	public ModelAndView editAccount(Long id) {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("Account/Info");
		try {
			AccountManager accountManager = accountManagerService
					.getObjectById(id);
			Core_Dept dept  = deptService.getObjectById(accountManager.getDeptid());
			accountManager.setDept(dept);
			maView.addObject("account", accountManager);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return maView;
	}

	@Description("保存账号")
	@RequestMapping("saveAccount")
	@ResponseBody
	public DoResult saveAccount(
			AccountManager accountManager,
			@RequestParam(value = "myImage", required = false) MultipartFile file) {
		DoResult result = new DoResult();
		try {
			if (accountManager.getId() == null) {
				if (file != null) {
					// 将CommonsMultipartFile转化为file类
					// CommonsMultipartFile cf = (CommonsMultipartFile) file;
					// DiskFileItem fi = (DiskFileItem) cf.getFileItem();
					// File f = fi.getStoreLocation();
					InputStream iStream = file.getInputStream();
					ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
					byte[] bb = new byte[2048];
					int ch;
					ch = iStream.read(bb);
					while (ch != -1) {
						bytestream.write(bb, 0, ch);
						ch = iStream.read(bb);
					}
					// 保存到数据库
					accountManager.setPhoto(bytestream.toByteArray());
					accountManager.setCreatetime(new Date());
					accountManagerService.save(accountManager);
					result.setStateMsg("保存成功");
					result.setStateType(DoResultType.success);
				} else {
					result.setStateMsg("保存失败:图片为空");
					result.setStateType(DoResultType.success);
				}
			} else {
				if (file != null) {
					// 将CommonsMultipartFile转化为file类
					CommonsMultipartFile cf = (CommonsMultipartFile) file;
					DiskFileItem fi = (DiskFileItem) cf.getFileItem();
					File f = fi.getStoreLocation();
					// 保存到数据库
					accountManager.setPhoto(ToBytes.getBytes(f));
					accountManagerService.update(accountManager);
				} else {
					accountManagerService.update(accountManager);
				}
				result.setStateMsg("保存成功");
				result.setStateType(DoResultType.success);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setStateMsg("保存成功");
			result.setStateType(DoResultType.fail);
			LogUtil.info("保存账号接口[saveAccount]异常:{}", e.getMessage());
		}
		return result;
	}

	@Description("删除账号")
	@RequestMapping("delete")
	@ResponseBody
	public DoResult delete(Long id) {
		DoResult result = new DoResult();
		try {
			accountManagerService.delete(id);
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

	@Description("读取图标")
	@RequestMapping("getIcon")
	@ResponseBody
	public void getphoto(long id, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("img/jpeg");
		response.setCharacterEncoding("utf-8");
		try {
			AccountManager deptaddress = accountManagerService
					.getObjectById(id);
			byte[] icon = deptaddress.getPhoto();
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

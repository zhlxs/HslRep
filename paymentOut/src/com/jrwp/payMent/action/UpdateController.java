package com.jrwp.payMent.action;

import com.github.pagehelper.PageInfo;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.payMent.dao.HotelUpdateFileMapper;
import com.jrwp.payMent.dao.HotelUpdateMapper;
import com.jrwp.payMent.entity.HotelUpdate;
import com.jrwp.payMent.entity.HotelUpdateFile;
import com.jrwp.payMent.help.UpdateFileHelper;
import com.jrwp.payMent.help.UpdateIndexHelper;
import com.jrwp.payMent.until.FileUntil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

@Description("自动更新")
@Controller
@RequestMapping("updateController")
public class UpdateController {
    @Resource
    private HotelUpdateMapper hotelUpdateMapper;
    @Resource
    private HotelUpdateFileMapper hotelUpdateFileMapper;

    @Description("主页")
    @RequestMapping("index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("update/index");
        return modelAndView;
    }

    @Description("主页indexJson")
    @RequestMapping("indexJson")
    @ResponseBody
    public UpdateIndexHelper indexJson() {
        UpdateIndexHelper updateIndexHelper = null;
        try {
            List<HotelUpdate> hotelUpdates = hotelUpdateMapper.list();
            PageInfo pageInfo = new PageInfo(hotelUpdates);
            updateIndexHelper = new UpdateIndexHelper(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updateIndexHelper;
    }

    @Description("增加升级程序")
    @RequestMapping("addUpdate")
    public ModelAndView addUpdate() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("update/updateInfo");
        return modelAndView;
    }


    @Description("编辑升级程序")
    @RequestMapping("editUpdate")
    public ModelAndView editUpdate(BigDecimal id) {
        ModelAndView modelAndView = new ModelAndView();
        HotelUpdate update = hotelUpdateMapper.selectByPrimaryKey(id);
        modelAndView.addObject("update", update);
        modelAndView.setViewName("update/updateInfo");
        return modelAndView;
    }

    @Description("删除升级程序")
    @RequestMapping("deleteUpdate")
    @ResponseBody
    public DoResult deleteUpdate(BigDecimal id) {
        DoResult result = new DoResult();
        try {
            hotelUpdateMapper.deleteByPrimaryKey(id);
            result.setStateMsg("删除成功");
            result.setStateType(DoResultType.success);
        } catch (Exception e) {
            result.setStateMsg(e.getMessage());
            result.setStateType(DoResultType.fail);
            e.printStackTrace();
        }
        return result;
    }

    @Description("保存升级程序")
    @RequestMapping("saveUpdate")
    @ResponseBody
    public DoResult save(HotelUpdate hotelUpdate) {
        DoResult result = new DoResult();
        try {
            if (hotelUpdate.getId() == null) {
                hotelUpdateMapper.insertSelective(hotelUpdate);
            } else {
                hotelUpdateMapper.updateByPrimaryKeySelective(hotelUpdate);
            }
            result.setStateMsg("保存成功");
            result.setStateType(DoResultType.success);
        } catch (Exception e) {
            result.setStateMsg(e.getMessage());
            result.setStateType(DoResultType.fail);
            e.printStackTrace();
        }
        return result;
    }

    @Description("dll文件主页")
    @RequestMapping("dllindex")
    public ModelAndView dellindex(BigDecimal updateid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("update/dllindex");
        modelAndView.addObject("updateid", updateid);
        return modelAndView;
    }

    @Description("dll文件主页Json")
    @RequestMapping("dllindexJson")
    @ResponseBody
    public UpdateFileHelper dllindexJson(BigDecimal updateid) {
        UpdateFileHelper updateIndexHelper = null;
        try {
            List<HotelUpdateFile> hotelUpdateFiles = hotelUpdateFileMapper.list(updateid);
            PageInfo pageInfo = new PageInfo(hotelUpdateFiles);
            updateIndexHelper = new UpdateFileHelper(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updateIndexHelper;
    }

    @Description("增加dll")
    @RequestMapping("addDll")
    public ModelAndView addDll(BigDecimal updateid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("update/dllInfo");
        modelAndView.addObject("updateid", updateid);
        return modelAndView;
    }

    @Description("编辑dll")
    @RequestMapping("editDll")
    public ModelAndView editDll(BigDecimal id) {
        ModelAndView modelAndView = new ModelAndView();
        HotelUpdateFile updateFile = hotelUpdateFileMapper.selectByPrimaryKey(id);
        modelAndView.addObject("dll", updateFile);
        modelAndView.setViewName("update/dllInfo");
        return modelAndView;
    }

    @Description("删除dll")
    @RequestMapping("deleteDll")
    @ResponseBody
    public DoResult deleteDll(BigDecimal id) {
        DoResult result = new DoResult();
        try {
            hotelUpdateFileMapper.deleteByPrimaryKey(id);
            result.setStateMsg("删除成功");
            result.setStateType(DoResultType.success);
        } catch (Exception e) {
            result.setStateMsg(e.getMessage());
            result.setStateType(DoResultType.fail);
            e.printStackTrace();
        }
        return result;
    }

    @Description("启用关闭dll")
    @RequestMapping("changeDll")
    @ResponseBody
    public DoResult changeDll(HotelUpdateFile hotelUpdateFile) {
        DoResult result = new DoResult();
        try {
            hotelUpdateFileMapper.updateByPrimaryKeySelective(hotelUpdateFile);
            result.setStateMsg("操作成功");
            result.setStateType(DoResultType.success);
        } catch (Exception e) {
            result.setStateMsg(e.getMessage());
            result.setStateType(DoResultType.fail);
            e.printStackTrace();
        }
        return result;
    }

    @Description("保存dll")
    @RequestMapping("saveDll")
    @ResponseBody
    public DoResult saveDll(String version, @RequestParam("file") MultipartFile file, BigDecimal updateid, String clientCopyPath, HttpServletRequest request) {
        DoResult result = new DoResult();
        try {
            String filename = file.getOriginalFilename();  //上传文件名
            //file.get
            if (version == null) {
                version = FileUntil.getVersion(FileUntil.M2F(file));
            }
            String url = "http://" + request.getLocalAddr() + ":" + request.getLocalPort() + "/paymentOut/Down/updateItems/" + filename;
            String path = request.getServletContext().getRealPath("/Down/updateItems/");
            File filepath = new File(path, filename);
            if (!filepath.getParentFile().exists()) { //判断路径是否存在，如果不存在就创建一个
                filepath.getParentFile().mkdirs();
            }
            file.transferTo(new File(path + File.separator + filename));        //将上传文件保存到一个目标文件当中
            check(filename); //先根据dll名字 看是否存在
            HotelUpdateFile hotelUpdateFile = new HotelUpdateFile();
            hotelUpdateFile.setVersion(version);
            hotelUpdateFile.setUpdateid(updateid);
            hotelUpdateFile.setDllname(filename);
            hotelUpdateFile.setDownloadpath(url);
            hotelUpdateFile.setIsstart((short) 1);
            hotelUpdateFile.setIsversion((short) 1);
            hotelUpdateFile.setClientcopypath(clientCopyPath);
            hotelUpdateFileMapper.insertSelective(hotelUpdateFile);
            result.setStateMsg("保存成功");
            result.setStateType(DoResultType.success);
        } catch (Exception e) {
            result.setStateMsg(e.getMessage());
            result.setStateType(DoResultType.fail);
            e.printStackTrace();
        }
        return result;
    }

    public void check(String fileName) {
        List<HotelUpdateFile> hotelUpdateFiles = hotelUpdateFileMapper.selectByDllName(fileName);
        for (HotelUpdateFile hotelUpdateFile : hotelUpdateFiles) {
            hotelUpdateFile.setIsstart((short) 0);
            hotelUpdateFileMapper.updateByPrimaryKeySelective(hotelUpdateFile);
        }
    }
}

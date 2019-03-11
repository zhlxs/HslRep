package com.jrwp.payMent.action;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Core_User;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.service.IUserService;
import com.jrwp.payMent.entity.PoliceClassService;
import com.jrwp.payMent.entity.UserService;
import com.jrwp.payMent.help.PoliceServiceHelper;
import com.jrwp.payMent.service.IPoliceClassService;
import com.jrwp.payMent.service.UserServiceBiz;
import org.omg.CORBA.Request;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.List;

@Description("用户对应业务管理")
@Controller
@RequestMapping("userServiceController")
public class UserServiceController {

    @Resource
    private UserServiceBiz userServiceBiz;
    @Resource
    private IUserService userService;
    @Resource
    private IPoliceClassService policeClassService;

    @Description("用户对应业务添加")
    @RequestMapping("addUserService")
    public ModelAndView addUserService(String uiCode) {
        ModelAndView model = new ModelAndView();
        model.setViewName("userService/adduserService");
        model.addObject("uiCode", uiCode);
        return model;
    }

    /**
     * 获取一级分类的子集
     *
     * @param list
     */
    public void getChilds(List<PoliceClassService> list, String uiCode) {
        for (PoliceClassService classService : list) {
            classService.setIsdelay(false);
            classService.setIsexpand(true);
            List<PoliceClassService> childs = policeClassService.getLevel2Uservice(classService.getSerCode(), uiCode);
            initChilds(childs);
            classService.setChildren(childs);
        }

    }

    /**
     * 判断子集是否有子集
     *
     * @param childs
     */
    public void initChilds(List<PoliceClassService> childs) {
        for (PoliceClassService service : childs) {
            //判断是否有子集
            if (policeClassService.countsize(service.getSerCode()) > 0) {
                service.setIsexpand(false);
                service.setIsdelay(true);
                service.setChildren(new ArrayList<PoliceClassService>());
            } else {
                service.setIsexpand(false);
                service.setIsdelay(false);
            }
        }
    }

    @Description("用户对应业务添加Json")
    @RequestMapping("addUserServiceJson")
    @ResponseBody
    public PoliceServiceHelper addUserServiceJson(@RequestParam(value = "page", defaultValue = "1") int pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "20") int
                                                          pageSize, String uiCode, HttpServletRequest request) {
        PoliceServiceHelper policeServiceHelper = new PoliceServiceHelper();

        Core_User user = userService.getObjectById(Long.valueOf(uiCode));
        String pcCode = user.getPcCode();
        Integer size = 1;
        policeServiceHelper.setCurrentPage(pageNum);
        policeServiceHelper.setItemsPerPage(pageSize);
        policeServiceHelper.setTotalItems(size);
        policeServiceHelper.setTotalPages(size / pageSize);
        List<PoliceClassService> list = policeClassService.getTopServices(( pageNum - 1 ) * pageSize, pageNum *
                pageSize, pcCode);
        getChilds(list, uiCode);
        policeServiceHelper.setItems(list);

        return policeServiceHelper;

    }
    @Description("用户开通业务树列表Json")
    @RequestMapping("addUserServiceTreeJson")
    @ResponseBody
    public PoliceServiceHelper policeServiceTreeJson(HttpServletRequest request, String parentCode,String uiCode) {
        PoliceServiceHelper policeServiceHelper = new PoliceServiceHelper();
        List<PoliceClassService> childs;
        try {
            childs = policeClassService.getLevel2Uservice(parentCode,uiCode);
            initChilds(childs);
            policeServiceHelper.setItems(childs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return policeServiceHelper;
    }

    @Description("用户对应业务保存")
    @RequestMapping("saveUserService")
    @ResponseBody
    public DoResult saveUserService(String[] checkid, String uiCode) {
        DoResult result = new DoResult();

        try {
            Core_User user = userService.getObjectById(Long.valueOf(uiCode));
            for (String s : checkid) {

                UserService userService = new UserService();
                userService.setChargeMode(user.getPayType().toString());
                userService.setSerCode(s);
                userService.setUiCode(uiCode);
                userServiceBiz.save(userService);
            }
            result.setStateMsg("添加成功！");
            result.setStateType(DoResultType.success);
        } catch (Exception e) {
            result.setStateMsg("添加失败！");
            result.setStateType(DoResultType.fail);
            e.printStackTrace();
        }
        return result;
    }

    @Description("用户对应业务删除")
    @RequestMapping("deleteUserService")
    @ResponseBody
    public DoResult deleteUserService(String usCode) {
        DoResult result = new DoResult();

        try {
            if (usCode != null && !usCode.equals("")) {
                userServiceBiz.deleteByUscode(usCode);
                result.setStateType(DoResultType.success);

            }
        } catch (Exception e) {
            result.setStateMsg("删除失败！");
            result.setStateType(DoResultType.fail);
            e.printStackTrace();
        }
        return result;
    }
}

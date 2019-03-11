package com.jrwp.payMent.action;


import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.payMent.dao.HotelUpdateFileMapper;
import com.jrwp.payMent.entity.UpdateItem;
import com.jrwp.wx.action.ClientController;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("dataApi")
public class DataApi {
	private final Logger logger=Logger.getLogger(DataApi.class);
    //    @Resource
//    private IMachineService machineService;
    /*@Resource
    private AccessAuthcodeDao accessAuthcodeDao;*/
    @Resource
    private HotelUpdateFileMapper hotelUpdateFileMapper;

    //    @RequestMapping(value = "check", produces = "application/json;charset=utf-8")
//    @ResponseBody
//    public String login(String machineID, Integer deptid, String machineIP, HttpServletRequest request) {
//        DoResult result = new DoResult();
//        try {
//            if (machineID == null) {
//                result.setStateMsg("机器码为空");
//                result.setStateType(DoResultType.fail);
//                return JacksonUtil.toJson(result);
//            } else {
//                result = machineService.check(machineID, deptid, machineIP);
//            }
//        } catch (Exception e) {
//            result.setStateType(DoResultType.fail);
//            result.setStateMsg("接口异常");
//            e.printStackTrace();
//        }
//        return JacksonUtil.toJson(result);
//    }
    /*@RequestMapping(value = "getAccess", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String login(BigDecimal id) {
        DoResult result = new DoResult();
        try {
            AccessAuthcode accessAuthcode = accessAuthcodeDao.selectByPrimaryKey(id);
            result.setStateValue(JacksonUtil.toJson(accessAuthcode));
            result.setStateMsg("获取成功");
            result.setStateType(DoResultType.success);
        } catch (Exception e) {
            result.setStateType(DoResultType.fail);
            result.setStateMsg("接口异常");
            e.printStackTrace();
        }
        return JacksonUtil.toJson(result);
    }*/

    @RequestMapping(value = "clientItems", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String clientDlls(BigDecimal type, Integer version) {
        DoResult result = new DoResult();
        try {
            if (type == null) {
                result.setStateMsg("type为空");
                result.setStateType(DoResultType.fail);
                return JacksonUtil.toJson(result);
            } else {
                // result = machineService.check(machineID, deptid, machineIP);
                List<UpdateItem> list = new ArrayList<>();
                if (type.intValue() == 1) {
                    list = hotelUpdateFileMapper.listbyupdateid(type);
                } else if (type.intValue() == 2) {
                    list = hotelUpdateFileMapper.listbyupdateid(type);
                    if (list != null && list.size() > 0) {
                        if (list.get(0).getVersion().equals(version.toString())) {
                            list = new ArrayList<>();
                        }
                    }
                }
                result.setStateValue(JacksonUtil.toJson(list));
                result.setStateMsg("获取成功");
                result.setStateType(DoResultType.success);
            }
        } catch (Exception e) {
            result.setStateType(DoResultType.fail);
            result.setStateMsg("接口异常");
            e.printStackTrace();
            logger.error("更新出错=",e);
        }
        return JacksonUtil.toJson(result);
    }
}

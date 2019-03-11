/*package com.jrwp.wx.action;

import com.github.pagehelper.PageHelper;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.core.utils.MD5Util;
import com.jrwp.wx.entity.*;
import com.jrwp.wx.service.AppointmeInfoService;
import com.jrwp.wx.service.SquenceInfoService;
import com.jrwp.wx.until.ListUntil;
import com.jrwp.wx.until.OpenidUtil;
import com.jrwp.wx.until.TimequantumUntil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Description("队列信息管理")
@Controller
//@RequestMapping("getSquenceAction")
public class GetSquenceAction {
    @Resource
    private AppointmeInfoService appointmeInfoService;
    @Resource
    private SquenceInfoService squenceInfoService;

    @RequestMapping(value = "getNextSquence", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getNextSquence(HttpServletRequest request, String apiauthtoken, Integer deptid, HttpServletResponse response,
                                 Integer winNumber, Integer id, Integer type) {

        String code = DigestUtils.md5Hex("KTHWXAPPOINTME").toUpperCase();
        SquenceHelper squence = null;
        DoResult result = new DoResult();
        if (apiauthtoken != null && apiauthtoken.equals(code)) {
            try {
                //先把传过来的ID 对应数据 状态改为4 或者 3
                if (id != null) {
                    squenceInfoService.updateStatusByPrimaryKey(id, null, type);
                }
                Calendar c = Calendar.getInstance();
                Date nowdate = new Date();
                int now = c.get(Calendar.HOUR_OF_DAY);
                //现在处于什么时间段
                int nowtimequan = TimequantumUntil.getTimqun(now);
                squence = squenceInfoService.getCallSquence(nowtimequan, 1, 1, nowdate, deptid);//获取有没有早到的
                if (squence == null) {
                    squence = squenceInfoService.getCallSquence(nowtimequan, 1, 0, nowdate, deptid);//获取预约的
                    if (squence == null) {
                        squence = squenceInfoService.getCallSquence(null, 0, null, nowdate, deptid);//获取现场号
                        if (squence == null) {
                            squence = squenceInfoService.getCallSquence(null, 1, 1, nowdate, deptid);//获取有没有早到的
                        }
                    }
                }
                //int appCount = appointmeInfoService.getAppCount(new Date(), deptid);

                if (squence == null) {
                    //如果都没有 则没有人办业务
                    squence = new SquenceHelper();
                    squence.setId(0);
                    squence.setSquence("0");
                    squence.setLocalCount(0);
                    // squence.setAppCount(appCount);
                } else {
                    //获取等待人数
                    int localCount = squenceInfoService.getLocalCount(new Date(), deptid);
                    squence.setLocalCount(localCount - 1);

                    // squence.setAppCount(appCount);
                    //获取squence成功后要将 此条信息的 status 改为2 即已叫号
                    squenceInfoService.updateStatusByPrimaryKey(squence.getId(), winNumber, 2);
                }
                result.setStateType(DoResultType.success);
                result.setStateValue(JacksonUtil.toJson(squence));
                result.setStateMsg("获取成功");
                System.out.println(JacksonUtil.toJson(result));
            } catch (Exception e) {
                e.printStackTrace();
                result.setStateType(DoResultType.fail);
                result.setStateMsg("接口出现异常");
            }
        } else {
            result.setStateType(DoResultType.fail);
            result.setStateMsg("令牌错误");
        }
        System.out.println(JacksonUtil.toJson(result));
        return JacksonUtil.toJson(result);
    }

    @RequestMapping(value = "recall", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String recall(HttpServletRequest request, String apiauthtoken, Integer id, Integer type) {

        //type  1 重复  2 到号
        String code = DigestUtils.md5Hex("KTHWXAPPOINTME").toUpperCase();
        SquenceHelper squence = null;
        DoResult result = new DoResult();
        if (apiauthtoken != null && apiauthtoken.equals(code)) {
            try {
                if (id != null) {
                    if (type == 1) {
                        type = 5;
                    } else if (type == 2) {
                        type = 4;
                    } else if (type == 3) {
                        type = 3;
                    }
                    squenceInfoService.updateStatusByPrimaryKey(id, null, type);
                }
                result.setStateType(DoResultType.success);
                //result.setStateValue(JacksonUtil.toJson(squence));
                result.setStateMsg("获取成功");
                System.out.println(JacksonUtil.toJson(result));
            } catch (Exception e) {
                e.printStackTrace();
                result.setStateType(DoResultType.fail);
                result.setStateMsg("接口出现异常");
            }
        } else {
            result.setStateType(DoResultType.fail);
            result.setStateMsg("令牌错误");
        }
        System.out.println(JacksonUtil.toJson(result));
        return JacksonUtil.toJson(result);
    }

    @RequestMapping(value = "getAppCount", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public String getAppCount(HttpServletRequest request, String apiauthtoken, Integer deptid) {
        String code = DigestUtils.md5Hex("KTHWXAPPOINTME").toUpperCase();
        DoResult result = new DoResult();
        if (apiauthtoken != null && apiauthtoken.equals(code)) {
            try {
                int count = appointmeInfoService.getAppCount(new Date(), deptid);
                result.setStateType(DoResultType.success);
                result.setStateValue(count);
                result.setStateMsg("获取成功");
            } catch (Exception e) {
                result.setStateType(DoResultType.fail);
                result.setStateMsg("接口出现异常");
                e.printStackTrace();
            }
        } else {
            result.setStateType(DoResultType.fail);
            result.setStateMsg("令牌错误");
        }
        System.out.println(result);
        return JacksonUtil.toJson(result);
    }

    @RequestMapping(value = "getLocalCount", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public String getLocalCount(HttpServletRequest request, String apiauthtoken, Integer deptid) {
        String code = DigestUtils.md5Hex("KTHWXAPPOINTME").toUpperCase();
        // SquenceHelper squence = null;
        DoResult result = new DoResult();
        if (apiauthtoken != null && apiauthtoken.equals(code)) {
            try {
                int count = squenceInfoService.getLocalCount(new Date(), deptid);
                result.setStateType(DoResultType.success);
                result.setStateValue(count);
                result.setStateMsg("获取成功");
            } catch (Exception e) {
                result.setStateType(DoResultType.fail);
                result.setStateMsg("接口出现异常");
                e.printStackTrace();
            }
        } else {
            result.setStateType(DoResultType.fail);
            result.setStateMsg("令牌错误");
        }
        System.out.println(result);
        return JacksonUtil.toJson(result);
    }

//    @RequestMapping(value = "getVistor", produces = "application/json;charset=utf-8")
//    @ResponseBody
//    public String getVistor(HttpServletRequest request, String parmString) {
//        //parmString="{\"apiauthtoken\":\"DB848A594A30BB02BD80E20240D479EB\",\"deptid\":46,\"pageSize\":20,\"pageNum\":1}";
//        String code = DigestUtils.md5Hex("KTHWXAPPOINTME").toUpperCase();
//        // SquenceHelper squence = null;
//        DoResult result = new DoResult();
//        CallNumParm callNumParm = null;
//        if (parmString != null) {
//            callNumParm = JacksonUtil.readValue(parmString, CallNumParm.class);
//        } else {
//            result.setStateMsg("参数字符串是空");
//            result.setStateType(DoResultType.fail);
//            return JacksonUtil.toJson(result);
//        }
//        if (callNumParm.getApiauthtoken() != null && callNumParm.getApiauthtoken().equals(code)) {
//            try {
//                // int count = squenceInfoService.getLocalCount(new Date(), deptid);
//                List<Visitor> taget = new ArrayList<>();
//                PageHelper.startPage(1, 4);
//                List<Visitor> gone = squenceInfoService.getVisitor(new Date(), callNumParm.getDeptid(), 3,1);
//                for (Visitor visitor : gone) {
//                    String[] names = visitor.getName().split("@");
//                    if (names.length == 2) {
//                        visitor.setName(names[0]);
//                        visitor.setWindowNumber(names[1]);
//                    }
//                    visitor.setState("3");
//                    taget.add(visitor);
//                }
//                PageHelper.startPage(1, 3);
//                List<Visitor> called = squenceInfoService.getCalledVisitor(new Date(), callNumParm.getDeptid());
//                for (Visitor visitor : called) {
//                    String[] names = visitor.getName().split("@");
//                    if (names.length == 2) {
//                        visitor.setName(names[0]);
//                        visitor.setWindowNumber(names[1]);
//                    }
//                    visitor.setState("1");
//                    taget.add(visitor);
//                }
//                PageHelper.startPage(1, 3);
//                List<Visitor> recalled = squenceInfoService.getVisitor(new Date(), callNumParm.getDeptid(), 5,2);
//                for (Visitor visitor : recalled) {
//                    String[] names = visitor.getName().split("@");
//                    if (names.length == 2) {
//                        visitor.setName(names[0]);
//                        visitor.setWindowNumber(names[1]);
//                    }
//                    visitor.setState("5");
//                    taget.add(visitor);
//                }
//                PageHelper.startPage(callNumParm.getPageNum(), callNumParm.getPageSize());
//                List<Visitor> wait = squenceInfoService.getVisitor(new Date(), callNumParm.getDeptid(), 1,2);
    //   for (Visitor visitor : wait) {
//                    visitor.setState("0");
//                    taget.add(visitor);
//                }
//                result.setStateType(DoResultType.success);
//                result.setStateValue(JacksonUtil.toJson(taget));
//                result.setStateMsg("获取成功");
//            } catch (Exception e) {
//                result.setStateType(DoResultType.fail);
//                result.setStateMsg("接口出现异常");
//                e.printStackTrace();
//            }
//        } else {
//            result.setStateType(DoResultType.fail);
//            result.setStateMsg("令牌错误");
//        }
//        System.out.println(result);
//        return JacksonUtil.toJson(result);
//    }

    @RequestMapping(value = "getVistor", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getVistor(HttpServletRequest request, String parmString) {
        //parmString="{\"apiauthtoken\":\"DB848A594A30BB02BD80E20240D479EB\",\"deptid\":46,\"pageSize\":20,\"pageNum\":1}";
        String code = DigestUtils.md5Hex("KTHWXAPPOINTME").toUpperCase();
        // SquenceHelper squence = null;
        DoResult result = new DoResult();
        CallNumParm callNumParm = null;
        if (parmString != null) {
            callNumParm = JacksonUtil.readValue(parmString, CallNumParm.class);
        } else {
            result.setStateMsg("参数字符串是空");
            result.setStateType(DoResultType.fail);
            return JacksonUtil.toJson(result);
        }
        if (callNumParm.getApiauthtoken() != null && callNumParm.getApiauthtoken().equals(code)) {
            try {
                // int count = squenceInfoService.getLocalCount(new Date(), deptid);
                List<Visitor> taget = new ArrayList<>();
                List<Visitor> all = new ArrayList<>();
                //先取到 List
                List<Visitor> sequenceHlper = com.jrwp.wx.until.SquenceHelper.getInstance().dateChange(1, null);
                all = ListUntil.listCopy(sequenceHlper);
                List<Visitor> gone = new ArrayList<>();
                List<Visitor> called = new ArrayList<>();
                List<Visitor> recalled = new ArrayList<>();
                List<Visitor> wait = new ArrayList<>();
                for (Visitor visitor : all) {
                    if (visitor.getDeptid().longValue() == callNumParm.getDeptid().longValue()) {
                        if (visitor.getState().equals("1")) {
                            if (wait.size() < 20) {
                                wait.add(visitor);
                            }
                        } else if (visitor.getState().equals("2")) {
                            if (called.size() < 3) {
                                called.add(visitor);
                            }
                        } else if (visitor.getState().equals("3")) {
                            if (gone.size() < 4) {
                                gone.add(visitor);
                            }
                        } else if (visitor.getState().equals("5")) {
                            if (recalled.size() < 3) {
                                recalled.add(visitor);
                            }
                        }
                    }
                }
                for (Visitor visitor : wait) {
                    visitor.setState("0");
                    taget.add(visitor);
                }
                for (Visitor visitor : gone) {
                    String[] names = visitor.getName().split("@");
                    if (names.length == 2) {
                        visitor.setName(names[0]);
                        visitor.setWindowNumber(names[1]);
                    }
                    visitor.setState("3");
                    taget.add(visitor);
                }
                for (Visitor visitor : called) {
                    String[] names = visitor.getName().split("@");
                    if (names.length == 2) {
                        visitor.setName(names[0]);
                        visitor.setWindowNumber(names[1]);
                    }
                    visitor.setState("1");
                    taget.add(visitor);
                }
                for (Visitor visitor : recalled) {
                    String[] names = visitor.getName().split("@");
                    if (names.length == 2) {
                        visitor.setName(names[0]);
                        visitor.setWindowNumber(names[1]);
                    }
                    visitor.setState("5");
                    taget.add(visitor);
                }
                result.setStateType(DoResultType.success);
                result.setStateValue(JacksonUtil.toJson(taget));
                result.setStateMsg("获取成功");
            } catch (Exception e) {
                result.setStateType(DoResultType.fail);
                result.setStateMsg("接口出现异常");
                e.printStackTrace();
            }
        } else {
            result.setStateType(DoResultType.fail);
            result.setStateMsg("令牌错误");
        }
        System.out.println("返回值======" + JacksonUtil.toJson(result));

        return JacksonUtil.toJson(result);
    }

}
*/
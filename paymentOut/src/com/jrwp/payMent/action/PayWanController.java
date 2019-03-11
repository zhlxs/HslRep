package com.jrwp.payMent.action;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrwp.core.annotation.Description;
import com.jrwp.core.entity.Core_Dept;
import com.jrwp.core.entity.Core_User;
import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.service.IDeptService;
import com.jrwp.core.service.IUserService;
import com.jrwp.core.utils.LogUtil;
import com.jrwp.core.utils.MsgManager;
import com.jrwp.follow.dao.DeptPayConfigDao;
import com.jrwp.follow.entity.PayConfig;
import com.jrwp.payMent.entity.PayWanInfo;
import com.jrwp.payMent.entity.WanCount;
import com.jrwp.payMent.help.PayWanHelper;
import com.jrwp.payMent.help.WanCountHelper;
import com.jrwp.payMent.service.PayWanService;
import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Description("订单wan管理")
@Controller
@RequestMapping("payWanController")
public class PayWanController {
    @Resource
    private PayWanService payWanService;
    @Resource
    private IDeptService deptService;
    @Resource
    private IUserService userService;
    @Resource
    private DeptPayConfigDao deptPayConfigDao;

    @Description("订单wan列表")
    @RequestMapping("wanList")
    public ModelAndView orderList(HttpServletRequest request, HttpSession session) {
        ModelAndView model = new ModelAndView();
        try {
            Core_User user = (Core_User) session.getAttribute("user");
            model.setViewName("wan/mainList");
            if (user.getId() == 1) {
            } else {
                Long deptid = userService.getdeptid(user.getUserName());
                Core_Dept dept = deptService.getObjectById(deptid);
                String deptcode = "0";
                List<Integer> ids = deptPayConfigDao.isHave(dept.getDeptCode());
                if (ids.size() == 0) {
                    Core_Dept parent = deptService.getObjectById(dept.getParentId());
                    List<Integer> ids1 = deptPayConfigDao.isHave(parent.getDeptCode());
                    if (ids1.size() != 0) {
                        deptcode = parent.getDeptCode();
                    }
                } else {
                    deptcode = dept.getDeptCode();
                }
                model.addObject("deptcode", deptcode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MsgManager.writeError(request, e);
        }

        return model;
    }

    @Description("订单wan列表JSON")
    @RequestMapping("wanListJson")
    @ResponseBody
    public PayWanHelper orderListJson(HttpSession session, @RequestParam(value = "page", defaultValue = "1") int pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "20") int pageSize, String queryinfo) {
        PayWanHelper payWanHelper = null;
        List<PayWanInfo> payWanInfoList = null;
        try {
            Core_User user = (Core_User) session.getAttribute("user");
            if (user.getId() == 1) {
                if (queryinfo == null) {
                    String nowdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    queryinfo = "{\"whereInfos\":[{\"fieldName\":\"s.paytime\",\"whereField\":\">=\"," +
                            "\"fieldType\":\"DateTime\"," + "\"whereType\":\"where\",\"andOr\":\"and\"," + "\"queryValue\":" + "\""
                            + nowdate + "\"}," + "{\"fieldName\":\"s.paytime\",\"whereField\":\"<=\",\"fieldType\":\"DateTime\"," +
                            "\"whereType\":\"where\"," + "\"andOr\":\"and\",\"queryValue\":\"" + nowdate + "\"}]}";
                }

                PageHelper.startPage(pageNum, pageSize);
                payWanInfoList = payWanService.listBycode(queryinfo);
                PageInfo<PayWanInfo> pageInfo = new PageInfo<PayWanInfo>(payWanInfoList);
                payWanHelper = new PayWanHelper(pageInfo);
                return payWanHelper;
            }
            if (queryinfo == null) {
                //  Core_User user = (Core_User) session.getAttribute("user");
                Long deptid = userService.getdeptid(user.getUserName());
                Core_Dept dept = deptService.getObjectById(deptid);
                String deptcode = "0";
                List<Integer> ids = deptPayConfigDao.isHave(dept.getDeptCode());
                if (ids.size() == 0) {
                    Core_Dept parent = deptService.getObjectById(dept.getParentId());
                    List<Integer> ids1 = deptPayConfigDao.isHave(parent.getDeptCode());
                    if (ids1.size() != 0) {
                        deptcode = parent.getDeptCode();
                    }
                } else {
                    deptcode = dept.getDeptCode();
                }
                String nowdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                queryinfo = "{\"whereInfos\":[{\"fieldName\":\"s.paytime\",\"whereField\":\">=\"," + "\"fieldType\":\"DateTime\"," +
                        "\"whereType\":\"where\",\"andOr\":\"and\"," + "\"queryValue\":" + "\"" + nowdate + "\"}," +
                        "{\"fieldName\":\"s.paytime\",\"whereField\":\"<=\",\"fieldType\":\"DateTime\"," + "\"whereType\":\"where\"," +
                        "" + "" + "\"andOr\":\"and\",\"queryValue\":\"" + nowdate + "\"}," + "{\"fieldName\":\"s.deptcode\"," +
                        "\"whereField\":\"=\",\"fieldType\":\"String\"," + "\"whereType\":\"where\"," + "\"andOr\":\"and\"," +
                        "\"queryValue\":\"" + deptcode + "\"}" + "]}";
            }
            PageHelper.startPage(pageNum, pageSize);
            payWanInfoList = payWanService.listBycode(queryinfo);
            PageInfo<PayWanInfo> pageInfo = new PageInfo<PayWanInfo>(payWanInfoList);
            //  pageInfo.setTotal(list.size());
            payWanHelper = new PayWanHelper(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payWanHelper;
    }

    @Description("订单wan详情页面")
    @RequestMapping("wanDetail")
    public ModelAndView orderListJson(Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("wan/wanDetail");
        try {
            PayWanInfo payWanInfo = payWanService.getWanInfo(id);
            modelAndView.addObject("payWanInfo", payWanInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @Description("部门列表树Json")
    @RequestMapping("listTreeJson")
    @ResponseBody
    public List<Core_Dept> deptTreeJson(Long parentid, HttpSession session) {
        //这个方法比较特殊 因为外网需要要根据 设备查询，底级为设备
        //所以 需要判断 部门树是否是最低级部门
        //如果是底级部门 则查询这个部门底下归属的设备
        List<Core_Dept> deptlist = null;
        try {
            //首次加载
            if (parentid == 0) {
                Core_User user = (Core_User) session.getAttribute("user");
                Long deptid = userService.getdeptid(user.getUserName());
                //超级管理员
                if (deptid == 0) {
                    deptlist = deptService.getNextLevel(parentid);
                } else {
                    //如果不是超级管理员 则顶级部门时当前部门
                    Core_Dept dept = deptService.getObjectById(deptid);
                    deptlist = new ArrayList<>();
                    deptlist.add(dept);
                }
                getChilds(deptlist);
            } else {
                deptlist = deptService.getNextLevel(parentid);
                initChilds(deptlist);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deptlist;
    }

//    @Description("订单wan信息打印")
//    @RequestMapping("printModel")
//    @ResponseBody
//    public DoResult printModel(HttpSession session, @RequestParam(value = "page", defaultValue = "1") int pageNum, @RequestParam
//            (value = "pageSize", defaultValue = "20") int pageSize, String payStatus, String begintime, String endtime, String
//            payType, Long[] deptids, Integer[] macIds, Integer[] parentdeptids, String ordernumber, String configName,
//                               HttpServletRequest request) {
//
//        DoResult result = new DoResult();
//        PayWanHelper payWanHelper = orderListJson(session, pageNum, pageSize, payStatus, begintime, endtime, payType, deptids,
//                macIds, parentdeptids, ordernumber, configName, request);
//        List<PayWanInfo> list = payWanHelper.getItems();
//        try {
//            if (list.size() != 0) {
//                // 选择路径
////                String strDirPath = request.getSession().getServletContext().getRealPath("/");
////                String fileName = strDirPath + "\\Down\\excel\\";
////                // 开始导出，往表插数据
////               // String now_date = com.jrwp.payMent.help.EXCELHelper.printExcel(fileName, list);
////                String url = "http://" + request.getLocalAddr() + ":" + request.getLocalPort() + "/paymentOut/Down/excel/" +
////                        now_date + ".xls";
////                url = new String(url.getBytes("utf-8"), "utf-8");
////                LogUtil.debug("导出完成:{}", fileName);
////                result.setStateType(DoResultType.success);
////                result.setStateValue(url);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    @Description("订单wan数据统计页面")
    @RequestMapping("wanCount")
    public ModelAndView orderCount() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sjtj/wanCount");
        return modelAndView;
    }

    @Description("订单wan数据统计Json")
    @RequestMapping("wanCountJson")
    @ResponseBody
    public WanCountHelper wanCountJson(HttpSession session, @RequestParam(value = "page", defaultValue = "1") int pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "20") int pageSize, String begintime, String
                                                   endtime, String payType, String type, Integer machinetype, String configname,
                                       HttpServletRequest request) {
        WanCountHelper wanCountHelper = null;
        List<WanCount> list = null;
        if (begintime == null && endtime == null) {
            //如果没有勾选部门 时间没有筛选 ，则查询本部门下当天的数据
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String time = simpleDateFormat.format(date);
            begintime = endtime = time;
        }
        try {
            List<String> startCodes = null;
            PageHelper.startPage(pageNum, pageSize);
            list = payWanService.coutwanbyconfig(payType, begintime, endtime, machinetype, configname);
            PageInfo<WanCount> pageInfo = new PageInfo<WanCount>(list);
            wanCountHelper = new WanCountHelper(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wanCountHelper;
    }

    @Description("订单wan数据统计信息打印")
    @RequestMapping("printWanDataCountModel")
    @ResponseBody
    public DoResult printWanDataCountModel(HttpSession session, @RequestParam(value = "page", defaultValue = "1") int pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "20") int pageSize, String begintime,
                                           String endtime, String payType, Long[] deptids, Integer[] macIds, Integer[]
                                                       parentdeptids, String type, HttpServletRequest request) {

        DoResult result = new DoResult();
//        WanCountHelper wanCountHelper = wanCountJson(session, pageNum, pageSize, begintime, endtime, payType, deptids, macIds,
//                parentdeptids, type, request);
//        List<WanCount> list = wanCountHelper.getItems();
//        try {
//            if (list.size() != 0) {
//                // 选择路径
//                String strDirPath = request.getSession().getServletContext().getRealPath("/");
//                String fileName = strDirPath + "\\Down\\excel\\";
//                // 开始导出，往表插数据
//                String now_date = com.jrwp.payMent.help.EXCELHelper.printsjtjExcel(fileName, list, type);
//                String url = "http://" + request.getLocalAddr() + ":" + request.getLocalPort() + "/paymentOut/Down/excel/" +
//                        now_date + ".xls";
//                url = new String(url.getBytes("utf-8"), "utf-8");
//                LogUtil.debug("导出完成:{}", fileName);
//                result.setStateType(DoResultType.success);
//                result.setStateValue(url);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return result;
    }

    /**
     * 获取子集
     *
     * @param list
     */
    public void getChilds(List<Core_Dept> list) {
        for (Core_Dept dept : list) {
            dept.setIsdelay(false);
            dept.setIsexpand(true);
            List<Core_Dept> childs = deptService.getNextLevel(dept.getId());
            if (childs.size() > 0) {
                initChilds(childs);
                dept.setChildren(childs);
            } else {
                List<Core_Dept> machines = payWanService.getMachines(dept.getId());
                if (machines.size() > 0) {
                    dept.setChildren(machines);
                }
            }
        }
    }

    /**
     * 判断子集是否有子集
     *
     * @param childs
     */
    public void initChilds(List<Core_Dept> childs) {
        for (Core_Dept dept : childs) {
            //判断是否有子集
            if (deptService.getsize(dept.getId()) > 0) {
                dept.setIsexpand(false);
                dept.setIsdelay(true);
                dept.setChildren(new ArrayList<Core_Dept>());
            } else {
                dept.setIsexpand(false);
                dept.setIsdelay(false);
                //如果没有子集  则是底级部门 查询 设备
                List<Core_Dept> list = payWanService.getMachines(dept.getId());
                if (list.size() > 0) {
                    dept.setChildren(list);
                }
            }
        }
    }
}

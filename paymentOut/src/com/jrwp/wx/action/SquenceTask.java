package com.jrwp.wx.action;

import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.wx.entity.Visitor;
import com.jrwp.wx.service.SquenceInfoService;
import com.jrwp.wx.until.Jpush;
import com.jrwp.wx.until.SquenceHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 作者：灰色的蓝
 * 这个类是采用极光推送，向叫号屏推送数据的
 * 日期：2018-08-23
 */
//@Component("SquenceTask")
public class SquenceTask {

    @Resource
    private SquenceInfoService squenceInfoService;

    @Scheduled(cron = "0/1 * * * * ?")
    public void getVistor() {
        try {
            //System.out.println("============");
            List<Visitor> taget = new ArrayList<>();
            List<Visitor> gone = squenceInfoService.getVisitor(new Date(), null, 3, 1);
            List<Visitor> called = squenceInfoService.getCalledVisitor(new Date(), null);
            List<Visitor> recalled = squenceInfoService.getVisitor(new Date(), null, 5, 2);
            List<Visitor> wait = squenceInfoService.getVisitor(new Date(), null, 1, 2);
            taget.addAll(gone);
            taget.addAll(called);
            taget.addAll(recalled);
            taget.addAll(wait);
            SquenceHelper.getInstance().dateChange(2, new ArrayList<Visitor>(taget));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //极光推送
//    @Scheduled(cron = "0/1 * * * * ?")
//    public void getVistor() {
//        try {
//            //System.out.println("============");
//            List<Visitor> taget = new ArrayList<>();
//            List<Visitor> gone = squenceInfoService.getVisitor(new Date(), null, 3, 1);
//            List<Visitor> called = squenceInfoService.getCalledVisitor(new Date(), null);
//            List<Visitor> recalled = squenceInfoService.getVisitor(new Date(), null, 5, 2);
//            List<Visitor> wait = squenceInfoService.getVisitor(new Date(), null, 1, 2);
////            taget.addAll(gone);
////            taget.addAll(called);
////            taget.addAll(recalled);
////            taget.addAll(wait);
//            for (Visitor visitor : wait) {
//                visitor.setState("0");
//                taget.add(visitor);
//            }
//            for (Visitor visitor : gone) {
//                String[] names = visitor.getName().split("@");
//                if (names.length == 2) {
//                    visitor.setName(names[0]);
//                    visitor.setWindowNumber(names[1]);
//                }
//                visitor.setState("3");
//                taget.add(visitor);
//            }
//            for (Visitor visitor : called) {
//                String[] names = visitor.getName().split("@");
//                if (names.length == 2) {
//                    visitor.setName(names[0]);
//                    visitor.setWindowNumber(names[1]);
//                }
//                visitor.setState("1");
//                taget.add(visitor);
//            }
//            for (Visitor visitor : recalled) {
//                String[] names = visitor.getName().split("@");
//                if (names.length == 2) {
//                    visitor.setName(names[0]);
//                    visitor.setWindowNumber(names[1]);
//                }
//                visitor.setState("5");
//                taget.add(visitor);
//            }
//
//            //设置推送参数
//            Map<String, String> parm = new HashMap<String, String>();
//            //设备id,指定设备推送id
//            parm.put("id", "13165ffa4e594a31ed1");
//            //设置提示信息,内容是文章标题
//            DoResult result = new DoResult();
//            result.setStateMsg("获取成功");
//            result.setStateType(DoResultType.success);
//            result.setStateValue( JacksonUtil.toJson(taget));
//            parm.put("msg", JacksonUtil.toJson(result));
//            Jpush.jpushAndroid(parm);
//            // SquenceHelper.getInstance().dateChange(2,new ArrayList<Visitor>(taget));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}

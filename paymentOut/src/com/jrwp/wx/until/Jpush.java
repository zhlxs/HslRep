package com.jrwp.wx.until;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.jrwp.core.utils.JacksonUtil;

import java.util.Map;

public class Jpush {
    private static String APP_KEY = "6a5e7e4bc6490578fe778443";
    private static String MASTER_SECRET = "94bb8f903e1fb0f6b30caba5";

    //极光推送>>Android
    //Map<String, String> parm是我自己传过来的参数,同学们可以自定义参数
    public static void jpushAndroid(Map<String, String> parm) {

        //创建JPushClient(极光推送的实例)
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        //推送的关键,构造一个payload
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.android())//指定android平台的用户
                .setAudience(Audience.all())//你项目中的所有用户
               // .setNotification(Notification.android(parm.get("msg"), "这是title", parm))
                //发送内容
                .setOptions(Options.newBuilder().setApnsProduction(false).build())
                //这里是指定开发环境,不用设置也没关系
                .setMessage(Message.content(parm.get("msg")))//自定义信息
                .build();

        try {
            PushResult pu = jpushClient.sendPush(payload);
            System.out.println(JacksonUtil.toJson(pu));
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }

    public static void jpushAndroidShopping(Map<String, String> parm, String alias) {

        // 设置好账号的app_key和masterSecret

        //创建JPushClient
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        //创建option
        PushPayload payload = PushPayload.newBuilder()
                //所有平台的用户
                .setPlatform(Platform.all())
                //根据别名推送
                .setAudience(Audience.alias(alias.replace(".", "_")))
                //通知消息
//                .setNotification(Notification.newBuilder()
//                        .addPlatformNotification(IosNotification.newBuilder() //发送ios
//                                .setAlert(parm.get("msg")) //消息体
//                                .setBadge(+1)
//                                .setSound("happy") //ios提示音
//                                .addExtras(parm) //附加参数
//                                .build())
//                        .addPlatformNotification(AndroidNotification.newBuilder() //发送android
//                                .addExtras(parm) //附加参数
//                                .setAlert(parm.get("msg")) //消息体
//                                .build())
//                        .build())
                .setOptions(Options.newBuilder().setApnsProduction(true).build())//指定开发环境 true为生产模式 false 为测试模式 (android不区分模式,ios区分模式)
                //自定义信息
                .setMessage(Message.newBuilder().setMsgContent(parm.get("msg")).addExtras(parm).build()).build();
        try {
            PushResult pu = jpushClient.sendPush(payload);
            System.out.println(pu.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }
}

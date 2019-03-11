package com.jrwp.wx.action;

import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.wx.dao.MachineDataMapper;
import com.jrwp.wx.entity.MachineData;
import com.jrwp.wx.service.impl.WebServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Endpoint;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("wx/webserviceAction")
public class WebServiceAction {
    @Resource
    private MachineDataMapper machineDataMapper;

    @RequestMapping("test")
    public String test(String code) {
        try {
            //url = "http://" + url;
            //   Endpoint.publish(url, new WebServiceImpl());
            // System.out.println("publish success~");
            // System.out.println(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "code=" + code;
    }

    //这个action 用来触发发布服务
    @RequestMapping("publish")
    public String publish(String url) {
        try {
            url = "http://" + url;
            Endpoint.publish(url, new WebServiceImpl());
            System.out.println("publish success~");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:" + url + "?wsdl";
    }

    @RequestMapping(value = "dateSelfInterface", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getNextSquence(HttpServletRequest request, String apiauthtoken, String parms, HttpServletResponse response) {
        System.out.println(parms);
        String code = DigestUtils.md5Hex("KTHMACHIENDATA").toUpperCase();
        DoResult result = new DoResult();
        String msg = "";
        if (apiauthtoken != null && apiauthtoken.equals(code)) {
            try {
                MachineData machineData = JacksonUtil.readValue(parms, MachineData.class);
                if (machineDataMapper.selectByBH(machineData.getBh()) == 1) {
                    machineData.setUpdatetime(new Date());
                    int a = machineDataMapper.updateByBH(machineData);
                    if (a > 0) {
                        result.setStateValue(1);
                        result.setStateType(DoResultType.success);
                        result.setStateMsg("更新成功");
                        msg = "更新成功";
                    }
                } else {
                    int id = machineDataMapper.insert(machineData);
                    if (id > 0) {
                        result.setStateValue(1);
                        result.setStateType(DoResultType.success);
                        result.setStateMsg("插入成功");
                        msg = "插入成功";
                    }
                }
                writelog(request, machineData.getBh(), msg);
                SimpleDateFormat sf = new SimpleDateFormat("HH时mm分ss秒");
                System.out.println(sf.format(new Date()));
            } catch (Exception e) {
                e.printStackTrace();
                result.setStateType(DoResultType.fail);
                result.setStateValue(-1);
                writelog(request, parms, "接口出现异常");
                result.setStateMsg("接口出现异常");
            }
        } else {
            result.setStateType(DoResultType.fail);
            result.setStateValue(0);
            result.setStateMsg("令牌错误");
        }
        //System.out.println(JacksonUtil.toJson(result));
        return JacksonUtil.toJson(result);
    }

    public void writelog(HttpServletRequest request, String bh, String msg) {
        // TODO Auto-generated method stub
        try {

            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String path = request.getServletContext().getRealPath("/") + "\\datalog\\" + simpleDateFormat.format(date) + ".txt";
            File file = new File(path);
            //如果父目录不存在 则创建
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            //如果今天的文件不存在 则创建
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintStream stream = new PrintStream(new FileOutputStream(file, true));
            stream.append("编号为：" + bh + ";");// 在已有的基础上添加字符串
            stream.append(msg + ";日期" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + "\r\n");// 在已有的基础上添加字符串
            //关闭输出流
            stream.flush();
            stream.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}

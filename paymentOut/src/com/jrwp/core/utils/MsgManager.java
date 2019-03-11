package com.jrwp.core.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MsgManager {

    public static void writeError(HttpServletRequest request, Exception e) {
        // TODO Auto-generated method stub
        try {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
            String path = request.getServletContext().getRealPath("/") + "\\errorTxt\\" +  simpleDateFormat.format(date)+ ".txt";

            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            PrintStream stream = new PrintStream(file);


            e.printStackTrace(stream);
            //System.out.println("已保存错误信息到日志文件中");

            //关闭输出流
            stream.flush();
            stream.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //新建一个文件

    }
    public static void writeSout(HttpServletRequest request, String msg) {
        // TODO Auto-generated method stub
        try {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH点");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
            String path = request.getServletContext().getRealPath("/") + "\\msgTxt\\" +  simpleDateFormat.format(date)+"sout"+ ".txt";

            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            PrintStream stream = new PrintStream(file);
            stream.print(simpleDateFormat1.format(new Date())+":"+msg);
            //关闭输出流
            stream.flush();
            stream.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //新建一个文件

    }
}

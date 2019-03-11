package com.jrwp.core.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorManager {

    public static void writeError(HttpServletRequest request, Exception e) {
        // TODO Auto-generated method stub
        try {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-ms");
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
}

package com.jrwp.wx.until;
  
import java.io.BufferedReader;    
import java.io.InputStreamReader;  
import java.io.OutputStreamWriter;  
import java.net.URL;
import java.net.URLConnection;


public class PostXml {
	public static String postServer(String urlStr,String requestXml) throws Exception {
		String line = "";
        StringBuffer resultSting = new StringBuffer();
		try {  
			 //声明URL
            URL url = new URL(urlStr);
            //1.创建链接
            URLConnection con = url.openConnection();
            byte[] xmlData = requestXml.getBytes();
            con.setDoOutput(true);   
            con.setDoInput(true);  
            con.setUseCaches(false); 
            con.setRequestProperty("Pragma:", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml");
            con.setRequestProperty("Content-length",String.valueOf(xmlData.length)); 
            OutputStreamWriter out = new OutputStreamWriter(
                    con.getOutputStream());
            System.out.println(">>>>>>>>>>传入的报文xmlInfo=" + requestXml);
            out.write(new String(requestXml.getBytes("UTF-8")));
            out.flush();
            out.close();
            //3.获取返回报文
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            //对返回值报文进行打印
            for (line = br.readLine(); line != null; line = br.readLine()) {
                System.out.println(">>>>>>>>>>>>>>>>>>>返回的结果报文内容为:---------"+line);
                //对返回的报文进行拼接,然后返回给业务层,在业务层进行判断
                resultSting.append(line);
            }
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new Exception();
        }
		return resultSting.toString();
	}
}

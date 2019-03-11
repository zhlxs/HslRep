package com.jrwp.wx.until;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.alibaba.fastjson.JSON;
import com.jrwp.wx.entity.ModelResultJson;
import com.jrwp.wx.entity.OrderCount;

public class Utils {
	
	public static int islaterOrearly(String time,int now){
    	System.out.println("============="+time);
    	int a = 0;
    	String[] timequan = time.split("-");
    	String[] timeqian = timequan[0].split(":");

    	String[] timehou = timequan[1].split(":");
    	int qiantime = Integer.valueOf(timeqian[0]).intValue();
    	int houtime = Integer.valueOf(timehou[0]).intValue();
    	if(now < qiantime){
    		a = 1;//早到
    	}
    	if(now >= houtime){
    		a = 2;//迟到
    	}
    	if(qiantime == now){
    		a = 0;//准时
    	}
    	return a;
    }
	public static String getTime() {
		// TODO Auto-generated method stub
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendar = Calendar.getInstance();
		String dateName = df.format(calendar.getTime());
		int a = 10;
		int b = 99;
		int c = (int)(Math.random()*(b-a))+a; 
		return dateName + c;
	}
	
	/** 
     * 发送POST请求 
     *  
     * @param url 
     *            目的地址 
     * @param parameters 
     *            请求参数，Map类型。 
     * @return 远程响应结果 
     */  
    public static String sendPost1(String url, Map<String,String> parameters) {  
        String result = "";// 返回的结果  
        BufferedReader in = null;// 读取响应输入流  
        PrintWriter out = null;  
        StringBuffer sb = new StringBuffer();// 处理请求参数  
        String params = "";// 编码之后的参数  
        try {  
            // 编码请求参数  
            if (parameters.size() == 1) {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                           parameters.get(name));  
                }  
                params = sb.toString(); 
            } else if(parameters.size() > 1){  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            parameters.get(name)).append("&");  
                }  
                String temp_params = sb.toString();  
                params = temp_params.substring(0, temp_params.length() - 1);  
            } 
           // System.out.println("发送的额参数="+params);
            // 创建URL对象  
            System.out.println("=====================发送上传参数请求请求");
            java.net.URL connURL = new java.net.URL(url);  
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
                    .openConnection();  
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");  
            httpConn.setRequestProperty("Connection", "Keep-Alive");  
            httpConn.setRequestProperty("User-Agent",  
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
            // 设置POST方式  
            httpConn.setDoInput(true);  
            httpConn.setDoOutput(true);  
            // 获取HttpURLConnection对象对应的输出流  
            out = new PrintWriter(httpConn.getOutputStream());  
            // 发送请求参数  
            out.write(params);  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式  
            in = new BufferedReader(new InputStreamReader(httpConn  
                    .getInputStream(), "UTF-8"));  
            String line;  
            // 读取返回的内容  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }
	public static int getTimqun(int now, List<OrderCount> orderCount) {
		// TODO Auto-generated method stub
		int timeid = 0;
		if(orderCount != null){
			for(int i=0; i<orderCount.size();i++){
				String[] timequan = orderCount.get(i).getTime().split("-");
		    	String[] timeqian = timequan[0].split(":");
		    	String[] timehou = timequan[1].split(":");
		    	int qiantime = Integer.valueOf(timeqian[0]).intValue();
		    	int houtime = Integer.valueOf(timehou[0]).intValue();
		    	if(qiantime <= now && now < houtime){
		    		timeid = orderCount.get(i).getId().intValue();
		    		break;
		    	}
			}
		}
		return timeid;
	}
	
	/**
     * 接口解析方法
     * @param in
     * @param coding
     * @return
     */
	public static String inputStream2String(InputStream in, String coding) {
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public static String getBusinessName(int type) {//01-机动车业务；02-驾驶证业务；04-违法业务
		// TODO Auto-generated method stub
		String name = "";
		if(type == 0){
			name = "代办业务";
		}
		if(type == 1){
			name = "综合业务";
		}
		if(type == 2){
			name = "代办业务";
		}
		if(type == 4){
			name = "疑难业务";
		}
		return name;
	}
	
	// 加密
		public static String getBase64(String str) {
			byte[] b = null;
			String s = null;
			try {
				b = str.getBytes("utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (b != null) {
				s = new BASE64Encoder().encode(b);
			}
			return s;
		}
	 
		// 解密
		public static String getFromBase64(String s) {
			byte[] b = null;
			String result = null;
			if (s != null) {
				BASE64Decoder decoder = new BASE64Decoder();
				try {
					b = decoder.decodeBuffer(s);
					result = new String(b, "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return result;
		}
		
		public static String getParamsByKey(String key){
			String value = null;
			try{
				InputStream in = Utils.class.getClassLoader().getResourceAsStream("/projectparameter.properties");
				Properties prop = new Properties();
				prop.load(in);
				value = prop.getProperty(key);
				in.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			return value;
			
		}
		
		public static String getSerialnumber(String squence){
			String serialnumber = null;
			try{
				//Date date = new Date();
		    	//DateFormat df3 = DateFormat.getTimeInstance();//只显示出时分秒
				Date d = new Date();  
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
		        String dateNowStr = sdf.format(d); 
		        String aa = dateNowStr.substring(2, dateNowStr.length());
		    	serialnumber = aa + getParamsByKey("sbkzjsjbh") + squence + "00";
			}catch(Exception e){
				e.printStackTrace();
			}
			return serialnumber;
		}
		
		public static boolean isOver30(String dateString,int overmin){
			boolean flag = false;
			try{
				long nd = 1000 * 24 * 60 * 60;
				long nh = 1000 * 60 * 60;
				long nm = 1000 * 60;
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        Date date = null;
		        date = formatter.parse(dateString);
				long diff = System.currentTimeMillis() - date.getTime();
				long min = diff % nd % nh / nm;
				if(min > overmin){
					flag = true;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return flag;
		}
		
		public static boolean isEarly(String appointdate,String xcdate){
			boolean flag = false;
			try{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        //Date date1 = null;
		        Date date1 = formatter.parse(appointdate);
		        Date date2 = formatter.parse(xcdate);
				if(date2.getTime() < date1.getTime()){
					flag = true;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return flag;
		}
		
		public static String warmingInfo(String jsonList) throws Exception{
			StringBuffer next = new StringBuffer("预警原因：");
			try{
				List<ModelResultJson> resultList = JSON.parseArray(jsonList,ModelResultJson.class); 
				for (int i = 0; i < resultList.size(); i++) {
					int a = resultList.get(i).getResultType();
					if( a == 1){
						next.append("涉恐、");
					} else if(a == 2){
						next.append("涉稳、");
					} else if(a == 3){
						next.append("在逃、");
					} else if(a == 4){
						next.append("涉毒、");
					} else if(a == 5){
						next.append("前科、");
					} else if(a == 6){
						next.append("上访、");
					} else if(a == 7){
						next.append("精神病人员、");
					} else if(a == 8){
						next.append("违法犯罪、");
					}	
				}
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
			return next.toString();
		}
}

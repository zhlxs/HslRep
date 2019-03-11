package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.jrwp.JSPay.until.WXPayUtil;
import com.jrwp.core.utils.HttpRequestUntil;

class MusicThread extends Thread {

	// 2):在A类中覆盖Thread类中的run方法.
	public void run() {
		// 3):在run方法中编写需要执行的操作
		String parmString = "{\"apiauthtoken\":\"DB848A594A30BB02BD80E20240D479EB\",\"deptid\":2060,\"pageSize\":20,\"pageNum\":1}";
		while (true) {

			String result = Test.sendPost(
					"http://pay.jxkth.com/paymentOut/getSquenceAction/getVistor?parmString="
							+ URLEncoder.encode(parmString), "");
			System.out.println(result);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class Test {

	public static void main(String[] args) {
		// String phoneString = "KTHTK123";
		// String parmString =
		// "{\"apiauthtoken\":\"DB848A594A30BB02BD80E20240D479EB\",\"deptid\":2060,\"pageSize\":20,\"pageNum\":1}";
		// while (true) {
		//
		// String result =
		// Test.sendPost("http://localhost:8090/paymentOut/getSquenceAction/getVistor?parmString="
		// + URLEncoder
		// .encode
		// (parmString), "");
		//
		// System.out.println(result);
		// try {
		// Thread.sleep(3);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		// for (int j = 0; j < 100; j++) {
		// System.out.println("运行游戏" + j);
		// //4):在main方法(线程)中,创建线程对象,并启动线程.
		// MusicThread music = new MusicThread();
		// music.start();
		//
		// }
		System.out
				.println(URLEncoder
						.encode("{\"apiauthtoken\":\"DB848A594A30BB02BD80E20240D479EB\",\"deptid\":2060,\"pageSize\":20,\"pageNum\":1}"));

	}

	public static void getWXBill() {
		try {
			String billURl = "https://api.mch.weixin.qq.com/pay/downloadbill";
			SortedMap<String, String> parameters = new TreeMap<String, String>();
			String nonceStr = WXPayUtil.generateNonceStr();
			String appid = "wx6d4d594ef75edec4";
			String mch_id = "1333513601";
			String apikey = "1765jkk9lx3prpogktzn1wmxlffsqsnk";
			parameters.put("appid", appid);
			parameters.put("mch_id", mch_id);
			// parameters.put("device_info", "");//微信支付分配的终端设备号，填写此字段，只下载该设备号
			// 的对账单
			parameters.put("nonce_str", nonceStr);
			parameters.put("bill_date", "20180719");// 下载对账单的日期，格式：20140603，日期不可为当天。
			// bill_type:ALL返回当日所有订单信息,默认值SUCCESS返回当日成功支付的订单。REFUND，返回当日退款订单
			parameters.put("bill_type", "REFUND");
			String sign = WXPayUtil.generateSignature(parameters, apikey);
			parameters.put("sign", sign);
			String xml = WXPayUtil.mapToXml(parameters);
			String xmlStr = HttpRequestUntil.sendPost(billURl, xml);
			System.out.println(xmlStr);
			String str = xmlStr;// 获取对账报文
			String newStr = str.replaceAll(",", " "); // 去空格
			String[] tempStr = newStr.split("`"); // 数据分组
			String[] t = tempStr[0].split(" ");// 分组标题
			int k = 1; // 纪录数组下标
			int j = tempStr.length / 24; // 计算循环次数
			for (int i = 0; i < j; i++) {
				System.out.println(t[0] + ":" + tempStr[k]);// ﻿交易时间
				System.out.println(t[1] + ":" + tempStr[k + 1]);// 公众账号ID
				System.out.println(t[2] + ":" + tempStr[k + 2]);// 商户号
				System.out.println(t[3] + ":" + tempStr[k + 3]);// 子商户号
				System.out.println(t[4] + ":" + tempStr[k + 4]);// 设备号
				System.out.println(t[5] + ":" + tempStr[k + 5]);// 微信订单号
				System.out.println(t[6] + ":" + tempStr[k + 6]);// 商户订单号
				System.out.println(t[7] + ":" + tempStr[k + 7]);// 用户标识
				System.out.println(t[8] + ":" + tempStr[k + 8]);// 交易类型
				System.out.println(t[9] + ":" + tempStr[k + 9]);// 交易状态
				System.out.println(t[10] + ":" + tempStr[k + 10]);// 付款银行
				System.out.println(t[11] + ":" + tempStr[k + 11]);// 货币种类
				System.out.println(t[12] + ":" + tempStr[k + 12]);// 总金额
				System.out.println(t[13] + ":" + tempStr[k + 13]);// 企业红包金额
				System.out.println(t[14] + ":" + tempStr[k + 14]);// 退款申请时间
				System.out.println(t[15] + ":" + tempStr[k + 15]);// 退款成功时间
				System.out.println(t[16] + ":" + tempStr[k + 16]);// 微信退款单号
				System.out.println(t[17] + ":" + tempStr[k + 17]);// 商户退款单号
				System.out.println(t[18] + ":" + tempStr[k + 18]);// 退款金额
				System.out.println(t[19] + ":" + tempStr[k + 19]);// 企业红包退款金额
				System.out.println(t[20] + ":" + tempStr[k + 20]);// 退款类型
				System.out.println(t[21] + ":" + tempStr[k + 21]);// 退款状态
				System.out.println(t[22] + ":" + tempStr[k + 22]);// 商品名称
				System.out.println(t[23] + ":" + tempStr[k + 23]);// 商户数据包
				System.out.println("---------");// 摘取有用数据存入数据库
				k = k + 26;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getDistanceTime(String str1, String str2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return day + "天" + hour + "小时" + min + "分" + sec + "秒";
	}

	public static Long getMin(String str1, String str2) {
		long minute = 0;
		try {
			Calendar dateOne = Calendar.getInstance(), dateTwo = Calendar
					.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateOne.setTime(df.parse(str1)); // 设置为当前系统时间
			dateTwo.setTime(df.parse(str2)); // 设置为2015年1月15日
			long timeOne = dateOne.getTimeInMillis();
			long timeTwo = dateTwo.getTimeInMillis();
			minute = (timeOne - timeTwo) / (1000 * 60);// 转化minute
			System.out.println("相隔" + minute + "分钟");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return minute;
	}

	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
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

	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

}

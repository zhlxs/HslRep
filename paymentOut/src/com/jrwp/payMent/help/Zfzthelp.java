package com.jrwp.payMent.help;

public class Zfzthelp {

	//支付状态
	public static String zfzt(String id) {
		if (id.equals("0")) {
			return "等待生成二维码";
		} else if (id.equals("1")) {
			return "待支付";
		}else if (id.equals("2")) {
			return "已支付";
		}else if (id.equals("3")) {
			return "失败";
		}else if (id.equals("4")) {
			return "超时";
		}else{
			return "取消";
		}
	}
	
	//支付方式
	public static String zffs(String id) {
		if (id.equals("1")) {
			return "支付宝";
		} else if (id.equals("2")) {
			return "微信";
		}else{
			return "";
		}
	}
}

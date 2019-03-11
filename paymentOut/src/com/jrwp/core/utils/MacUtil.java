package com.jrwp.core.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MacUtil {

	/**
	 * java获取客户端网卡的MAC地址
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println(getLocalMac());
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getLocalMac() throws SocketException {
		InetAddress ia = null;
		try {
			ia = InetAddress.getLocalHost();
			return getMac(ia);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static String getMac(InetAddress ia) throws SocketException {
		// 获取网卡，获取地址
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < mac.length; i++) {
			// if (i != 0) {
			// sb.append("-");
			// }
			// 字节转换为整数
			int temp = mac[i] & 0xff;
			String str = Integer.toHexString(temp);
			if (str.length() == 1) {
				sb.append("0" + str);
			} else {
				sb.append(str);
			}
		}
		return sb.toString().toUpperCase();
	}
}
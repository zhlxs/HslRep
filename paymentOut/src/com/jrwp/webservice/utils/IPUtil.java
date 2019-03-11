package com.jrwp.webservice.utils;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author: ShenHaijie
 * @date: 2018年11月14日
 */
public class IPUtil {
	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		// Enumeration<String> headerNames = request.getHeaderNames();
		// while (headerNames.hasMoreElements()) {
		// String headerName = headerNames.nextElement();
		// System.err.println(headerName + "|" + request.getHeader(headerName));
		// }
		String remoteAddr = request.getHeader("X-Real-IP");
		if (StringUtils.isBlank(remoteAddr)) {
			remoteAddr = request.getHeader("X-Forwarded-For");
		}
		if (StringUtils.isBlank(remoteAddr)) {
			remoteAddr = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(remoteAddr)) {
			remoteAddr = request.getHeader("WL-Proxy-Client-IP");
		}
		return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}

	/**
	 * ip字符串转long型
	 * 
	 * @param ipStr
	 *            ip字符串转
	 * @return
	 */
	public static long ip2Long(String ipStr) {
		String[] ipArray = ipStr.split("\\.");
		long ipNum = Long.parseLong(ipArray[0]) * 256 * 256 * 256 + Long.parseLong(ipArray[1]) * 256 * 256
				+ Long.parseLong(ipArray[2]) * 256 + Long.parseLong(ipArray[3]);
		ipNum = ipNum >>> 0;
		return ipNum;
	}

	/**
	 * long型转ip字符串
	 * 
	 * @param ipNum
	 *            long型
	 * @return
	 */
	public static String long2ip(long ipNum) {
		String ip = null;
		long[] numArray = new long[4];
		numArray[0] = ipNum >>> 24;
		numArray[1] = (ipNum & 0x00FFFFFF) >>> 16;
		numArray[2] = (ipNum & 0x0000FFFF) >>> 8;
		numArray[3] = ipNum & 0x000000FF;
		ip = String.valueOf(numArray[0]) + "." + String.valueOf(numArray[1]) + "." + String.valueOf(numArray[2]) + "."
				+ String.valueOf(numArray[3]);
		return ip;
	}

	public static boolean isIp(String ip) {
		String regex = "^((0[0-9]|1[0-9]\\d{1,2})|(2[0-5][0-5])|(2[0-4][0-9])|(\\d{1,2}))\\.((0[0-9]|1[0-9]\\d{1,2})|(2[0-5][0-5])|(2[0-4][0-9])|(\\d{1,2}))\\.((0[0-9]|1[0-9]\\d{1,2})|(2[0-4][0-9])|(2[0-5][0-5])|(\\d{1,2}))\\.((0[0-9]|1[0-9]\\d{1,2})|(2[0-4][0-9])|(2[0-5][0-5])|(\\d{1,2}))$";
		return Pattern.matches(regex, ip);
	}

	// public static void main(String[] args) {
	// long ip2Long = ip2Long("10.179.145.230");
	// System.out.println(ip2Long);
	// System.out.println(long2ip(ip2Long));
	// System.out.println(isIp("10.179.145.230"));
	// }
}

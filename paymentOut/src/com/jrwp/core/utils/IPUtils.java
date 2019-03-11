package com.jrwp.core.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPUtils {

	public static void main(String[] args) {
		String ip = getIP();
		System.out.println(ip);
	}

	public static String getIP() {
		InetAddress netAddress = getInetAddress();
		return getHostIp(netAddress);
	}

	public static InetAddress getInetAddress() {

		try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println("unknown host!");
		}
		return null;

	}

	public static String getHostIp(InetAddress netAddress) {
		if (null == netAddress) {
			return null;
		}
		String ip = netAddress.getHostAddress(); // get the ip address
		return ip;
	}

	public static String getHostName(InetAddress netAddress) {
		if (null == netAddress) {
			return null;
		}
		String name = netAddress.getHostName(); // get the host address
		return name;
	}

}
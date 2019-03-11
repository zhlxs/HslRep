package com.jrwp.core.utils;

public class GrentUntil {

	public static Integer getBrowserName(String agent) {
		if (agent.indexOf("msie 4") > 0) {
			return 4;
		} else if (agent.indexOf("msie 5") > 0) {
			return 5;
		} else if (agent.indexOf("msie 6") > 0) {
			return 6;
		} else if (agent.indexOf("msie 7") > 0) {
			return 7;
		} else if (agent.indexOf("msie 8") > 0) {
			return 8;
		}

		else if (agent.indexOf("msie 9") > 0) {
			return 9;
		} else if (agent.indexOf("msie 10") > 0) {
			return 10;
		} else if (agent.indexOf("msie") > 0) {
			return 11;
		} else if (agent.indexOf("opera") > 0) {
			return 99;
		} else if (agent.indexOf("opera") > 0) {
			return 99;
		} else if (agent.indexOf("firefox") > 0) {
			return 88;
		} else if (agent.indexOf("webkit") > 0) {
			return 89;
		} else if (agent.indexOf("gecko") > 0 && agent.indexOf("rv:11") > 0) {
			return 11;
		} else {
			return 100;
		}
	}
}

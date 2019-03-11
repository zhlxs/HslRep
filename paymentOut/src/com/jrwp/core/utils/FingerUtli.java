package com.jrwp.core.utils;

public class FingerUtli {
	// 指纹指位对应的指纹数据
	public static String getZwzw(String zwzw) {
		String zwzw2 = null;
		switch (zwzw) {
		case "11":
			zwzw2 = "右手大拇指";
			break;
		case "12":
			zwzw2 = "右手食指";
			break;
		case "13":
			zwzw2 = "右手中指";
			break;
		case "14":
			zwzw2 = "右手无名指";
			break;
		case "15":
			zwzw2 = "右手小拇指";
			break;
		case "16":
			zwzw2 = "左手大拇指";
			break;
		case "17":
			zwzw2 = "左手食指";
			break;
		case "18":
			zwzw2 = "左手中指";
			break;
		case "19":
			zwzw2 = "左手无名指";
			break;
		case "20":
			zwzw2 = "左手小拇指";
			break;

		default:
			break;
		}
		return zwzw2;
	}

	public static String calculateZWCJJG(String zw_zcjg1, String zw_zcjg2) {
		if (zw_zcjg1.equals("1") && zw_zcjg2.equals("1")) {
			return "1";
		} else if ((zw_zcjg1.equals("1") && zw_zcjg2.equals("2")) || (zw_zcjg1.equals("2") && zw_zcjg2.equals("1"))) {
			return "2";
		} else if ((zw_zcjg1.equals("1") && zw_zcjg2.equals("3")) || (zw_zcjg1.equals("3") && zw_zcjg2.equals("1"))) {
			return "3";
		} else if (zw_zcjg1.equals("2") && zw_zcjg2.equals("2")) {
			return "4";
		} else if ((zw_zcjg1.equals("2") && zw_zcjg2.equals("3")) || (zw_zcjg1.equals("3") && zw_zcjg2.equals("2"))) {
			return "5";
		} else if (zw_zcjg1.equals("3") && zw_zcjg2.equals("3")) {
			return "6";
		} else {
			return "0";
		}
	}
}

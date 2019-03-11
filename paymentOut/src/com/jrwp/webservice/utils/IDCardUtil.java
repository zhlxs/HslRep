package com.jrwp.webservice.utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 二代身份证工具类
 */
public class IDCardUtil {

	// public static void main(String[] args) {
	// System.out.println("432902198112100613 | " + isIDCard("432902198112100613"));
	// System.out.println("43290219831016241X | " + isIDCard("43290219831016241X"));
	// System.out.println("34082119821227512x | " + isIDCard("34082119821227512x"));
	// System.out.println("130503671201001 | " + isIDCard("130503671201001"));
	// System.out.println("--------------------");
	// System.out.println(getInfo("432902198112100613").toString());
	// System.out.println("--------------------");
	// System.out.println(getInfo("43290219831016241X").toString());
	// System.out.println("--------------------");
	// System.out.println(getInfo("34082119821227512x").toString());
	// System.out.println("--------------------");
	// System.out.println(getInfo("130503671201001").toString());
	// }

	private final static Map<Integer, String> zoneNum = new HashMap<Integer, String>();

	static {
		zoneNum.put(11, "北京");
		zoneNum.put(12, "天津");
		zoneNum.put(13, "河北");
		zoneNum.put(14, "山西");
		zoneNum.put(15, "内蒙古");
		zoneNum.put(21, "辽宁");
		zoneNum.put(22, "吉林");
		zoneNum.put(23, "黑龙江");
		zoneNum.put(31, "上海");
		zoneNum.put(32, "江苏");
		zoneNum.put(33, "浙江");
		zoneNum.put(34, "安徽");
		zoneNum.put(35, "福建");
		zoneNum.put(36, "江西");
		zoneNum.put(37, "山东");
		zoneNum.put(41, "河南");
		zoneNum.put(42, "湖北");
		zoneNum.put(43, "湖南");
		zoneNum.put(44, "广东");
		zoneNum.put(45, "广西");
		zoneNum.put(46, "海南");
		zoneNum.put(50, "重庆");
		zoneNum.put(51, "四川");
		zoneNum.put(52, "贵州");
		zoneNum.put(53, "云南");
		zoneNum.put(54, "西藏");
		zoneNum.put(61, "陕西");
		zoneNum.put(62, "甘肃");
		zoneNum.put(63, "青海");
		zoneNum.put(64, "新疆");
		zoneNum.put(71, "台湾");
		zoneNum.put(81, "香港");
		zoneNum.put(82, "澳门");
		zoneNum.put(91, "外国");
	}

	private final static int[] PARITYBIT = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
	private final static int[] POWER_LIST = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	/**
	 * 身份证验证
	 *
	 * @param cardNo
	 *            号码
	 * @return
	 */
	public static boolean isIDCard(String cardNo) {
		if (cardNo == null || (cardNo.length() != 15 && cardNo.length() != 18))
			return false;
		final char[] cs = cardNo.toUpperCase().toCharArray();
		// 校验位数
		int power = 0;
		for (int i = 0; i < cs.length; i++) {
			if (i == cs.length - 1 && cs[i] == 'X') {
				break;// 最后一位可以 是X或x
			}
			if (cs[i] < '0' || cs[i] > '9') {
				return false;
			}
			if (i < cs.length - 1) {
				power += (cs[i] - '0') * POWER_LIST[i];
			}
		}

		// 校验区位码
		if (!zoneNum.containsKey(Integer.valueOf(cardNo.substring(0, 2)))) {
			return false;
		}

		int[] dateArray = getDateArray(cardNo);
		// 校验年份

		final int year = dateArray[0];
		if (year < 1900 || year > Calendar.getInstance().get(Calendar.YEAR)) {
			return false;
		}

		// 校验月份
		final int month = dateArray[1];
		if (month < 1 || month > 12) {
			return false;
		}

		// 校验天数
		final int day = dateArray[2];
		if (day < 1 || day > 31) {
			return false;
		}

		// 校验"校验码"
		if (cardNo.length() == 15) {
			return true;
		}
		return cs[cs.length - 1] == PARITYBIT[power % 11];
	}

	private static int[] getDateArray(String cardNo) {
		int[] dateArray = new int[3];
		int yearSpan = cardNo.length() == 15 ? 2 : 4;
		// 年份
		dateArray[0] = Integer.parseInt((yearSpan == 2 ? "19" : "") + cardNo.substring(6, 6 + yearSpan));
		// 月份
		dateArray[1] = Integer.parseInt(cardNo.substring(6 + yearSpan, 6 + yearSpan + 2));
		// 日期
		dateArray[2] = Integer.parseInt(cardNo.substring(8 + yearSpan, 8 + yearSpan + 2));
		return dateArray;
	}

	/**
	 * 根据身份证号判断性别
	 *
	 * @param cardNo
	 *            身份证号
	 * @return 0 女；1 男
	 */
	public static int getSex(String cardNo) {
		int idxSexStart = cardNo.length() == 15 ? 14 : 16;
		String idxSexStr = cardNo.substring(idxSexStart, idxSexStart + 1);
		return Integer.parseInt(idxSexStr) % 2;
	}

	/**
	 * 根据身份证号判断出生日期
	 *
	 * @param cardNo
	 * @return
	 */
	public static String getBirthday(String cardNo) {
		int[] dateArray = getDateArray(cardNo);
		String month = (dateArray[1] < 10 ? "0" : "") + dateArray[1];
		String day = (dateArray[2] < 10 ? "0" : "") + dateArray[2];
		return dateArray[0] + "-" + month + "-" + day;
	}

	/**
	 * 根据身份证号判断年龄
	 *
	 * @param cardNo
	 * @return
	 */
	public static String getAge(String cardNo) {
		int[] dateArray = getDateArray(cardNo);
		Calendar idCardCal = Calendar.getInstance();
		Calendar currentTimeCal = Calendar.getInstance();
		idCardCal.set(dateArray[0], dateArray[1] - 1, dateArray[2]);
		int yearAge = (currentTimeCal.get(Calendar.YEAR)) - (idCardCal.get(Calendar.YEAR));
		idCardCal.set(currentTimeCal.get(Calendar.YEAR), dateArray[1] - 1, dateArray[2]);
		int monthFloor = (currentTimeCal.before(idCardCal) ? 1 : 0);
		return Integer.toString(yearAge - monthFloor);
	}

	/**
	 * 根据身份证获取个人省份
	 *
	 * @param cardNo
	 *            身份证号
	 * @return
	 */
	public static String getProvince(String cardNo) {
		Integer provinceNo = Integer.valueOf(cardNo.substring(0, 2));
		if (!zoneNum.containsKey(provinceNo)) {
			return "未知";
		}
		return zoneNum.get(provinceNo);
	}

	/**
	 * 根据身份证获取个人信息
	 *
	 * @param cardNo
	 *            身份证号
	 * @return
	 */
	public static Map<String, String> getInfo(String cardNo) {

		Map<String, String> result = new HashMap<String, String>();
		result.put("cardNo", cardNo);
		if (isIDCard(cardNo)) {
			result.put("isIDCard", "true");
		} else {
			result.put("isIDCard", "false");
			return result;
		}
		// 性别
		result.put("sex", (getSex(cardNo) == 1) ? "男" : "女");
		// 出生日期
		result.put("birthday", getBirthday(cardNo));
		// 年龄
		result.put("age", getAge(cardNo));
		// 省份
		result.put("province", getProvince(cardNo));
		return result;
	}
}

package com.jrwp.wx.until;

public class DesUtil {
	 /**
     * 加密
     * @param srcStr
     * @return
     */
    public static String encrypt(String srcStr) {
    	String aa = srcStr.replace("0", "a").replace("1", "c").replace("2", "f").replace("3", "e").replace("4", "h")
    	.replace("5", "j").replace("6", "l").replace("7", "m").replace("8", "p").replace("9", "r");
    	//new StringBuilder(String.copyValueOf(arr)).reverse().toString();
    	return new StringBuilder(aa).reverse().toString();
    }

    /**
     * 解密
     *
     * @param hexStr
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String decrypt(String hexStr) {
    	String aa = new StringBuilder(hexStr).reverse().toString().replace("a", "0").replace("c", "1").replace("f", "2").replace("e", "3").replace("h", "4")
    	    	.replace("j", "5").replace("l", "6").replace("m", "7").replace("p", "8").replace("r", "9");
    	return aa;
    }
}

package com.jrwp.core.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESEncodeUtil {
	// 必须16位，需同步更改front_aes.js文件和数据库配置文件jdbc.properties
	private static final String key = "jiangxikth418418";
	private static final String keyAlgorithm = "AES";
	private static final String transformationFormat = "AES/ECB/PKCS5Padding";
	private static final String charset = "UTF-8";

	public static void main(String[] args) throws Exception {
		System.out.println(AESEncodeUtil.encode("jdbc:oracle:thin:@192.168.0.35:1521:orcl"));
		// System.out.println(AESEncodeUtil.decode("lHPELGsWlY7MfDzDqXyORmMLNrA6sFgjgyQ/V02VTNM="));
	}

	/**
	 * 加密
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String encode(String content) throws Exception {
		return encode(content, key);
	}

	/**
	 * AES加密为base 64 code
	 * 
	 * @param content
	 *            待加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return 加密后的base 64 code
	 * @throws Exception
	 */
	public static String encode(String content, String encryptKey) throws Exception {
		return base64Encode(aesEncryptToBytes(content, encryptKey));
	}

	/**
	 * 解密
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String decode(String content) throws Exception {
		return decode(content, key);
	}

	/**
	 * 将base 64 code AES解密
	 * 
	 * @param encryptStr
	 *            待解密的base 64 code
	 * @param decryptKey
	 *            解密密钥
	 * @return 解密后的string
	 * @throws Exception
	 */
	public static String decode(String encryptStr, String decryptKey) throws Exception {
		return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
	}

	/**
	 * base 64 encode
	 * 
	 * @param bytes
	 *            待编码的byte[]
	 * @return 编码后的base 64 code
	 */
	private static String base64Encode(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	/**
	 * base 64 decode
	 * 
	 * @param base64Code
	 *            待解码的base 64 code
	 * @return 解码后的byte[]
	 * @throws Exception
	 */
	private static byte[] base64Decode(String base64Code) throws Exception {
		return Base64.decodeBase64(base64Code);
	}

	/**
	 * AES加密
	 * 
	 * @param content
	 *            待加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return 加密后的byte[]
	 * @throws Exception
	 */
	private static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance(keyAlgorithm);
		kgen.init(128);
		Cipher cipher = Cipher.getInstance(transformationFormat);
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), keyAlgorithm));
		return cipher.doFinal(content.getBytes(charset));
	}

	/**
	 * AES解密
	 * 
	 * @param encryptBytes
	 *            待解密的byte[]
	 * @param decryptKey
	 *            解密密钥
	 * @return 解密后的String
	 * @throws Exception
	 */
	private static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance(keyAlgorithm);
		kgen.init(128);
		Cipher cipher = Cipher.getInstance(transformationFormat);
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), keyAlgorithm));
		byte[] decryptBytes = cipher.doFinal(encryptBytes);
		return new String(decryptBytes, charset);
	}
}

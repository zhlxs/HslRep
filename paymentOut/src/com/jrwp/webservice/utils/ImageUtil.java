package com.jrwp.webservice.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.stream.FileImageOutputStream;

import org.apache.commons.codec.binary.Base64;

public class ImageUtil {
	// 图片文件头信息
	public static final HashMap<String, String> imageTypes = new HashMap<String, String>();
	static {
		imageTypes.put("FFD8FFE0", "jpg");
		imageTypes.put("FFD8FFE1", "jpg");
		imageTypes.put("FFD8FFE8", "jpg");
		imageTypes.put("89504E47", "png");
		imageTypes.put("47494638", "gif");
		imageTypes.put("49492A00", "tif");
		imageTypes.put("00000100", "ico");
		imageTypes.put("424D", "bmp");
	}

//	 public static void main(String[] args) throws Exception {
//	 String filePath = "E:\\工作文档\\图\\zzzp2";
//	 String folderPath = "E:\\工作文档\\图";
//	 String base64String = image2Base64(filePath);
//	 System.out.println(base64String);
//	 base64ToImage(base64String, folderPath);
//	 }

	/**
	 * 图片转base64字符串
	 * 
	 * @param path
	 *            图片路径
	 * @return
	 * @throws Exception
	 */
	public static String image2Base64(String filePath) throws Exception {
		FileImageOutputStream imageIn = null;
		ByteArrayOutputStream byteOut = null;
		try {
			// 读取图片
			imageIn = new FileImageOutputStream(new File(filePath));
			// 将图片写入字节数组输出流中
			byteOut = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = imageIn.read(b)) != -1) {
				byteOut.write(b, 0, n);
			}
			byteOut.flush();
		} catch (Exception e) {
			throw e;
		} finally {
			if (imageIn != null) {
				try {
					imageIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (byteOut != null) {
				try {
					byteOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// "data:image/jpeg;base64," +
		return Base64.encodeBase64String(byteOut.toByteArray());
	}

	/**
	 * base64转图片
	 * 
	 * @param base64
	 *            base64字符串
	 * @param path
	 *            文件夹路径
	 * @throws Exception
	 */
	public static void base64ToImage(String base64, String folderPath) throws Exception {
		byte[] data = Base64.decodeBase64(base64);
		FileImageOutputStream imageOut = null;
		String fileName = new Date().getTime() + String.valueOf(new Random().nextInt(999) % 800 + 100);
		String fileExtName = getImageType(data);
		fileExtName = fileExtName == null ? "jpg" : fileExtName;
		String filePath = folderPath + File.separator + fileName + "." + fileExtName;
		File image = new File(filePath);

		try {
			if (!image.getParentFile().exists()) {
				image.getParentFile().mkdirs();
			}

			imageOut = new FileImageOutputStream(image);
			imageOut.write(data);
			imageOut.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			if (imageOut != null) {
				try {
					imageOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 根据图片byte数组获取图片类型
	 * 
	 * @param imageData
	 *            图片byte数组
	 * @return 图片类型
	 */
	public static String getImageType(byte[] imageData) {
		byte[] localData = new byte[4];
		for (int i = 0; i < localData.length; i++) {
			localData[i] = imageData[i];
		}
		return imageTypes.get(bytesToHexString(localData));
	}

	/**
	 * byte数组转换成十六进制String
	 * 
	 * @param data
	 *            byte数组
	 * @return
	 */
	public static String bytesToHexString(byte[] data) {
		StringBuilder builder = new StringBuilder();
		if (data == null || data.length <= 0) {
			return null;
		}
		String hv;
		for (int i = 0; i < data.length; i++) {
			// 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
			hv = Integer.toHexString(data[i] & 0xFF).toUpperCase();
			if (hv.length() < 2) {
				builder.append(0);
			}
			builder.append(hv);
		}
		return builder.toString();
	}
}

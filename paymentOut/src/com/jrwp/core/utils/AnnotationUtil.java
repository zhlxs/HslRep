package com.jrwp.core.utils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ClassUtils;

/**
 * 注解工具
 * 
 * @author USER
 * 
 */
public class AnnotationUtil {

	/**
	 * 获得指定类的注解value值
	 * 
	 * @param clazz
	 * @param annotationType
	 * @return
	 */
	public static String getAnnotationDefaultValue(Class<?> clazz,
			Class<? extends Annotation> annotationType) {
		Object annotationValue = AnnotationUtil.getAnnotationValue(clazz,
				annotationType, "value");
		String value = null;
		if (annotationValue != null) {
			value = annotationValue.toString();
		}
		return value;
	}

	/**
	 * 获得指定类的注解指定方法值
	 * 
	 * @param clazz
	 * @param annotationType
	 * @param str
	 * @return
	 */
	public static Object getAnnotationValue(Class<?> clazz,
			Class<? extends Annotation> annotationType, String str) {
		Annotation annotation = AnnotationUtils.findAnnotation(clazz,
				annotationType);
		Object value = AnnotationUtils.getValue(annotation, str);
		return value;
	}

	/**
	 * 获得指定方法的注解value值
	 * 
	 * @param method
	 * @param annotationType
	 * @return
	 */
	public static String getAnnotationDefaultValue(Method method,
			Class<? extends Annotation> annotationType) {
		Object annotationValue = getAnnotationValue(method, annotationType,
				"value");
		String value = null;
		if (annotationValue != null) {
			value = annotationValue.toString();
		}
		return value;
	}

	public static Object getAnnotationValue(Method method,
			Class<? extends Annotation> annotationType, String str) {
		Annotation annotation = AnnotationUtils.findAnnotation(method,
				annotationType);
		Object value = AnnotationUtils.getValue(annotation, str);
		return value;
	}

	public static void main(String[] args) {

		String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
				+ ClassUtils
						.convertClassNameToResourcePath("com.jrwp.core.action")
				+ "/**/*.class";
		try {
			Resource[] resources = new PathMatchingResourcePatternResolver()
					.getResources(pattern);
			for (Resource r : resources) {
				System.out.println("URL:" + r.getURL());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(11);
		// try {
		// File file = ResourceUtils.getFile("classpath*:com/**/*.class");
		// if (file.exists()) {
		// System.out.println(file.getPath());
		// findFile(file);
		// } else {
		// System.out.println("文件不存在");
		// }
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	public static void findFile(File... file) {
		for (File f : file) {
			if (f.exists()) {
				if (f.isDirectory()) {
					findFile(f.listFiles());
				} else if (f.isFile()) {
					System.out.println(f.getPath());
				}
			}
		}
	}

}

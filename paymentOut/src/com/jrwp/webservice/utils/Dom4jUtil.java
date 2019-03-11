package com.jrwp.webservice.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 
 * @author: ShenHaijie
 * @date: 2018年11月1日
 */
public class Dom4jUtil {

	public static Element getRootElement(String xmlString) throws DocumentException {
		Document document = DocumentHelper.parseText(xmlString);
		return document.getRootElement();
	}

	public static Element getHeadElement(String xmlString) throws DocumentException {
		return getRootElement(xmlString).element("head");
	}

	public static Element getBodyElement(String xmlString) throws DocumentException {
		return getRootElement(xmlString).element("body");
	}

	public static void removeElement(Element element, String name) throws DocumentException {
		if (element.element(name) != null) {
			element.element(name).detach();
		}
	}
}

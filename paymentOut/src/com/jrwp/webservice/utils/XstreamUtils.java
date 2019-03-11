package com.jrwp.webservice.utils;

import java.io.Reader;
import java.io.Writer;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.xmlpull.v1.XmlPullParserException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.io.xml.XppReader;

public class XstreamUtils {
	private final static String character = "UTF-8";
	private final static Pattern pattern = Pattern.compile("[\u4E00-\u9FA5]+");
	private final static String defaultdateFormat = "yyyy-MM-dd HH:mm:ss.S";

	private XstreamUtils() {
	};

	/**
	 * 获得带标记（<![CDATA[ ]]>）的XStream对象
	 * 
	 * @param encoder
	 *            是否url转码
	 * @return
	 */
	public static XStream hasMarkerXStreamWriter(final boolean encoder) {
		// 解析器 DomDriver,XppDriver,StaxDriver
		XStream xStream = new XStream(new XppDriver() {

			@Override
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out) {

					@Override
					protected void writeText(QuickWriter writer, String text) {
						writer.write("<![CDATA[");
						if (encoder) {
							try {
								while (pattern.matcher(text).find()) {
									text = URLEncoder.encode(text, character);
									break;
								}
								writer.write(text);
							} catch (Exception e) {
								writer.write(text);
							}
						} else {
							writer.write(text);
						}
						writer.write("]]>");
					}
				};
			}

			@Override
			public HierarchicalStreamReader createReader(Reader in) {
				try {
					return new XppReader(in, super.createParser()) {

						@Override
						protected String pullText() {

							if (encoder) {
								try {
									return URLDecoder.decode(super.pullText(), character);
								} catch (Exception e) {
									return super.pullText();
								}
							} else {
								return super.pullText();
							}
						}

					};
				} catch (XmlPullParserException e) {
					throw new StreamException("Cannot create XmlPullParser", e);
				}
			}
		});
		return xStream;
	}

	/**
	 * 
	 * 获得不带标记的XStream对象
	 * 
	 * @param encoder
	 *            是否url转码
	 * @return
	 */
	public static XStream xStreamWriter(final boolean encoder) {
		XStream xStream = new XStream(new XppDriver() {

			@Override
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out) {

					@Override
					protected void writeText(QuickWriter writer, String text) {
						if (encoder) {
							try {
								while (pattern.matcher(text).find()) {
									text = URLEncoder.encode(text, character);
									break;
								}
								writer.write(text);
							} catch (Exception e) {
								writer.write(text);
							}
						} else {
							writer.write(text);
						}
					}
				};
			}

			@Override
			public HierarchicalStreamReader createReader(Reader in) {
				try {
					return new XppReader(in, super.createParser()) {

						@Override
						protected String pullText() {

							if (encoder) {
								try {
									return URLDecoder.decode(super.pullText(), character);
								} catch (Exception e) {
									return super.pullText();
								}
							} else {
								return super.pullText();
							}
						}

					};
				} catch (XmlPullParserException e) {
					throw new StreamException("Cannot create XmlPullParser", e);
				}
			}
		});
		return xStream;
	}

	/**
	 * 
	 * 获得xml字符串，并URLEncoder中文转码
	 * 
	 * @param obj
	 * @param rootName
	 *            根标签名字
	 * @param dateFormat
	 *            日期格式，默认yyyy-MM-dd HH:mm:ss.S
	 * @param marker
	 *            是否带标记
	 * @return
	 */
	public static String getXmlURLEncoder(Object obj, String rootName, String dateFormat, boolean marker) {
		String xmlString = null;
		XStream nowXStream = marker == true ? hasMarkerXStreamWriter(true) : xStreamWriter(true);
		dateFormat = dateFormat == null ? defaultdateFormat : dateFormat;
		nowXStream.registerConverter(new DateConverter(dateFormat, null, TimeZone.getTimeZone("GMT+8")));
		nowXStream.autodetectAnnotations(true);
		nowXStream.alias(rootName, obj.getClass());
		nowXStream.aliasSystemAttribute(null, "class");
		xmlString = nowXStream.toXML(obj);
		nowXStream = null;
		return replaceAll(xmlString);
	}

	/**
	 * 
	 * 获得xml字符串
	 * 
	 * @param obj
	 * @param rootName
	 *            根标签名字
	 * @param dateFormat
	 *            日期格式，默认yyyy-MM-dd HH:mm:ss.S
	 * @param marker
	 *            是否带标记
	 * @return
	 */
	public static String getXml(Object obj, String rootName, String dateFormat, boolean marker) {
		String xmlString = null;
		XStream nowXStream = marker == true ? hasMarkerXStreamWriter(false) : xStreamWriter(false);
		dateFormat = dateFormat == null ? defaultdateFormat : dateFormat;
		nowXStream.registerConverter(new DateConverter(dateFormat, null, TimeZone.getTimeZone("GMT+8")));
		nowXStream.autodetectAnnotations(true);
		nowXStream.alias(rootName, obj.getClass());
		nowXStream.aliasSystemAttribute(null, "class");
		xmlString = nowXStream.toXML(obj);
		nowXStream = null;
		return replaceAll(xmlString);
	}

	/**
	 * 
	 * 
	 * 获得xml解析结果，并URLDecoder中文转码
	 * 
	 * @param clazz
	 * @param rootName
	 *            根标签名字
	 * @param xmlString
	 *            xml字符串
	 * @param dateFormat
	 *            日期格式，默认yyyy-MM-dd HH:mm:ss.S
	 * @param marker
	 *            是否带标记
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T parseXmlURLDecoder(Class<T> clazz, String rootName, String xmlString, String dateFormat,
			boolean marker) {
		XStream nowXStream = marker == true ? hasMarkerXStreamWriter(true) : xStreamWriter(true);
		nowXStream.allowTypes(new Class[] { clazz });
		dateFormat = dateFormat == null ? defaultdateFormat : dateFormat;
		nowXStream.registerConverter(new DateConverter(dateFormat, null, TimeZone.getTimeZone("GMT+8")));
		nowXStream.autodetectAnnotations(true);
		nowXStream.alias(rootName, clazz);
		T object = (T) nowXStream.fromXML(xmlString);
		nowXStream = null;
		return object;
	}

	/**
	 * 
	 * 获得xml解析结果
	 * 
	 * @param clazz
	 * @param rootName
	 *            根标签名字
	 * @param xmlString
	 *            xml字符串
	 * @param dateFormat
	 *            日期格式，默认yyyy-MM-dd HH:mm:ss.S
	 * @param marker
	 *            是否带标记
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public static <T> T parseXml(Class<T> clazz, String rootName, String xmlString, String dateFormat, boolean marker) {
		XStream nowXStream = marker == true ? hasMarkerXStreamWriter(false) : xStreamWriter(false);
		nowXStream.allowTypes(new Class[] { clazz });
		dateFormat = dateFormat == null ? defaultdateFormat : dateFormat;
		nowXStream.registerConverter(new DateConverter(dateFormat, null, TimeZone.getTimeZone("GMT+8")));
		nowXStream.autodetectAnnotations(true);
		nowXStream.alias(rootName, clazz);
		T object = (T) nowXStream.fromXML(xmlString);
		nowXStream = null;
		return object;
	}

	/**
	 * 替换长下划线为短下划线
	 * 
	 * @param string
	 * @return
	 */
	private static String replaceAll(String string) {
		return string.replaceAll("__", "_");
	}

	/**
	 * xml文档头
	 * 
	 * @param string
	 * @return
	 */
	public static String getXmlFileHead() {
		return "<?xml version=\"1.0\" encoding=\"GBK\"?>\n";
	}

	/**
	 * 替换掉xml文档中的class="......."
	 * 
	 * @param xml
	 * @return
	 */
	public static String replaceTypeName(String xml) {
		return xml.replaceAll(" class=\"\\S*\"", "");
	}
}

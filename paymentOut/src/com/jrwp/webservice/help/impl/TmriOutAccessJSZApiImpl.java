package com.jrwp.webservice.help.impl;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jrwp.webservice.client.TmriJaxRpcOutNewAccessProxy;
import com.jrwp.webservice.entity.tmrioutaccess.QueryHead;
import com.jrwp.webservice.entity.tmrioutaccess.QueryXml;
import com.jrwp.webservice.entity.tmrioutaccess.WriteHead;
import com.jrwp.webservice.entity.tmrioutaccess.WriteXml;
import com.jrwp.webservice.entity.tmrioutaccess.request.Request25C10;
import com.jrwp.webservice.entity.tmrioutaccess.request.Request25C11;
import com.jrwp.webservice.entity.tmrioutaccess.request.Request25C12;
import com.jrwp.webservice.entity.tmrioutaccess.response.Response25C10;
import com.jrwp.webservice.entity.tmrioutaccess.response.Response25C11;
import com.jrwp.webservice.help.TmriOutAccessJSZApi;
import com.jrwp.webservice.utils.Dom4jUtil;
import com.jrwp.webservice.utils.XstreamUtils;

/**
 * 
 * @author: ShenHaijie
 * @date: 2018年11月1日
 */
@Component(value = "tmriOutAccessJSZApi")
public class TmriOutAccessJSZApiImpl implements TmriOutAccessJSZApi {
	private Logger logger = LoggerFactory.getLogger(TmriOutAccessJSZApiImpl.class);
	@Autowired
	private TmriJaxRpcOutNewAccessProxy tmriOutAccessClient;

	/**
	 * 叫号评价备案信息读取接口
	 */

	public List<Response25C10> getDriverPhoto(Request25C10 request25C10) {
		String jkid = "25C10";
		List<Response25C10> result = new ArrayList<Response25C10>();
		try {
			// 将json对象转换为实体类

			// Response25C01 request = new Response25C01();
			// ReflectUtil.reflectSetValue(request, jsonObject);

			// 合成查询条件
			QueryXml<Request25C10> query25C10 = new QueryXml<Request25C10>();
			query25C10.setQueryCondition(request25C10);

			// 生成查询条件xml
			String UTF8XmlDoc = XstreamUtils.getXmlFileHead()
					+ XstreamUtils.getXmlURLEncoder(query25C10, "root", null, false);
			logger.info("[" + jkid + "]查询条件:\n" + UTF8XmlDoc);

			// 调用远程接口
			String resultXml = tmriOutAccessClient.queryObjectOut("25", jkid, UTF8XmlDoc);

			logger.info("[" + jkid + "]远程响应:\n" + resultXml);

			// 获得响应头
			Element head = Dom4jUtil.getHeadElement(resultXml);
			// 将响应头转换为实体类
			QueryHead responseHead = XstreamUtils.parseXmlURLDecoder(QueryHead.class, head.getName(), head.asXML(),
					null, false);

			// 存放响应内容的容器
			// List<Response25C10> responseBody = new ArrayList<Response25C10>();
			// 判断是否查询成功
			if ("1".equals(responseHead.getCode())) {
				// 获得响应体
				Element body = Dom4jUtil.getBodyElement(resultXml);
				// 获得响应体中的元素
				if (body != null) {
					@SuppressWarnings("unchecked")
					List<Element> elements = body.elements();
					for (Element item : elements) {
						// 将响应体元素转换为实体类
						Response25C10 response25C10 = XstreamUtils.parseXmlURLDecoder(Response25C10.class,
								item.getName(), item.asXML(), null, false);
						result.add(response25C10);
					}
				}
			}
		} catch (Exception e) {
			logger.error("接口[" + jkid + "]调用失败:\n", e);
		}

		return result;
	}

	public WriteHead writeApprInfo(Request25C12 request25C12) {
		String jkid = "25C12";
		WriteHead result = new WriteHead();
		try {
			// 合成查询条件
			WriteXml<Request25C12> query25C12 = new WriteXml<Request25C12>();
			query25C12.setQueue(request25C12);

			// 生成查询条件xml
			String UTF8XmlDoc = XstreamUtils.getXmlFileHead()
					+ XstreamUtils.getXmlURLEncoder(query25C12, "root", null, false);
			logger.info("[" + jkid + "]写入数据:\n" + UTF8XmlDoc);

			// 调用远程接口
			String resultXml = tmriOutAccessClient.writeObjectOut("25", jkid, UTF8XmlDoc);

			logger.info("[" + jkid + "]远程响应:\n" + resultXml);

			// 获得响应头
			Element head = Dom4jUtil.getHeadElement(resultXml);
			// 将响应头转换为实体类
			result = XstreamUtils.parseXmlURLDecoder(WriteHead.class, head.getName(), head.asXML(), null, false);

		} catch (Exception e) {
			logger.error("接口[" + jkid + "]调用失败:\n", e);
		}

		return result;
	}

	// public WriteHead writeQHInfo(Request25C11 request25C11) {
	// String jkid = "25C12";
	// WriteHead result = new WriteHead();
	// try {
	// // 合成查询条件
	// WriteXml<Request25C11> query25C11 = new WriteXml<Request25C11>();
	// query25C11.setQueue(request25C11);
	//
	// // 生成查询条件xml
	// String UTF8XmlDoc = XstreamUtils.getXmlFileHead()
	// + XstreamUtils.getXmlURLEncoder(query25C11, "root", null, false);
	// logger.info("[" + jkid + "]写入数据:\n" + UTF8XmlDoc);
	//
	// // 调用远程接口
	// String resultXml = tmriOutAccessClient.writeObjectOut("25", jkid,
	// UTF8XmlDoc);
	//
	// logger.info("[" + jkid + "]远程响应:\n" + resultXml);
	//
	// // 获得响应头
	// Element head = Dom4jUtil.getHeadElement(resultXml);
	// // 将响应头转换为实体类
	// result = XstreamUtils.parseXmlURLDecoder(WriteHead.class, head.getName(),
	// head.asXML(),
	// null, false);
	// } catch (Exception e) {
	// logger.error("接口[writeApprInfo]调用失败:\n", e);
	// }
	// return result;
	// }

	@Override
	public Response25C11 writeBCInfo(Request25C11 request25c11) {
		String jkid = "25C11";
		Response25C11 result = new Response25C11();
		try {
			// 合成查询条件
			WriteXml<Request25C11> query25C11 = new WriteXml<Request25C11>();
			query25C11.setQueue(request25c11);

			// 生成查询条件xml
			String UTF8XmlDoc = XstreamUtils.getXmlFileHead()
					+ XstreamUtils.getXmlURLEncoder(query25C11, "root", null, false);
			logger.info("[" + jkid + "]写入数据:\n" + UTF8XmlDoc);

			// 调用远程接口
			String resultXml = tmriOutAccessClient.writeObjectOut("25", jkid, UTF8XmlDoc);

			logger.info("[" + jkid + "]远程响应:\n" + resultXml);

			// 获得响应头
			Element head = Dom4jUtil.getHeadElement(resultXml);
			// 将响应头转换为实体类
			result = XstreamUtils.parseXmlURLDecoder(Response25C11.class, head.getName(), head.asXML(), null, false);

		} catch (Exception e) {
			logger.error("接口[" + jkid + "]调用失败:\n", e);
		}

		return result;
	}

}

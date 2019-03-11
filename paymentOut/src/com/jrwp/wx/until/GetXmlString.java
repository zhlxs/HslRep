package com.jrwp.wx.until;

import com.jrwp.wx.entity.AppraiseJson;

public class GetXmlString {
	public static String firstXmlString(String sbkzjsjip){
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<QueryCondition>");
		sb.append("<sbkzjsjip>" + sbkzjsjip + "</sbkzjsjip>");
		sb.append("</QueryCondition>");
		return sb.toString();
	}
	
	public static String secondXmlString(AppraiseJson appraiseJson){
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<queue>");
		sb.append("<qhxxxlh>" + appraiseJson.getQhxxxlh() + "</qhxxxlh>");
		sb.append("<pjlb>" + appraiseJson.getPjlb() + "</pjlb>");
		sb.append("<pjjg>" + appraiseJson.getPjjg() + "</pjjg>");
		sb.append("</queue>");
		return sb.toString();
	}
}

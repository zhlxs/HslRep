package com.jrwp.core.help;

import java.util.List;

public class QueryHelper {
	public static String getWhereSql(String sql, List<WhereInfo> whereInfos) {
		StringBuilder sb = new StringBuilder("");
		if(sql != null && !sql.equals(""))
			sb.append(sql);
		else
			sb.append("where 1=1");
		if (whereInfos != null) {
			for (WhereInfo whereInfo : whereInfos) {
				sb.append(" ").append(whereInfo.getAndOr()).append(" ")
				.append(whereInfo.getFieldName()).append(" ");
				if (whereInfo.getWhereField().equals("like")) {
					sb.append(whereInfo.getWhereField()).append(" ");
					if (whereInfo.getFieldType().equals("String")) {
						sb.append("'%" + whereInfo.getQueryValue() + "%'");
					}
				} else if (whereInfo.getWhereField().equals(">=")
						|| whereInfo.getWhereField().equals("<=")
						|| whereInfo.getWhereField().equals("=")) {
					sb.append(whereInfo.getWhereField()).append(" ");
					if (whereInfo.getFieldType().equals("DateTime")) {
						if (whereInfo.getWhereField().equals(">=")) {
							sb.append("to_date(CONCAT('"
									+ whereInfo.getQueryValue()
									+ "',' 00:00:00'),'yyyy-mm-dd hh24:mi:ss')");
						} else if (whereInfo.getWhereField().equals("<=")) {
							sb.append("to_date(CONCAT('"
									+ whereInfo.getQueryValue()
									+ "',' 23:59:59'),'yyyy-mm-dd hh24:mi:ss')");
						} else {
							sb.append("to_date('" + whereInfo.getQueryValue()
									+ "','yyyy-mm-dd hh24:mi:ss')");
						}
					} else if (whereInfo.getFieldType().equals("String")) {
						if (whereInfo.getFieldName().equals("dwdm")) {
							sb.append(whereInfo.getQueryValue()).append("000");
						} else {
							sb.append(whereInfo.getQueryValue());
						}
					}
				}else if (whereInfo.getWhereField().equals("in")){
					sb.append(whereInfo.getWhereField()).append(" ");
					if(whereInfo.getFieldType().equals("String")){
						if(whereInfo.getFieldName().equals("dwdm")){
							String queryValue = whereInfo.getQueryValue().toString();
							queryValue = queryValue.replaceAll("\\[", "'");
							queryValue = queryValue.replaceAll("\\]", "'");
							queryValue = queryValue.replaceAll(", ", "000','");
							sb.append("(" + queryValue + ")").append(" ");
						}else if(whereInfo.getFieldName().equals("sqjbh")){
							String queryValue = whereInfo.getQueryValue().toString();
							queryValue = queryValue.replaceAll("\\[", "'");
							queryValue = queryValue.replaceAll("\\]", "'");
							queryValue = queryValue.replaceAll(", ", "','");
							sb.append("(" + queryValue + ")").append(" ");
						}else{
							String queryValue = whereInfo.getQueryValue().toString();
							sb.append("(" + queryValue + ")").append(" ");
						}
					}
					else if (whereInfo.getFieldType().equals("Long")){
						sb.append("(select id from core_dept start with id =")
						.append(whereInfo.getQueryValue())
						.append(" connect by prior id=parentid)");
					}
				}
			}
		}
		return sb.toString();
	}

	public static String getWhereSqxx(String sql, List<WhereInfo> whereInfos) {
		StringBuilder sb = new StringBuilder("");
		if(sql != null && !sql.equals(""))
			sb.append(sql);
		else
			sb.append("where 1=1");
		if (whereInfos != null) {
			for (WhereInfo whereInfo : whereInfos) {
				if(whereInfo.getFieldName().equals("dwdm")){
					sb.append(" ");continue;
				}
				else{
					sb.append(" ").append(whereInfo.getAndOr()).append(" ")
					.append(whereInfo.getFieldName()).append(" ");}
				if (whereInfo.getWhereField().equals("like")) {
					sb.append(whereInfo.getWhereField()).append(" ");
					if (whereInfo.getFieldType().equals("String")) {
						sb.append("'%" + whereInfo.getQueryValue() + "%'");
					}
				} else if (whereInfo.getWhereField().equals(">=")
						|| whereInfo.getWhereField().equals("<=")
						|| whereInfo.getWhereField().equals("=")) {
					sb.append(whereInfo.getWhereField()).append(" ");
					if (whereInfo.getFieldType().equals("DateTime")) {
						if (whereInfo.getWhereField().equals(">=")) {
							sb.append("to_date(CONCAT('"
									+ whereInfo.getQueryValue()
									+ "',' 00:00:00'),'yyyy-mm-dd hh24:mi:ss')");
						} else if (whereInfo.getWhereField().equals("<=")) {
							sb.append("to_date(CONCAT('"
									+ whereInfo.getQueryValue()
									+ "',' 23:59:59'),'yyyy-mm-dd hh24:mi:ss')");
						} else {
							sb.append("to_date('" + whereInfo.getQueryValue()
									+ "','yyyy-mm-dd hh24:mi:ss')");
						}
					}if (whereInfo.getFieldType().equals("Dateget")) {
						if (whereInfo.getWhereField().equals(">=")) {
							sb.append("to_date(CONCAT('"
									+ whereInfo.getQueryValue()
									+ "',' 00:00:00'),'yyyy-mm-dd hh24:mi:ss')-45");
						} else if (whereInfo.getWhereField().equals("<=")) {
							sb.append("to_date(CONCAT('"
									+ whereInfo.getQueryValue()
									+ "',' 23:59:59'),'yyyy-mm-dd hh24:mi:ss')-45");
						} else {
							sb.append("to_date('" + whereInfo.getQueryValue()
									+ "','yyyy-mm-dd hh24:mi:ss')-45");
						}
					}
				}else if (whereInfo.getWhereField().equals("in")){
					sb.append(whereInfo.getWhereField()).append(" ");
					if(whereInfo.getFieldType().equals("String")){
						String queryValue = whereInfo.getQueryValue().toString();
						queryValue = queryValue.replaceAll("\\[", "'");
						queryValue = queryValue.replaceAll("\\]", "'");
						queryValue = queryValue.replaceAll(", ", "','");
						sb.append("(" + queryValue + ")").append(" ");
					}
				}
			}
		}
		return sb.toString();
	}
}

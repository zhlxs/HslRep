package com.jrwp.core.help;

import java.util.List;

public class WhereInfo {
	private WhereType whereType;
	private List<WhereInfo> childWhere;
	private String andOr;
	private String fieldName;
	private String fieldType;
	private String whereField;
	private Object queryValue;

	public WhereType getWhereType() {
		return whereType;
	}

	public void setWhereType(WhereType whereType) {
		this.whereType = whereType;
	}

	public List<WhereInfo> getChildWhere() {
		return childWhere;
	}

	public void setChildWhere(List<WhereInfo> childWhere) {
		this.childWhere = childWhere;
	}

	public String getAndOr() {
		return andOr;
	}

	public void setAndOr(String andOr) {
		this.andOr = andOr;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getWhereField() {
		return whereField;
	}

	public void setWhereField(String whereField) {
		this.whereField = whereField;
	}

	public Object getQueryValue() {
		return queryValue;
	}

	public void setQueryValue(Object queryValue) {
		this.queryValue = queryValue;
	}

}

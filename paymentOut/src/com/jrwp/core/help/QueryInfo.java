package com.jrwp.core.help;

import java.util.List;
import java.util.Map;

public class QueryInfo {
	private Map<String, String> selectFields;
	private List<WhereInfo> whereInfos;
	private List<FormTable> formTables;
	private Map<String, String> orderInfos;

	public Map<String, String> getSelectFields() {
		return selectFields;
	}

	public void setSelectFields(Map<String, String> selectFields) {
		this.selectFields = selectFields;
	}

	public List<WhereInfo> getWhereInfos() {
		return whereInfos;
	}

	public void setWhereInfos(List<WhereInfo> whereInfos) {
		this.whereInfos = whereInfos;
	}

	public List<FormTable> getFormTables() {
		return formTables;
	}

	public void setFormTables(List<FormTable> formTables) {
		this.formTables = formTables;
	}

	public Map<String, String> getOrderInfos() {
		return orderInfos;
	}

	public void setOrderInfos(Map<String, String> orderInfos) {
		this.orderInfos = orderInfos;
	}

}

package com.jrwp.core.help;

public class FormTable {
	private String tableName;
	private String tableAlias;
	private JoinType joinType;
	private String joinClause;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableAlias() {
		return tableAlias;
	}

	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}

	public JoinType getJoinType() {
		return joinType;
	}

	public void setJoinType(JoinType joinType) {
		this.joinType = joinType;
	}

	public String getJoinClause() {
		return joinClause;
	}

	public void setJoinClause(String joinClause) {
		this.joinClause = joinClause;
	}

}

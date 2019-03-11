package com.jrwp.wx.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EvaluateSetting {
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	private int waitingTime;
	private boolean autoEvaluate;
	private boolean questionnaireState;
	private String autoEvaluateID;
	private List<Evaluate> evaluateList;
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getWaitingTime() {
		return waitingTime;
	}
	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}
	public boolean isAutoEvaluate() {
		return autoEvaluate;
	}
	public void setAutoEvaluate(boolean autoEvaluate) {
		this.autoEvaluate = autoEvaluate;
	}
	public boolean isQuestionnaireState() {
		return questionnaireState;
	}
	public void setQuestionnaireState(boolean questionnaireState) {
		this.questionnaireState = questionnaireState;
	}
	public String getAutoEvaluateID() {
		return autoEvaluateID;
	}
	public void setAutoEvaluateID(String autoEvaluateID) {
		this.autoEvaluateID = autoEvaluateID;
	}
	public List<Evaluate> getEvaluateList() {
		return evaluateList;
	}
	public void setEvaluateList(List<Evaluate> evaluateList) {
		this.evaluateList = evaluateList;
	}
	
	
}

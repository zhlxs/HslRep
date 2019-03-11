package com.jrwp.core.entity;

public class Msg {

	private String touser;
	private String msgtype;
	private Content text;

	public Msg() {
		text = new Content();
	}

	public String getMsgtype() {
		return msgtype;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public Content getText() {
		return text;
	}

	public void setText(Content text) {
		this.text = text;
	}
}
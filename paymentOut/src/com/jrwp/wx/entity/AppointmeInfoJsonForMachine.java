package com.jrwp.wx.entity;

public class AppointmeInfoJsonForMachine {
	private Long id;
	private String appointmenttime;
	private String time;
	private String business;
	private String cardNumber;
	private String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAppointmenttime() {
		return appointmenttime;
	}
	public void setAppointmenttime(String appointmenttime) {
		this.appointmenttime = appointmenttime;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

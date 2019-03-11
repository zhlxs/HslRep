package com.jrwp.wx.entity;

public class SquenceHelper {
    private String squence;
    private int id;
   // private int appCount;
    private int localCount;
    private String name;

//    public int getAppCount() {
//        return appCount;
//    }
//
//    public void setAppCount(int appCount) {
//        this.appCount = appCount;
//    }

    
    public int getLocalCount() {
        return localCount;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLocalCount(int localCount) {
        this.localCount = localCount;
    }

    public String getSquence() {
        return squence;
    }

    public void setSquence(String squence) {
        this.squence = squence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

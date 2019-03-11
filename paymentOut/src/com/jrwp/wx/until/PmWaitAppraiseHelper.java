package com.jrwp.wx.until;

import java.util.ArrayList;
import java.util.List;

import com.jrwp.wx.entity.AppraiseInfoForAndroid;

public class PmWaitAppraiseHelper {
	private static PmWaitAppraiseHelper instance;
	private List<AppraiseInfoForAndroid> taget = new ArrayList<>();
	
	public synchronized List<AppraiseInfoForAndroid> dateChange(int i,List<AppraiseInfoForAndroid> list){
		if(i== 1){
			return getTaget();
		} else {
			setTaget(list);
			return null;
		}
	}
	
	public synchronized static PmWaitAppraiseHelper getInstance() {
		if(instance == null) {
			instance = new PmWaitAppraiseHelper();
		}
		return instance;
	}
	public List<AppraiseInfoForAndroid> getTaget(){
		synchronized (this) {
            return taget;
        }
	}
	
	public void setTaget(List<AppraiseInfoForAndroid> taget){
		synchronized (this) {
            this.taget =taget;
        }
	}
}

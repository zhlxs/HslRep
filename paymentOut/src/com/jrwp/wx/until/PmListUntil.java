package com.jrwp.wx.until;

import java.util.ArrayList;
import java.util.List;

import com.jrwp.wx.entity.AppraiseInfoForAndroid;
import com.jrwp.wx.entity.PmVisitor;

public class PmListUntil {
	public static List<PmVisitor> listCopy(List<PmVisitor> source) {
        List<PmVisitor> visitorList = new ArrayList<>();
        for (PmVisitor visitor : source) {
        	PmVisitor copyVisitor = new PmVisitor();
            copyVisitor.setState(visitor.getState());
            copyVisitor.setQueueNumber(visitor.getQueueNumber());
            copyVisitor.setName(visitor.getName());
            copyVisitor.setWindowNumber(visitor.getWindowNumber());
            copyVisitor.setDeptid(visitor.getDeptid());
            copyVisitor.setCallcount(visitor.getCallcount());
            visitorList.add(copyVisitor);
        }
        return visitorList;
    }
	
	public static List<AppraiseInfoForAndroid> listCopy2(List<AppraiseInfoForAndroid> source) {
        List<AppraiseInfoForAndroid> visitorList = new ArrayList<>();
        for (AppraiseInfoForAndroid visitor : source) {
        	AppraiseInfoForAndroid copyVisitor = new AppraiseInfoForAndroid();
            copyVisitor.setJqm(visitor.getJqm());
            copyVisitor.setSquence_infoid(visitor.getSquence_infoid());
            visitorList.add(copyVisitor);
        }
        return visitorList;
    }
}

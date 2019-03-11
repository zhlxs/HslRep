package com.jrwp.wx.until;

import com.jrwp.wx.entity.Visitor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListUntil {
    public static List<Visitor> listCopy(List<Visitor> source) {
        List<Visitor> visitorList = new ArrayList<>();
        for (Visitor visitor : source) {
            Visitor copyVisitor = new Visitor();
            copyVisitor.setState(visitor.getState());
            copyVisitor.setQueueNumber(visitor.getQueueNumber());
            copyVisitor.setName(visitor.getName());
            copyVisitor.setWindowNumber(visitor.getWindowNumber());
            copyVisitor.setDeptid(visitor.getDeptid());
            visitorList.add(copyVisitor);
        }
        return visitorList;
    }

}

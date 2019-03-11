package com.jrwp.wx.service;

import com.jrwp.wx.entity.AppointmeInfo;
import com.jrwp.wx.entity.TimeQuan;

import java.util.Date;
import java.util.List;

public interface AppointmeInfoService {
    int insert(AppointmeInfo appointmeInfo);

    TimeQuan getTimequn(int id);

    int getTimequnCount(String day, int time,int deptid);

    void cancleApp(int id);
    int getAppCount( Date date,Integer deptid);

    AppointmeInfo getAppInfoByopenidOrCardnumber(String openid, String cardnumber, Date date, int deptid);

    List<AppointmeInfo> selectByopenidOrCardnumber(String openid, String cardnumber,String date);
    AppointmeInfo findByid(int id);
}

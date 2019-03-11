package com.jrwp.wx.service.impl;

import com.jrwp.wx.dao.AppointmeInfoMapper;
import com.jrwp.wx.entity.AppointmeInfo;
import com.jrwp.wx.entity.TimeQuan;
import com.jrwp.wx.service.AppointmeInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AppointmeInfoImpl implements AppointmeInfoService {
    @Resource
    private AppointmeInfoMapper appointmeInfoMapper;

    @Override
    public int insert(AppointmeInfo appointmeInfo) {
        return appointmeInfoMapper.insert(appointmeInfo);
    }

    @Override
    public TimeQuan getTimequn(int id) {
        return appointmeInfoMapper.getTimequn(id);
    }

    @Override
    public int getTimequnCount(String day, int time, int deptid) {
        return appointmeInfoMapper.getTimequnCount(day, time, deptid);
    }

    @Override
    public void cancleApp(int id) {
        appointmeInfoMapper.cancleApp(id);
    }

    @Override
    public int getAppCount(Date date, Integer deptid) {
        return appointmeInfoMapper.getAppCount(date, deptid);
    }

    @Override
    public AppointmeInfo getAppInfoByopenidOrCardnumber(String openid, String cardnumber, Date date, int deptid) {
        return appointmeInfoMapper.getAppInfoByopenidOrCardnumber(openid, cardnumber, date, deptid);
    }

    @Override
    public List<AppointmeInfo> selectByopenidOrCardnumber(String openid, String cardnumber,String date) {
        return appointmeInfoMapper.selectByopenidOrCardnumber(openid, cardnumber,date);
    }

    @Override
    public AppointmeInfo findByid(int id) {
        return appointmeInfoMapper.findByid(id);
    }
}

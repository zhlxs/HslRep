package com.jrwp.wx.service;

import com.jrwp.wx.entity.SquenceHelper;
import com.jrwp.wx.entity.SquenceInfo;
import com.jrwp.wx.entity.Visitor;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SquenceInfoService {
    String getnextSquence(int isappointment, Date date, Integer result,Integer deptid);

    int insertSelective(SquenceInfo record);

    SquenceHelper getCallSquence(Integer nowtimequan, int isappointment, Integer isearly, Date date, int deptid);

    int updateStatusByPrimaryKey(int id,Integer winNumber, Integer status);

    int getLocalCount(Date date,Integer deptid,String businesstype);

    SquenceInfo isSignin(String openid, String cardnumber, Date date);

    Map<String, String> getfrontCount(Date date, SquenceInfo squenceInfo);
    List<Visitor> getVisitor( Date nowdate, Integer deptid,int status,Integer seq);
    List<Visitor> getCalledVisitor( Date nowdate,Integer deptid);
}

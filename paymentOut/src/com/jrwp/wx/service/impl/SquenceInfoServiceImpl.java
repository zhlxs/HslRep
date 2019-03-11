package com.jrwp.wx.service.impl;

import com.jrwp.wx.dao.SquenceInfoMapper;
import com.jrwp.wx.entity.SquenceHelper;
import com.jrwp.wx.entity.SquenceInfo;
import com.jrwp.wx.entity.Visitor;
import com.jrwp.wx.service.SquenceInfoService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SquenceInfoServiceImpl implements SquenceInfoService {
    @Resource
    private SquenceInfoMapper squenceInfoMapper;

    @Override
    public String getnextSquence(int isappointment, Date date, Integer isearly, Integer deptid) {
        String squence = squenceInfoMapper.getSquence(isappointment, date, isearly, deptid);
        System.out.println("目前最大squence是：" + squence);
        String head = "";
        if (isappointment == 0) {
            head = "B";
        } else {

            if (isearly == 1) {
                head = "C";
            } else {
                head = "A";
            }
        }
        int squ = 1;
        if (squence != null && !squence.equals("")) {
            squ = Integer.parseInt(squence.substring(1, 4)) + 1;
        }
        StringBuffer next = new StringBuffer(head);
        int i = String.valueOf(squ).length();
        for (int j = i; j < 3; j++) {
            next.append("0");
        }
        next.append(squ);
        return next.toString();
    }

    @Override
    public int insertSelective(SquenceInfo record) {
        return squenceInfoMapper.insertSelective(record);
    }

    @Override
    public SquenceHelper getCallSquence(Integer nowtimequan, int isappointment, Integer isearly, Date date, int deptid) {
        return squenceInfoMapper.getCallSquence(nowtimequan, isappointment, isearly, date, deptid);
    }

    @Override
    public int updateStatusByPrimaryKey(int id, Integer winNumber, Integer status) {
        return squenceInfoMapper.updateStatusByPrimaryKey(id, winNumber,  status);
    }

    @Override
    public int getLocalCount(Date date, Integer deptid,String businesstype) {
        return squenceInfoMapper.getLocalCount(date, deptid,businesstype);
    }

    @Override
    public SquenceInfo isSignin(String openid, String cardnumber, Date date) {

        SquenceInfo squenceInfo = squenceInfoMapper.selectByOpenid(openid, cardnumber, date);

        if (squenceInfo != null) {
            return squenceInfo;
        }
        return null;
    }

    @Override
    public Map<String, String> getfrontCount(Date date, SquenceInfo squenceInfo) {
        Map map = new HashMap<String, String>();
        try {
            List<SquenceInfo> list = squenceInfoMapper.getAllsequenceNow(date);
            Integer isearly = squenceInfo.getIsearly();
            Integer isappointment = squenceInfo.getIsappointment();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date thisdate = sf.parse(squenceInfo.getCreatetime().substring(0, squenceInfo.getCreatetime().length() - 1));
            if (isearly != null && isearly == 1) {
                //早到号
                map.put("count", "--");
                Calendar dateOne = Calendar.getInstance(), dateTwo = Calendar.getInstance();
                dateOne.setTime(new Date());    //设置为当前系统时间
                dateTwo.setTime(thisdate);          //设置为2015年1月15日
                long timeOne = dateOne.getTimeInMillis();
                long timeTwo = dateTwo.getTimeInMillis();
                long minute = ( timeOne - timeTwo ) / ( 1000 * 60 );//转化minute
                System.out.println("相隔" + minute + "分钟");
                map.put("time", minute);
            }
            if (isappointment == 1 && isearly != 1) {
                //预约准时号
                int count = 0;
                for (SquenceInfo info : list) {

                    if (info.getIsappointment() == 1 && info.getIsearly() != 1) {
                        Date date2 = sf.parse(info.getCreatetime());
                        if (date2.getTime() < thisdate.getTime()) {
                            count++;
                        }
                    }
                    map.put("count", count);
                    map.put("time", count * 3);
                }
            }
            if (isappointment == 0) {
                //预约准时号
                int count = 0;
                for (SquenceInfo info : list) {
                    if (info.getIsappointment() == 1 && info.getIsearly() != 1) {
                        count++;
                    } else if (info.getIsappointment() == 0) {
                        Date date2 = sf.parse(info.getCreatetime());
                        if (date2.getTime() < thisdate.getTime()) {
                            count++;
                        }
                    }
                    map.put("count", count);
                    map.put("time", count * 3);
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return map;
    }

    @Override
    public List<Visitor> getVisitor(Date nowdate, Integer deptid,int status,Integer seq) {
        return squenceInfoMapper.getVisitor(nowdate, deptid,status,seq);
    }

    @Override
    public List<Visitor> getCalledVisitor(Date nowdate, Integer deptid) {
        return squenceInfoMapper.getCalledVisitor(nowdate, deptid);
    }

}

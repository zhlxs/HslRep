package com.jrwp.wx.dao;

import com.jrwp.wx.entity.SquenceHelper;
import com.jrwp.wx.entity.SquenceInfo;
import com.jrwp.wx.entity.Visitor;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SquenceInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SQUENCE_INFO
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SQUENCE_INFO
     *
     * @mbggenerated
     */
    int insert(SquenceInfo record);

    String getSquence(@Param("isappointment") int isappointment, @Param("nowdate") Date date, @Param("isearly") Integer isearly,@Param("deptid") Integer dptid);

    SquenceHelper getCallSquence(@Param("nowtimequan") Integer nowtimequan, @Param("isappointment") int isappointment, @Param
            ("isearly") Integer isearly, @Param("nowdate") Date date, @Param("deptid") int deptid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SQUENCE_INFO
     *
     * @mbggenerated
     */
    int insertSelective(SquenceInfo record);
    List<Visitor> getVisitor(@Param("nowdate") Date nowdate,@Param("deptid") Integer deptid,@Param("status") int status,@Param("seq") Integer seq);
    List<Visitor> getCalledVisitor(@Param("nowdate") Date nowdate,@Param("deptid") Integer deptid);

    int getLocalCount(@Param("nowdate") Date date, @Param("deptid") Integer deptid,@Param("businesstype")String businesstype);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SQUENCE_INFO
     *
     * @mbggenerated
     */
    SquenceInfo selectByPrimaryKey(Integer id);

    SquenceInfo selectByOpenid(@Param("openid") String openid, @Param("cardnumber") String cardnumber, @Param("nowdate") Date date);

    List<SquenceInfo> getAllsequenceNow(@Param("nowdate") Date date);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SQUENCE_INFO
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SquenceInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SQUENCE_INFO
     *
     * @mbggenerated
     */
    int updateStatusByPrimaryKey(@Param("id") int id,@Param("winNumber") Integer winNumber,@Param("status") Integer status);
}
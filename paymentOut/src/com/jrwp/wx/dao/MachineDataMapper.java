package com.jrwp.wx.dao;

import com.jrwp.wx.entity.MachineData;

public interface MachineDataMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACHINEDATA
     *
     * @mbggenerated
     */
    int insert(MachineData record);

    int selectByBH(String bh);
    int updateByBH(MachineData machineData);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACHINEDATA
     *
     * @mbggenerated
     */
    int insertSelective(MachineData record);
}
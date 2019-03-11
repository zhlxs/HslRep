package com.jrwp.follow.dao;

import com.jrwp.follow.entity.PayConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeptPayConfigDao {

    PayConfig getdeptConfig(@Param("deptcode") String deptcode, @Param("type") Integer type);

    PayConfig getByAppidAndMchId(@Param("appid") String appid, @Param("mchid") String mchid);

    List<Integer> isHave(@Param("deptcode") String deptcode);
}

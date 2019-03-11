package com.jrwp.appr.dao;

import com.jrwp.appr.entity.AppraiseDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppraiseDetailDao {

    AppraiseDetail getObjectById(@Param("id") Long id);

    List<AppraiseDetail> list(@Param("appraisemodelid")Long appraisemodelid);
}

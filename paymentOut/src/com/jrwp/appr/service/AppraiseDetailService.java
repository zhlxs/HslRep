package com.jrwp.appr.service;

import com.jrwp.appr.entity.AppraiseDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppraiseDetailService {

	AppraiseDetail getObjectById(@Param("id") Long id);

	List<AppraiseDetail> list(@Param("appraisemodelid") Long appraisemodelid);
}

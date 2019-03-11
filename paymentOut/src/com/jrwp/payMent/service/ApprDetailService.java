package com.jrwp.payMent.service;

import com.jrwp.payMent.entity.AppraiseDetail;

import java.util.List;

public interface  ApprDetailService {
    List<AppraiseDetail> getByModelId(int id);
    List<AppraiseDetail> getDetailByModelId(int id);
    void insert(AppraiseDetail appraiseDetail);
    void insert1(AppraiseDetail appraiseDetail);
    int insertSelective(AppraiseDetail appraiseDetail);
    int deleteByModelId(Integer id);
    AppraiseDetail selectByPrimaryKey(Integer id);
    int deleteByPrimaryKey(Integer id);
    int insertIcon(AppraiseDetail appraiseDetail);
    int updateByPrimaryKeySelective(AppraiseDetail appraiseDetail);
    int updateByPrimaryKey(AppraiseDetail appraiseDetail);
}

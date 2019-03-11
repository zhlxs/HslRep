package com.jrwp.payMent.service;

import com.jrwp.payMent.entity.AppraiseModel;

import java.util.List;

public interface ApprModelService {
    List<AppraiseModel> list(String modelName);
    List<AppraiseModel> list1(String modelName);
    void save(AppraiseModel appraiseModel);
    AppraiseModel getById(Integer id);
    int delete(Integer id);
    int insert(AppraiseModel appraiseModel);
    int insertSelective(AppraiseModel appraiseModel);
    int insert1(AppraiseModel appraiseModel);
    int updateByPrimaryKey(AppraiseModel record);
    int updateByPrimaryKeySelective(AppraiseModel record);
}

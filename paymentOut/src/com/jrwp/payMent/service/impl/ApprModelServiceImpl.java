package com.jrwp.payMent.service.impl;

import com.jrwp.payMent.dao.AppraiseModelMapper;
import com.jrwp.payMent.entity.AppraiseModel;
import com.jrwp.payMent.service.ApprModelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApprModelServiceImpl implements ApprModelService {
    @Resource
    private AppraiseModelMapper appraiseModelMapper;

    @Override
    public List<AppraiseModel> list(String modelName) {
        return appraiseModelMapper.list(modelName);
    }

    @Override
    public void save(AppraiseModel appraiseModel) {
        if (appraiseModel.getId()==null){
            //添加
            appraiseModelMapper.insert(appraiseModel);
        }else {
            appraiseModelMapper.updateByPrimaryKey(appraiseModel);
        }
    }

    @Override
    public AppraiseModel getById(Integer id) {
        return appraiseModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(Integer id) {
        return appraiseModelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(AppraiseModel appraiseModel) {
        return appraiseModelMapper.insert(appraiseModel);
    }

    @Override
    public int updateByPrimaryKey(AppraiseModel record) {
        return appraiseModelMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<AppraiseModel> list1(String modelName) {
        return appraiseModelMapper.list1(modelName);
    }

    @Override
    public int insert1(AppraiseModel appraiseModel) {
        return appraiseModelMapper.insert1(appraiseModel);
    }

    @Override
    public int updateByPrimaryKeySelective(AppraiseModel record) {
        return appraiseModelMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insertSelective(AppraiseModel appraiseModel) {
        return appraiseModelMapper.insertSelective(appraiseModel);
    }
}

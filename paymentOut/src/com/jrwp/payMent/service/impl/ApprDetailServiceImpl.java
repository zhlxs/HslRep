package com.jrwp.payMent.service.impl;

import com.jrwp.payMent.dao.AppraiseDetailMapper;
import com.jrwp.payMent.entity.AppraiseDetail;
import com.jrwp.payMent.service.ApprDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class ApprDetailServiceImpl  implements ApprDetailService{
    @Resource
    private AppraiseDetailMapper appraiseDetailMapper ;

    @Override
    public List<AppraiseDetail> getByModelId(int id) {
        return appraiseDetailMapper.getByModelId(id);
    }

    @Override
    public void insert(AppraiseDetail appraiseDetail) {
        appraiseDetailMapper.insert(appraiseDetail);
    }

    @Override
    public int deleteByModelId(Integer id) {
        return appraiseDetailMapper.deleteByModelId(id);
    }

    @Override
    public AppraiseDetail selectByPrimaryKey(Integer id) {
        return appraiseDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return appraiseDetailMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertIcon(AppraiseDetail appraiseDetail) {
        return appraiseDetailMapper.insertIcon(appraiseDetail);
    }

    @Override
    public int updateByPrimaryKeySelective(AppraiseDetail appraiseDetail) {
        return appraiseDetailMapper.updateByPrimaryKeySelective(appraiseDetail);
    }

    @Override
    public void insert1(AppraiseDetail appraiseDetail) {
        appraiseDetailMapper.insert1(appraiseDetail);
    }

    @Override
    public int insertSelective(AppraiseDetail appraiseDetail) {
        return appraiseDetailMapper.insertSelective(appraiseDetail);
    }

    @Override
    public List<AppraiseDetail> getDetailByModelId(int id) {
        return appraiseDetailMapper.getDetailByModelId(id);
    }

    @Override
    public int updateByPrimaryKey(AppraiseDetail appraiseDetail) {
        return appraiseDetailMapper.updateByPrimaryKey(appraiseDetail);
    }
}

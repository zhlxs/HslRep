package com.jrwp.appr.service.impl;

import com.jrwp.appr.dao.AppraiseModelDao;
import com.jrwp.appr.entity.AppraiseModel;
import com.jrwp.appr.service.AppraiseModelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class AppraiseModelServiceImpl implements AppraiseModelService{

    @Resource
    private AppraiseModelDao appraiseModelDao;

    @Override
    public AppraiseModel getObjectById(Long id) {
        return appraiseModelDao.getObjectById(id);
    }
}

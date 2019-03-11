package com.jrwp.appr.service.impl;

import com.jrwp.appr.dao.AppraiseDetailDao;
import com.jrwp.appr.entity.AppraiseDetail;
import com.jrwp.appr.service.AppraiseDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppraiseDetailServiceImpl implements AppraiseDetailService{

    @Resource
    private AppraiseDetailDao appraiseDetailDao;

    @Override
    public AppraiseDetail getObjectById(Long id) {
        return appraiseDetailDao.getObjectById(id);
    }

    @Override
    public List<AppraiseDetail> list(Long appraisemodelid) {
        return appraiseDetailDao.list(appraisemodelid);
    }
}

package com.jrwp.appr.service.impl;

import com.jrwp.appr.dao.TestCountDao;
import com.jrwp.appr.entity.TestCount;
import com.jrwp.appr.service.TestCountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestCountServiceImpl implements TestCountService {

    @Resource
    private TestCountDao testCountDao;

    @Override
    public void insertSelective(TestCount count) {
        testCountDao.insertSelective(count);
    }
}

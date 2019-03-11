package com.jrwp.payMent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.payMent.dao.DeptServiceMapper;
import com.jrwp.payMent.entity.UserService;
import com.jrwp.payMent.service.DeptServiceBiz;
@Service
public class DeptServiceBizImpl  implements DeptServiceBiz{
    @Resource
    private DeptServiceMapper deptServiceMapper;
    @Override
    public List<UserService> getByUsCode(String uiCode) {
        return deptServiceMapper.getByUsCode(uiCode);
    }

    @Override
    public void save(UserService userService) {
        deptServiceMapper.insert(userService);
    }

    @Override
    public void updateisdel(String usCode) {
        deptServiceMapper.updateisdel(usCode);
    }
}

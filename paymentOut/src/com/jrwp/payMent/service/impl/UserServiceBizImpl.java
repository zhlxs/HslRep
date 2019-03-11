package com.jrwp.payMent.service.impl;

import com.jrwp.payMent.dao.UserServiceMapper;
import com.jrwp.payMent.entity.UserService;
import com.jrwp.payMent.service.UserServiceBiz;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class UserServiceBizImpl  implements UserServiceBiz{
    @Resource
    private UserServiceMapper userServiceMapper;
    @Override
    public List<UserService> getByUsCode(String uiCode) {
        return userServiceMapper.getByUsCode(uiCode);
    }

    @Override
    public void save(UserService userService) {
        userServiceMapper.insert(userService);
    }

    @Override
    public void deleteByUscode(String usCode) {
        userServiceMapper.deleteByPrimaryKey(usCode);
    }
}

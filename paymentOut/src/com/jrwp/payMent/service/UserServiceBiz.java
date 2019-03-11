package com.jrwp.payMent.service;

import com.jrwp.payMent.entity.UserService;

import java.util.List;

public interface UserServiceBiz {

    List<UserService> getByUsCode(String uiCode);

    void save(UserService userService);
    void deleteByUscode(String usCode);
}

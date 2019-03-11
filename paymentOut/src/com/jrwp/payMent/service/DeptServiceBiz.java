package com.jrwp.payMent.service;

import com.jrwp.payMent.entity.UserService;

import java.util.List;

public interface DeptServiceBiz {

    List<UserService> getByUsCode(String uiCode);

    void save(UserService userService);
    void updateisdel(String usCode);
}

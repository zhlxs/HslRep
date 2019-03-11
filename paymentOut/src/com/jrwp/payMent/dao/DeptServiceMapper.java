package com.jrwp.payMent.dao;

import com.jrwp.payMent.entity.UserService;

import java.util.List;

public interface DeptServiceMapper {

    List<UserService> getByUsCode(String uiCode);
   
    
    void updateisdel(String usCode);

    int insert(UserService record);

}
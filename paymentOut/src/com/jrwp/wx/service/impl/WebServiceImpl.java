package com.jrwp.wx.service.impl;

import com.jrwp.core.dao.IUserDao;
import com.jrwp.core.entity.Core_User;
import com.jrwp.core.service.IUserService;
import com.jrwp.core.service.impl.UserServiceImpl;
import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.wx.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@javax.jws.WebService(endpointInterface = "com.jrwp.wx.service.WebService")
public class WebServiceImpl implements WebService {

    private IUserDao userDao;

    public WebServiceImpl() {
        ApplicationContext ac = new FileSystemXmlApplicationContext(String.valueOf(this.getClass().getClassLoader().getResource
                ("applicationContext.xml")));
        System.out.println(String.valueOf(this.getClass().getClassLoader().getResource("applicationContext.xml")));
        this.userDao = (IUserDao) ac.getBean("userDao");
    }

    @Override
    public String sayYouName(String name) {
        return "你好！你的名字是：" + name;
    }

    @Override
    public String getUserByid(Integer id) {
        System.out.println("进入getUser方法 id是" + id);
        Core_User user = userDao.getObjectById(Long.valueOf(id));
        return JacksonUtil.toJson(user);
    }
}

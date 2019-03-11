package com.jrwp.wx.service;

import javax.jws.WebParam;

@javax.jws.WebService
public interface WebService {
    public String sayYouName(@WebParam(name="name") String name);
    public String getUserByid(@WebParam(name="id") Integer id);
}

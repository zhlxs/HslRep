package com.jrwp.follow.dao;

import com.jrwp.follow.entity.AccessToken;

public interface AccessTokenDao {

    AccessToken getToken(String appid);
    void save(AccessToken accessToken);
    void update(AccessToken accesstoken);

}

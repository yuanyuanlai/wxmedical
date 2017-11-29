package com.wxmedical.mapper;

import com.wxmedical.model.wx.AccessToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccessTokenMapper {

    public void insertAccessToken(AccessToken accessToken);


    public AccessToken findById(String id);
}

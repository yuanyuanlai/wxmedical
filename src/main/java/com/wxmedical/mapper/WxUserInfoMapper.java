package com.wxmedical.mapper;


import com.wxmedical.model.wx.WeixinUserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WxUserInfoMapper {


    void insertNewWxUser(WeixinUserInfo userInfo);

    int updateWxUser(WeixinUserInfo userInfo);

    WeixinUserInfo findWxUserInfoByOpenId(String openId);
}

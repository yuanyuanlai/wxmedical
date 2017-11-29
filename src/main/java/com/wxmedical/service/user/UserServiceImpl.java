package com.wxmedical.service.user;


import com.wxmedical.constant.IConstant;
import com.wxmedical.mapper.WxUserInfoMapper;
import com.wxmedical.model.wx.WeixinUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    WxUserInfoMapper userInfoMapper;


    /**
    *
     * 插入微信客户表
     * *
     */
    public String insertWxUserInfo(String openId){
        WeixinUserInfo userInfo =new WeixinUserInfo();

        userInfo.setOpenId(openId);
        userInfo.setSubscribe(IConstant.subscribe_state);
          userInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
        userInfoMapper.insertNewWxUser(userInfo);
        String respContent = "谢谢您的关注！";
        return respContent;
    }


}

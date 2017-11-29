package com.wxmedical.thread;

import com.wxmedical.model.wx.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 用单线程去获取access_token
 */
public class AccessTokenThread  implements Runnable {
    private static Logger log = LoggerFactory.getLogger(AccessTokenThread.class);
    public static AccessToken accessToken = null;

    public AccessTokenThread(){

    }


    @Override
    public void run() {

    }
}
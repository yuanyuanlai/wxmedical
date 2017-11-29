package com.wxmedical.controller;


import com.wxmedical.service.core.CoreService;
import com.wxmedical.util.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("SpringJavaAutowiringInspection")
@RestController
@RequestMapping("")
public class CoreRequestController {

    private static Logger logger = LoggerFactory.getLogger(CoreRequestController.class);
    @Autowired
    private CoreService coreService;


    //验证是否来自微信服务器的消息
    @RequestMapping(value = "/wxcheck",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String checkSignature(@RequestParam(name = "signature" ,required = false) String signature  ,
                                 @RequestParam(name = "timestamp" ,required = false) String timestamp  ,
                                 @RequestParam(name = "nonce",required = false) String  nonce ,
                                 @RequestParam(name = "echostr",required = false) String  echostr){
        logger.info("----〉〉〉验证签名"+signature +"timestamp"+ nonce);
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {

             return echostr;
        }
        return "";
    }

    @RequestMapping(value = "/wxcheck",method = RequestMethod.POST, produces = "application/xml; charset=UTF-8")
    public  String post(HttpServletRequest req){
        // 调用核心业务类接收消息、处理消息跟推送消息
        // 消息的接收、处理、响应
        logger.info("----〉〉〉处理请求"+req);
        // 调用核心业务类接收消息、处理消息
        String respMessage = coreService.processRequest(req);
        return respMessage;
    }
}

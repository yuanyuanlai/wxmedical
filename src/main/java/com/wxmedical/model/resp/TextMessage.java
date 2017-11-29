package com.wxmedical.model.resp;

/**
 * create by laiyuanyuan
 * 17/11/22 下午8:06
 * function：
 * CSII
 */
public class TextMessage extends BaseMessage {
    // 回复的消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
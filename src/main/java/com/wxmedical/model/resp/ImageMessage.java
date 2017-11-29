package com.wxmedical.model.resp;

/**
 * create by laiyuanyuan
 * 图文消息
 * 17/11/22 下午8:24
 * function：
 * CSII
 */
public class ImageMessage extends BaseMessage{

    // 图片链接
    private String PicUrl;
    private String MediaId;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

}

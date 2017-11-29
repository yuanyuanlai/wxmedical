package com.wxmedical.constant;

public interface IConstant {

    String serverUrl="http://tansijun.oicp.net";

    String serverContext = serverUrl+"/wxmecical";
    String tokenValue ="lyy921";
    // 测试号
    String appId="wx08fb9a7eb205c8e0";
    String appSecret ="5c1e6899a85d3288108146dd8bd12a13";

    // accesstoken url
    String accessTokenUrl ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+IConstant.appId+"&secret="+IConstant.appSecret;


    // 获取用户基本信息（GET）限500000（次/天）
    String access_user_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";

    // 发送模板消息
    String message_send_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    //通过TOKEN换取永久二维码换取TICKET
    String qcode_url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";

    //通过TICKET换取二维码图片
    String qcode_img_url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";

    // Button key
    String Songer_Intro ="V1001_TODAY_SINGER";
    // Button Key
    String Day_Songe="V1001_TODAY_MUSIC";
    int subscribe_state =1; //已关注
    int unsubscribe_state =0; //取消关注
}

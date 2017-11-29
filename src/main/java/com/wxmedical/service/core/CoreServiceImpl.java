package com.wxmedical.service.core;

import com.wxmedical.constant.IConstant;
import com.wxmedical.model.Article;
import com.wxmedical.model.resp.NewsMessage;
import com.wxmedical.model.resp.TextMessage;
import com.wxmedical.service.replay.ReplyTextService;
import com.wxmedical.service.token.TokenService;
import com.wxmedical.service.user.UserService;
import com.wxmedical.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 核心服务类
 */
@Service("coreService")
public class CoreServiceImpl implements  CoreService{

    private static Logger logger = LoggerFactory.getLogger(CoreServiceImpl.class);
    @Autowired
    private ReplyTextService replyTextService;


    @Autowired
    private TokenService tokenService; //token服务service

    @Autowired
    private UserService userService; //微信用户表


    /**
     * 处理微信发来的请求（包括事件的推送）
     *
     * @param request
     * @return
     */
    @Override
    public String processRequest(HttpServletRequest request) {
        logger.info("处理腾讯请求");
        String respXml = "请求处理异常，请稍候尝试！";
        // 默认返回的文本消息内容
        String respContent = "未知的消息类型！";
        try {
            // 默认返回的文本消息内容
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) { //事件类型的推送
                // 事件类型
                String eventType = requestMap.get("Event");
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { //往关注表中插入关注数据。
                    userService.insertWxUserInfo(requestMap.get("FromUserName"));
                    logger.info("用户："+requestMap.get("FromUserName")+"关注了公众号");
                }else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)){ //取消关注
                    logger.info("用户："+requestMap.get("FromUserName")+"取消关注公众号");
                }else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // TODO 处理菜单点击事件
                    if(IConstant.Day_Songe.equals(requestMap.get("EventKey"))){

                        // 创建图文消息
                        NewsMessage newsMessage = new NewsMessage();
                        newsMessage.setToUserName(fromUserName);
                        newsMessage.setFromUserName(toUserName);
                        newsMessage.setCreateTime(new Date().getTime());
                        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                        newsMessage.setFuncFlag(0);

                        List<Article> articleList = new ArrayList<Article>();
                        Article article = new Article();
                        article.setTitle("今日网易云推荐音乐");
                        article.setDescription("搜集每日网易云推荐Top10佳音乐，希望会喜欢！");
                        article.setPicUrl(IConstant.serverUrl+"/img/song.jpg");
                        article.setUrl("http://music.163.com/#/playlist?id=722172893");
                        articleList.add(article);
                        // 设置图文消息个数
                        newsMessage.setArticleCount(articleList.size());
                        // 设置图文消息包含的图文集合
                        newsMessage.setArticles(articleList);
                        // 将图文消息对象转换成xml字符串
                        respXml = MessageUtil.messageToXml(newsMessage);


                    }else if(IConstant.Songer_Intro.equals(requestMap.get("EventKey"))){

                        // 创建图文消息
                        NewsMessage newsMessage = new NewsMessage();
                        newsMessage.setToUserName(fromUserName);
                        newsMessage.setFromUserName(toUserName);
                        newsMessage.setCreateTime(new Date().getTime());
                        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                        newsMessage.setFuncFlag(0);

                        List<Article> articleList = new ArrayList<Article>();
                        Article article = new Article();
                        article.setTitle("今日网易云推荐音乐");
                        article.setDescription("搜集每日网易云推荐Top10佳音乐，希望会喜欢！");
                        article.setPicUrl(IConstant.serverContext+"/img/song.jpg");
                        article.setUrl(IConstant.serverContext+"/getSong.do");
                        articleList.add(article);
                        // 设置图文消息个数
                        newsMessage.setArticleCount(articleList.size());
                        // 设置图文消息包含的图文集合
                        newsMessage.setArticles(articleList);
                        // 将图文消息对象转换成xml字符串
                        respXml = MessageUtil.messageToXml(newsMessage);
                    }else{

                        respContent = "未知菜单指令，请联系管理员！";
                        textMessage.setContent(respContent);
                        // 将文本消息对象转换成xml
                        respXml = MessageUtil.messageToXml(textMessage);
                    }

                }
            }else  if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) { //文本消息，可做关键字回复 TODO
                respContent = "您发送的是文本消息！";

                String content = requestMap.get("Content");

                respContent =content;
                // 设置文本消息的内容
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml
                respXml = MessageUtil.messageToXml(textMessage);
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {// 图片消息
                respContent = "您发送的是图片消息！";
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml
                respXml = MessageUtil.messageToXml(textMessage);
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {  // 语音消息
                respContent = "您发送的是语音消息！";
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml
                respXml = MessageUtil.messageToXml(textMessage);
            }  else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) { // 视频消息
                respContent = "您发送的是视频消息！";
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml
                respXml = MessageUtil.messageToXml(textMessage);
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)) {// 视频消息
                respContent = "您发送的是小视频消息！";
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml
                respXml = MessageUtil.messageToXml(textMessage);
            }  else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {  // 地理位置消息
                respContent = "您发送的是地理位置消息！";
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml
                respXml = MessageUtil.messageToXml(textMessage);
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {  // 链接消息
                respContent = "您发送的是链接消息！";
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml
                respXml = MessageUtil.messageToXml(textMessage);
            }  // 自定义菜单





        } catch (Exception e) {
            e.printStackTrace();
        }
        return  respXml;
    }
        /**
     * 判断是否是QQ表情
     *
     * @param content
     * @return
     */
    public static boolean isQqFace(String content) {
        boolean result = false;

        // 判断QQ表情的正则表达式
        String qqfaceRegex = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";
        Pattern p = Pattern.compile(qqfaceRegex);
        Matcher m = p.matcher(content);
        if (m.matches()) {
            result = true;
        }
        return result;
    }
}

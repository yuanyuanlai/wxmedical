package com.wxmedical.service.token;


import com.wxmedical.constant.IConstant;
import com.wxmedical.mapper.AccessTokenMapper;
import com.wxmedical.model.wx.AccessToken;
import com.wxmedical.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {


    @Autowired
    private AccessTokenMapper accessTokenMapper;


    @Override
    public AccessToken getAcceToken() {

        AccessToken accessToken = accessTokenMapper.findById(IConstant.appId);
        if(null==accessToken||"".equals(accessToken.getAccessToken())||null ==accessToken.getAccessToken()){ //未获取
            accessToken = CommonUtil.getToken(IConstant.appId,IConstant.appSecret);
            accessTokenMapper.insertAccessToken(accessToken);
            return  accessToken;
        }else if(isExpired(accessToken)){ //过期重新获取
            accessToken = CommonUtil.getToken(IConstant.appId,IConstant.appSecret);
            accessTokenMapper.insertAccessToken(accessToken);
            return  accessToken;
        }else{ //未过期，返回数据库中的配置
            return  accessToken;
        }

    }


    /**
     * 检查是否过期
     * <br>比腾讯的过期时间早10分钟（60*10*1000=600000毫秒），
     * 腾讯的过期时间是7200秒（两个小时）
     * @param accessToken
     * @return
     */
    private boolean isExpired(AccessToken accessToken){
        if(accessToken.getCreateTime() == null|| accessToken.getExpiresIn()== 0 ) {
            return true;
        }
        return (System.currentTimeMillis()- accessToken.getCreateTime().getTime()) > (accessToken.getExpiresIn() * 1000 - 600 * 1000);
    }
}

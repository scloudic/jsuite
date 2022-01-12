package com.scloudic.jsuite.weixin.core.service;

import com.scloudic.jsuite.weixin.core.model.AccessToken;

public interface WeiXinService {
    /**
     * 获取全局唯一后台接口调用凭据
     * @param appId  微信APPID
     * @param secret 微信密钥
     * @param cache  缓存接口
     * @return AccessToken
     */
    public AccessToken getAccessToken(String appId, String secret, WeiXinAccessTokenCache cache);
}

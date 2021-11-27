package com.scloudic.jsuite.weixin.core.service;

import com.scloudic.jsuite.weixin.core.model.AccessToken;

/**
 * 微信token缓存机制
 */
public interface WeiXinAccessTokenCache {
    /**
     * 从缓存中获取token
     *
     * @return AccessToken
     */
    public AccessToken getAccessToken();

    /**
     * 设置token缓存
     *
     * @param accessToken AccessToken
     */
    public void setAccessToken(AccessToken accessToken);

    public boolean lock(String appId);

    public void unLock(String appId);

}

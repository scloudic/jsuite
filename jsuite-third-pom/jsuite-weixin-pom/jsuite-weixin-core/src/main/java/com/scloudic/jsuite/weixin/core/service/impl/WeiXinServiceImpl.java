package com.scloudic.jsuite.weixin.core.service.impl;

import com.scloudic.jsuite.weixin.core.service.WeiXinAccessTokenCache;
import com.scloudic.jsuite.weixin.core.service.WeiXinService;
import com.scloudic.jsuite.weixin.core.model.AccessToken;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.httpclient.HttpClient;
import com.scloudic.rabbitframework.core.httpclient.ResponseBody;
import com.scloudic.rabbitframework.core.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public abstract class WeiXinServiceImpl implements WeiXinService {
    private static final Logger logger = LoggerFactory.getLogger(WeiXinServiceImpl.class);

    @Override
    public AccessToken getAccessToken(String appId, String secret, WeiXinAccessTokenCache cache) {
        if (cache != null) {
            AccessToken accessToken = cache.getAccessToken();
            if (accessToken != null) {
                return accessToken;
            }
        }
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
        HttpClient httpClient = HttpClient.getInstance();
        ResponseBody r = httpClient.get(String.format(requestUrl, appId, secret));
        String str = r.string();
        logger.debug("获取微信accessToken:" + str);
        Map<String, Object> map = JsonUtils.getObject(str, Map.class);
        Object obj = map.get("errcode");
        if (obj != null) {
            String msg = "获取accessToken失败,errorCode:" + obj.toString() + ",errmsg:" + map.get("errmsg");
            logger.error(msg);
            throw new BizException(msg);
        }
        AccessToken accessToken = JsonUtils.getObject(str, AccessToken.class);
        if (cache != null) {
            cache.setAccessToken(accessToken);
        }
        return accessToken;
    }
}

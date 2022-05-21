package com.scloudic.jsuite.weixin.mini.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.scloudic.jsuite.weixin.core.service.impl.WeiXinServiceImpl;
import com.scloudic.jsuite.weixin.mini.model.MiniLoginInfo;
import com.scloudic.jsuite.weixin.mini.model.PhoneInfo;
import com.scloudic.jsuite.weixin.mini.service.WeiXinMiniService;
import com.scloudic.rabbitframework.core.httpclient.HttpClientUtils;
import com.scloudic.rabbitframework.core.httpclient.RequestParams;
import com.scloudic.rabbitframework.core.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("weiXinMiniService")
public class WeiXinMpServiceImpl extends WeiXinServiceImpl
        implements WeiXinMiniService {
    private static final Logger logger = LoggerFactory.getLogger(WeiXinMiniService.class);

    @Override
    public MiniLoginInfo login(String appId, String secret, String jsCode) {
        try {
            RequestParams requestParams = new RequestParams();
            requestParams.put("appid", appId);
            requestParams.put("secret", secret);
            requestParams.put("js_code", jsCode);
            requestParams.put("grant_type", "authorization_code");
            String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
            String result = HttpClientUtils.get(requestUrl, requestParams).string();
            MiniLoginInfo miniLoginInfo = JSONObject.parseObject(result, MiniLoginInfo.class);
            if (miniLoginInfo.getErrcode().intValue() == 0) {
                return miniLoginInfo;
            }
            throw new RuntimeException("微信小程序登录失败,errmsg:" + miniLoginInfo.getErrmsg());
        } catch (Exception e) {
            logger.error("微信小程序登录失败:" + e.getMessage(), e);
            throw new RuntimeException("微信小程序登录失败");
        }
    }

    @Override
    public PhoneInfo getPhoneNumber(String code, String accessToken) {
        try {
            String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber";
            Map<String, String> params = new HashMap<>();
            params.put("code", code);
            params.put("access_token", accessToken);
            String resultStr = HttpClientUtils.post(url, JsonUtils.toJson(params), null).string();
            JSONObject jsonObject = JsonUtils.getObject(resultStr, JSONObject.class);
            int errCode = jsonObject.getIntValue("errcode");
            String errMsg = jsonObject.getString("errmsg");
            if (errCode != 0) {
                throw new RuntimeException("微信小程序手机号获取失败,errMsg:" + errMsg);
            }
            PhoneInfo phoneInfo = JsonUtils.getObject(jsonObject.getJSONObject("phone_info").toJSONString(), PhoneInfo.class);
            return phoneInfo;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("微信小程序手机号获取失败," + e.getMessage());
        }
    }
}
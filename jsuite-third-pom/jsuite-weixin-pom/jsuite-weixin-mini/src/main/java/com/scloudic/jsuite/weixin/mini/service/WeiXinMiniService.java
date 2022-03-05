package com.scloudic.jsuite.weixin.mini.service;

import com.scloudic.jsuite.weixin.core.service.WeiXinService;
import com.scloudic.jsuite.weixin.mini.model.MiniLoginInfo;
import com.scloudic.jsuite.weixin.mini.model.PhoneInfo;

public interface WeiXinMiniService extends WeiXinService {
    /**
     * 小程序登录
     *
     * @param appId  小程序 appId
     * @param secret 小程序 appSecret
     * @param jsCode 登录时获取的 code
     * @return MiniLoginInfo
     */
    public MiniLoginInfo login(String appId, String secret, String jsCode);

    /**
     * 获取手机号
     *
     * @param code
     * @return
     */
    public PhoneInfo getPhoneNumber(String code, String accessToken);
}
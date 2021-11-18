package com.scloudic.jsuite.weixin.fficialaccount.service;

import com.scloudic.jsuite.weixin.core.service.WeiXinService;
import com.scloudic.jsuite.weixin.fficialaccount.model.OfficialAccountUser;
import com.scloudic.jsuite.weixin.fficialaccount.model.OfficialAccountUserDetail;

public interface OfficialAccountService extends WeiXinService {
    public String url = "https://api.weixin.qq.com/cgi-bin/";

    /**
     * 获取用户列表
     *
     * @param accessToken token
     * @param nextOpenid  下一个获取的openid,可以为空
     * @return
     */
    public OfficialAccountUser getUsers(String accessToken, String nextOpenid);

    /**
     * 获取用户基本信息
     *
     * @param accessToken 调用接口凭证
     * @param openid      普通用户的标识，对当前公众号唯一
     * @param lang      返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
     */
    public OfficialAccountUserDetail getUserInfo(String accessToken, String openid, String lang);

}

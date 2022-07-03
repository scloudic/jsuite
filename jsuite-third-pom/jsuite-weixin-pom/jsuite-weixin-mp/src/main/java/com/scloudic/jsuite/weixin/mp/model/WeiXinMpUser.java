package com.scloudic.jsuite.weixin.mp.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信公众号用户信息
 */
@Setter
@Getter
public class WeiXinMpUser {
    /*关注该公众账号的总用户数*/
    private Long total;
    /*拉取的OPENID个数，最大值为10000*/
    private Long count;
    private WeiXinMpUserData data;
}

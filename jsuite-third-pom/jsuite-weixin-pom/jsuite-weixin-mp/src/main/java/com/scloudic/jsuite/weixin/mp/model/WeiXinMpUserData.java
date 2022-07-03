package com.scloudic.jsuite.weixin.mp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 微信公众号用户信息
 */
@Setter
@Getter
public class WeiXinMpUserData {
    private List<String> openid;
    private String next_openid;
}

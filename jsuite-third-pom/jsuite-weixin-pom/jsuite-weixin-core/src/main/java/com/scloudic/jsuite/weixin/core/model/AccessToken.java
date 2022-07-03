package com.scloudic.jsuite.weixin.core.model;


import lombok.Getter;
import lombok.Setter;

/**
 * 通过微信获取的token
 */
@Getter
@Setter
public class AccessToken {
    /**
     * 可访问的token
     */
    private String access_token;
    /**
     * 过期时间，单位：秒
     */
    private Long expires_in;
}

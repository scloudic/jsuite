package com.scloudic.jsuite.weixin.core.model;

/**
 * 通过微信获取的token
 */
public class AccessToken {
    /**
     * 可访问的token
     */
    private String access_token;
    /**
     * 过期时间，单位：秒
     */
    private Long expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }
}

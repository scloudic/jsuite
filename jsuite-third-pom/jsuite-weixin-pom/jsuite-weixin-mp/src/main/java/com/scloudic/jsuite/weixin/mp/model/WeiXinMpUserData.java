package com.scloudic.jsuite.weixin.mp.model;

import java.util.List;

/**
 * 微信公众号用户信息
 */
public class WeiXinMpUserData {
    private List<String> openid;
    private String next_openid;

    public List<String> getOpenid() {
        return openid;
    }

    public void setOpenid(List<String> openid) {
        this.openid = openid;
    }

    public String getNext_openid() {
        return next_openid;
    }

    public void setNext_openid(String next_openid) {
        this.next_openid = next_openid;
    }
}

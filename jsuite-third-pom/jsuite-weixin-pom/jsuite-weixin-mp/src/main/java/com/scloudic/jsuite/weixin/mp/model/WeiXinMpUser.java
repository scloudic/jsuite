package com.scloudic.jsuite.weixin.mp.model;

/**
 * 微信公众号用户信息
 */
public class WeiXinMpUser {
    /*关注该公众账号的总用户数*/
    private Long total;
    /*拉取的OPENID个数，最大值为10000*/
    private Long count;
    private WeiXinMpUserData data;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public WeiXinMpUserData getData() {
        return data;
    }

    public void setData(WeiXinMpUserData data) {
        this.data = data;
    }
}

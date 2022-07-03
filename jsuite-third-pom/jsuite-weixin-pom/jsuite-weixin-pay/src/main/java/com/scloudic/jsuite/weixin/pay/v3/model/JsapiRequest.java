package com.scloudic.jsuite.weixin.pay.v3.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JsapiRequest {
    /**
     * 通知地址
     */
    private String notifyUrl;
    /**
     * 支付金额(单位:分)
     */
    private Integer total;
    /**
     * openId
     */
    private String openid;
    /**
     * 标题
     */
    private String subject;
    /**
     * 商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一
     * 示例值：1217752501201407033233368018
     */
    private String outTradeNo;
    /**
     * 用户支付ip地址
     */
    private String payerClientIp;
}

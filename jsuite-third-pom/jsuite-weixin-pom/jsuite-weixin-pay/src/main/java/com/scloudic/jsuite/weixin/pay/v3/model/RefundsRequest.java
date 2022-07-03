package com.scloudic.jsuite.weixin.pay.v3.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefundsRequest {
    /**
     * 原支付交易对应的微信订单号与outTradeNo二选一
     */
    private String transactionId;
    /**
     * 原支付交易对应的商户订单号与transactionId二选一
     */
    private String outTradeNo;
    /**
     * 商户退款单号 必填
     */
    private String outRefundNo;
    /**
     * 退款金额 必填
     */
    private String refundAmount;
    /**
     * 原退款金额 必填
     */
    private String srcRefundAmount;
    /**
     * 货币类型 默认：CNY
     */
    private String currency = "CNY";
    /**
     * 回调地址
     */
    private String notifyUrl;

}

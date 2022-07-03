package com.scloudic.jsuite.weixin.pay.v3.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShareUnfreezeRequest {
    /**
     * 微信订单号
     * 微信支付订单号
     */
    private String transaction_id;
    /**
     * 商户分账单号
     * 商户系统内部的分账单号，在商户系统内部唯一，同一分账单号多次请求等同一次
     */
    private String out_order_no;
    /**
     * 分账描述
     * 分账的原因描述，分账账单中需要体现
     */
    private String description;
}

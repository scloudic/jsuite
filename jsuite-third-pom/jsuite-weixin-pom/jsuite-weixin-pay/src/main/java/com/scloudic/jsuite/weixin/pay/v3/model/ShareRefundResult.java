package com.scloudic.jsuite.weixin.pay.v3.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShareRefundResult {
    private String order_id;
    private String out_order_no;
    private String out_return_no;
    /**
     * 微信回退单号,微信分账回退单号，微信支付系统返回的唯一标识
     */
    private String return_id;
    private String return_mchid;
    private Long amount;
    private String description;
    private String result;
    private String fail_reason;
    private String create_time;
    private String finish_time;
}

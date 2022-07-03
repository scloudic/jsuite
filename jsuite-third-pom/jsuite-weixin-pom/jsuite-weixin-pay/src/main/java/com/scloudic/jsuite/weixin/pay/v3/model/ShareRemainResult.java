package com.scloudic.jsuite.weixin.pay.v3.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShareRemainResult {
    /**
     * 微信订单号
     */
    private String transaction_id;
    /**
     * 订单剩余待分金额，整数，单元为分
     */
    private String unsplit_amount;
}

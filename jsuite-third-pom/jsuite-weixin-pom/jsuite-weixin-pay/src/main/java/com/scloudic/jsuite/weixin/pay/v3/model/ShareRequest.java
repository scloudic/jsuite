package com.scloudic.jsuite.weixin.pay.v3.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 请求分账参数
 */
@Setter
@Getter
public class ShareRequest {
    /**
     * 应用ID,微信分配的商户appid
     */
    private String appid;
    /**
     * 微信订单号
     */
    private String transaction_id;
    /**
     * 商户分账单号
     */
    private String out_order_no;

    private boolean unfreeze_unsplit;
    /**
     * 请求分账接收方列表
     */
    private List<ShareRevRequest> receivers;
}

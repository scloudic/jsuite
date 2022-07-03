package com.scloudic.jsuite.weixin.pay.v3.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 分账回退请求
 */
@Setter
@Getter
public class ShareRefundRequest {
    /**
     * 微信分账单号,
     * 微信分账单号，微信支付系统返回的唯一标识。
     */
    private String order_id;
    /**
     * 商户分账单号
     * 原发起分账请求时使用的商户系统内部的分账单号。微信分账单号与商户分账单号二选一填写
     */
    private String out_order_no;
    /**
     * 商户回退单号
     * 此回退单号是商户在自己后台生成的一个新的回退单号，在商户后台唯一
     */
    private String out_return_no;
    /**
     * 回退商户号
     * 账接口中的分账接收方商户号
     */
    private String return_mchid;
    /**
     * 回退金额
     * 需要从分账接收方回退的金额，单位为分，只能为整数，不能超过原始分账单分出给该接收方的金额
     */
    private Long amount;
    /**
     * 回退描述
     */
    private String description;
}

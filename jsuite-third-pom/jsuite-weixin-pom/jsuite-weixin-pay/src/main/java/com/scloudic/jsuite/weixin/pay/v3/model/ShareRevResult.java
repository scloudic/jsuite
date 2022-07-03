package com.scloudic.jsuite.weixin.pay.v3.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShareRevResult {
    /**
     * 单位分
     */
    private Long amount;
    private String description;
    /**
     * 接收方类型
     * 1、MERCHANT_ID：商户号
     * 2、PERSONAL_OPENID：个人openid（由父商户APPID转换得到）
     * 示例值：MERCHANT_ID
     */
    private String type;
    /**
     * 接收方账号
     * 1、分账接收方类型为MERCHANT_ID时，分账接收方账号为商户号
     * 2、分账接收方类型为PERSONAL_OPENID时，分账接收方账号为个人openid
     */
    private String account;
    /**
     * 分账结果
     */
    private String result;
    /**
     * 失败原因
     */
    private String fail_reason;
    /**
     * 分账创建时间
     */
    private String create_time;
    /**
     * 分账完成时间
     */
    private String finish_time;
    /**
     * 分账明细单号
     */
    private String detail_id;
}


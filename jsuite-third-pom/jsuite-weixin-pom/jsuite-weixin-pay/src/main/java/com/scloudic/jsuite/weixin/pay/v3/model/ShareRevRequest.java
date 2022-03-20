package com.scloudic.jsuite.weixin.pay.v3.model;

/**
 * 分账接收方
 */
public class ShareRevRequest {
    /**
     * 1、MERCHANT_ID：商户号
     * 2、PERSONAL_OPENID：个人openid（由父商户APPID转换得到）
     * 示例值：MERCHANT_ID
     */
    private String type;
    /**
     * 分账接收方账号：
     * 1、类型是MERCHANT_ID时，是商户号（mch_id或者sub_mch_id）
     * 2、类型是PERSONAL_OPENID时，是个人openid
     * openid获取方法
     * 示例值：86693852
     */
    private String account;
    /**
     * 可选项，在接收方类型为个人的时可选填，若有值，会检查与 name 是否实名匹配，不匹配会拒绝分账请求
     * 1、分账接收方类型是PERSONAL_OPENID，是个人姓名的密文（选传，传则校验） 此字段的加密方法详见：敏感信息加密说明
     * 2、使用微信支付平台证书中的公钥
     * 3、使用RSAES-OAEP算法进行加密
     * 4、将请求中HTTP头部的Wechatpay-Serial设置为证书序列号
     * 示例值：hu89ohu89ohu89o
     */
    private String name;
    /**
     * 分账金额,单位分
     */
    private Long amount;
    /**
     * 分账的原因描述，分账账单中需要体现
     */
    private String description;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

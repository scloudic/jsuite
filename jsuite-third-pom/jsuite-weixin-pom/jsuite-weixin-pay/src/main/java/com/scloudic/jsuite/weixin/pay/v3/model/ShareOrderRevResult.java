package com.scloudic.jsuite.weixin.pay.v3.model;

public class ShareOrderRevResult {
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFail_reason() {
        return fail_reason;
    }

    public void setFail_reason(String fail_reason) {
        this.fail_reason = fail_reason;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(String detail_id) {
        this.detail_id = detail_id;
    }
}


package com.scloudic.jsuite.weixin.pay.v3.model;

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

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getSrcRefundAmount() {
        return srcRefundAmount;
    }

    public void setSrcRefundAmount(String srcRefundAmount) {
        this.srcRefundAmount = srcRefundAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

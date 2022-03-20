package com.scloudic.jsuite.weixin.pay.v3.model;

public class ShareSearchRequest {
    private String transactionId;
    private String outOrderNo;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
    }
}

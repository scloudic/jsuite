package com.scloudic.jsuite.weixin.pay.v3.model;

public class ShareRemainResult {
    /**
     * 微信订单号
     */
    private String transaction_id;
    /**
     * 订单剩余待分金额，整数，单元为分
     */
    private String unsplit_amount;

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getUnsplit_amount() {
        return unsplit_amount;
    }

    public void setUnsplit_amount(String unsplit_amount) {
        this.unsplit_amount = unsplit_amount;
    }
}

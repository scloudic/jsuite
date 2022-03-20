package com.scloudic.jsuite.weixin.pay.v3.model;

public class ShareSearchRefundRequest {
    /**
     * 商户回退单号	调用回退接口提供的商户系统内部的回退单号
     */
    private String outReturnNo;
    /**
     * 商户分账单号
     * 原发起分账请求时使用的商户系统内部的分账单号
     */
    private String outOrderNo;

    public String getOutReturnNo() {
        return outReturnNo;
    }

    public void setOutReturnNo(String outReturnNo) {
        this.outReturnNo = outReturnNo;
    }

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
    }
}

package com.scloudic.jsuite.weixin.pay.v3.model;

import java.util.List;

/**
 * 请求分账参数
 */
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

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_order_no() {
        return out_order_no;
    }

    public void setOut_order_no(String out_order_no) {
        this.out_order_no = out_order_no;
    }

    public boolean isUnfreeze_unsplit() {
        return unfreeze_unsplit;
    }

    public void setUnfreeze_unsplit(boolean unfreeze_unsplit) {
        this.unfreeze_unsplit = unfreeze_unsplit;
    }

    public List<ShareRevRequest> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<ShareRevRequest> receivers) {
        this.receivers = receivers;
    }
}

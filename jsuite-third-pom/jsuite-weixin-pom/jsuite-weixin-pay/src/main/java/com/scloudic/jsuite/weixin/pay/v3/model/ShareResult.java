package com.scloudic.jsuite.weixin.pay.v3.model;

import java.util.List;

public class ShareResult {
    private String transaction_id;
    private String out_order_no;
    /**
     * 微信分账单号，微信支付系统返回的唯一标识
     */
    private String order_id;
    /**
     * 分账单状态（每个接收方的分账结果请查看receivers中的result字段），枚举值：
     * 1、PROCESSING：处理中
     * 2、FINISHED：分账完成
     */
    private String state;
    /**
     * 分账接收方列表
     */
    private List<ShareRevResult> receivers;

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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<ShareRevResult> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<ShareRevResult> receivers) {
        this.receivers = receivers;
    }
}

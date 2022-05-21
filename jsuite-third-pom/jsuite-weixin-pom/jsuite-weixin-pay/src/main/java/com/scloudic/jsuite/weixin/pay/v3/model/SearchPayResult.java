package com.scloudic.jsuite.weixin.pay.v3.model;

import java.util.Map;

public class SearchPayResult {
    private String appid;
    private String mchid;
    private String out_trade_no;
    private String transaction_id;
    /**
     * 交易状态，枚举值：
     * SUCCESS：支付成功
     * REFUND：转入退款
     * NOTPAY：未支付
     * CLOSED：已关闭
     * REVOKED：已撤销（仅付款码支付会返回）
     * USERPAYING：用户支付中（仅付款码支付会返回）
     * PAYERROR：支付失败（仅付款码支付会返回）
     */
    private String trade_state;
    private String trade_state_desc;
    private String success_time;
    private Map<String, String> payer;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getTrade_state() {
        return trade_state;
    }

    public void setTrade_state(String trade_state) {
        this.trade_state = trade_state;
    }

    public String getTrade_state_desc() {
        return trade_state_desc;
    }

    public void setTrade_state_desc(String trade_state_desc) {
        this.trade_state_desc = trade_state_desc;
    }

    public String getSuccess_time() {
        return success_time;
    }

    public Map<String, String> getPayer() {
        return payer;
    }

    public void setPayer(Map<String, String> payer) {
        this.payer = payer;
    }

    public void setSuccess_time(String success_time) {
        this.success_time = success_time;
    }
}

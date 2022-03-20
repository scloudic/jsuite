package com.scloudic.jsuite.weixin.pay.v3.model;

public class ShareRefundResult {
    private String order_id;
    private String out_order_no;
    private String out_return_no;
    /**
     * 微信回退单号,微信分账回退单号，微信支付系统返回的唯一标识
     */
    private String return_id;
    private String return_mchid;
    private Long amount;
    private String description;
    private String result;
    private String fail_reason;
    private String create_time;
    private String finish_time;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOut_order_no() {
        return out_order_no;
    }

    public void setOut_order_no(String out_order_no) {
        this.out_order_no = out_order_no;
    }

    public String getOut_return_no() {
        return out_return_no;
    }

    public void setOut_return_no(String out_return_no) {
        this.out_return_no = out_return_no;
    }

    public String getReturn_id() {
        return return_id;
    }

    public void setReturn_id(String return_id) {
        this.return_id = return_id;
    }

    public String getReturn_mchid() {
        return return_mchid;
    }

    public void setReturn_mchid(String return_mchid) {
        this.return_mchid = return_mchid;
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
}

package com.scloudic.jsuite.weixin.pay.v3.model;

import com.scloudic.jsuite.weixin.pay.utils.WeiXinEnums;

public class V3PayResponse<T> {
    private int code;
    private String msg;
    private String requestUrl;
    private WeiXinEnums.WeiXinPayStatus payStatus;
    private T data;
    private String requestBody;

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public WeiXinEnums.WeiXinPayStatus getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(WeiXinEnums.WeiXinPayStatus payStatus) {
        this.payStatus = payStatus;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

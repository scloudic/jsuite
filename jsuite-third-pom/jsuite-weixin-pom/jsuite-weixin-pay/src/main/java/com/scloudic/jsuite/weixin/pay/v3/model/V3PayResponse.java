package com.scloudic.jsuite.weixin.pay.v3.model;

import com.scloudic.jsuite.weixin.pay.utils.WeiXinEnums;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class V3PayResponse<T> {
    private int code;
    private String msg;
    private String requestUrl;
    private WeiXinEnums.WeiXinPayStatus payStatus;
    private T data;
    private String requestBody;
    private String responseBody;
}

package com.scloudic.jsuite.weixin.pay.v3.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareDelReceiversRequest {
    private String appid;
    private String type;
    private String account;
}

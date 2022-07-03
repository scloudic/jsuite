package com.scloudic.jsuite.weixin.pay.v3.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class V3Pay {
    private String timeStamp;
    private String nonceStr;
    private String prepayIdPackage;
    private String signType;
    private String paySign;
    private String appId;
}

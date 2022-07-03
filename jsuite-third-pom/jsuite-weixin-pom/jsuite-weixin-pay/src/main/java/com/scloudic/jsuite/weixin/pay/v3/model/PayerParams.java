package com.scloudic.jsuite.weixin.pay.v3.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayerParams {
    /**
     * 商户号
     */
    private String merchantId;
    /**
     * 证书编号
     */
    private String certificateSerialNumber;
    /**
     * 私钥
     */
    private String privateKey;
    /**
     * apiV3Key
     */
    private String apiV3Key;
    /**
     * appId
     */
    private String appId;

}
package com.scloudic.jsuite.weixin.pay.v3.model;

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

    public String getCertificateSerialNumber() {
        return certificateSerialNumber;
    }

    public void setCertificateSerialNumber(String certificateSerialNumber) {
        this.certificateSerialNumber = certificateSerialNumber;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getApiV3Key() {
        return apiV3Key;
    }

    public void setApiV3Key(String apiV3Key) {
        this.apiV3Key = apiV3Key;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
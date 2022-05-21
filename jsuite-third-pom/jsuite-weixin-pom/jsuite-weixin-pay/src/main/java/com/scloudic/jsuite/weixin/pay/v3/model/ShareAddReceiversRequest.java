package com.scloudic.jsuite.weixin.pay.v3.model;

import com.scloudic.jsuite.weixin.pay.utils.WeiXinEnums;

public class ShareAddReceiversRequest {
    private String appid;
    private String type;
    private String account;
    private String name;
    private WeiXinEnums.RelationType relationType;
    /**
     * 是否商户
     */
    private boolean merchant;
    /**
     * 子商户与接收方具体的关系，本字段最多10个字。
     * 当字段relation_type的值为CUSTOM时，本字段必填;
     * 当字段relation_type的值不为CUSTOM时，本字段无需填写
     */
    private String customRelation;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WeiXinEnums.RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(WeiXinEnums.RelationType relationType) {
        this.relationType = relationType;
    }

    public String getCustomRelation() {
        return customRelation;
    }

    public void setCustomRelation(String customRelation) {
        this.customRelation = customRelation;
    }

    public boolean isMerchant() {
        return merchant;
    }

    public void setMerchant(boolean merchant) {
        this.merchant = merchant;
    }
}

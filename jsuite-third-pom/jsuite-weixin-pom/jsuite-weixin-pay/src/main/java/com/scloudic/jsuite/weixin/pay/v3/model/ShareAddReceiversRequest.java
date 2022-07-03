package com.scloudic.jsuite.weixin.pay.v3.model;

import com.scloudic.jsuite.weixin.pay.utils.WeiXinEnums;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}

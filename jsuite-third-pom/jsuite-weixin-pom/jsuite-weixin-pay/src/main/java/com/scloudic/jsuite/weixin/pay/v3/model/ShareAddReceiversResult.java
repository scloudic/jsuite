package com.scloudic.jsuite.weixin.pay.v3.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShareAddReceiversResult {
    private String type;
    private String account;
    private String name;
    private String relation_type;
    private String customRelation;
}

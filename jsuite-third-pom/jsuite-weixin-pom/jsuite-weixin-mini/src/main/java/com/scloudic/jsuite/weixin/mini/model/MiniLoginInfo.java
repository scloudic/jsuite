package com.scloudic.jsuite.weixin.mini.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MiniLoginInfo {
    private String openid;
    private String session_key;
    private String unionid;
    private Integer errcode;
    private String errmsg;
}

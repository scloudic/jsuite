package com.scloudic.jsuite.weixin.mini.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private String openId;
    private String unionId;
    private Integer gender;
    private String province;
    private String city;
    private String avatarUrl;
    private String nickName;
}

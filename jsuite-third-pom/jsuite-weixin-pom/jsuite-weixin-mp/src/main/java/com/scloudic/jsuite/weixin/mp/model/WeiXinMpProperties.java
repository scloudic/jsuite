package com.scloudic.jsuite.weixin.mp.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 公众号配置信息
 */
@Setter
@Getter
public class WeiXinMpProperties {
    private String appId;
    private String appSecret;
    private String token;
    private String encodingAESKey;
}

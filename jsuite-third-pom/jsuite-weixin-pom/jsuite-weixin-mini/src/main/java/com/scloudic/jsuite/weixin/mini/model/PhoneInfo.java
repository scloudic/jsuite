package com.scloudic.jsuite.weixin.mini.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneInfo {
    /**
     * 用户绑定的手机号（国外手机号会有区号）
     */
    private String phoneNumber;
    /**
     * 没有区号的手机号
     */
    private String purePhoneNumber;
    /**
     * 区号
     */
    private String countryCode;
    /**
     * 数据水印,其结构为：appid(string)、timestamp(number)字段
     */
    private Object watermark;
}

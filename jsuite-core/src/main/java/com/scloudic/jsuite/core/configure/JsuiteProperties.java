package com.scloudic.jsuite.core.configure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:jsuite.properties")
@Getter
@Setter
public class JsuiteProperties {
    @Value("${file.local.store:}")
    private String localFileStore;
    @Value("${file.url:}")
    private String fileUrl;
    @Value("${file.imgthumb.ssmall.with:200}")
    private int imgThumbSsmallWith;
    @Value("${file.imgthumb.small.with:500}")
    private int imgThumbSmallWith;
    @Value("${security.realmServiceName:}")
    private String realmServiceName;
    //kaptcha
    @Value("${kaptcha.border:yes}")
    private String kaptchaBorder;
    @Value("${kaptcha.textproducer.font.color:black}")
    private String kaptchaFontColor;
    @Value("${kaptcha.image.width:160}")
    private String kaptchaImageWidth;
    @Value("${kaptcha.image.height:60}")
    private String kaptchaImageHeight;
    @Value("${kaptcha.char.length:5}")
    private String kaptchaCharLength;
    @Value("${kaptcha.cache:session}")
    private String kaptchaCache;
    @Value("${kaptcha.cache.expire:120}")
    private Long kaptchaCacheExpire;
    //阿里云oss
    @Value("${file.ali.ossAccessKey:}")
    private String aliOssAccessKey;
    @Value("${file.ali.ossAccessKeySecret:}")
    private String aliOssAccessKeySecret;
    @Value("${file.ali.ossEndPoint:}")
    private String aliOssEndPoint;
    @Value("${file.ali.ossBucket:}")
    private String aliOssBucket;
    @Value("${file.ali.ossFirstPath:}")
    private String aliOssFirstPath;
    //管理端
    @Value("${sysuser.avatar.path:avatar}")
    private String avatarPath;
    @Value("${sysuser.showAdmin.list:false}")
    private boolean showAdmin;
}

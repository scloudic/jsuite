package com.scloudic.jsuite.common.api.web.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:jsuite.properties")
public class CaptchaProperties {
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
    public String getKaptchaFontColor() {
        return kaptchaFontColor;
    }

    public void setKaptchaFontColor(String kaptchaFontColor) {
        this.kaptchaFontColor = kaptchaFontColor;
    }

    public String getKaptchaBorder() {
        return kaptchaBorder;
    }

    public void setKaptchaBorder(String kaptchaBorder) {
        this.kaptchaBorder = kaptchaBorder;
    }

    public String getKaptchaImageWidth() {
        return kaptchaImageWidth;
    }

    public void setKaptchaImageWidth(String kaptchaImageWidth) {
        this.kaptchaImageWidth = kaptchaImageWidth;
    }

    public String getKaptchaImageHeight() {
        return kaptchaImageHeight;
    }

    public void setKaptchaImageHeight(String kaptchaImageHeight) {
        this.kaptchaImageHeight = kaptchaImageHeight;
    }

    public String getKaptchaCharLength() {
        return kaptchaCharLength;
    }

    public void setKaptchaCharLength(String kaptchaCharLength) {
        this.kaptchaCharLength = kaptchaCharLength;
    }

    public String getKaptchaCache() {
        return kaptchaCache;
    }

    public void setKaptchaCache(String kaptchaCache) {
        this.kaptchaCache = kaptchaCache;
    }

    public Long getKaptchaCacheExpire() {
        return kaptchaCacheExpire;
    }

    public void setKaptchaCacheExpire(Long kaptchaCacheExpire) {
        this.kaptchaCacheExpire = kaptchaCacheExpire;
    }
}
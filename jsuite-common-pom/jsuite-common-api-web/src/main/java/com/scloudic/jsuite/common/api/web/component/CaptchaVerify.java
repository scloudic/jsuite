package com.scloudic.jsuite.common.api.web.component;

import com.google.code.kaptcha.Constants;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.redisson.RedisCache;
import com.scloudic.rabbitframework.security.web.servlet.SecurityHttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class CaptchaVerify {
    @Autowired(required = false)
    private RedisCache redisCache;
    @Autowired
    private CaptchaProperties captchaProperties;

    public boolean verify(HttpServletRequest request, String key, String verifyValue) {
        String id = Constants.KAPTCHA_SESSION_KEY;
        String value = "";
        if ("redis".equals(captchaProperties.getKaptchaCache())) {
            id = ":" + key;
            value = redisCache.get(id);
        } else {
            HttpSession session = null;
            if (request instanceof SecurityHttpServletRequest) {
                session = ((SecurityHttpServletRequest) request).getHttpSession();
            } else {
                session = request.getSession();
            }
            //session机制不考虑过期
            Object obj = session.getAttribute(id);
            value = String.valueOf(obj != null ? obj : "");
            session.removeAttribute(id);
        }
        if (StringUtils.isNotBlank(value) && verifyValue.equals(value)) {
            return true;
        }
        return false;
    }

    public void setCaptcha(HttpServletRequest request, String key, String value) {
        String id = Constants.KAPTCHA_SESSION_KEY;
        if ("redis".equals(captchaProperties.getKaptchaCache())) {
            id = ":" + key;
            redisCache.set(id, value, captchaProperties.getKaptchaCacheExpire());
        } else {
            HttpSession session = null;
            if (request instanceof SecurityHttpServletRequest) {
                session = ((SecurityHttpServletRequest) request).getHttpSession();
            } else {
                session = request.getSession();
            }
            session.setAttribute(id, value);
        }
    }
}

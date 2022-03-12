package com.scloudic.jsuite.common.api.web.controllers;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.scloudic.jsuite.common.api.web.component.CaptchaProperties;
import com.scloudic.jsuite.common.api.web.component.CaptchaVerify;
import com.scloudic.rabbitframework.redisson.RedisCache;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/jsuite/captcha")
public class CaptchaController extends AbstractRabbitController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired(required = false)
    private RedisCache redisCache;
    @Autowired
    private CaptchaProperties captchaProperties;
    @Autowired
    private CaptchaVerify captchaVerify;

    /**
     * 验证码生成
     */
    @GetMapping("captchaImage/{uuid}")
    public void getKaptchaImage(@PathVariable("uuid") String uuid,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");
            String capStr = null;
            BufferedImage bi = null;
            capStr = defaultKaptcha.createText();
            bi = defaultKaptcha.createImage(capStr);
            captchaVerify.setCaptcha(request, uuid, capStr);
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out, null);
        }
    }
}
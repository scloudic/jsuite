package com.scloudic.jsuite.common.api.web.controllers;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.scloudic.rabbitframework.security.web.servlet.SecurityHttpServletRequest;
import com.scloudic.rabbitframework.web.AbstractContextResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.inject.Singleton;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Component
@Singleton
@Path("/jsuite/captcha")
public class CaptchaController extends AbstractContextResource {
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    /**
     * 验证码生成
     */
    @GET
    @Path(value = "/captchaImage")
    public Object getKaptchaImage(@Context HttpServletRequest request,
                                  @Context HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            HttpSession session = null;
            if (request instanceof SecurityHttpServletRequest) {
                session = ((SecurityHttpServletRequest) request).getHttpSession();
            } else {
                session = request.getSession();
            }
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");
            String capStr = null;
            String code = null;
            BufferedImage bi = null;
            capStr = code = defaultKaptcha.createText();
            bi = defaultKaptcha.createImage(capStr);
            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, code);
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {

            }
        }
        return null;
    }
}
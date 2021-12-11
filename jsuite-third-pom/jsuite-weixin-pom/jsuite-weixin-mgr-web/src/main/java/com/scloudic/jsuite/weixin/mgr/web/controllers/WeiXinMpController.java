package com.scloudic.jsuite.weixin.mgr.web.controllers;

import com.scloudic.jsuite.common.entity.ThirdBindInfo;
import com.scloudic.jsuite.common.service.ThirdBindInfoService;
import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.weixin.core.exception.CryptException;
import com.scloudic.jsuite.weixin.mp.model.WeiXinMpProperties;
import com.scloudic.jsuite.weixin.mp.service.WeiXinMpService;
import com.scloudic.rabbitframework.core.utils.JsonUtils;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.web.AbstractContextResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 微信公众号
 */
@Component
@Path("/jsuite/weiXinMp")
@Singleton
public class WeiXinMpController extends AbstractContextResource {
    private static final Logger logger = LoggerFactory.getLogger(WeiXinMpController.class);
    @Autowired
    private WeiXinMpService weiXinMpService;
    @Autowired
    private ThirdBindInfoService thirdBindInfoService;

    /**
     * 公众号配置消息验证
     *
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce     nonce
     * @param echostr   echostr
     * @return string
     */
    @GET
    @Path("notifyMsg")
    @Produces(MediaType.TEXT_PLAIN)
    public String notify(@QueryParam("signature") String signature,
                         @QueryParam("timestamp") String timestamp,
                         @QueryParam("nonce") String nonce,
                         @QueryParam("echostr") String echostr) {
        String msgSignature = weiXinMpService.notifyVerify(getWeiXinMpProperties(), timestamp, nonce);
        if (msgSignature.equals(signature)) {
            return echostr;
        }
        throw new CryptException(CryptException.ValidateSignatureError);
    }


    private WeiXinMpProperties getWeiXinMpProperties() {
        ThirdBindInfo thirdBindInfo = thirdBindInfoService.getThirdBinInfoByCode(Enums.ThirdKey.WEIXIN_MP.getValue());
        if (thirdBindInfo == null) {
            throw new CryptException(CryptException.ValidateSignatureError);
        }
        String bindParams = thirdBindInfo.getThirdBindParams();
        WeiXinMpProperties weiXinMpProperties = JsonUtils.getObject(bindParams, WeiXinMpProperties.class);
        return weiXinMpProperties;
    }

    /**
     * 接收消息通知
     *
     * @param msg
     * @return
     */
    @POST
    @Path("notifyMsg")
    @Produces(MediaType.TEXT_PLAIN)
    public String notifyMsg(String msg) {
        if (StringUtils.isBlank(msg)) {
            throw new CryptException(CryptException.ComputeSignatureError);
        }
        logger.debug("msg:" + msg);
        return "success";
    }
}

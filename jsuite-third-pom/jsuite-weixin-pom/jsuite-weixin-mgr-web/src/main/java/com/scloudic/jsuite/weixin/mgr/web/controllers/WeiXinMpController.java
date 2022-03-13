package com.scloudic.jsuite.weixin.mgr.web.controllers;

import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.third.entity.ThirdBindInfo;
import com.scloudic.jsuite.third.service.ThirdBindInfoService;
import com.scloudic.jsuite.weixin.core.exception.CryptException;
import com.scloudic.jsuite.weixin.mp.model.WeiXinMpProperties;
import com.scloudic.jsuite.weixin.mp.service.WeiXinMpService;
import com.scloudic.rabbitframework.core.utils.JsonUtils;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 微信公众号
 */
@RestController
@RequestMapping("/jsuite/weiXinMp")
public class WeiXinMpController extends AbstractRabbitController {
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
    @GetMapping("notifyMsg")
    public String notify(@RequestParam("signature") String signature,
                         @RequestParam("timestamp") String timestamp,
                         @RequestParam("nonce") String nonce,
                         @RequestParam("echostr") String echostr) {
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
    @PostMapping("notifyMsg")
    public String notifyMsg(String msg) {
        if (StringUtils.isBlank(msg)) {
            throw new CryptException(CryptException.ComputeSignatureError);
        }
        logger.debug("msg:" + msg);
        return "success";
    }
}

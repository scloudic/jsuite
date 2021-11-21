package com.scloudic.jsuite.weixin.mgr.web.controllers;

import com.scloudic.jsuite.common.entity.ThirdBindInfo;
import com.scloudic.jsuite.common.service.ThirdBindInfoService;
import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.weixin.core.exception.AesException;
import com.scloudic.jsuite.weixin.fficialaccount.model.OfficialAccountProperties;
import com.scloudic.jsuite.weixin.fficialaccount.service.OfficialAccountService;
import com.scloudic.rabbitframework.core.utils.JsonUtils;
import com.scloudic.rabbitframework.web.AbstractContextResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * 微信公众号
 */
@Component
@Path("/jsuite/weixin/officialAccount")
@Singleton
public class OfficialAccountController extends AbstractContextResource {
    private static final Logger logger = LoggerFactory.getLogger(OfficialAccountController.class);
    @Autowired
    private OfficialAccountService officialAccountService;
    @Autowired
    private ThirdBindInfoService thirdBindInfoService;

    @GET
    @Path("notify")
    @Produces(MediaType.TEXT_PLAIN)
    public String notify(@QueryParam("signature") String signature,
                         @QueryParam("timestamp") String timestamp,
                         @QueryParam("nonce") String nonce,
                         @QueryParam("echostr") String echostr) {
        ThirdBindInfo thirdBindInfo = thirdBindInfoService.getThirdBinInfoByCode(Enums.ThirdKey.WEIXINO_FFICIAL_ACCOUNT.getValue());
        if (thirdBindInfo == null) {
            return "";
        }
        String bindParams = thirdBindInfo.getThirdBindParams();
        OfficialAccountProperties officialAccountProperties = JsonUtils.getObject(bindParams, OfficialAccountProperties.class);
        String msgSignature = officialAccountService.notifyVerify(officialAccountProperties, timestamp, nonce);
        if (msgSignature.equals(signature)) {
            return echostr;
        }
        throw new AesException(AesException.ValidateSignatureError);
    }
}

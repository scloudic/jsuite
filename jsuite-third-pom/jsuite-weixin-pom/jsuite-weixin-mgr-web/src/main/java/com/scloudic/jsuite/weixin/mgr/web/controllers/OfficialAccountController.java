package com.scloudic.jsuite.weixin.mgr.web.controllers;

import com.scloudic.rabbitframework.web.AbstractContextResource;
import com.scloudic.rabbitframework.web.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @GET
    @Path("notify")
    @Produces(MediaType.TEXT_HTML)
    public String notify(@QueryParam("signature") String signature,
                         @QueryParam("timestamp") String timestamp,
                         @QueryParam("nonce") String nonce,
                         @QueryParam("echostr") String echostr) {
        logger.debug("signature:" + signature + ",timestamp:" + timestamp + ",nonce:" + nonce + ",echostr:" + echostr);
        return echostr;

    }
}

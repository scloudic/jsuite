package com.scloudic.jsuite.mgr.web.controllers.admin;

import com.scloudic.jsuite.mgr.web.MgrJsuiteProperties;
import com.scloudic.rabbitframework.security.authz.annotation.UserAuthentication;
import com.scloudic.rabbitframework.web.AbstractContextResource;
import org.glassfish.jersey.server.mvc.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 主页请求控制
 *
 * @since 1.0
 */
@Component
@Singleton
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class AdminIndexController extends AbstractContextResource {
    private static final Logger logger = LoggerFactory.getLogger(AdminIndexController.class);
    @Autowired
    private MgrJsuiteProperties mgrJsuiteProperties;

    @GET
    @Path("")
    @UserAuthentication
    public Object index() {
        logger.info("index跳转页");
        return new Viewable(mgrJsuiteProperties.getAdminPath() + "/index.html");
    }

    @GET
    @Path("main")
    @UserAuthentication
    public Object main() {
        return new Viewable(mgrJsuiteProperties.getAdminPath() + "/main.html");
    }
}

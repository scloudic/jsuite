package com.scloudic.jsuite.mgr.web.controllers.admin;

import com.scloudic.jsuite.mgr.web.MgrJsuiteProperties;
import com.scloudic.rabbitframework.security.authz.annotation.UserAuthentication;
import com.scloudic.rabbitframework.web.AbstractContextResource;
import org.glassfish.jersey.server.mvc.Viewable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/")
@Singleton
public class UserProfileIndexController extends AbstractContextResource {
    @Autowired
    private MgrJsuiteProperties mgrJsuiteProperties;

    @GET
    @Path("/userProfile")
    @UserAuthentication
    @Produces(MediaType.TEXT_HTML)
    public Object index() {
        return new Viewable(mgrJsuiteProperties.getAdminPath() + "/user/profileIndex.html");
    }
}

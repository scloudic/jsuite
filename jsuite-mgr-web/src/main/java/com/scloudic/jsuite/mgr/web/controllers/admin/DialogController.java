package com.scloudic.jsuite.mgr.web.controllers.admin;

import com.scloudic.jsuite.mgr.web.MgrJsuiteProperties;
import com.scloudic.rabbitframework.security.authz.annotation.UserAuthentication;
import com.scloudic.rabbitframework.web.AbstractContextResource;
import org.glassfish.jersey.server.mvc.Viewable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Component
@Singleton
@Path("/admin/dialog")
@Produces(MediaType.TEXT_HTML)
public class DialogController extends AbstractContextResource {
    @Autowired
    private MgrJsuiteProperties mgrJsuiteProperties;

    @GET
    @Path("fileDialog")
    @UserAuthentication
    public Object fileDialog(@DefaultValue("")
                             @QueryParam("fileType") String fileType) {
        Map<String, Object> params = new HashMap<>();
        params.put("fileType", fileType);
        return new Viewable(mgrJsuiteProperties.getAdminPath()
                + "/dialog/fileDialog.html", params);
    }
}

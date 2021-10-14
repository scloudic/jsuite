package com.scloudic.jsuite.mgr.web.controllers;

import com.scloudic.rabbitframework.web.AbstractContextResource;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 错误处理跳转
 *
 * @since 1.0
 */
@Singleton
@Path("/error")
@Produces(MediaType.TEXT_HTML)
public class ErrorController extends AbstractContextResource {
    @GET
    @Path("404")
    public Object sys404() {
        return new Viewable("/error/404.html");
    }

    @GET
    @Path("500")
    public Object sys500() {
        return new Viewable("/error/500.html");
    }

    /**
     * 用户没有权限跳转
     *
     * @return
     */
    @GET
    @Path("unauthorized")
    public Object unauthorized() {
        return new Viewable("/error/unauthorized.html");
    }
}

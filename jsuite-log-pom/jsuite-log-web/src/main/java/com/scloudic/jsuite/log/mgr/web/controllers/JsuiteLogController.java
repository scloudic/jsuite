package com.scloudic.jsuite.log.mgr.web.controllers;


import com.scloudic.jsuite.log.model.LogBean;
import com.scloudic.jsuite.log.service.JsuiteLogService;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.security.authz.annotation.UriPermissions;
import com.scloudic.rabbitframework.web.AbstractContextResource;
import com.scloudic.rabbitframework.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

@Component
@Singleton
@Path("/jsuite/logMgr")
public class JsuiteLogController extends AbstractContextResource {
    @Autowired
    private JsuiteLogService jsuiteLogService;

    @GET
    @Path("list")
    @UriPermissions
    public Result<PageBean<LogBean>> list(@Context HttpServletRequest request,
                                          @QueryParam("searchKey") String searchKey,
                                          @QueryParam("startDate") String startDate,
                                          @QueryParam("operateType") String operateType,
                                          @QueryParam("endDate") String endDate,
                                          @DefaultValue("1") @QueryParam("pageNum") Long pageNum,
                                          @DefaultValue("15") @QueryParam("pageSize") Long pageSize) {
        PageBean<LogBean> pageBean = jsuiteLogService.findLog(searchKey,operateType, startDate, endDate, pageNum, pageSize);
        return success(pageBean);
    }
}

package com.scloudic.jsuite.sysuser.mgr.web.controllers;

import com.scloudic.jsuite.sysuser.mgr.entity.SysMenu;
import com.scloudic.jsuite.sysuser.mgr.service.SysMenuService;
import com.scloudic.jsuite.sysuser.mgr.web.component.HomeMenuComponent;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.security.authz.annotation.UserAuthentication;
import com.scloudic.rabbitframework.web.AbstractContextResource;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import org.glassfish.jersey.server.mvc.Viewable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页获取功能菜单
 */
@Component
@Path("/jsuite/homeMenu")
@Singleton
public class HomeMenuController extends AbstractContextResource {
    @Autowired
    private HomeMenuComponent homeMenuComponent;
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 获取首页所有功能菜单
     *
     * @param parentMenuId 父菜单ID
     * @return json
     */
    @GET
    @Path("list")
    @UserAuthentication
    public Result<List<Map<String, Object>>> homeMenu(@QueryParam("parentMenuId") @DefaultValue("1") Long parentMenuId) {
        List<Map<String, Object>> homeMenu = homeMenuComponent.homeMenu(parentMenuId);
        return success(homeMenu);
    }

    /**
     * 根据父菜单主键按层级加载获取当前用户可访问的功能菜单。
     *
     * @param parentMenuId 父菜单ID
     * @return json
     */
    @GET
    @Path("findRoleMenuByParentId")
    @UserAuthentication
    public Result<List<Map<String, Object>>> findRoleMenuByMenuParentId(@QueryParam("parentMenuId") @DefaultValue("-1") Long parentMenuId) {
        List<Map<String, Object>> result = homeMenuComponent.findRoleMenuByMenuParentId(parentMenuId);
        return success(result);
    }


    /**
     * 功能首页跳转，主要用于前后端不分离，通过后台调用跳转到不同的前端首页
     *
     * @param request  request
     * @param menuCode 菜单编码
     * @param params   传参参数
     * @return 跳转界面
     */
    @GET
    @Path("funIndex/{menuCode}")
    @UserAuthentication
    @FormValid
    @Produces(MediaType.TEXT_HTML)
    public Object funIndex(@Context HttpServletRequest request,
                           @NotBlank @PathParam("menuCode") String menuCode,
                           @QueryParam("params") String params) {
        if (StringUtils.isBlank(menuCode)) {
            throw new BizException("菜单标识不能为空!");
        }
        Where where = new Where();
        where.createCriteria().andEqual(SysMenu::getMenuCode, menuCode);
        SysMenu sysMenu = sysMenuService.selectOneByParams(where);
        if (sysMenu == null) {
            throw new BizException("menu.null");
        }
        Map<String, String> paramsMap = null;
        if (StringUtils.isNotBlank(sysMenu.getFrontEndParamName())
                && StringUtils.isNotBlank(params)) {
            paramsMap = new HashMap<>();
            paramsMap.put(sysMenu.getFrontEndParamName(), params);
        }
        return new Viewable(sysMenu.getFrontEndUrl(), paramsMap);
    }
}

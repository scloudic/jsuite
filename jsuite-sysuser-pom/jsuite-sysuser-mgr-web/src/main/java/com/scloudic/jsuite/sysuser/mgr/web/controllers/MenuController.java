package com.scloudic.jsuite.sysuser.mgr.web.controllers;

import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.jsuite.sysuser.mgr.entity.SysMenu;
import com.scloudic.jsuite.sysuser.mgr.entity.SysRoleMenu;
import com.scloudic.jsuite.sysuser.mgr.service.SysMenuService;
import com.scloudic.jsuite.sysuser.mgr.service.SysRoleMenuService;
import com.scloudic.jsuite.sysuser.mgr.web.vo.MenuVo;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.core.utils.UUIDUtils;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.security.authz.annotation.UriPermissions;
import com.scloudic.rabbitframework.web.AbstractContextResource;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.*;

@Component
@Path("/jsuite/menuMgr")
@Singleton
public class MenuController extends AbstractContextResource {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 查询菜单功能列表
     *
     * @param request request
     * @param menuId  菜单主键
     * @return json
     */
    @GET
    @Path("findMenu")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "功能菜单tree查询")
    public Result<List<MenuVo>> findMenu(@Context HttpServletRequest request,
                                         @DefaultValue("-1") @QueryParam("menuId") String menuId) {
        List<MenuVo> menuVos = new ArrayList<>();
        menuVos = findChildrenMainMenu(menuId, new HashMap<>());
        return success(menuVos);
    }

    /**
     * 获取菜单集合
     *
     * @param request    request
     * @param menuName   查询菜单名称
     * @param rootStatus 是否包括根级节点
     * @return json
     */
    @GET
    @Path("listMenu")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "功能菜单列表查询")
    public Result<List<SysMenu>> listMenu(@Context HttpServletRequest request,
                                          @QueryParam("menuName") String menuName,
                                          @DefaultValue("2") @QueryParam("rootStatus") Integer rootStatus) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();

        if (rootStatus == 2) {
            criteria.andNotEqual(SysMenu::getParentMenuId, -1);
        }
        if (StringUtils.isNotBlank(menuName)) {
            criteria.andLike(SysMenu::getMenuName, "%" + menuName + "%");
        }
        where.setOrderBy(SysMenu::getSortNum);
        return success(sysMenuService.selectByParams(where));

    }

    private List<MenuVo> findChildrenMainMenu(String parentMenuId, Map<Long, SysRoleMenu> menuMap) {
        List<MenuVo> result = new ArrayList<MenuVo>();
        List<SysMenu> menus = sysMenuService.findChildMenuByParentMenuId(parentMenuId, null);
        for (SysMenu menu : menus) {
            String menuId = menu.getSysMenuId();
            MenuVo menuVo = new MenuVo();
            menuVo.setMenuId(menuId);
            menuVo.setMenuLevel(menu.getMenuLevel());
            menuVo.setMenuName(menu.getMenuName());
            menuVo.setParentMenuId(menu.getParentMenuId());
            menuVo.setBtnFlag(menu.getBtnFlag());
            menuVo.setIconPath(menu.getIconPath());
            menuVo.setSortNum(menu.getSortNum());
            menuVo.setMenuDesc(menu.getMenuDesc());
            menuVo.setMenuType(menu.getMenuType());
            menuVo.setTarget(menu.getTarget());
            menuVo.setCreatTime(menu.getCreateTime());
            menuVo.setBackEndUrl(menu.getBackEndUrl());
            menuVo.setMenuCode(menu.getMenuCode());
            menuVo.setFrontEndUrl(menu.getFrontEndUrl());
            if (menuMap.get(menuId) != null) {
                menuVo.setCheckStatus(true);
            }
            int childMenuNum = sysMenuService.findChildMenuByParentMenuId(menuId, null).size();
            menuVo.setChildMenuNum(childMenuNum);
            if (childMenuNum > 0) {
                menuVo.setChildren(findChildrenMainMenu(menuId, menuMap));
            } else {
                menuVo.setChildren(new ArrayList<MenuVo>());
            }
            result.add(menuVo);

        }
        return result;
    }

    @POST
    @Path("addMenu")
    @FormValid
    @UriPermissions
    @Log(operatorType = Log.OperateType.ADD, remark = "添加功能菜单")
    public Result addMenu(@Context HttpServletRequest request,
                          @NotBlank @FormParam("menuName") String menuName,
                          @NotBlank @FormParam("parentMenuId") String parentMenuId,
                          @DefaultValue("1") @FormParam("target") Integer target,
                          @FormParam("frontEndUrl") String frontEndUrl,
                          @FormParam("frontEndParamName") String frontEndParamName,
                          @FormParam("backEndUrl") String backEndUrl,
                          @FormParam("menuCode") String menuCode,
                          @DefaultValue("0") @FormParam("sortNum") Integer sortNum,
                          @DefaultValue("1") @FormParam("menuType") Integer menuType,
                          @FormParam("iconPath") String iconPath,
                          @FormParam("menuDesc") String menuDesc,
                          @DefaultValue("1") @FormParam("btnFlag") Integer btnFlag) {

        if (StringUtils.isNotBlank(menuCode)) {
            Where where = new Where();
            Criteria criteria = where.createCriteria();
            criteria.andEqual(SysMenu::getMenuCode, menuCode);
            long totalCount = sysMenuService.selectCountByParams(where);
            if (totalCount > 0) {
                throw new BizException("菜单标识不能重复!");
            }
        } else {
            menuCode = null;
        }
        SysMenu sysMenu = sysMenuService.selectById(parentMenuId);
        Date date = new Date();
        SysMenu menu = new SysMenu();
        menu.setSysMenuId(UUIDUtils.getTimeUUID32());
        menu.setMenuLevel(sysMenu.getMenuLevel().intValue() + 1);
        menu.setMenuDesc(menuDesc);
        menu.setBtnFlag(btnFlag);
        menu.setCreateTime(date);
        menu.setIconPath(iconPath);
        menu.setMenuCode(menuCode);
        menu.setFrontEndParamName(frontEndParamName);
        menu.setMenuName(menuName);
        menu.setParentMenuId(parentMenuId);
        menu.setFrontEndUrl(frontEndUrl);
        menu.setBackEndUrl(backEndUrl);
        menu.setTarget(target);
        menu.setSortNum(sortNum);
        menu.setUpdateTime(date);
        menu.setMenuType(menuType);
        sysMenuService.saveMenu(menu);
        return success();
    }

    @POST
    @Path("updateMenu")
    @FormValid
    @UriPermissions
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改功能菜单")
    public Result updateMenu(@Context HttpServletRequest request,
                             @NotBlank @FormParam("menuName") String menuName,
                             @NotBlank @FormParam("menuId") String menuId,
                             @FormParam("url") String url,
                             @FormParam("target") Integer target,
                             @FormParam("frontEndUrl") String frontEndUrl,
                             @FormParam("backEndUrl") String backEndUrl,
                             @FormParam("menuCode") String menuCode,
                             @FormParam("frontEndParamName") String frontEndParamName,
                             @FormParam("sortNum") Integer sortNum,
                             @FormParam("menuType") Integer menuType,
                             @FormParam("iconPath") String iconPath,
                             @FormParam("menuDesc") String menuDesc,
                             @FormParam("btnFlag") Integer btnFlag) {
        if (StringUtils.isNotBlank(menuCode)) {
            Where where = new Where();
            Criteria criteria = where.createCriteria();
            criteria.andEqual(SysMenu::getMenuCode, menuCode);
            criteria.andNotEqual(SysMenu::getSysMenuId, menuId);
            long totalCount = sysMenuService.selectCountByParams(where);
            if (totalCount > 0) {
                throw new BizException("菜单标识不能重复!");
            }
        } else {
            menuCode = null;
        }
        Date date = new Date();
        SysMenu menu = new SysMenu();
        menu.setSysMenuId(menuId);
        menu.setMenuDesc(menuDesc);
        menu.setBtnFlag(btnFlag);
        menu.setFrontEndParamName(frontEndParamName);
        menu.setIconPath(iconPath);
        menu.setMenuName(menuName);
        menu.setFrontEndUrl(frontEndUrl);
        menu.setBackEndUrl(backEndUrl);
        menu.setMenuCode(menuCode);
        menu.setTarget(target);
        menu.setSortNum(sortNum);
        menu.setUpdateTime(date);
        menu.setMenuType(menuType);
        sysMenuService.updateMenu(menu);
        return success();
    }

    /**
     * 删除菜单
     *
     * @param request request
     * @param menuId  菜单主键
     * @return json
     */
    @POST
    @Path("delMenu")
    @FormValid
    @UriPermissions
    @Log(operatorType = Log.OperateType.DEL, remark = "删除功能菜单")
    public Result delMenu(@Context HttpServletRequest request,
                          @NotBlank @FormParam("menuId") String menuId) {
        if ("1".equals(menuId)) {
            throw new BizException("can.not.menu");
        }
        sysMenuService.delMenu(menuId);
        return success();
    }

    /**
     * 获取所有菜单,并根据角色主键判断菜单是否选中
     *
     * @param request      request
     * @param parentMenuId 父菜单主键
     * @param sysRoleId    角色主键
     * @return json
     */
    @GET
    @Path("findRoleMenu")
    @UriPermissions
    @FormValid
    @Log(operatorType = Log.OperateType.SELECT, remark = "根据角色查询功能菜单")
    public Result<List<MenuVo>> findRoleMenu(@Context HttpServletRequest request,
                                             @DefaultValue("1") @QueryParam("parentMenuId") String parentMenuId,
                                             @NotBlank @QueryParam("sysRoleId") Long sysRoleId) {
        List<MenuVo> menuVos = new ArrayList<MenuVo>();
        Map<Long, SysRoleMenu> menuMap = sysRoleMenuService.findSysRoleMenuByRoleId(sysRoleId);
        menuVos = findChildrenMainMenu(parentMenuId, menuMap);
        return success(menuVos);
    }
}
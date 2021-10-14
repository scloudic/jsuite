package com.scloudic.jsuite.sysuser.mgr.web.controllers;

import com.scloudic.jsuite.core.configure.JsuiteProperties;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.jsuite.sysuser.mgr.entity.SysRole;
import com.scloudic.jsuite.sysuser.mgr.entity.SysUserRole;
import com.scloudic.jsuite.sysuser.mgr.service.SysRoleService;
import com.scloudic.jsuite.sysuser.mgr.service.SysUserRoleService;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.jbatis.mapping.RowBounds;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.security.authz.annotation.UriPermissions;
import com.scloudic.rabbitframework.security.realm.SecurityAuthorizingRealm;
import com.scloudic.rabbitframework.web.AbstractContextResource;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import com.scloudic.rabbitframework.web.utils.ServletContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.Date;
import java.util.List;

/**
 * 角色管理
 */
@Component
@Path("/jsuite/roleMgr")
@Singleton
public class RoleController extends AbstractContextResource {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private JsuiteProperties jsuiteProperties;

    /**
     * 获取所有角色信息
     *
     * @param request  request
     * @param roleName 角色名称
     * @return json
     */
    @GET
    @Path("findRoles")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "查询角色列表")
    public Result<List<SysRole>> findAllRoles(@Context HttpServletRequest request,
                                              @QueryParam("roleName") String roleName) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        if (StringUtils.isNotBlank(roleName)) {
            criteria.andEqual(SysRole::getRoleName, roleName);
        }
        List<SysRole> roles = sysRoleService.selectByParams(where);
        return success(roles);
    }


    /**
     * 分页获取角色信息
     *
     * @param request  request
     * @param pageNum  开始页 默认1
     * @param pageSize 每页显示 默认15
     * @param roleName 角色名称
     * @return json
     */
    @GET
    @Path("findRolesPage")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "分页查询角色列表")
    public Result<PageBean<SysRole>> findRolesPage(@Context HttpServletRequest request,
                                                   @QueryParam("pageNum") Long pageNum,
                                                   @QueryParam("pageSize") Long pageSize,
                                                   @QueryParam("roleName") String roleName) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        if (StringUtils.isNotBlank(roleName)) {
            criteria.andEqual(SysRole::getRoleName, roleName);
        }
        Long totalCount = sysRoleService.selectCountByParams(where);
        PageBean<SysRole> pageBean = new PageBean<SysRole>(pageNum, pageSize, totalCount);
        List<SysRole> sysRoles = sysRoleService.selectPageByParams(where, new RowBounds(pageBean.getStartPage(), pageBean.getPageSize()));
        pageBean.setDatas(sysRoles);
        return success(pageBean);
    }


    /**
     * 添加角色
     *
     * @param request  request
     * @param roleName 角色名称
     * @param roleCode 角色编码
     * @param roleDesc 角色描述
     * @return json
     */
    @POST
    @Path("addRole")
    @FormValid
    @UriPermissions
    @Log(operatorType = Log.OperateType.ADD, remark = "添加角色")
    public Result addRole(@Context HttpServletRequest request,
                          @NotBlank @FormParam("roleName") String roleName,
                          @NotBlank @FormParam("roleCode") String roleCode,
                          @FormParam("roleDesc") String roleDesc) {
        SysRole role = sysRoleService.getRoleByRoleCode(roleCode);
        if (role != null) {
            throw new BizException("roleCode.duplicate");
        }
        Date date = new Date();
        SysRole saveRole = new SysRole();
        saveRole.setRoleCode(roleCode);
        saveRole.setRoleName(roleName);
        saveRole.setRoleDesc(roleDesc);
        saveRole.setCreateTime(date);
        saveRole.setUpdateTime(date);
        sysRoleService.saveRole(saveRole);
        return success();
    }

    /**
     * 修改角色
     *
     * @param request   request
     * @param sysRoleId 角色主键
     * @param roleName  角色名称
     * @param roleCode  角色编码
     * @param roleDesc  角色描述
     * @return json
     */
    @POST
    @Path("updateRole")
    @FormValid
    @UriPermissions
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改角色")
    public Result updateRole(@Context HttpServletRequest request,
                             @NotBlank @FormParam("sysRoleId") Long sysRoleId,
                             @NotBlank @FormParam("roleName") String roleName,
                             @NotBlank @FormParam("roleCode") String roleCode,
                             @FormParam("roleDesc") String roleDesc) {
        SysRole role = sysRoleService.getRoleByRoleCode(roleCode);
        if (role != null && role.getSysRoleId().longValue() != sysRoleId.longValue()) {
            throw new BizException("roleCode.duplicate");
        }
        SysRole updateRole = new SysRole();
        updateRole.setSysRoleId(sysRoleId);
        updateRole.setRoleCode(roleCode);
        updateRole.setRoleName(roleName);
        updateRole.setRoleDesc(roleDesc);
        updateRole.setUpdateTime(new Date());
        sysRoleService.updateRole(updateRole);
        return success();
    }

    /**
     * 删除角色
     *
     * @param request   rqeuest
     * @param sysRoleId 角色主键
     * @return json
     */
    @POST
    @Path("delRole")
    @FormValid
    @UriPermissions
    @Log(operatorType = Log.OperateType.DEL, remark = "修改角色")
    public Result delRole(@Context HttpServletRequest request,
                          @NotBlank @FormParam("sysRoleId") Long sysRoleId) {
        sysRoleService.delRole(sysRoleId);
        return success();
    }

    /**
     * 添加角色菜单
     *
     * @param request   request
     * @param sysRoleId 角色主键
     * @param roleCode  角色编码
     * @param menuIds   菜单主键,多个逗号分离
     * @return json
     */
    @POST
    @Path("addRoleMenu")
    @FormValid
    @UriPermissions
    @Log(operatorType = Log.OperateType.ADD, remark = "添加角色菜单")
    public Result addRoleMenu(@Context HttpServletRequest request,
                              @NotBlank @FormParam("sysRoleId") Long sysRoleId,
                              @NotBlank @FormParam("roleCode") String roleCode,
                              @NotBlank @FormParam("menuIds") String menuIds) {
        String[] menuIdsStr = menuIds.split(",");
        sysRoleService.addRoleMenu(sysRoleId, roleCode, menuIdsStr);
        //修改角色菜单清除所有用户权限
        new Thread(() -> {
            String realmServiceName = jsuiteProperties.getRealmServiceName();
            if (StringUtils.isNotBlank(realmServiceName)) {
                SecurityAuthorizingRealm securityAuthorizingRealm = (SecurityAuthorizingRealm) ServletContextHelper.getBean(realmServiceName);
                Where where = new Where();
                where.createCriteria().andEqual(SysUserRole::getSysRoleId, sysRoleId);
                List<SysUserRole> sysUserRoles = sysUserRoleService.selectByParams(where);
                sysUserRoles.forEach(sysUserRole -> {
                    securityAuthorizingRealm.cleanAuthorizationCache(sysUserRole.getSysUserId());
                });
            }
        }).start();
        return success();
    }
}

package com.scloudic.jsuite.sysuser.mgr.web.controllers;

import com.scloudic.jsuite.core.configure.JsuiteProperties;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.jsuite.sysuser.mgr.entity.SysRole;
import com.scloudic.jsuite.sysuser.mgr.entity.SysUserRole;
import com.scloudic.jsuite.sysuser.mgr.service.SysRoleService;
import com.scloudic.jsuite.sysuser.mgr.service.SysUserRoleService;
import com.scloudic.jsuite.sysuser.mgr.web.model.RoleMenuDto;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.jbatis.mapping.RowBounds;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.security.SecurityUtils;
import com.scloudic.rabbitframework.security.authz.annotation.UriPermissions;
import com.scloudic.rabbitframework.security.realm.SecurityAuthorizingRealm;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import com.scloudic.rabbitframework.web.utils.ServletContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 角色管理
 */

@RestController
@RequestMapping("/jsuite/roleMgr")
public class RoleController extends AbstractRabbitController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private JsuiteProperties jsuiteProperties;

    /**
     * 获取所有角色信息
     *
     * @param roleName 角色名称
     * @return json
     */
    @GetMapping("findRoles")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "查询角色列表")
    public Result<List<SysRole>> findAllRoles(@RequestParam(value = "roleName", required = false) String roleName) {
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
     * @param pageNum  开始页 默认1
     * @param pageSize 每页显示 默认15
     * @param roleName 角色名称
     * @return json
     */
    @GetMapping("findRolesPage")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "分页查询角色列表")
    public Result<PageBean<SysRole>> findRolesPage(@RequestParam(value = "pageNum", required = false) Long pageNum,
                                                   @RequestParam(value = "pageSize", required = false) Long pageSize,
                                                   @RequestParam(value = "roleName", required = false) String roleName) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        if (StringUtils.isNotBlank(roleName)) {
            criteria.andEqual(SysRole::getRoleName, roleName);
        }
        if (!"1".equals(SecurityUtils.getUserId())) {
            criteria.andNotEqual(SysRole::getSysRoleId, 1L);
        }
        Long totalCount = sysRoleService.selectCountByParams(where);
        PageBean<SysRole> pageBean = new PageBean<SysRole>(pageNum, pageSize, totalCount);
        List<SysRole> sysRoles = sysRoleService.selectPageByParams(where, new RowBounds(pageBean.getStartPage(), pageBean.getPageSize()));
        pageBean.setDatas(sysRoles);
        return success(pageBean);
    }

    @PostMapping("addRole")
    @FormValid(fieldFilter = "sysRoleId")
    @UriPermissions
    @Log(operatorType = Log.OperateType.ADD, remark = "添加角色")
    public Result addRole(@RequestBody SysRole sysRole) {
        SysRole role = sysRoleService.getRoleByRoleCode(sysRole.getRoleCode());
        if (role != null) {
            throw new BizException("roleCode.duplicate");
        }
        Date date = new Date();
        sysRole.setCreateTime(date);
        sysRole.setUpdateTime(date);
        sysRoleService.saveRole(sysRole);
        return success();
    }

    @PostMapping("updateRole")
    @FormValid
    @UriPermissions
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改角色")
    public Result updateRole(@RequestBody SysRole sysRole) {
        String roleDesc = sysRole.getRoleDesc();
        String roleCode = sysRole.getRoleCode();
        Long sysRoleId = sysRole.getSysRoleId();
        String roleName = sysRole.getRoleName();
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


    @PostMapping("delRole")
    @FormValid(fieldFilter = {"roleName", "roleCode"})
    @UriPermissions
    @Log(operatorType = Log.OperateType.DEL, remark = "修改角色")
    public Result delRole(@RequestBody SysRole sysRole) {
        sysRoleService.delRole(sysRole.getSysRoleId());
        return success();
    }

    /**
     * 添加角色菜单
     *
     * @param roleMenuDto
     * @return
     */
    @PostMapping("addRoleMenu")
    @FormValid
    @UriPermissions
    @Log(operatorType = Log.OperateType.ADD, remark = "添加角色菜单")
    public Result addRoleMenu(@RequestBody RoleMenuDto roleMenuDto) {
        sysRoleService.addRoleMenu(roleMenuDto.getSysRoleId(),
                roleMenuDto.getRoleCode(), roleMenuDto.getMenuIds());
        //修改角色菜单清除所有用户权限
        new Thread(() -> {
            String realmServiceName = jsuiteProperties.getRealmServiceName();
            if (StringUtils.isNotBlank(realmServiceName)) {
                SecurityAuthorizingRealm securityAuthorizingRealm = (SecurityAuthorizingRealm) ServletContextHelper.getBean(realmServiceName);
                if (securityAuthorizingRealm == null) {
                    logger.warn("清除用户权限失败!");
                    return;
                }
                Where where = new Where();
                where.createCriteria().andEqual(SysUserRole::getSysRoleId, roleMenuDto.getSysRoleId());
                List<SysUserRole> sysUserRoles = sysUserRoleService.selectByParams(where);
                sysUserRoles.forEach(sysUserRole -> {
                    securityAuthorizingRealm.cleanAuthorizationCache(sysUserRole.getSysUserId());
                });
            }
        }).start();
        return success();
    }
}

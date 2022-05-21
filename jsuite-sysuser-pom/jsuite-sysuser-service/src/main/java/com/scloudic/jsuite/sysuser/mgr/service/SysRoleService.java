package com.scloudic.jsuite.sysuser.mgr.service;

import com.scloudic.jsuite.sysuser.mgr.entity.SysRole;
import com.scloudic.jsuite.sysuser.mgr.entity.SysRoleMenu;
import com.scloudic.rabbitframework.jbatis.service.IService;

import java.util.List;

/**
 * 角色服务类
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 根据角色编码获取角色信息
     *
     * @param roleCode
     * @return
     */
    SysRole getRoleByRoleCode(String roleCode);

    /**
     * 保存角色
     *
     * @param role
     * @return
     */
    int saveRole(SysRole role);

    /**
     * 修改角色
     *
     * @param role
     * @return
     */
    int updateRole(SysRole role);

    /**
     * 删除角色,级联删除角色菜单{@link SysRoleMenu}
     *
     * @param roleId
     * @return
     */
    int delRole(Long roleId);


    List<SysRole> findSysRoleByRoleIds(List<Long> roleIds);

    /**
     * 根据角色添加角色菜单
     *
     * @param roleId
     * @param roleCode
     * @param menuIds
     */
    void addRoleMenu(Long roleId, String roleCode, List<String> menuIds);
}

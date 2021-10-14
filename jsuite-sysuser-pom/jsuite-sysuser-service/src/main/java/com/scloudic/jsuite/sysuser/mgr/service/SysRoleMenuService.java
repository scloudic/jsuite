package com.scloudic.jsuite.sysuser.mgr.service;

import com.scloudic.jsuite.sysuser.mgr.entity.SysRoleMenu;

import java.util.Map;

public interface SysRoleMenuService {
    /**
     * 根据角色主键获取对应的所有菜单信息
     *
     * @param roleId
     * @return
     */
    Map<Long,SysRoleMenu> findSysRoleMenuByRoleId(Long roleId);

}

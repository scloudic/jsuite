package com.scloudic.jsuite.sysuser.mgr.service;

import com.scloudic.jsuite.sysuser.mgr.entity.SysRoleMenu;
import com.scloudic.jsuite.sysuser.mgr.mapper.SysRoleMenuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    private static final Logger logger = LoggerFactory.getLogger(SysRoleMenuServiceImpl.class);
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 根据角色主键获取对应的所有菜单信息
     *
     * @param roleId
     * @return
     */
    @Override
    public Map<Long, SysRoleMenu> findSysRoleMenuByRoleId(Long roleId) {
        if (roleId == null || roleId.longValue() == 0) {
            logger.warn("roleId is  null");
            return new HashMap<>();
        }
        Map<Long, SysRoleMenu> menuMap = sysRoleMenuMapper.findRoleMenuByRoleId(roleId);
        if (menuMap == null) {
            menuMap = new HashMap<>();
        }
        return menuMap;
    }
}

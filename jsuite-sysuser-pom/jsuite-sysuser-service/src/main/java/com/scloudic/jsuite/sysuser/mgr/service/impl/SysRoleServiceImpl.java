package com.scloudic.jsuite.sysuser.mgr.service.impl;

import com.scloudic.jsuite.sysuser.mgr.entity.SysRole;
import com.scloudic.jsuite.sysuser.mgr.entity.SysRoleMenu;
import com.scloudic.jsuite.sysuser.mgr.mapper.SysRoleMapper;
import com.scloudic.jsuite.sysuser.mgr.mapper.SysRoleMenuMapper;
import com.scloudic.jsuite.sysuser.mgr.mapper.SysUserRoleMapper;
import com.scloudic.jsuite.sysuser.mgr.service.SysRoleService;
import com.scloudic.rabbitframework.core.exceptions.ServiceException;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.jbatis.service.IServiceImpl;
import com.scloudic.rabbitframework.core.utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysRoleServiceImpl extends
        IServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    private static final Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public SysRoleMapper getBaseMapper() {
        return sysRoleMapper;
    }

    /**
     * 根据角色编码获取角色信息
     *
     * @param roleCode
     * @return
     */
    @Override
    public SysRole getRoleByRoleCode(String roleCode) {
        Where whereParamType = new Where();
        Criteria criteria = whereParamType.createCriteria();
        criteria.andEqual(SysRole::getRoleCode, roleCode);
        List<SysRole> roles = sysRoleMapper.selectByParams(whereParamType);
        if (roles.size() > 0) {
            return roles.get(0);
        }
        return null;
    }

    /**
     * 保存角色
     *
     * @param role
     * @return
     */
    @Transactional
    @Override
    public int saveRole(SysRole role) {
        try {
            return sysRoleMapper.insertByEntity(role);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 修改角色
     *
     * @param role
     * @return
     */
    @Transactional
    @Override
    public int updateRole(SysRole role) {
        try {
            return sysRoleMapper.updateByEntity(role);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 删除角色,级联删除角色菜单{@link SysRoleMenu}
     *
     * @param roleId
     * @return
     */
    @Transactional
    @Override
    public int delRole(Long roleId) {
        try {
            Where roleMenuParam = new Where();
            Criteria criteria = roleMenuParam.createCriteria();
            criteria.andEqual(SysRoleMenu::getSysRoleId, roleId);
            sysRoleMenuMapper.deleteByParams(roleMenuParam);
            sysUserRoleMapper.deleteByParams(roleMenuParam);
            return sysRoleMapper.deleteById(roleId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<SysRole> findSysRoleByRoleIds(List<Long> roleIds) {
        Where paramType = new Where();
        Criteria criteria = paramType.createCriteria();
        criteria.andIn(SysRole::getSysRoleId, roleIds);
        return sysRoleMapper.selectByParams(paramType);
    }

    @Transactional
    @Override
    public void addRoleMenu(Long roleId, String roleCode, String[] menuIds) {
        try {
            Where roleMenuParam = new Where();
            Criteria criteria = roleMenuParam.createCriteria();
            criteria.andEqual(SysRoleMenu::getSysRoleId, roleId);
            sysRoleMenuMapper.deleteByParams(roleMenuParam);
            List<SysRoleMenu> roleMenus = new ArrayList<SysRoleMenu>();
            int menuIdSize = menuIds.length;
            for (int i = 0; i < menuIdSize; i++) {
                String menuId = menuIds[i];
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setSysRoleMenuId(UUIDUtils.getTimeUUID32());
                sysRoleMenu.setSysRoleId(roleId);
                sysRoleMenu.setRoleCode(roleCode);
                sysRoleMenu.setSysMenuId(menuId);
                roleMenus.add(sysRoleMenu);
            }
            if (roleMenus.size() > 0) {
                sysRoleMenuMapper.batchInsertEntity(roleMenus);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}

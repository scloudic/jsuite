package com.scloudic.jsuite.sysuser.mgr.service.impl;

import com.scloudic.jsuite.sysuser.mgr.entity.SysRole;
import com.scloudic.jsuite.sysuser.mgr.entity.SysUserRole;
import com.scloudic.jsuite.sysuser.mgr.mapper.SysUserRoleMapper;
import com.scloudic.jsuite.sysuser.mgr.service.SysUserRoleService;
import com.scloudic.rabbitframework.jbatis.service.IServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserRoleServiceImpl extends IServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public SysUserRoleMapper getBaseMapper() {
        return sysUserRoleMapper;
    }

    @Override
    public List<SysRole> findSysRoleByUserId(String sysUserId) {
        return sysUserRoleMapper.findSysRoleByUserId(sysUserId);
    }
}

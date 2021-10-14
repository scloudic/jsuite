package com.scloudic.jsuite.sysuser.mgr.service;

import com.scloudic.jsuite.sysuser.mgr.entity.SysRole;
import com.scloudic.jsuite.sysuser.mgr.entity.SysUserRole;
import com.scloudic.rabbitframework.jbatis.service.IService;

import java.util.List;

public interface SysUserRoleService extends IService<SysUserRole> {
    List<SysRole> findSysRoleByUserId(String sysUserId);
}

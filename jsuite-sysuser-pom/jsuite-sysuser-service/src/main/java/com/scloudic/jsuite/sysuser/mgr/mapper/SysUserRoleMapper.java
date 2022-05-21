package com.scloudic.jsuite.sysuser.mgr.mapper;

import com.scloudic.jsuite.sysuser.mgr.entity.SysRole;
import com.scloudic.jsuite.sysuser.mgr.entity.SysUserRole;
import com.scloudic.rabbitframework.jbatis.annontations.Mapper;
import com.scloudic.rabbitframework.jbatis.annontations.Param;
import com.scloudic.rabbitframework.jbatis.annontations.Select;
import com.scloudic.rabbitframework.jbatis.mapping.BaseMapper;

import java.util.List;

/**
 * database table sys_user_role mapper interface
 **/
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    @Select("select sr.* from sys_role sr,sys_user_role sur where sr.sys_role_id=sur.sys_role_id and sur.sys_user_id=#{sysUserId}")
    List<SysRole> findSysRoleByUserId(@Param("sysUserId") String sysUserId);
}

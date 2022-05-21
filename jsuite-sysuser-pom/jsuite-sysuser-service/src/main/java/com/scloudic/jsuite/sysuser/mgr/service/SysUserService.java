package com.scloudic.jsuite.sysuser.mgr.service;

import com.scloudic.jsuite.sysuser.mgr.entity.SysUser;
import com.scloudic.jsuite.sysuser.mgr.entity.SysUserRole;
import com.scloudic.rabbitframework.jbatis.service.IService;
import com.scloudic.rabbitframework.core.utils.PageBean;

import java.util.List;

/**
 * 用户信息服务接口
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 根据用户登录名，获取正在使用的用户
     *
     * @param loginName
     * @return
     */
    SysUser getSysUserByLoginName(String loginName);

    PageBean<SysUser> findUserInfoByParams(String name, String userPhone, Long pageNum, Long pageSize,
                                           Integer activeStatus, String startDate, String endDate, boolean showAdmin);

    int saveSysUserAndRoles(SysUser sysUser, List<Long> roleIds);

    int updateSysUserAndRoles(SysUser sysUser, List<SysUserRole> sysUserRole);

    int updateSysUser(SysUser sysUser);

    SysUser getSysUserByUserId(String userId, Integer activityStatus, Integer delStatus);

    SysUser getSysUserByUserId(String userId);
}

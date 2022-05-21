package com.scloudic.jsuite.mgr.web.test;

import com.scloudic.jsuite.sysuser.mgr.entity.SysUser;
import com.scloudic.jsuite.sysuser.mgr.service.SysUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest extends ApplicationTest {
    @Autowired
    private SysUserService sysUserService;
    @Test
    public void testInsertUser() {
        SysUser sysUser = new SysUser();
        sysUser.setSysUserId("11");
        sysUser.setLoginName("1111");
        sysUserService.saveSysUserAndRoles(sysUser, null);
    }
}

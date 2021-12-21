package com.scloudic.jsuite.mgr.web.security;

import com.scloudic.jsuite.sysuser.mgr.entity.SysMenu;
import com.scloudic.jsuite.sysuser.mgr.entity.SysUser;
import com.scloudic.jsuite.sysuser.mgr.service.SysMenuService;
import com.scloudic.jsuite.sysuser.mgr.service.SysUserService;
import com.scloudic.rabbitframework.core.utils.JsonUtils;
import com.scloudic.rabbitframework.core.utils.PasswordUtils;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.security.LoginFailException;
import com.scloudic.rabbitframework.security.SecurityUser;
import com.scloudic.rabbitframework.security.realm.SecurityAuthorizingRealm;
import com.scloudic.rabbitframework.security.realm.SecurityLoginToken;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("jsuiteRealm")
public class JsuiteRealm extends SecurityAuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(JsuiteRealm.class);
    @Lazy
    @Autowired
    private SysUserService sysUserService;
    @Lazy
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 执行权限操作，获取权限信息,在配有缓存时只调用一次
     *
     * @param securityUser
     * @return
     */
    @Override
    protected AuthorizationInfo executeGetAuthorizationInfo(SecurityUser securityUser) {
        logger.debug("executeGetAuthorizationInfo 权限加载开始");
        String userId = securityUser.getUserId();
        List<String> permissions = new ArrayList<String>();
        List<SysMenu> menus = sysMenuService.findUserRoleMenuByUserId(userId, null);
        int menuSize = menus.size();
        for (int i = 0; i < menuSize; i++) {
            String url = menus.get(i).getBackEndUrl();
            if (StringUtils.isNotBlank(url)) {
                permissions.add(url);
            }
        }
        logger.debug("用户权限：" + JsonUtils.toJson(permissions));
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 执行登陆操作，获取登陆信息
     *
     * @param securityLoginToken
     * @return
     */
    @Override
    protected AuthenticationInfo executeGetAuthenticationInfo(SecurityLoginToken securityLoginToken) {
        logger.info("用户登录!");
        String userName = securityLoginToken.getUsername();
        String password = new String(securityLoginToken.getPassword());
        SysUser userInfo = sysUserService.getSysUserByLoginName(userName);
        if (userInfo == null || (!PasswordUtils.verify(password, userInfo.getLoginPwd()))) {
            throw new LoginFailException("login.error");
        }
        char[] pwd = userInfo.getLoginPwd().toCharArray();
        SecurityUser securityUser = new SecurityUser();
        securityUser.setLoginName(userInfo.getLoginName());
        securityUser.setRealName(userInfo.getRealName());
        securityUser.setNickName(userInfo.getRealName());
        securityUser.setUserId(userInfo.getSysUserId());
        securityLoginToken.setPassword(pwd);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(securityUser, pwd, getName());
        return info;
    }
}

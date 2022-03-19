package com.scloudic.jsuite.sysuser.mgr.web.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:jsuite.properties")
public class JsuiteSysUserProperties {
    @Value("${sysuser.avatar.path:avatar}")
    private String avatarPath;
    @Value("${sysuser.showAdmin.list:false}")
    private boolean showAdmin;
    @Value("${sysuser.login.path:login}")
    private String loginPagePath;

    public String getLoginPagePath() {
        return loginPagePath;
    }

    public void setLoginPagePath(String loginPagePath) {
        this.loginPagePath = loginPagePath;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public boolean isShowAdmin() {
        return showAdmin;
    }

    public void setShowAdmin(boolean showAdmin) {
        this.showAdmin = showAdmin;
    }
}

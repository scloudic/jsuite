package com.scloudic.jsuite.mgr.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:jsuite.properties")
public class MgrJsuiteProperties {
    @Value("${mgr.admin.path}")
    private String adminPath;

    public String getAdminPath() {
        return adminPath;
    }

    public void setAdminPath(String adminPath) {
        this.adminPath = adminPath;
    }
}

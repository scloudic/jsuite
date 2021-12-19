package com.scloudic.jsuite.sysuser.mgr.web.template;

import com.scloudic.jsuite.sysuser.mgr.entity.SysUser;
import com.scloudic.jsuite.sysuser.mgr.service.SysUserService;
import com.scloudic.rabbitframework.security.SecurityUser;
import com.scloudic.rabbitframework.security.SecurityUtils;
import com.scloudic.rabbitframework.web.annotations.TemplateVariable;
import com.scloudic.rabbitframework.web.mvc.freemarker.TemplateDirective;
import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 获取用户信息标签
 *
 * @since 1.0
 */
@Component
@TemplateVariable("user")
public class UserTag extends TemplateDirective {
    @Autowired
    private SysUserService sysUserService;

    @Override
    public void render(Environment environment,
                       Map map, TemplateModel[] templateModels,
                       TemplateDirectiveBody templateDirectiveBody) throws Exception {
        BeansWrapper beansWrapper = getBeansWrapper();
        SecurityUser securityUser = SecurityUtils.getSecurityUser();
        boolean isLogin = true;
        if (securityUser == null) {
            isLogin = false;
        }
        String userId = securityUser.getUserId();
        SysUser sysUser = sysUserService.selectById(userId);
        TemplateModel isLoginModel = beansWrapper.wrap(isLogin);
        TemplateModel userModel = beansWrapper.wrap(sysUser);
        environment.setVariable("userInfo", userModel);
        environment.setVariable("isLogin", isLoginModel);
        templateDirectiveBody.render(environment.getOut());
    }
}

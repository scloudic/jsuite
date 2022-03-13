package com.scloudic.jsuite.sysuser.mgr.web.template;

import com.scloudic.rabbitframework.security.SecurityUtils;
import com.scloudic.rabbitframework.web.annotations.TemplateVariable;
import com.scloudic.rabbitframework.web.freemarker.TemplateDirective;
import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateModel;

import java.util.Map;

/**
 * 用户权限标签
 */
@TemplateVariable("permission")
public class PermissionTag extends TemplateDirective {
    @Override
    public void render(Environment environment,
                       Map map, TemplateModel[] templateModels,
                       TemplateDirectiveBody templateDirectiveBody) throws Exception {
        BeansWrapper beansWrapper = getBeansWrapper();
        SimpleScalar simpleScalar = (SimpleScalar) map.get("url");
        String value = simpleScalar.getAsString();
        boolean isPermission = SecurityUtils.getSubject().isPermitted(value);
        TemplateModel isPermissionModel = beansWrapper.wrap(isPermission);
        environment.setVariable("isPermission", isPermissionModel);
        templateDirectiveBody.render(environment.getOut());
    }
}

package com.scloudic.jsuite.mgr.web.template;

import com.scloudic.jsuite.sysuser.mgr.entity.SysRole;
import com.scloudic.jsuite.sysuser.mgr.service.SysRoleService;
import com.scloudic.rabbitframework.web.annotations.TemplateVariable;
import com.scloudic.rabbitframework.web.mvc.freemarker.TemplateDirective;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 获取所有角色信息
 *
 * @since 1.0
 */
@Component
@TemplateVariable("roleAll")
public class RoleAllTag extends TemplateDirective {
    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public void render(Environment environment, Map map,
                       TemplateModel[] templateModels,
                       TemplateDirectiveBody templateDirectiveBody) throws Exception {
        List<SysRole> sysRoles = sysRoleService.selectEntityAll();
        TemplateModel templateModel = getBeansWrapper().wrap(sysRoles);
        environment.setVariable("sysRoles", templateModel);
        templateDirectiveBody.render(environment.getOut());
    }
}

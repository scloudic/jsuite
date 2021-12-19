package com.scloudic.jsuite.sysuser.mgr.web.template;

import com.scloudic.jsuite.sysuser.mgr.web.component.HomeMenuComponent;
import com.scloudic.rabbitframework.web.annotations.TemplateVariable;
import com.scloudic.rabbitframework.web.mvc.freemarker.TemplateDirective;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateScalarModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 管理后台功能菜单标签,只能登录之后才能获取功能菜单数据
 *
 * @since 1.0
 */
@Component
@TemplateVariable("homeMenu")
public class HomeMenuTag extends TemplateDirective {
    @Autowired
    private HomeMenuComponent homeMenuComponent;

    @Override
    public void render(Environment environment, Map map,
                       TemplateModel[] templateModels,
                       TemplateDirectiveBody templateDirectiveBody) throws Exception {
        TemplateScalarModel templateNumberModel = (TemplateScalarModel) map.get("menuParentId");
        String parentId = templateNumberModel.getAsString();
        List<Map<String, Object>> result = homeMenuComponent.findRoleMenuByMenuParentId(parentId);
        TemplateModel templateModel = getBeansWrapper().wrap(result);
        environment.setVariable("menuInfos", templateModel);
        templateDirectiveBody.render(environment.getOut());
    }
}

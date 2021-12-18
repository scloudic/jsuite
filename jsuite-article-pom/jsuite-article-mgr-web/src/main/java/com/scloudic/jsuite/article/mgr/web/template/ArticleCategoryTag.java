package com.scloudic.jsuite.article.mgr.web.template;

import com.scloudic.rabbitframework.web.annotations.TemplateVariable;
import com.scloudic.rabbitframework.web.mvc.freemarker.TemplateDirective;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateModel;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 获取
 *
 * @since 1.0
 */
@Component
@TemplateVariable("articleCategory")
public class ArticleCategoryTag extends TemplateDirective {
    @Override
    public void render(Environment environment, Map map,
                       TemplateModel[] templateModels,
                       TemplateDirectiveBody templateDirectiveBody) throws Exception {

    }
}

package com.scloudic.jsuite.article.api.web.template;

import com.scloudic.jsuite.article.entity.ArticleCategory;
import com.scloudic.jsuite.article.service.ArticleCategoryService;
import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.security.SecurityUtils;
import com.scloudic.rabbitframework.web.annotations.TemplateVariable;
import com.scloudic.rabbitframework.web.mvc.freemarker.TemplateDirective;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateScalarModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取文章分类
 *
 * @since 1.0
 */
@Component
@TemplateVariable("articleCategory")
public class ArticleCategoryTag extends TemplateDirective {
    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Override
    public void render(Environment environment, Map map,
                       TemplateModel[] templateModels,
                       TemplateDirectiveBody templateDirectiveBody) throws Exception {
        TemplateScalarModel templateNumberModel = (TemplateScalarModel) map.get("articleId");
        List<ArticleCategory> articleCategories = new ArrayList<>();
        String articleId = "";
        if (templateNumberModel != null) {
            articleId = templateNumberModel.getAsString();
            if (StringUtils.isNotBlank(articleId)) {
                articleCategories = articleCategoryService.findArticleCategoryByArticleId(articleId);
            }
        }
        if (StringUtils.isBlank(articleId)) {
            Where where = new Where();
            Criteria criteria = where.createCriteria();
            criteria.andEqual(ArticleCategory::getDelStatus, Enums.DelStatus.NORMAL.getValue());
            articleCategories = articleCategoryService.selectByParams(where);
        }
        TemplateModel templateModel = getBeansWrapper().wrap(articleCategories);
        environment.setVariable("articleCategories", templateModel);
        templateDirectiveBody.render(environment.getOut());
    }
}

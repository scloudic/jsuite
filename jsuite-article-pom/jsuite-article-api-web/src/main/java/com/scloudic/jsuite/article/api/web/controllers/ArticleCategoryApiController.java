package com.scloudic.jsuite.article.api.web.controllers;

import com.scloudic.jsuite.article.entity.ArticleCategory;
import com.scloudic.jsuite.article.service.ArticleCategoryService;
import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import com.scloudic.rabbitframework.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jsuite/api/articleCategory")
public class ArticleCategoryApiController extends AbstractRabbitController {
    @Autowired
    private ArticleCategoryService articleCategoryService;

    @GetMapping("list")
    public Result<List<ArticleCategory>> list() {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        criteria.andEqual(ArticleCategory::getDelStatus, Enums.DelStatus.NORMAL.getValue());
        List<ArticleCategory> articleCategories = articleCategoryService.selectByParams(where);
        return success(articleCategories);
    }
}

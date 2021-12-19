package com.scloudic.jsuite.article.api.web.controllers;

import com.scloudic.jsuite.article.entity.ArticleCategory;
import com.scloudic.jsuite.article.service.ArticleCategoryService;
import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.security.authz.annotation.UriPermissions;
import com.scloudic.rabbitframework.web.AbstractContextResource;
import com.scloudic.rabbitframework.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Component
@Path("/jsuite/api/articleCategory")
@Singleton
public class ArticleCategoryApiController extends AbstractContextResource {
    @Autowired
    private ArticleCategoryService articleCategoryService;

    @GET
    @Path("list")
    public Result<List<ArticleCategory>> list() {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        criteria.andEqual(ArticleCategory::getDelStatus, Enums.DelStatus.NORMAL.getValue());
        List<ArticleCategory> articleCategories = articleCategoryService.selectByParams(where);
        return success(articleCategories);
    }
}

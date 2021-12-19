package com.scloudic.jsuite.article.service;

import com.scloudic.jsuite.article.entity.Article;
import com.scloudic.jsuite.article.entity.ArticleCategoryMapping;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.jbatis.service.IService;

import java.util.List;

public interface ArticleService extends IService<Article> {
    public void save(Article article, List<ArticleCategoryMapping> categoryMappings);

    public void updateArticle(Article article, List<ArticleCategoryMapping> categoryMappings);
}


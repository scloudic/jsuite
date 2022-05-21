package com.scloudic.jsuite.article.service;

import com.scloudic.jsuite.article.entity.ArticleCategory;
import com.scloudic.rabbitframework.jbatis.service.IService;

import java.util.List;

public interface ArticleCategoryService extends IService<ArticleCategory> {
    /**
     * 获取所有分类,并通过文章主键判断分类是否为选中状态
     *
     * @param articleId 文章主键
     * @return list
     */
    public List<ArticleCategory> findArticleCategoryByArticleId(String articleId);
}


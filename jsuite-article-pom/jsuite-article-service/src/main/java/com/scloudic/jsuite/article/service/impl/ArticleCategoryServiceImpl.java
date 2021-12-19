package com.scloudic.jsuite.article.service.impl;

import com.scloudic.jsuite.article.entity.ArticleCategory;
import com.scloudic.jsuite.article.mapper.ArticleCategoryMapper;
import com.scloudic.jsuite.article.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scloudic.rabbitframework.jbatis.service.IServiceImpl;

import java.util.List;

@Service
public class ArticleCategoryServiceImpl extends IServiceImpl<ArticleCategoryMapper, ArticleCategory>
        implements ArticleCategoryService {
    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;

    @Override
    public ArticleCategoryMapper getBaseMapper() {
        return articleCategoryMapper;
    }

    @Override
    public List<ArticleCategory> findArticleCategoryByArticleId(String articleId) {
        return articleCategoryMapper.findArticleCategoryByArticleId(articleId);
    }
}


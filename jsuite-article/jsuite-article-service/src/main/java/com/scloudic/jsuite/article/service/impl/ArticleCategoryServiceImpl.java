package com.scloudic.jsuite.article.service.impl;

import com.scloudic.jsuite.article.entity.ArticleCategory;
import com.scloudic.jsuite.article.mapper.ArticleCategoryMapper;
import com.scloudic.jsuite.article.service.ArticleCategoryService;
import com.scloudic.rabbitframework.jbatis.service.IServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleCategoryServiceImpl extends IServiceImpl<ArticleCategoryMapper,ArticleCategory> implements ArticleCategoryService {
    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;
    @Override
    public ArticleCategoryMapper getBaseMapper() {
        return articleCategoryMapper;
    }
}


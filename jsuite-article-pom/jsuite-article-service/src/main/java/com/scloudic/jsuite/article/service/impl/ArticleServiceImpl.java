package com.scloudic.jsuite.article.service.impl;

import com.scloudic.jsuite.article.entity.Article;
import com.scloudic.jsuite.article.entity.ArticleCategoryMapping;
import com.scloudic.jsuite.article.mapper.ArticleCategoryMappingMapper;
import com.scloudic.jsuite.article.mapper.ArticleMapper;
import com.scloudic.jsuite.article.service.ArticleService;
import com.scloudic.rabbitframework.core.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scloudic.rabbitframework.jbatis.service.IServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleServiceImpl extends IServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleCategoryMappingMapper articleCategoryMappingMapper;

    @Override
    public ArticleMapper getBaseMapper() {
        return articleMapper;
    }


    @Transactional
    @Override
    public void save(Article article, List<ArticleCategoryMapping> categoryMappings) {
        articleMapper.insertByEntity(article);
        articleCategoryMappingMapper.batchInsertEntity(categoryMappings);
    }
}


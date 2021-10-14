package com.scloudic.jsuite.article.service.impl;

import com.scloudic.jsuite.article.entity.Article;
import com.scloudic.jsuite.article.mapper.ArticleMapper;
import com.scloudic.jsuite.article.service.ArticleService;
import com.scloudic.rabbitframework.jbatis.service.IServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends IServiceImpl<ArticleMapper,Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public ArticleMapper getBaseMapper() {
        return articleMapper;
    }
}


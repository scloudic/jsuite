package com.scloudic.jsuite.article.service;

import com.scloudic.jsuite.article.entity.Article;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.jbatis.service.IService;

public interface ArticleService extends IService<Article> {
    public PageBean<Article> listArticle(String searchKey, Long pageNum, Long pageSize);
}


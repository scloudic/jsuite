package com.scloudic.jsuite.article.service.impl;
import com.scloudic.jsuite.article.entity.ArticleComment;
import com.scloudic.jsuite.article.mapper.ArticleCommentMapper;
import com.scloudic.jsuite.article.service.ArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scloudic.rabbitframework.jbatis.service.IServiceImpl;
@Service
public class ArticleCommentServiceImpl extends IServiceImpl<ArticleCommentMapper,ArticleComment> implements ArticleCommentService {
    @Autowired
    private ArticleCommentMapper articleCommentMapper;
    @Override
    public ArticleCommentMapper getBaseMapper() {
        return articleCommentMapper;
    }
}


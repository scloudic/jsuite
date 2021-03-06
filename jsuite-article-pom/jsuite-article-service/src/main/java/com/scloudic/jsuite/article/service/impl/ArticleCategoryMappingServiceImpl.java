package com.scloudic.jsuite.article.service.impl;
import com.scloudic.jsuite.article.entity.ArticleCategoryMapping;
import com.scloudic.jsuite.article.mapper.ArticleCategoryMappingMapper;
import com.scloudic.jsuite.article.service.ArticleCategoryMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scloudic.rabbitframework.jbatis.service.IServiceImpl;
@Service
public class ArticleCategoryMappingServiceImpl extends IServiceImpl<ArticleCategoryMappingMapper,ArticleCategoryMapping> implements ArticleCategoryMappingService {
    @Autowired
    private ArticleCategoryMappingMapper articleCategoryMappingMapper;
    @Override
    public ArticleCategoryMappingMapper getBaseMapper() {
        return articleCategoryMappingMapper;
    }
}


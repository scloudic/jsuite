package com.scloudic.jsuite.article.service.impl;
import com.scloudic.jsuite.article.entity.UserArticleFavorite;
import com.scloudic.jsuite.article.mapper.UserArticleFavoriteMapper;
import com.scloudic.jsuite.article.service.UserArticleFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scloudic.rabbitframework.jbatis.service.IServiceImpl;
@Service
public class UserArticleFavoriteServiceImpl extends IServiceImpl<UserArticleFavoriteMapper,UserArticleFavorite> implements UserArticleFavoriteService {
    @Autowired
    private UserArticleFavoriteMapper userArticleFavoriteMapper;
    @Override
    public UserArticleFavoriteMapper getBaseMapper() {
        return userArticleFavoriteMapper;
    }
}


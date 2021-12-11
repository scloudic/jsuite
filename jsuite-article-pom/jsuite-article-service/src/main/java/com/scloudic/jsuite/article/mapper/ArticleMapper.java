package com.scloudic.jsuite.article.mapper;

import com.scloudic.jsuite.article.entity.Article;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.jbatis.annontations.Select;
import com.scloudic.rabbitframework.jbatis.mapping.BaseMapper;
import com.scloudic.rabbitframework.jbatis.annontations.Mapper;
import com.scloudic.rabbitframework.jbatis.mapping.RowBounds;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    @Select("select ar.*,ac.article_category_name from article ar,article_category_mapping acm,article_category ac " +
            "where ar.article_id=acm.article_id and acm.article_category_id=ac.article_category_id ")
    public List<Article> listArticleJoinCategory(Where where, RowBounds rowBounds);

    @Select("select count(*) count from article ar,article_category_mapping acm,article_category ac " +
            "where ar.article_id=acm.article_id and acm.article_category_id=ac.article_category_id ")
    public Long countArticleJoinCategory(Where where);
}

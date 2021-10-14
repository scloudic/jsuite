package com.scloudic.jsuite.article.mapper;
import com.scloudic.jsuite.article.entity.Article;
import com.scloudic.rabbitframework.jbatis.mapping.BaseMapper;
import com.scloudic.rabbitframework.jbatis.annontations.Mapper;
/**
* database table article mapper interface
**/
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}

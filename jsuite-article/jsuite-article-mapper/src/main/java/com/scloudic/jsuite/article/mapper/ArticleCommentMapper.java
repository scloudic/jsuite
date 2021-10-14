package com.scloudic.jsuite.article.mapper;
import com.scloudic.jsuite.article.entity.ArticleComment;
import com.scloudic.rabbitframework.jbatis.mapping.BaseMapper;
import com.scloudic.rabbitframework.jbatis.annontations.Mapper;
/**
* database table article_comment mapper interface
**/
@Mapper
public interface ArticleCommentMapper extends BaseMapper<ArticleComment> {

}

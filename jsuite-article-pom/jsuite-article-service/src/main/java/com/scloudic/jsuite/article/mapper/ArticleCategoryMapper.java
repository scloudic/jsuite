package com.scloudic.jsuite.article.mapper;

import com.scloudic.jsuite.article.entity.ArticleCategory;
import com.scloudic.rabbitframework.jbatis.annontations.Param;
import com.scloudic.rabbitframework.jbatis.annontations.Select;
import com.scloudic.rabbitframework.jbatis.mapping.BaseMapper;
import com.scloudic.rabbitframework.jbatis.annontations.Mapper;

import java.util.List;

@Mapper
public interface ArticleCategoryMapper extends BaseMapper<ArticleCategory> {
    /**
     * 获取所有分类,并通过文章主键判断分类是否为选中状态
     *
     * @param articleId 文章主键
     * @return list
     */
    @Select("select distinct ca.article_category_id,ca.article_category_name," +
            "case when cm.article_id is not null then 1 else 2 end article_check_status from article_category ca " +
            "left join article_category_mapping cm on ca.article_category_id=cm.article_category_id and cm.article_id=#{articleId} " +
            "where ca.del_status=1 ")
    public List<ArticleCategory> findArticleCategoryByArticleId(@Param("articleId") String articleId);
}

package com.scloudic.jsuite.article.entity;
import com.scloudic.rabbitframework.jbatis.annontations.*;
import java.io.Serializable;

@Table
public class ArticleCategoryMapping implements Serializable {
	private static final long serialVersionUID = 1L;
    public static final String FIELDS = " article_category_mapping_id,article_id,article_category_id ";

    /**
    *
    * 文章分类关联主键
    *
    */
    @ID
    private Long articleCategoryMappingId;

    /**
    *
    * 文章主键
    *
    */
    @Column
    private String articleId;

    /**
    *
    * 文章分类主键
    *
    */
    @Column
    private Long articleCategoryId;

    public void setArticleCategoryMappingId(Long articleCategoryMappingId) {
        this.articleCategoryMappingId = articleCategoryMappingId;
    }

    public Long getArticleCategoryMappingId() {
        return articleCategoryMappingId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleCategoryId(Long articleCategoryId) {
        this.articleCategoryId = articleCategoryId;
    }

    public Long getArticleCategoryId() {
        return articleCategoryId;
    }

}

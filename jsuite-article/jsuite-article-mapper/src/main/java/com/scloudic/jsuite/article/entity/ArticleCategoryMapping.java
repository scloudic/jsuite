package com.scloudic.jsuite.article.entity;
import com.scloudic.rabbitframework.jbatis.annontations.*;
import java.io.Serializable;

/**
* This class corresponds to the database table article_category_mapping
*/
@Table
public class ArticleCategoryMapping implements Serializable {
	private static final long serialVersionUID = 1L;
    public static final String FIELDS = " article_category_mapping_id,article_id,article_category_id ";
    /**
    * This field corresponds to the database column article_category_mapping.article_category_mapping_id
    * <p>
    * description:文章分类关联主键
    */
    @ID
    private Long articleCategoryMappingId;

    /**
    * This field corresponds to the database column article_category_mapping.article_id
    * <p>
    * description:文章主键
    */
    @Column
    private String articleId;

    /**
    * This field corresponds to the database column article_category_mapping.article_category_id
    * <p>
    * description:文章分类主键
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

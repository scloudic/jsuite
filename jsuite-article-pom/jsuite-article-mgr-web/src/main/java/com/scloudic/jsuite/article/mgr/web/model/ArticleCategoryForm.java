package com.scloudic.jsuite.article.mgr.web.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ArticleCategoryForm {
    /**
     * 文章分类主键
     */
    @NotNull
    private Long articleCategoryId;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 图标地址
     */
    private String iconPath;

    /**
     * 分类类型(1、资讯、2、专题)
     */
    private Integer categoryType = 1;

    private Integer commentStatus = 1;
    private Integer sortNum = 0;
    /**
     * 文章分类父主键
     */
    @NotNull
    private Long articleCategoryParentId;

    /**
     * 文章分类名称
     */
    @NotBlank
    private String articleCategoryName;

    /**
     * 内容
     */
    private String content;

    public Long getArticleCategoryId() {
        return articleCategoryId;
    }

    public void setArticleCategoryId(Long articleCategoryId) {
        this.articleCategoryId = articleCategoryId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    public Long getArticleCategoryParentId() {
        return articleCategoryParentId;
    }

    public void setArticleCategoryParentId(Long articleCategoryParentId) {
        this.articleCategoryParentId = articleCategoryParentId;
    }

    public String getArticleCategoryName() {
        return articleCategoryName;
    }

    public void setArticleCategoryName(String articleCategoryName) {
        this.articleCategoryName = articleCategoryName;
    }

    public String getContent() {
        return content;
    }

    public Integer getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
package com.scloudic.jsuite.article.mgr.web.model;

import javax.validation.constraints.NotNull;

public class ArticleCategoryDelDto {
    @NotNull
    private Long articleCategoryId;

    public Long getArticleCategoryId() {
        return articleCategoryId;
    }

    public void setArticleCategoryId(Long articleCategoryId) {
        this.articleCategoryId = articleCategoryId;
    }
}

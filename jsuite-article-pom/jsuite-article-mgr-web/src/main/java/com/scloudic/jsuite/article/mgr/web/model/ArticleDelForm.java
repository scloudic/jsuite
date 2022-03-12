package com.scloudic.jsuite.article.mgr.web.model;

import javax.validation.constraints.NotBlank;

public class ArticleDelForm {
    @NotBlank
    private String articleId;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
}

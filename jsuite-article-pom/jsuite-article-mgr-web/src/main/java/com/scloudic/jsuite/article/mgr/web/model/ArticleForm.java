package com.scloudic.jsuite.article.mgr.web.model;

import javax.validation.constraints.NotBlank;
import javax.ws.rs.FormParam;

public class ArticleForm {
    @NotBlank
    @FormParam("articleId")
    private String articleId;
    @FormParam("summary")
    private String summary;
    @FormParam("articleContent")
    private String articleContent;
    @FormParam("userName")
    private String userName;
    @FormParam("commentStatus")
    private Integer commentStatus = 1;
    @FormParam("linkTo")
    private String linkTo;
    @NotBlank
    @FormParam("articleTitle")
    private String articleTitle;
    @FormParam("thumbnailPath")
    private String thumbnailPath;
    @FormParam("editMode")
    private String editMode = "html";
    @FormParam("sortNum")
    private Integer sortNum = 0;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getLinkTo() {
        return linkTo;
    }

    public void setLinkTo(String linkTo) {
        this.linkTo = linkTo;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }
}

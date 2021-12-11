package com.scloudic.jsuite.article.entity;
import com.scloudic.rabbitframework.jbatis.mapping.GenerationType;
import java.util.Date;
import java.beans.Transient;
import com.scloudic.rabbitframework.jbatis.annontations.*;
import java.io.Serializable;

@Table
public class Article implements Serializable {
	private static final long serialVersionUID = 1L;
    public static final String FIELDS = " article_id,summary,comment_count,create_time,article_content,user_name,comment_status,link_to,article_title,thumbnail_path,update_time,article_status,user_id,sort_num,edit_mode,view_count ";
    public static final String NO_CONTENT_FIELDS = " article_id,summary,comment_count,create_time,user_name,comment_status,link_to,article_title,thumbnail_path,update_time,article_status,user_id,sort_num,edit_mode,view_count ";

    /**
    *
    * 文章主键
    *
    */
    @ID(keyType = GenerationType.MANUAL)
    private String articleId;

    /**
    *
    * 摘要
    *
    */
    @Column
    private String summary;

    /**
    *
    * 评论总数
    *
    */
    @Column
    private Integer commentCount;

    /**
    *
    * 创建时间
    *
    */
    @Column
    private Date createTime;

    /**
    *
    * 文章内容
    *
    */
    @Column
    private String articleContent;

    /**
    *
    * 作者
    *
    */
    @Column
    private String userName;

    /**
    *
    * 删除状态(1、正常,2、删除)
    *
    */
    @Column
    private Integer delStatus;

    /**
    *
    * 评论状态(1、允许评论，2、不允许评论)
    *
    */
    @Column
    private Integer commentStatus;

    /**
    *
    * 外部链接
    *
    */
    @Column
    private String linkTo;

    /**
    *
    * 文章标题
    *
    */
    @Column
    private String articleTitle;

    /**
    *
    * 缩略图地址(json)
    *
    */
    @Column
    private String thumbnailPath;

    /**
    *
    * 最后一次修改时间
    *
    */
    @Column
    private Date updateTime;

    /**
    *
    * 文章状态(1、已发布,2、草稿)
    *
    */
    @Column
    private Integer articleStatus;

    /**
    *
    * 发布人
    *
    */
    @Column
    private String userId;

    /**
    *
    * 排序编号
    *
    */
    @Column
    private Integer sortNum;

    /**
    *
    * 编辑模式(html,markdown)
    *
    */
    @Column
    private String editMode;

    /**
    *
    * 访问量
    *
    */
    @Column
    private Integer viewCount;

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    @Transient
    public Integer getDelStatus() {
        return delStatus;
    }

    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Integer getCommentStatus() {
        return commentStatus;
    }

    public void setLinkTo(String linkTo) {
        this.linkTo = linkTo;
    }

    public String getLinkTo() {
        return linkTo;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setArticleStatus(Integer articleStatus) {
        this.articleStatus = articleStatus;
    }

    public Integer getArticleStatus() {
        return articleStatus;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setEditMode(String editMode) {
        this.editMode = editMode;
    }

    public String getEditMode() {
        return editMode;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

}

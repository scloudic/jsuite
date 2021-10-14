package com.scloudic.jsuite.article.entity;
import java.util.Date;
import java.beans.Transient;
import com.scloudic.rabbitframework.jbatis.mapping.GenerationType;
import com.scloudic.rabbitframework.jbatis.annontations.*;
import java.io.Serializable;

/**
* This class corresponds to the database table article
*/
@Table
public class Article implements Serializable {
	private static final long serialVersionUID = 1L;
    public static final String FIELDS = " article_id,summary,comment_count,create_time,article_content,comment_status,link_to,article_title,thumbnail_path,update_time,article_status,user_id,sort_num,edit_mode,biz_id,view_count ";

    /**
    * This field corresponds to the database column article.article_id
    * <p>
    * description:文章主键
    */
    @ID(keyType = GenerationType.MANUAL)
    private String articleId;

    /**
    * This field corresponds to the database column article.summary
    * <p>
    * description:摘要
    */
    @Column
    private String summary;

    /**
    * This field corresponds to the database column article.comment_count
    * <p>
    * description:评论总数
    */
    @Column
    private Integer commentCount;

    /**
    * This field corresponds to the database column article.create_time
    * <p>
    * description:创建时间
    */
    @Column
    private Date createTime;

    /**
    * This field corresponds to the database column article.article_content
    * <p>
    * description:文章内容
    */
    @Column
    private String articleContent;

    /**
    * This field corresponds to the database column article.del_status
    * <p>
    * description:删除状态(1、正常,2、删除)
    */
    @Column
    private Integer delStatus;

    /**
    * This field corresponds to the database column article.comment_status
    * <p>
    * description:评论状态(1、允许评论，2、不允许评论)
    */
    @Column
    private Integer commentStatus;

    /**
    * This field corresponds to the database column article.link_to
    * <p>
    * description:外部链接
    */
    @Column
    private String linkTo;

    /**
    * This field corresponds to the database column article.article_title
    * <p>
    * description:文章标题
    */
    @Column
    private String articleTitle;

    /**
    * This field corresponds to the database column article.thumbnail_path
    * <p>
    * description:缩略图地址(json)
    */
    @Column
    private String thumbnailPath;

    /**
    * This field corresponds to the database column article.update_time
    * <p>
    * description:最后一次修改时间
    */
    @Column
    private Date updateTime;

    /**
    * This field corresponds to the database column article.article_status
    * <p>
    * description:文章状态(1、已发布,2、草稿)
    */
    @Column
    private Integer articleStatus;

    /**
    * This field corresponds to the database column article.user_id
    * <p>
    * description:发布人
    */
    @Column
    private String userId;

    /**
    * This field corresponds to the database column article.sort_num
    * <p>
    * description:排序编号
    */
    @Column
    private Integer sortNum;

    /**
    * This field corresponds to the database column article.edit_mode
    * <p>
    * description:编辑模式(html,markdown)
    */
    @Column
    private String editMode;

    /**
    * This field corresponds to the database column article.biz_id
    * <p>
    * description:所属商家
    */
    @Column
    private String bizId;

    /**
    * This field corresponds to the database column article.view_count
    * <p>
    * description:访问量
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

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getBizId() {
        return bizId;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

}

package com.scloudic.jsuite.article.entity;
import com.scloudic.rabbitframework.jbatis.mapping.GenerationType;
import java.util.Date;
import com.scloudic.rabbitframework.jbatis.annontations.*;
import java.io.Serializable;

@Table
public class UserArticleFavorite implements Serializable {
	private static final long serialVersionUID = 1L;
    public static final String FIELDS = " article_favorite_id,article_id,summary,article_title,thumbnail_path,create_time,user_id ";

    /**
    *
    * 用户文章收藏主键
    *
    */
    @ID(keyType = GenerationType.MANUAL)
    private String articleFavoriteId;

    /**
    *
    * 文章主键
    *
    */
    @Column
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
    * 创建时间
    *
    */
    @Column
    private Date createTime;

    /**
    *
    * 收藏人
    *
    */
    @Column
    private String userId;

    public void setArticleFavoriteId(String articleFavoriteId) {
        this.articleFavoriteId = articleFavoriteId;
    }

    public String getArticleFavoriteId() {
        return articleFavoriteId;
    }

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

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

}

package com.scloudic.jsuite.article.entity;
import java.util.Date;
import com.scloudic.rabbitframework.jbatis.mapping.GenerationType;
import com.scloudic.rabbitframework.jbatis.annontations.*;
import java.io.Serializable;

/**
* This class corresponds to the database table user_article_favorite
*/
@Table
public class UserArticleFavorite implements Serializable {
	private static final long serialVersionUID = 1L;
    public static final String FIELDS = " article_favorite_id,article_id,summary,article_title,thumbnail_path,create_time,user_id ";
    /**
    * This field corresponds to the database column user_article_favorite.article_favorite_id
    * <p>
    * description:用户文章收藏主键
    */
    @ID(keyType = GenerationType.MANUAL)
    private String articleFavoriteId;

    /**
    * This field corresponds to the database column user_article_favorite.article_id
    * <p>
    * description:文章主键
    */
    @Column
    private String articleId;

    /**
    * This field corresponds to the database column user_article_favorite.summary
    * <p>
    * description:摘要
    */
    @Column
    private String summary;

    /**
    * This field corresponds to the database column user_article_favorite.article_title
    * <p>
    * description:文章标题
    */
    @Column
    private String articleTitle;

    /**
    * This field corresponds to the database column user_article_favorite.thumbnail_path
    * <p>
    * description:缩略图地址(json)
    */
    @Column
    private String thumbnailPath;

    /**
    * This field corresponds to the database column user_article_favorite.create_time
    * <p>
    * description:创建时间
    */
    @Column
    private Date createTime;

    /**
    * This field corresponds to the database column user_article_favorite.user_id
    * <p>
    * description:收藏人
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

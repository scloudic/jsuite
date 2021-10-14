package com.scloudic.jsuite.article.entity;
import java.util.Date;
import java.beans.Transient;
import com.scloudic.rabbitframework.jbatis.annontations.*;
import java.io.Serializable;

/**
* This class corresponds to the database table article_category
*/
@Table
public class ArticleCategory implements Serializable {
	private static final long serialVersionUID = 1L;
    public static final String FIELDS = " article_category_id,summary,update_time,create_time,user_id,icon_path,category_type,article_category_parent_id,article_category_name,biz_id,content ";

    /**
    * This field corresponds to the database column article_category.article_category_id
    * <p>
    * description:文章分类主键
    */
    @ID
    private Long articleCategoryId;

    /**
    * This field corresponds to the database column article_category.summary
    * <p>
    * description:摘要
    */
    @Column
    private String summary;

    /**
    * This field corresponds to the database column article_category.update_time
    * <p>
    * description:修改时间
    */
    @Column
    private Date updateTime;

    /**
    * This field corresponds to the database column article_category.create_time
    * <p>
    * description:创建时间
    */
    @Column
    private Date createTime;

    /**
    * This field corresponds to the database column article_category.user_id
    * <p>
    * description:创建人
    */
    @Column
    private String userId;

    /**
    * This field corresponds to the database column article_category.icon_path
    * <p>
    * description:图标地址
    */
    @Column
    private String iconPath;

    /**
    * This field corresponds to the database column article_category.category_type
    * <p>
    * description:分类类型(1、资讯、2、专题)
    */
    @Column
    private Integer categoryType;

    /**
    * This field corresponds to the database column article_category.article_category_parent_id
    * <p>
    * description:文章分类父主键
    */
    @Column
    private Long articleCategoryParentId;

    /**
    * This field corresponds to the database column article_category.article_category_name
    * <p>
    * description:文章分类名称
    */
    @Column
    private String articleCategoryName;

    /**
    * This field corresponds to the database column article_category.del_status
    * <p>
    * description:删除状态(1、正常,2、删除)
    */
    @Column
    private Integer delStatus;

    /**
    * This field corresponds to the database column article_category.biz_id
    * <p>
    * description:所属商家
    */
    @Column
    private String bizId;

    /**
    * This field corresponds to the database column article_category.content
    * <p>
    * description:内容
    */
    @Column
    private String content;

    public void setArticleCategoryId(Long articleCategoryId) {
        this.articleCategoryId = articleCategoryId;
    }

    public Long getArticleCategoryId() {
        return articleCategoryId;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
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

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setArticleCategoryParentId(Long articleCategoryParentId) {
        this.articleCategoryParentId = articleCategoryParentId;
    }

    public Long getArticleCategoryParentId() {
        return articleCategoryParentId;
    }

    public void setArticleCategoryName(String articleCategoryName) {
        this.articleCategoryName = articleCategoryName;
    }

    public String getArticleCategoryName() {
        return articleCategoryName;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    @Transient
    public Integer getDelStatus() {
        return delStatus;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getBizId() {
        return bizId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}

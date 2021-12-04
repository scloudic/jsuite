package com.scloudic.jsuite.article.entity;
import java.util.Date;
import java.beans.Transient;
import com.scloudic.rabbitframework.jbatis.annontations.*;
import java.io.Serializable;

@Table
public class ArticleCategory implements Serializable {
	private static final long serialVersionUID = 1L;
    public static final String FIELDS = " article_category_id,summary,update_time,create_time,user_id,icon_path,category_type,article_category_parent_id,article_category_name,content ";

    /**
    *
    * 文章分类主键
    *
    */
    @ID
    private Long articleCategoryId;

    /**
    *
    * 摘要
    *
    */
    @Column
    private String summary;

    /**
    *
    * 修改时间
    *
    */
    @Column
    private Date updateTime;

    /**
    *
    * 创建时间
    *
    */
    @Column
    private Date createTime;

    /**
    *
    * 创建人
    *
    */
    @Column
    private String userId;

    /**
    *
    * 图标地址
    *
    */
    @Column
    private String iconPath;

    /**
    *
    * 分类类型(1、资讯、2、专题)
    *
    */
    @Column
    private Integer categoryType;

    /**
    *
    * 文章分类父主键
    *
    */
    @Column
    private Long articleCategoryParentId;

    /**
    *
    * 文章分类名称
    *
    */
    @Column
    private String articleCategoryName;

    /**
    *
    * 删除状态(1、正常,2、删除)
    *
    */
    @Column
    private Integer delStatus;

    /**
    *
    * 内容
    *
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

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}

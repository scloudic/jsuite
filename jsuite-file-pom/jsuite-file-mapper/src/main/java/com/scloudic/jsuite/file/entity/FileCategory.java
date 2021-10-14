package com.scloudic.jsuite.file.entity;
import com.scloudic.rabbitframework.jbatis.mapping.GenerationType;
import java.util.Date;
import com.scloudic.rabbitframework.jbatis.annontations.*;
import java.io.Serializable;

/**
* This class corresponds to the database table file_category
*/
@Table
public class FileCategory implements Serializable {
	private static final long serialVersionUID = 1L;
    public static final String FIELDS = " file_category_id,create_time,user_id,sort_num,file_category_name ";

    /**
    * This field corresponds to the database column file_category.file_category_id
    * <p>
    * description:文件分类主键
    */
    @ID(keyType = GenerationType.MANUAL)
    private String fileCategoryId;

    /**
    * This field corresponds to the database column file_category.create_time
    * <p>
    * description:创建时间
    */
    @Column
    private Date createTime;

    /**
    * This field corresponds to the database column file_category.user_id
    * <p>
    * description:创建人主键
    */
    @Column
    private String userId;

    /**
    * This field corresponds to the database column file_category.sort_num
    * <p>
    * description:排序序号
    */
    @Column
    private Integer sortNum;

    /**
    * This field corresponds to the database column file_category.file_category_name
    * <p>
    * description:文件分类名称
    */
    @Column
    private String fileCategoryName;
    @Column
    private String fileCategoryNameCode;

    public void setFileCategoryId(String fileCategoryId) {
        this.fileCategoryId = fileCategoryId;
    }

    public String getFileCategoryId() {
        return fileCategoryId;
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

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setFileCategoryName(String fileCategoryName) {
        this.fileCategoryName = fileCategoryName;
    }

    public String getFileCategoryName() {
        return fileCategoryName;
    }

    public String getFileCategoryNameCode() {
        return fileCategoryNameCode;
    }

    public void setFileCategoryNameCode(String fileCategoryNameCode) {
        this.fileCategoryNameCode = fileCategoryNameCode;
    }
}

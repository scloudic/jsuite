package com.scloudic.jsuite.file.entity;

import com.scloudic.rabbitframework.jbatis.annontations.Column;
import com.scloudic.rabbitframework.jbatis.annontations.ID;
import com.scloudic.rabbitframework.jbatis.annontations.Table;
import com.scloudic.rabbitframework.jbatis.mapping.GenerationType;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;

/**
* This class corresponds to the database table file_info
*/
@Table
public class FileInfo implements Serializable {
	private static final long serialVersionUID = 1L;
    public static final String FIELDS = " file_id,file_path,src_file_name,create_time,user_id,file_type,file_name,file_store_type,file_category_id,http_url ";

    /**
    * This field corresponds to the database column file_info.file_id
    * <p>
    * description:文件主键
    */
    @ID(keyType = GenerationType.MANUAL)
    private String fileId;

    /**
    * This field corresponds to the database column file_info.file_path
    * <p>
    * description:文件内部路径
    */
    @Column
    private String filePath;

    /**
    * This field corresponds to the database column file_info.src_file_name
    * <p>
    * description:源文件名
    */
    @Column
    private String srcFileName;

    /**
    * This field corresponds to the database column file_info.create_time
    * <p>
    * description:创建时间
    */
    @Column
    private Date createTime;

    /**
    * This field corresponds to the database column file_info.user_id
    * <p>
    * description:上传者主键
    */
    @Column
    private String userId;

    /**
    * This field corresponds to the database column file_info.file_type
    * <p>
    * description:文件类型
    */
    @Column
    private String fileType;

    /**
    * This field corresponds to the database column file_info.file_name
    * <p>
    * description:文件名
    */
    @Column
    private String fileName;

    /**
    * This field corresponds to the database column file_info.file_store_type
    * <p>
    * description:文件存储类型(如本地,阿里云)
    */
    @Column
    private String fileStoreType;

    /**
    * This field corresponds to the database column file_info.del_status
    * <p>
    * description:删除状态(1、正常,2、删除)
    */
    @Column
    private Integer delStatus;

    /**
    * This field corresponds to the database column file_info.file_category_id
    * <p>
    * description:文件分类主键
    */
    @Column
    private String fileCategoryId;

    /**
    * This field corresponds to the database column file_info.http_url
    * <p>
    * description:请求地址
    */
    @Column
    private String httpUrl;

    private String categoryName;

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setSrcFileName(String srcFileName) {
        this.srcFileName = srcFileName;
    }

    public String getSrcFileName() {
        return srcFileName;
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

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileStoreType(String fileStoreType) {
        this.fileStoreType = fileStoreType;
    }

    public String getFileStoreType() {
        return fileStoreType;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    @Transient
    public Integer getDelStatus() {
        return delStatus;
    }

    public void setFileCategoryId(String fileCategoryId) {
        this.fileCategoryId = fileCategoryId;
    }

    public String getFileCategoryId() {
        return fileCategoryId;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

package com.scloudic.jsuite.sysuser.mgr.entity;

import com.scloudic.rabbitframework.jbatis.annontations.Column;
import com.scloudic.rabbitframework.jbatis.annontations.ID;
import com.scloudic.rabbitframework.jbatis.annontations.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * This class corresponds to the database table sys_post
 */
@Table
public class SysPost implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String FIELDS = " post_id,update_time,create_time,post_name,post_code,sort_num,remark ";

    /**
     * This field corresponds to the database column sys_post.post_id
     * <p>
     * description:岗位主键
     */
    @ID
    @NotNull
    private Integer postId;

    /**
     * This field corresponds to the database column sys_post.update_time
     * <p>
     * description:最后一次修改时间
     */
    @Column
    private Date updateTime;

    /**
     * This field corresponds to the database column sys_post.active_status
     * <p>
     * description:活动状态(1、正常,2、停用)
     */
    @Column
    @NotNull
    private Integer activeStatus;

    /**
     * This field corresponds to the database column sys_post.create_time
     * <p>
     * description:创建时间
     */
    @Column
    private Date createTime;

    /**
     * This field corresponds to the database column sys_post.post_name
     * <p>
     * description:岗位名称
     */
    @Column
    @NotBlank
    private String postName;

    /**
     * This field corresponds to the database column sys_post.post_code
     * <p>
     * description:岗位编码
     */
    @Column
    @NotBlank
    private String postCode;

    /**
     * This field corresponds to the database column sys_post.sort_num
     * <p>
     * description:排序序号
     */
    @Column
    private Integer sortNum;

    /**
     * This field corresponds to the database column sys_post.del_status
     * <p>
     * description:删除状态(1、正常,2、删除,)
     */
    @Column
    private Integer delStatus;

    /**
     * This field corresponds to the database column sys_post.remark
     * <p>
     * description:备注
     */
    @Column
    private String remark;

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    @Transient
    public Integer getDelStatus() {
        return delStatus;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

}

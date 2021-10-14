package com.scloudic.jsuite.sysuser.mgr.entity;
import java.util.Date;
import java.beans.Transient;
import com.scloudic.rabbitframework.jbatis.annontations.*;
import java.io.Serializable;

/**
* This class corresponds to the database table sys_dept
*/
@Table
public class SysDept implements Serializable {
	private static final long serialVersionUID = 1L;
    public static final String FIELDS = " dept_id,dept_parent_id,create_time,dept_ids,dept_name,dept_phone,remark,update_time,sort_num,dept_mail,dept_lead ";

    /**
    * This field corresponds to the database column sys_dept.dept_id
    * <p>
    * description:部门主键
    */
    @ID
    private Integer deptId;

    /**
    * This field corresponds to the database column sys_dept.dept_parent_id
    * <p>
    * description:父主键(根为0)
    */
    @Column
    private Integer deptParentId;

    /**
    * This field corresponds to the database column sys_dept.create_time
    * <p>
    * description:创建时间
    */
    @Column
    private Date createTime;

    /**
    * This field corresponds to the database column sys_dept.dept_ids
    * <p>
    * description:部门id层次(多个逗号分离)
    */
    @Column
    private String deptIds;

    /**
    * This field corresponds to the database column sys_dept.dept_name
    * <p>
    * description:部门名称
    */
    @Column
    private String deptName;

    /**
    * This field corresponds to the database column sys_dept.dept_phone
    * <p>
    * description:负责人联系电话
    */
    @Column
    private String deptPhone;

    /**
    * This field corresponds to the database column sys_dept.del_status
    * <p>
    * description:删除状态(1、正常,2、删除,)
    */
    @Column
    private Integer delStatus;

    /**
    * This field corresponds to the database column sys_dept.remark
    * <p>
    * description:备注
    */
    @Column
    private String remark;

    /**
    * This field corresponds to the database column sys_dept.update_time
    * <p>
    * description:最后一次修改时间
    */
    @Column
    private Date updateTime;

    /**
    * This field corresponds to the database column sys_dept.active_status
    * <p>
    * description:活动状态(1、正常,2、停用)
    */
    @Column
    private Integer activeStatus;

    /**
    * This field corresponds to the database column sys_dept.sort_num
    * <p>
    * description:排序序号
    */
    @Column
    private Integer sortNum;

    /**
    * This field corresponds to the database column sys_dept.dept_mail
    * <p>
    * description:负责人联系邮箱
    */
    @Column
    private String deptMail;

    /**
    * This field corresponds to the database column sys_dept.dept_lead
    * <p>
    * description:部门负责人
    */
    @Column
    private String deptLead;
    @Column
    private String deptNames;

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptParentId(Integer deptParentId) {
        this.deptParentId = deptParentId;
    }

    public Integer getDeptParentId() {
        return deptParentId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setDeptIds(String deptIds) {
        this.deptIds = deptIds;
    }

    public String getDeptIds() {
        return deptIds;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptPhone(String deptPhone) {
        this.deptPhone = deptPhone;
    }

    public String getDeptPhone() {
        return deptPhone;
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

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setDeptMail(String deptMail) {
        this.deptMail = deptMail;
    }

    public String getDeptMail() {
        return deptMail;
    }

    public void setDeptLead(String deptLead) {
        this.deptLead = deptLead;
    }

    public String getDeptNames() {
        return deptNames;
    }

    public void setDeptNames(String deptNames) {
        this.deptNames = deptNames;
    }

    public String getDeptLead() {
        return deptLead;
    }

}

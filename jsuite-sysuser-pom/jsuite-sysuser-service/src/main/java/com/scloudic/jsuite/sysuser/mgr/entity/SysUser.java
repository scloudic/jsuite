package com.scloudic.jsuite.sysuser.mgr.entity;

import com.scloudic.rabbitframework.jbatis.annontations.Column;
import com.scloudic.rabbitframework.jbatis.annontations.ID;
import com.scloudic.rabbitframework.jbatis.annontations.Table;
import com.scloudic.rabbitframework.jbatis.mapping.GenerationType;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * This class corresponds to the database table sys_user
 */
@Table
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String FIELDS = " sys_user_id,dept_ids,create_time,gender,avatar_path,real_name,remark,login_pwd,update_time,user_mail,login_name,post_id,user_phone ";

    /**
     * This field corresponds to the database column sys_user.sys_user_id
     * <p>
     * description:用户主键
     */
    @ID(keyType = GenerationType.MANUAL)
    private String sysUserId;

    /**
     * This field corresponds to the database column sys_user.create_time
     * <p>
     * description:创建时间
     */
    @Column
    private Date createTime;

    /**
     * This field corresponds to the database column sys_user.gender
     * <p>
     * description:性别(1男2女3保密)
     */
    @Column
    private Integer gender;

    /**
     * This field corresponds to the database column sys_user.avatar_path
     * <p>
     * description:头像路径
     */
    @Column
    private String avatarPath;

    /**
     * This field corresponds to the database column sys_user.del_status
     * <p>
     * description:删除状态(1、正常,2、删除,)
     */
    @Column
    private Integer delStatus;

    /**
     * This field corresponds to the database column sys_user.real_name
     * <p>
     * description:真实姓名
     */
    @Column
    private String realName;

    /**
     * This field corresponds to the database column sys_user.remark
     * <p>
     * description:备注
     */
    @Column
    private String remark;

    /**
     * This field corresponds to the database column sys_user.login_pwd
     * <p>
     * description:登陆密码
     */
    @Column
    private String loginPwd;

    /**
     * This field corresponds to the database column sys_user.update_time
     * <p>
     * description:最后一次修改时间
     */
    @Column
    private Date updateTime;

    /**
     * This field corresponds to the database column sys_user.user_mail
     * <p>
     * description:邮箱
     */
    @Column
    private String userMail;

    /**
     * This field corresponds to the database column sys_user.login_name
     * <p>
     * description:登陆名称
     */
    @Column
    private String loginName;

    /**
     * This field corresponds to the database column sys_user.active_status
     * <p>
     * description:活动状态(1、正常,2、停用)
     */
    @Column
    private Integer activeStatus;

    /**
     * This field corresponds to the database column sys_user.post_id
     * <p>
     * description:岗位主键
     */
    @Column
    private Integer postId;

    /**
     * This field corresponds to the database column sys_user.user_phone
     * <p>
     * description:手机号
     */
    @Column
    private String userPhone;

    /**
     * This field corresponds to the database column sys_dept.dept_id
     * <p>
     * description:部门id
     */
    @Column
    private Integer deptId;

    private String deptNames;

    private String postName;

    private List<SysRole> sysRoles;

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getGender() {
        return gender;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    @Transient
    public Integer getDelStatus() {
        return delStatus;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptNames() {
        return deptNames;
    }

    public void setDeptNames(String deptNames) {
        this.deptNames = deptNames;
    }

    public List<SysRole> getSysRoles() {
        return sysRoles;
    }

    public void setSysRoles(List<SysRole> sysRoles) {
        this.sysRoles = sysRoles;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }
}

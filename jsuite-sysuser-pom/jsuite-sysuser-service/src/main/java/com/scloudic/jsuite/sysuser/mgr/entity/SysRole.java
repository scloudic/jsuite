package com.scloudic.jsuite.sysuser.mgr.entity;

import com.scloudic.rabbitframework.jbatis.annontations.Column;
import com.scloudic.rabbitframework.jbatis.annontations.ID;
import com.scloudic.rabbitframework.jbatis.annontations.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * This class corresponds to the database table sys_role
 */
@Table
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * This field corresponds to the database column sys_role.sys_role_id
     * <p>
     * description:角色主键
     */
    @ID
    private Long sysRoleId;

    /**
     * This field corresponds to the database column sys_role.role_name
     * <p>
     * description:角色名称
     */
    @Column
    private String roleName;

    /**
     * This field corresponds to the database column sys_role.update_time
     * <p>
     * description:最后一次修改时间
     */
    @Column
    private Date updateTime;

    /**
     * This field corresponds to the database column sys_role.create_time
     * <p>
     * description:创建时间
     */
    @Column
    private Date createTime;

    /**
     * This field corresponds to the database column sys_role.role_code
     * <p>
     * description:角色编码
     */
    @Column
    private String roleCode;

    /**
     * This field corresponds to the database column sys_role.role_desc
     * <p>
     * description:角色描述
     */
    @Column
    private String roleDesc;

    public void setSysRoleId(Long sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    public Long getSysRoleId() {
        return sysRoleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
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

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

}

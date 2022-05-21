package com.scloudic.jsuite.sysuser.mgr.entity;

import com.scloudic.rabbitframework.jbatis.annontations.Column;
import com.scloudic.rabbitframework.jbatis.annontations.ID;
import com.scloudic.rabbitframework.jbatis.annontations.Table;
import com.scloudic.rabbitframework.jbatis.mapping.GenerationType;

import java.io.Serializable;

/**
 * This class corresponds to the database table sys_user_role
 */
@Table
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String SYS_USER_ID = "sys_user_id";

    /**
     * This field corresponds to the database column sys_user_role.sys_user_role_id
     * <p>
     * description:用户角色主键
     */
    @ID(keyType = GenerationType.MANUAL)
    private String sysUserRoleId;

    /**
     * This field corresponds to the database column sys_user_role.sys_role_id
     * <p>
     * description:角色主键
     */
    @Column
    private Long sysRoleId;

    /**
     * This field corresponds to the database column sys_user_role.sys_user_id
     * <p>
     * description:用户主键
     */
    @Column
    private String sysUserId;

    /**
     * This field corresponds to the database column sys_user_role.role_code
     * <p>
     * description:角色编码
     */
    @Column
    private String roleCode;

    public void setSysUserRoleId(String sysUserRoleId) {
        this.sysUserRoleId = sysUserRoleId;
    }

    public String getSysUserRoleId() {
        return sysUserRoleId;
    }

    public void setSysRoleId(Long sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    public Long getSysRoleId() {
        return sysRoleId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleCode() {
        return roleCode;
    }

}

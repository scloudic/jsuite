package com.scloudic.jsuite.sysuser.mgr.entity;

import com.scloudic.rabbitframework.jbatis.annontations.Column;
import com.scloudic.rabbitframework.jbatis.annontations.ID;
import com.scloudic.rabbitframework.jbatis.annontations.Table;
import com.scloudic.rabbitframework.jbatis.mapping.GenerationType;

import java.io.Serializable;

/**
* This class corresponds to the database table sys_role_menu
*/
@Table
public class SysRoleMenu implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
    * This field corresponds to the database column sys_role_menu.sys_role_menu_id
    * <p>
    * description:角色菜单主键
    */
    @ID(keyType = GenerationType.MANUAL)
    private String sysRoleMenuId;

    /**
    * This field corresponds to the database column sys_role_menu.sys_role_id
    * <p>
    * description:角色主键
    */
    @Column
    private Long sysRoleId;

    /**
    * This field corresponds to the database column sys_role_menu.role_code
    * <p>
    * description:角色编码
    */
    @Column
    private String roleCode;

    /**
    * This field corresponds to the database column sys_role_menu.sys_menu_id
    * <p>
    * description:菜单主键
    */
    @Column
    private String sysMenuId;

    public void setSysRoleMenuId(String sysRoleMenuId) {
        this.sysRoleMenuId = sysRoleMenuId;
    }

    public String getSysRoleMenuId() {
        return sysRoleMenuId;
    }

    public void setSysRoleId(Long sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    public Long getSysRoleId() {
        return sysRoleId;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setSysMenuId(String sysMenuId) {
        this.sysMenuId = sysMenuId;
    }

    public String getSysMenuId() {
        return sysMenuId;
    }

}

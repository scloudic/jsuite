package com.scloudic.jsuite.sysuser.mgr.entity;

import com.scloudic.rabbitframework.jbatis.annontations.Column;
import com.scloudic.rabbitframework.jbatis.annontations.ID;
import com.scloudic.rabbitframework.jbatis.annontations.Table;
import com.scloudic.rabbitframework.jbatis.mapping.GenerationType;

import java.io.Serializable;
import java.util.Date;

/**
 * This class corresponds to the database table sys_menu
 */
@Table
public class SysMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * This field corresponds to the database column sys_menu.sys_menu_id
     * <p>
     * description:菜单主键
     */
    @ID(keyType = GenerationType.MANUAL)
    private String sysMenuId;

    /**
     * This field corresponds to the database column sys_menu.create_time
     * <p>
     * description:创建时间
     */
    @Column
    private Date createTime;

    /**
     * This field corresponds to the database column sys_menu.menu_type
     * <p>
     * description:菜单类型(1、主菜单，2、顶部菜单，3、底部菜单)
     */
    @Column
    private Integer menuType;

    /**
     * This field corresponds to the database column sys_menu.menu_desc
     * <p>
     * description:菜单描述
     */
    @Column
    private String menuDesc;

    /**
     * This field corresponds to the database column sys_menu.btn_flag
     * <p>
     * description:按钮标记(1、按钮接口,2、菜单接口)
     */
    @Column
    private Integer btnFlag;

    /**
     * This field corresponds to the database column sys_menu.front_end_url
     * <p>
     * description:链接地址
     */
    @Column
    private String frontEndUrl;

    /**
     * This field corresponds to the database column sys_menu.parent_menu_id
     * <p>
     * description:父菜单ID
     */
    @Column
    private String parentMenuId;

    /**
     * This field corresponds to the database column sys_menu.target
     * <p>
     * description:打开方式(1、当前页,2、新页面，3、模式对话框)
     */
    @Column
    private Integer target;

    /**
     * This field corresponds to the database column sys_menu.update_time
     * <p>
     * description:最后一次修改时间
     */
    @Column
    private Date updateTime;

    /**
     * This field corresponds to the database column sys_menu.icon_path
     * <p>
     * description:菜单图标路径
     */
    @Column
    private String iconPath;

    /**
     * This field corresponds to the database column sys_menu.menu_name
     * <p>
     * description:菜单名称
     */
    @Column
    private String menuName;

    /**
     * This field corresponds to the database column sys_menu.sort_num
     * <p>
     * description:排序序号
     */
    @Column
    private Integer sortNum;

    /**
     * This field corresponds to the database column sys_menu.menu_level
     * <p>
     * description:菜单级别
     */
    @Column
    private Integer menuLevel;

    /**
     * This field corresponds to the database column sys_menu.back_end_url
     * <p>
     * description:后台请求地址
     */
    @Column
    private String backEndUrl;

    /**
     * This field corresponds to the database column sys_menu.front_end_param_name
     * <p>
     * description:前端传参名
     */
    @Column
    private String frontEndParamName;
    /**
     * This field corresponds to the database column sys_menu.menu_code
     * <p>
     * description:前端传参名
     */
    @Column
    private String menuCode;

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setBtnFlag(Integer btnFlag) {
        this.btnFlag = btnFlag;
    }

    public Integer getBtnFlag() {
        return btnFlag;
    }

    public String getBackEndUrl() {
        return backEndUrl;
    }

    public void setBackEndUrl(String backEndUrl) {
        this.backEndUrl = backEndUrl;
    }

    public String getFrontEndParamName() {
        return frontEndParamName;
    }

    public void setFrontEndParamName(String frontEndParamName) {
        this.frontEndParamName = frontEndParamName;
    }

    public String getFrontEndUrl() {
        return frontEndUrl;
    }

    public void setFrontEndUrl(String frontEndUrl) {
        this.frontEndUrl = frontEndUrl;
    }

    public String getSysMenuId() {
        return sysMenuId;
    }

    public void setSysMenuId(String sysMenuId) {
        this.sysMenuId = sysMenuId;
    }

    public String getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(String parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Integer getTarget() {
        return target;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getSortNum() {
        return sortNum;
    }
    
    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }
}

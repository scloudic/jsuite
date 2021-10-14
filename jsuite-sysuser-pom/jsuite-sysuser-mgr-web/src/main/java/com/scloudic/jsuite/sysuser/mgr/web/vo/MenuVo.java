package com.scloudic.jsuite.sysuser.mgr.web.vo;

import java.util.Date;
import java.util.List;

public class MenuVo implements java.io.Serializable {
    private Long menuId;
    private Integer menuType;
    private String iconPath;
    private String menuName;
    private Integer menuLevel;
    private Integer sortNum;
    private String menuDesc;
    private Integer btnFlag;
    private Long parentMenuId;
    private Integer target;
    private Integer childMenuNum;
    private String frontEndParamName;
    private Date creatTime;
    private String frontEndUrl;
    private String backEndUrl;
    //选中状态
    private boolean checkStatus = false;
    private String menuCode;
    private List<MenuVo> children;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public Integer getBtnFlag() {
        return btnFlag;
    }

    public void setBtnFlag(Integer btnFlag) {
        this.btnFlag = btnFlag;
    }

    public Long getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Long parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Integer getChildMenuNum() {
        return childMenuNum;
    }

    public void setChildMenuNum(Integer childMenuNum) {
        this.childMenuNum = childMenuNum;
    }

    public void setChildren(List<MenuVo> children) {
        this.children = children;
    }

    public List<MenuVo> getChildren() {
        return children;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public void setCheckStatus(boolean checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getFrontEndUrl() {
        return frontEndUrl;
    }

    public void setFrontEndUrl(String frontEndUrl) {
        this.frontEndUrl = frontEndUrl;
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

    public boolean isCheckStatus() {
        return checkStatus;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public void setFrontEndParamName(String frontEndParamName) {
        this.frontEndParamName = frontEndParamName;
    }
}

package com.scloudic.jsuite.sysuser.mgr.web.vo;

import com.scloudic.jsuite.sysuser.mgr.entity.SysDept;

import java.util.List;

public class DeptVo extends SysDept {
    private Long childMenuNum;
    private boolean checkStatus = false;
    private List<DeptVo> children;

    public boolean isCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(boolean checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Long getChildMenuNum() {
        return childMenuNum;
    }

    public void setChildMenuNum(Long childMenuNum) {
        this.childMenuNum = childMenuNum;
    }

    public List<DeptVo> getChildren() {
        return children;
    }

    public void setChildren(List<DeptVo> children) {
        this.children = children;
    }
}

package com.scloudic.jsuite.common.mgr.web.vo;

import com.scloudic.rabbitframework.jbatis.annontations.Column;

import java.util.List;

public class AreaVO {
    private List<AreaVO> children;
    private Integer areaId;

    private String areaName;

    private String latitude;

    private Integer parentAreaId;

    private Integer areaLevel;

    private String longitude;

    private Integer activeStatus;

    private Integer sortNum;

    private Integer hotStatus;

    public void setChildren(List<AreaVO> children) {
        this.children = children;
    }

    public List<AreaVO> getChildren() {
        return children;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setParentAreaId(Integer parentAreaId) {
        this.parentAreaId = parentAreaId;
    }

    public Integer getParentAreaId() {
        return parentAreaId;
    }

    public Integer getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(Integer areaLevel) {
        this.areaLevel = areaLevel;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getHotStatus() {
        return hotStatus;
    }

    public void setHotStatus(Integer hotStatus) {
        this.hotStatus = hotStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }
}

package com.scloudic.jsuite.common.api.web.vo;

import java.util.List;

public class AreaMenuVO {
    private List<AreaMenuVO> children;
    private Integer areaId;

    private String areaName;

    private String latitude;

    private Integer parentAreaId;

    private Integer areaLevel;

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

    private String longitude;
    private Integer sortNum;
    private Integer hotStatus;

    public void setChildren(List<AreaMenuVO> children) {
        this.children = children;
    }

    public List<AreaMenuVO> getChildren() {
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
}

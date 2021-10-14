package com.scloudic.jsuite.common.entity;

import com.scloudic.rabbitframework.jbatis.annontations.*;
import com.scloudic.rabbitframework.jbatis.mapping.GenerationType;

import java.io.Serializable;
import java.util.Date;

/**
 * This class corresponds to the database table area_info
 */
@Table
public class AreaInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * This field corresponds to the database column area_info.area_id
     * <p>
     * description:机构主键
     */
    @ID(keyType = GenerationType.MANUAL)
    private Integer areaId;

    /**
     * This field corresponds to the database column area_info.area_name
     * <p>
     * description:机构名称
     */
    @Column
    private String areaName;

    /**
     * This field corresponds to the database column area_info.area_ids
     * <p>
     * description:地区主键集(多个逗号分离)
     */
    @Column
    private String areaIds;

    /**
     * This field corresponds to the database column area_info.latitude
     * <p>
     * description:纬度
     */
    @Column
    private String latitude;

    /**
     * This field corresponds to the database column area_info.area_level
     * <p>
     * description:机构级别
     */
    @Column
    private Integer areaLevel;

    /**
     * This field corresponds to the database column area_info.parent_area_id
     * <p>
     * description:父机构主键，根值为0
     */
    @Column
    private Integer parentAreaId;


    /**
     * This field corresponds to the database column area_info.active_status
     * <p>
     * description:活动状态(1、正常,2、停用)
     */
    @Column
    private Integer activeStatus;

    /**
     * This field corresponds to the database column area_info.longitude
     * <p>
     * description:经度
     */
    @Column
    private String longitude;
    @Column
    private String areaNames;
    @Column
    private Date createTime;
    @Column
    private Integer sortNum;
    @Column
    private Integer hotStatus;

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setAreaLevel(Integer areaLevel) {
        this.areaLevel = areaLevel;
    }

    public Integer getAreaLevel() {
        return areaLevel;
    }

    public void setParentAreaId(Integer parentAreaId) {
        this.parentAreaId = parentAreaId;
    }

    public Integer getParentAreaId() {
        return parentAreaId;
    }

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAreaNames() {
        return areaNames;
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

    public void setAreaNames(String areaNames) {
        this.areaNames = areaNames;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

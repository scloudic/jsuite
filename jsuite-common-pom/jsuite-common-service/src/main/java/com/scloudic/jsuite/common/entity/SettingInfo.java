package com.scloudic.jsuite.common.entity;

import java.beans.Transient;
import java.util.Date;

import com.scloudic.rabbitframework.jbatis.annontations.*;

import java.io.Serializable;

@Table
public class SettingInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String FIELDS = " setting_id,setting_name,create_time,setting_value,setting_code,remark ";

    /**
     * 配置主键
     */
    @ID
    private Integer settingId;

    /**
     * 配置名称
     */
    @Column
    private String settingName;

    /**
     * 创建时间
     */
    @Column
    private Date createTime;

    /**
     * 配置值
     */
    @Column
    private String settingValue;

    /**
     * 配置code
     */
    @Column
    private String settingCode;

    /**
     * 备注
     */
    @Column
    private String remark;

    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }

    public Integer getSettingId() {
        return settingId;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public String getSettingName() {
        return settingName;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingCode(String settingCode) {
        this.settingCode = settingCode;
    }

    public String getSettingCode() {
        return settingCode;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

}

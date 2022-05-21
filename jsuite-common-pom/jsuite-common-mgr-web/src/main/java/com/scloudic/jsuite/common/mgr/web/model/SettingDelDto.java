package com.scloudic.jsuite.common.mgr.web.model;

import javax.validation.constraints.NotNull;

public class SettingDelDto {
    @NotNull
    private Integer settingId;

    public Integer getSettingId() {
        return settingId;
    }

    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }
}

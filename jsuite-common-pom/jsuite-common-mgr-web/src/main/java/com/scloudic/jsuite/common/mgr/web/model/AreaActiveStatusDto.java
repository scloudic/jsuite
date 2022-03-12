package com.scloudic.jsuite.common.mgr.web.model;

import javax.validation.constraints.NotNull;

public class AreaActiveStatusDto extends AreaDelDto {
    @NotNull
    private Integer activeStatus;

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }
}

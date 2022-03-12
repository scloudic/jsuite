package com.scloudic.jsuite.common.mgr.web.model;

import javax.validation.constraints.NotNull;

public class AreaActiveStatusForm extends AreaDelForm {
    @NotNull
    private Integer activeStatus;

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }
}

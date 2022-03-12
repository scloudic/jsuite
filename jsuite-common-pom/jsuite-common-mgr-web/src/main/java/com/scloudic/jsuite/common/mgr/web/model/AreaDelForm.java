package com.scloudic.jsuite.common.mgr.web.model;

import javax.validation.constraints.NotNull;

public class AreaDelForm {
    @NotNull
    private Integer areaId;

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
}

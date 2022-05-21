package com.scloudic.jsuite.file.web.model;

import javax.validation.constraints.NotBlank;

public class FileCategoryDelDto {
    @NotBlank
    private String fileCategoryId;

    public String getFileCategoryId() {
        return fileCategoryId;
    }

    public void setFileCategoryId(String fileCategoryId) {
        this.fileCategoryId = fileCategoryId;
    }
}

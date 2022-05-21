package com.scloudic.jsuite.file.web.model;

import javax.validation.constraints.NotBlank;

public class FileInfoDto {
    @NotBlank
    private String fileId;
    @NotBlank
    private String fileName;
    @NotBlank
    private String fileCategoryId;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileCategoryId() {
        return fileCategoryId;
    }

    public void setFileCategoryId(String fileCategoryId) {
        this.fileCategoryId = fileCategoryId;
    }
}

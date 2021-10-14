package com.scloudic.jsuite.file.api.model;

import com.scloudic.jsuite.file.api.utils.FileUtils;

import java.io.Serializable;


public class FileBaseInfo implements Serializable {

    private String filePath;

    private String fileName;

    private String httpUrl;

    private FileUtils.FileStore fileStore;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public FileUtils.FileStore getFileStore() {
        return fileStore;
    }

    public void setFileStore(FileUtils.FileStore fileStore) {
        this.fileStore = fileStore;
    }
}

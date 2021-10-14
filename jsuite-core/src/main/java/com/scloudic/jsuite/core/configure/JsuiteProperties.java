package com.scloudic.jsuite.core.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:jsuite.properties")
public class JsuiteProperties {
    @Value("${file.local.store:}")
    private String localFileStore;
    @Value("${file.url:}")
    private String fileUrl;
    @Value("${file.imgthumb.ssmall.with:200}")
    private int imgThumbSsmallWith;
    @Value("${file.imgthumb.small.with:500}")
    private int imgThumbSmallWith;

    @Value("${security.realmServiceName:}")
    private String realmServiceName;

    public String getLocalFileStore() {
        return localFileStore;
    }

    public void setLocalFileStore(String localFileStore) {
        this.localFileStore = localFileStore;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public int getImgThumbSsmallWith() {
        return imgThumbSsmallWith;
    }

    public void setImgThumbSsmallWith(int imgThumbSsmallWith) {
        this.imgThumbSsmallWith = imgThumbSsmallWith;
    }

    public int getImgThumbSmallWith() {
        return imgThumbSmallWith;
    }

    public void setImgThumbSmallWith(int imgThumbSmallWith) {
        this.imgThumbSmallWith = imgThumbSmallWith;
    }

    public String getRealmServiceName() {
        return realmServiceName;
    }

    public void setRealmServiceName(String realmServiceName) {
        this.realmServiceName = realmServiceName;
    }
}

package com.scloudic.jsuite.filealioss.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:jsuite.properties")
public class FileAliOssProperties {
    @Value("${file.ali.ossAccessKey:}")
    private String aliOssAccessKey;
    @Value("${file.ali.ossAccessKeySecret:}")
    private String aliOssAccessKeySecret;
    @Value("${file.ali.ossEndPoint:}")
    private String aliOssEndPoint;
    @Value("${file.ali.ossBucket:}")
    private String aliOssBucket;
    @Value("${file.ali.ossFirstPath:}")
    private String aliOssFirstPath;
    
    public String getAliOssAccessKey() {
        return aliOssAccessKey;
    }

    public void setAliOssAccessKey(String aliOssAccessKey) {
        this.aliOssAccessKey = aliOssAccessKey;
    }

    public String getAliOssAccessKeySecret() {
        return aliOssAccessKeySecret;
    }

    public void setAliOssAccessKeySecret(String aliOssAccessKeySecret) {
        this.aliOssAccessKeySecret = aliOssAccessKeySecret;
    }

    public String getAliOssEndPoint() {
        return aliOssEndPoint;
    }

    public void setAliOssEndPoint(String aliOssEndPoint) {
        this.aliOssEndPoint = aliOssEndPoint;
    }

    public String getAliOssBucket() {
        return aliOssBucket;
    }

    public void setAliOssBucket(String aliOssBucket) {
        this.aliOssBucket = aliOssBucket;
    }

    public String getAliOssFirstPath() {
        return aliOssFirstPath;
    }

    public void setAliOssFirstPath(String aliOssFirstPath) {
        this.aliOssFirstPath = aliOssFirstPath;
    }
}

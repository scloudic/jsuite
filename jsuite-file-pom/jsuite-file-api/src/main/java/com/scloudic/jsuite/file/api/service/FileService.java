package com.scloudic.jsuite.file.api.service;

import com.scloudic.jsuite.file.api.model.FileBaseInfo;

import java.io.InputStream;

/**
 * 文件上传服务接口
 *
 * @author juyang.liang
 * @version 1.0
 * @since 1.8
 */
public interface FileService {
    /**
     * 文件上传接口
     *
     * @param fileCategoryName 文件分类名称
     * @param srcFileName      原文件名
     * @param inputStream      文件流
     * @return {@link FileBaseInfo}
     */
    public FileBaseInfo fileUpload(String fileCategoryName, String srcFileName, InputStream inputStream);
}

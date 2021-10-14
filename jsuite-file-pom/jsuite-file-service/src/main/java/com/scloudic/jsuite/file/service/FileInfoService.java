package com.scloudic.jsuite.file.service;

import com.scloudic.jsuite.file.entity.FileInfo;
import com.scloudic.rabbitframework.jbatis.service.IService;
import com.scloudic.rabbitframework.core.utils.PageBean;

import java.util.List;

public interface FileInfoService extends IService<FileInfo> {
    /**
     * 获取文件信息
     *
     * @param fileName
     * @param fileCategoryId
     * @param fileType
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageBean<FileInfo> findFileInfo(String fileName, String fileCategoryId, String fileType,
                                           Long pageNum, Long pageSize);

    /**
     * 批量文件信息保存
     *
     * @param fileInfos
     * @return
     */
    public int saveBatchInsert(List<FileInfo> fileInfos);
}


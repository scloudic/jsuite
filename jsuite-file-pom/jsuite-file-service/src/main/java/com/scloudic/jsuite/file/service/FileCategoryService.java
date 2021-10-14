package com.scloudic.jsuite.file.service;

import com.scloudic.jsuite.file.entity.FileCategory;
import com.scloudic.rabbitframework.jbatis.service.IService;
import com.scloudic.rabbitframework.core.utils.PageBean;

public interface FileCategoryService extends IService<FileCategory> {
    /**
     * 分页查询文件分类
     *
     * @param fileCategoryName
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageBean<FileCategory> findByParams(String fileCategoryName, Long pageNum, Long pageSize);
}


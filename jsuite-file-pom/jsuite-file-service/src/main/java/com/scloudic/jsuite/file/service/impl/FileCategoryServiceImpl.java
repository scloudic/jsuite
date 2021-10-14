package com.scloudic.jsuite.file.service.impl;

import com.scloudic.jsuite.file.entity.FileCategory;
import com.scloudic.jsuite.file.mapper.FileCategoryMapper;
import com.scloudic.jsuite.file.service.FileCategoryService;
import com.scloudic.rabbitframework.jbatis.mapping.RowBounds;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.jbatis.service.IServiceImpl;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FileCategoryServiceImpl extends IServiceImpl<FileCategoryMapper, FileCategory> implements FileCategoryService {
    @Autowired
    private FileCategoryMapper fileCategoryMapper;

    @Override
    public FileCategoryMapper getBaseMapper() {
        return fileCategoryMapper;
    }

    /**
     * 分页查询文件分类
     *
     * @param fileCategoryName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public PageBean<FileCategory> findByParams(String fileCategoryName, Long pageNum, Long pageSize) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        if (StringUtils.isNotBlank(fileCategoryName)) {
            criteria.andEqual(FileCategory::getFileCategoryName, "%" + fileCategoryName + "%");
        }
        where.setOrderBy(FileCategory::getSortNum);
        long totalCount = fileCategoryMapper.selectCountByParams(where);
        PageBean<FileCategory> pageBean = new PageBean<FileCategory>(pageNum, pageSize, totalCount);
        List<FileCategory> fileCategories = fileCategoryMapper.selectPageByParams(where, new RowBounds(pageBean.getStartPage(), pageBean.getPageSize()));
        pageBean.setDatas(fileCategories);
        return pageBean;
    }
}


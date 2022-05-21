package com.scloudic.jsuite.file.service.impl;

import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.file.entity.FileInfo;
import com.scloudic.jsuite.file.mapper.FileInfoMapper;
import com.scloudic.jsuite.file.service.FileInfoService;
import com.scloudic.rabbitframework.jbatis.mapping.lambda.SFunctionUtils;
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
public class FileInfoServiceImpl extends IServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {
    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Override
    public FileInfoMapper getBaseMapper() {
        return fileInfoMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public PageBean<FileInfo> findFileInfo(String fileName, String fileCategoryId,
                                           String fileType, Long pageNum, Long pageSize) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        criteria.andEqual(FileInfo::getDelStatus, Enums.DelStatus.NORMAL.getValue());
        if (StringUtils.isNotBlank(fileName)) {
            String fieldName = SFunctionUtils.getFieldName(FileInfo::getFileName);
            criteria.andEqual("fi." + fieldName, "%" + fileName + "%");
        }
        if (StringUtils.isNotBlank(fileType)) {
            String fieldName = SFunctionUtils.getFieldName(FileInfo::getFileType);
            criteria.andEqual("fi." + fieldName, fileType);
        }
        if (StringUtils.isNotBlank(fileCategoryId)) {
            String fieldName = SFunctionUtils.getFieldName(FileInfo::getFileCategoryId);
            criteria.andEqual("fi." + fieldName, fileCategoryId);
        }
        String createTimeFieldName = SFunctionUtils.getFieldName(FileInfo::getCreateTime);
        where.orderByDesc("fi." + createTimeFieldName);
        long totalCount = fileInfoMapper.countListByParams(where);
        PageBean<FileInfo> pageBean = new PageBean<>(pageNum, pageSize, totalCount);
        List<FileInfo> fileInfos = fileInfoMapper.findListByParams(where);
        pageBean.setDatas(fileInfos);
        return pageBean;
    }

    /**
     * 批量文件信息保存
     *
     * @param fileInfos
     * @return
     */
    @Transactional
    @Override
    public int saveBatchInsert(List<FileInfo> fileInfos) {
        return fileInfoMapper.batchInsertEntity(fileInfos);
    }
}


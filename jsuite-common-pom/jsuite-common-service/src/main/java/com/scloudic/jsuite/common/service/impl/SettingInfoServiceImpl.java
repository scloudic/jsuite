package com.scloudic.jsuite.common.service.impl;

import com.scloudic.jsuite.common.entity.SettingInfo;
import com.scloudic.jsuite.common.mapper.SettingInfoMapper;
import com.scloudic.jsuite.common.service.SettingInfoService;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.jbatis.mapping.RowBounds;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.jbatis.service.IServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingInfoServiceImpl extends IServiceImpl<SettingInfoMapper, SettingInfo> implements SettingInfoService {
    @Autowired
    private SettingInfoMapper settingInfoMapper;

    @Override
    public SettingInfoMapper getBaseMapper() {
        return settingInfoMapper;
    }

    @Override
    public PageBean<SettingInfo> findSettingInfoPage(Where where, Long pageNum, Long pageSize) {
        Long totalCount = settingInfoMapper.selectCountByParams(where);
        PageBean<SettingInfo> pageBean = new PageBean<SettingInfo>(pageNum, pageSize, totalCount);
        List<SettingInfo> list = settingInfoMapper.selectPageByParams(where, new RowBounds(pageBean.getStartPage(), pageBean.getPageSize()));
        pageBean.setDatas(list);
        return pageBean;
    }
}

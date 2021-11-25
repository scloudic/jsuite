package com.scloudic.jsuite.common.service.impl;

import com.scloudic.jsuite.common.entity.ThirdBindInfo;
import com.scloudic.jsuite.common.mapper.ThirdBindInfoMapper;
import com.scloudic.jsuite.common.service.ThirdBindInfoService;
import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scloudic.rabbitframework.jbatis.service.IServiceImpl;

@Service
public class ThirdBindInfoServiceImpl extends IServiceImpl<ThirdBindInfoMapper, ThirdBindInfo> implements ThirdBindInfoService {
    @Autowired
    private ThirdBindInfoMapper thirdBindInfoMapper;

    @Override
    public ThirdBindInfoMapper getBaseMapper() {
        return thirdBindInfoMapper;
    }

    @Override
    public ThirdBindInfo getThirdBinInfoByCode(String thirdBindCode) {
        Where where = new Where();
        where.createCriteria().andEqual(ThirdBindInfo::getThirdBindCode, thirdBindCode)
                .andEqual(ThirdBindInfo::getDelStatus, Enums.DelStatus.NORMAL.getValue());
        return thirdBindInfoMapper.selectOneByParams(where);
    }
}


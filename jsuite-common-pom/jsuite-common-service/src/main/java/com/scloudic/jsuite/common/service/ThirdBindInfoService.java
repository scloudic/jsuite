package com.scloudic.jsuite.common.service;

import com.scloudic.jsuite.common.entity.ThirdBindInfo;
import com.scloudic.rabbitframework.jbatis.service.IService;

public interface ThirdBindInfoService extends IService<ThirdBindInfo> {
    /**
     * 根据配置编码获取第三方信息
     *
     * @param thirdBindCode thirdBindCode
     * @return ThirdBindInfo
     */
    public ThirdBindInfo getThirdBinInfoByCode(String thirdBindCode);
}


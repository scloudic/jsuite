package com.scloudic.jsuite.common.service;

import com.scloudic.jsuite.common.entity.SettingInfo;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.jbatis.service.IService;

public interface SettingInfoService extends IService<SettingInfo> {
    public PageBean<SettingInfo> findSettingInfoPage(Where where, Long pageNum, Long pageSize);
}

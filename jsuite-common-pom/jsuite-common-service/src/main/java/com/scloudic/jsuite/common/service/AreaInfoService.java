package com.scloudic.jsuite.common.service;

import com.scloudic.jsuite.common.entity.AreaInfo;
import com.scloudic.rabbitframework.jbatis.service.IService;

import java.util.List;

public interface AreaInfoService extends IService<AreaInfo> {

    List<AreaInfo> findAreaByParentAreaId(Integer parentAreaId, Integer ActivityStatus);

    /**
     * 删除地区
     *
     * @param areaId
     * @return
     */
    int delArea(int areaId);

    int addAreaInfo(AreaInfo areaInfo);

    int updateAreaInfo(AreaInfo areaInfo);
}

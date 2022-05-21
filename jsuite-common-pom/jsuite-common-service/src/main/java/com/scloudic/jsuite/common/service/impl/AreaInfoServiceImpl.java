package com.scloudic.jsuite.common.service.impl;

import com.scloudic.jsuite.common.mapper.AreaInfoMapper;
import com.scloudic.jsuite.common.entity.AreaInfo;
import com.scloudic.jsuite.common.service.AreaInfoService;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.jbatis.mapping.lambda.SFunctionUtils;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.jbatis.service.IServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AreaInfoServiceImpl extends IServiceImpl<AreaInfoMapper, AreaInfo> implements AreaInfoService {
    @Autowired
    private AreaInfoMapper areaInfoMapper;

    @Override
    public AreaInfoMapper getBaseMapper() {
        return areaInfoMapper;
    }

    @Override
    public List<AreaInfo> findAreaByParentAreaId(Integer parentAreaId, Integer activeStatus) {
        Where paramType = new Where();
        Criteria criteria = paramType.createCriteria();
        if (activeStatus != null) {
            criteria.andEqual(AreaInfo::getActiveStatus, activeStatus);
        }
        criteria.andEqual(AreaInfo::getParentAreaId, parentAreaId);
        paramType.orderByAsc(AreaInfo::getAreaName);
        return areaInfoMapper.selectByParams(paramType);
    }

    @Transactional
    @Override
    public int delArea(int areaId) {
        return areaInfoMapper.deleteById(areaId);
    }

    @Transactional
    @Override
    public int addAreaInfo(AreaInfo areaInfo) {
        AreaInfo tmpAreaInfo = areaInfoMapper.selectById(areaInfo.getParentAreaId());
        areaInfo.setAreaLevel(tmpAreaInfo.getAreaLevel().intValue() + 1);
        return areaInfoMapper.insertByEntity(areaInfo);
    }

    @Transactional
    @Override
    public int updateAreaInfo(AreaInfo areaInfo) {
        AreaInfo tmpArea = areaInfoMapper.selectById(areaInfo.getAreaId());
        if (areaInfo.getAreaId().intValue() != 1 && !tmpArea.getAreaName().equals(areaInfo.getAreaName())) {
            String areaNames = tmpArea.getAreaNames();
            areaInfo.setAreaNames(StringUtils.replace(areaNames, tmpArea.getAreaName(), areaInfo.getAreaName()));
            Where where = new Where();
            Criteria criteria = where.createCriteria();
            String filedName = SFunctionUtils.getFieldName(AreaInfo::getAreaIds);
            criteria.andGreaterThan("find_in_set(" + areaInfo.getAreaId() + "," + filedName + ")", 0);
            criteria.andNotEqual(AreaInfo::getAreaId, areaInfo.getAreaId());
            List<AreaInfo> areaInfos = areaInfoMapper.selectByParams(where);
            for (AreaInfo ai : areaInfos) {
                String dns = ai.getAreaNames();
                ai.setAreaNames(StringUtils.replace(dns, tmpArea.getAreaName(), areaInfo.getAreaName()));
                areaInfoMapper.updateByEntity(ai);
            }
        }
        return areaInfoMapper.updateByEntity(areaInfo);
    }
}


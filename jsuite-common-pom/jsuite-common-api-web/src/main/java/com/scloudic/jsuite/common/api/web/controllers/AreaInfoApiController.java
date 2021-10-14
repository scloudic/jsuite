package com.scloudic.jsuite.common.api.web.controllers;

import com.scloudic.jsuite.common.api.web.vo.AreaMenuVO;
import com.scloudic.jsuite.common.api.web.vo.AreaVO;
import com.scloudic.jsuite.common.entity.AreaInfo;
import com.scloudic.jsuite.common.service.AreaInfoService;
import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.web.AbstractContextResource;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;

/**
 * 地区api接口
 *
 * @author juyang.liang
 */
@Component
@Singleton
@Path("/jsuite/api/area")
public class AreaInfoApiController extends AbstractContextResource {
    @Autowired
    private AreaInfoService areaInfoService;

    /**
     * 获取地区级别地区信息
     *
     * @param areaLevel 地区级别
     * @return obj
     */
    @GET
    @Path("getAreaByLevel")
    @FormValid
    public Result<List<AreaVO>> getAreaByLevel(@NotBlank @QueryParam("areaLevel") Integer areaLevel) {
        Where where = new Where();
        where.createCriteria().andEqual(AreaInfo::getAreaLevel, areaLevel)
                .andEqual(AreaInfo::getActiveStatus, Enums.ActiveStatus.OPEN.getValue());
        where.setOrderBy(AreaInfo::getAreaName, Where.OrderByType.ASC);
        List<AreaInfo> areaInfos = areaInfoService.selectByParams(where);
        List<AreaVO> areaVOS = new ArrayList<AreaVO>();
        for (AreaInfo areaInfo : areaInfos) {
            AreaVO areaVo = new AreaVO();
            areaVo.setAreaId(areaInfo.getAreaId());
            areaVo.setAreaName(areaInfo.getAreaName());
            areaVo.setParentAreaId(areaInfo.getParentAreaId());
            areaVo.setAreaLevel(areaInfo.getAreaLevel());
            areaVo.setLatitude(areaInfo.getLatitude());
            areaVo.setLongitude(areaInfo.getLongitude());
            areaVOS.add(areaVo);
        }
        return success(areaVOS);
    }

    /**
     * 根据地区主键获取子菜单
     *
     * @param areaId 地区主键
     * @return obj
     */
    @GET
    @Path("getAreaById")
    @FormValid
    public Result<List<AreaVO>> getAreaById(@NotBlank @QueryParam("areaId") Integer areaId) {
        Where where = new Where();
        where.createCriteria().andEqual(AreaInfo::getParentAreaId, areaId)
                .andEqual(AreaInfo::getActiveStatus, Enums.ActiveStatus.OPEN.getValue());
        where.setOrderBy(AreaInfo::getAreaName, Where.OrderByType.ASC);
        List<AreaInfo> areaInfos = areaInfoService.selectByParams(where);
        List<AreaVO> areaVOS = new ArrayList<AreaVO>();
        for (AreaInfo areaInfo : areaInfos) {
            AreaVO areaVo = new AreaVO();
            areaVo.setAreaId(areaInfo.getAreaId());
            areaVo.setSortNum(areaInfo.getSortNum());
            areaVo.setHotStatus(areaInfo.getHotStatus());
            areaVo.setAreaName(areaInfo.getAreaName());
            areaVo.setParentAreaId(areaInfo.getParentAreaId());
            areaVo.setAreaLevel(areaInfo.getAreaLevel());
            areaVo.setLatitude(areaInfo.getLatitude());
            areaVo.setLongitude(areaInfo.getLongitude());
            areaVOS.add(areaVo);
        }
        return success(areaVOS);
    }

    /**
     * 根据地区主键查询所有地区,树型结构
     *
     * @param areaId 级别
     * @return json
     */
    @GET
    @Path("findAll")
    public Result<List<AreaMenuVO>> findAll(@DefaultValue("0") @QueryParam("areaId") Integer areaId) {
        List<AreaMenuVO> areaVos = new ArrayList<>();
        Where paramType = new Where();
        paramType.createCriteria().andEqual(AreaInfo::getParentAreaId, areaId)
                .andEqual(AreaInfo::getActiveStatus, Enums.ActiveStatus.OPEN.getValue());
        paramType.setOrderBy(AreaInfo::getAreaName, Where.OrderByType.ASC);
        List<AreaInfo> areaInfos = areaInfoService.selectByParams(paramType);
        int areaInfoSize = areaInfos.size();
        if (areaInfoSize > 0) {
            areaVos = findChildArea(areaInfos.get(0).getAreaId());
        }
        return success(areaVos);
    }

    private List<AreaMenuVO> findChildArea(int parentAreaId) {
        List<AreaMenuVO> areaVos = new ArrayList<>();
        Where where = new Where();
        where.createCriteria().andEqual(AreaInfo::getParentAreaId, parentAreaId)
                .andEqual(AreaInfo::getActiveStatus, Enums.ActiveStatus.OPEN.getValue());
        where.setOrderBy(AreaInfo::getAreaName, Where.OrderByType.ASC);
        List<AreaInfo> areaInfos = areaInfoService.selectByParams(where);
        for (AreaInfo areaInfo : areaInfos) {
            Integer areaId = areaInfo.getAreaId();
            AreaMenuVO areaVo = new AreaMenuVO();
            areaVo.setAreaId(areaId);
            areaVo.setAreaName(areaInfo.getAreaName());
            areaVo.setParentAreaId(areaInfo.getParentAreaId());
            areaVo.setAreaLevel(areaInfo.getAreaLevel());
            areaVo.setLatitude(areaInfo.getLatitude());
            areaVo.setLongitude(areaInfo.getLongitude());
            areaVo.setSortNum(areaInfo.getSortNum());
            areaVo.setHotStatus(areaInfo.getHotStatus());
            where.clear();
            where.createCriteria().andEqual(AreaInfo::getParentAreaId, areaId)
                    .andEqual(AreaInfo::getActiveStatus, Enums.ActiveStatus.OPEN.getValue());
            Long count = areaInfoService.selectCountByParams(where);
            if (count > 0) {
                areaVo.setChildren(findChildArea(areaId));
            } else {
                areaVo.setChildren(new ArrayList<>());
            }
            areaVos.add(areaVo);
        }
        return areaVos;
    }
}

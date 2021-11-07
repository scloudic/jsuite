package com.scloudic.jsuite.common.api.web.controllers;

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
    public Result<List<AreaInfo>> getAreaByLevel(@NotBlank @QueryParam("areaLevel") Integer areaLevel) {
        Where where = new Where();
        where.createCriteria().andEqual(AreaInfo::getAreaLevel, areaLevel)
                .andEqual(AreaInfo::getActiveStatus, Enums.ActiveStatus.OPEN.getValue());
        where.setOrderBy(AreaInfo::getAreaName, Where.OrderByType.ASC);
        List<AreaInfo> areaInfos = areaInfoService.selectByParams(where);
        return success(areaInfos);
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
    public Result<List<AreaInfo>> getAreaById(@NotBlank @QueryParam("areaId") Integer areaId) {
        List<AreaInfo> areaInfos = areaInfoService.findAreaByParentAreaId(areaId, Enums.ActiveStatus.OPEN.getValue());
        areaInfos.forEach(areaInfo -> {
            Integer pAreaId = areaInfo.getAreaId();
            int childrenNum = areaInfoService.findAreaByParentAreaId(pAreaId, Enums.ActiveStatus.OPEN.getValue()).size();
            areaInfo.setChildrenNum(childrenNum);
            if (childrenNum > 0) {
                areaInfo.setIsParentNode("true");
            } else {
                areaInfo.setIsParentNode("false");
            }
        });
        return success(areaInfos);
    }
}

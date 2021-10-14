package com.scloudic.jsuite.common.mgr.web.controllers;

import com.scloudic.jsuite.common.entity.AreaInfo;
import com.scloudic.jsuite.common.mgr.web.vo.AreaVO;
import com.scloudic.jsuite.common.service.AreaInfoService;
import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.security.authz.annotation.UriPermissions;
import com.scloudic.rabbitframework.web.AbstractContextResource;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.List;

/**
 * 地区管理
 *
 * @author min.xie
 */
@Component
@Singleton
@Path("/jsuite/areaMgr/")
public class AreaInfoController extends AbstractContextResource {
    @Autowired
    private AreaInfoService areaInfoService;

    /**
     * 查询地区信息(列表结构)
     *
     * @param request  request
     * @param areaName 地区名称
     * @return obj
     */
    @GET
    @Path("areaListAll")
    @UriPermissions
    //@Log(operatorType = Log.OperateType.SELECT, remark = "地区查询")
    public Result<List<AreaInfo>> areaListAll(@Context HttpServletRequest request,
                                              @QueryParam("areaName") String areaName) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        if (StringUtils.isNotBlank(areaName)) {
            criteria.andLike(AreaInfo::getAreaName, "%" + areaName + "%");
        }
        where.setOrderBy(AreaInfo::getAreaName);
        List<AreaInfo> areaInfos = areaInfoService.selectByParams(where);
        return success(areaInfos);
    }

    /**
     * 查询地区信息树型结构
     *
     * @param request request
     * @param areaId  地区主键
     * @return obj
     */
    @GET
    @Path("areaTree")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "地区查询")
    public Result<List<AreaVO>> findAreaInfo(@Context HttpServletRequest request,
                                             @DefaultValue("0") @QueryParam("areaId") Integer areaId) {
        List<AreaVO> areaVos = findChildArea(areaId);
        return success(areaVos);
    }

    private List<AreaVO> findChildArea(int parentAreaId) {
        List<AreaVO> areaVos = new ArrayList<>();
        List<AreaInfo> areaInfos = areaInfoService.findAreaByParentAreaId(parentAreaId, null);
        for (AreaInfo areaInfo : areaInfos) {
            Integer areaId = areaInfo.getAreaId();
            AreaVO areaVo = new AreaVO();
            areaVo.setAreaId(areaId);
            areaVo.setAreaName(areaInfo.getAreaName());
            areaVo.setParentAreaId(areaInfo.getParentAreaId());
            areaVo.setAreaLevel(areaInfo.getAreaLevel());
            areaVo.setSortNum(areaInfo.getSortNum());
            areaVo.setHotStatus(areaInfo.getHotStatus());
            areaVo.setActiveStatus(areaInfo.getActiveStatus());
            areaVo.setLatitude(areaInfo.getLatitude());
            areaVo.setLongitude(areaInfo.getLongitude());
            int childrenNum = areaInfoService.findAreaByParentAreaId(areaId, null).size();
            if (childrenNum > 0) {
                areaVo.setChildren(findChildArea(areaId));
            } else {
                areaVo.setChildren(new ArrayList<>());
            }
            areaVos.add(areaVo);
        }
        return areaVos;
    }

    @POST
    @Path("add")
    @UriPermissions
    @FormValid
    @Log(operatorType = Log.OperateType.ADD, remark = "地区添加")
    public Result add(@Context HttpServletRequest request,
                      @NotBlank @FormParam("areaName") String areaName,
                      @FormParam("latitude") String latitude,
                      @NotBlank @FormParam("parentAreaId") Integer parentAreaId,
                      @DefaultValue("0") @FormParam("sortNum") Integer sortNum,
                      @DefaultValue("2") @FormParam("hotStatus") Integer hotStatus,
                      @FormParam("longitude") String longitude) {
        AreaInfo areaInfo = new AreaInfo();
        areaInfo.setParentAreaId(parentAreaId);
        areaInfo.setLatitude(latitude);
        areaInfo.setLongitude(longitude);
        areaInfo.setSortNum(sortNum);
        areaInfo.setHotStatus(hotStatus);
        areaInfo.setAreaName(areaName);
        areaInfoService.addAreaInfo(areaInfo);

        AreaInfo parentAreaInfo = areaInfoService.selectById(parentAreaId);
        String areaIds = parentAreaInfo.getAreaIds();
        String areaNames = areaInfo.getAreaName();
        if (parentAreaId.intValue() != 1) {
            areaNames = parentAreaInfo.getAreaNames();
            areaNames = areaNames + "," + areaInfo.getAreaName();
        }
        areaIds = areaIds + "," + areaInfo.getAreaId();
        AreaInfo updateAreaInfo = new AreaInfo();
        updateAreaInfo.setAreaId(areaInfo.getAreaId());
        updateAreaInfo.setAreaIds(areaIds);
        updateAreaInfo.setAreaName(areaNames);
        areaInfoService.updateAreaInfo(updateAreaInfo);
        return success();
    }

    @POST
    @Path("update")
    @UriPermissions
    @FormValid
    @Log(operatorType = Log.OperateType.UPDATE, remark = "地区修改")
    public Result update(@Context HttpServletRequest request,
                         @NotBlank @FormParam("areaId") Integer areaId,
                         @NotBlank @FormParam("areaName") String areaName,
                         @FormParam("latitude") String latitude,
                         @DefaultValue("0") @FormParam("sortNum") Integer sortNum,
                         @DefaultValue("2") @FormParam("hotStatus") Integer hotStatus,
                         @FormParam("longitude") String longitude) {
        AreaInfo areaInfo = new AreaInfo();
        areaInfo.setAreaId(areaId);
        areaInfo.setAreaName(areaName);
        areaInfo.setLongitude(longitude);
        areaInfo.setSortNum(sortNum);
        areaInfo.setHotStatus(hotStatus);
        areaInfo.setLatitude(latitude);
        areaInfoService.updateAreaInfo(areaInfo);
        return success();
    }

    @POST
    @Path("del")
    @UriPermissions
    @FormValid
    @Log(operatorType = Log.OperateType.DEL, remark = "地区删除")
    public Result del(@Context HttpServletRequest request,
                      @FormParam("areaId") Integer areaId) {
        List<AreaInfo> areaInfos = areaInfoService.findAreaByParentAreaId(areaId, null);

        if (areaInfos.size() > 0) {
            return failure("还有子集不能删除");
        }
        areaInfoService.delArea(areaId);
        return success();
    }

    @POST
    @Path("updateActiveStatus")
    @FormValid
    @UriPermissions
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改部门启用状态")
    public Result<Object> updateActiveStatus(@Context HttpServletRequest request,
                                             @NotBlank @FormParam("areaId") Integer areaId,
                                             @NotBlank @FormParam("activeStatus") Integer activeStatus) {
        AreaInfo areaInfo = new AreaInfo();
        areaInfo.setAreaId(areaId);
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        if (Enums.ActiveStatus.getActiveStatus(activeStatus) == null) {
            return failure("当前活动状态不存在！");
        }
        criteria.andEqual(AreaInfo::getParentAreaId, areaId);
        long count = areaInfoService.selectCountByParams(where);
        if (count > 0) {
            return failure("还有子集修改活动状态！");
        }
        areaInfo.setActiveStatus(activeStatus);
        areaInfoService.updateByEntity(areaInfo);
        return success();
    }
}

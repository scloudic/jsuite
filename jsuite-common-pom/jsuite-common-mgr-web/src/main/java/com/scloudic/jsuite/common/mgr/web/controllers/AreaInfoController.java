package com.scloudic.jsuite.common.mgr.web.controllers;

import com.scloudic.jsuite.common.entity.AreaInfo;
import com.scloudic.jsuite.common.mgr.web.model.AreaActiveStatusDto;
import com.scloudic.jsuite.common.mgr.web.model.AreaDelDto;
import com.scloudic.jsuite.common.service.AreaInfoService;
import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.security.authz.annotation.UriPermissions;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 地区管理
 *
 * @author min.xie
 */
@RestController
@RequestMapping("/jsuite/areaMgr/")
public class AreaInfoController extends AbstractRabbitController {
    @Autowired
    private AreaInfoService areaInfoService;

    /**
     * 查询地区信息树型结构
     *
     * @param areaId 地区主键
     * @return obj
     */
    @GetMapping("areaTree")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "地区查询")
    public Result<List<AreaInfo>> findAreaInfo(@RequestParam(value = "areaId", defaultValue = "0", required = false) Integer areaId) {
        List<AreaInfo> areaInfos = areaInfoService.findAreaByParentAreaId(areaId, null);
        areaInfos.forEach(areaInfo -> {
            Integer pAreaId = areaInfo.getAreaId();
            int childrenNum = areaInfoService.findAreaByParentAreaId(pAreaId, null).size();
            areaInfo.setChildrenNum(childrenNum);
            if (childrenNum > 0) {
                areaInfo.setIsParentNode("true");
            } else {
                areaInfo.setIsParentNode("false");
            }
        });
        return success(areaInfos);
    }

    @PostMapping("add")
    @UriPermissions
    @FormValid(fieldFilter = {"areaId", "activeStatus"})
    @Log(operatorType = Log.OperateType.ADD, remark = "地区添加")
    public Result add(@RequestBody AreaInfo areaInfo) {
        if (areaInfo.getSortNum() == null) {
            areaInfo.setSortNum(0);
        }
        if (areaInfo.getHotStatus() == null) {
            areaInfo.setHotStatus(2);
        }
        areaInfo.setActiveStatus(Enums.ActiveStatus.OPEN.getValue());
        areaInfo.setCreateTime(new Date());
        areaInfoService.addAreaInfo(areaInfo);

        AreaInfo parentAreaInfo = areaInfoService.selectById(areaInfo.getParentAreaId());
        String areaIds = parentAreaInfo.getAreaIds();
        String areaNames = areaInfo.getAreaName();
        if (areaInfo.getParentAreaId().intValue() != 1) {
            areaNames = parentAreaInfo.getAreaNames();
            areaNames = areaNames + "," + areaInfo.getAreaName();
        }
        areaIds = areaIds + "," + areaInfo.getAreaId();
        AreaInfo updateAreaInfo = new AreaInfo();
        updateAreaInfo.setAreaId(areaInfo.getAreaId());
        updateAreaInfo.setAreaIds(areaIds);
        updateAreaInfo.setAreaNames(areaNames);
        areaInfoService.updateAreaInfo(updateAreaInfo);
        return success();
    }

    @PostMapping("update")
    @UriPermissions
    @FormValid(fieldFilter = "activeStatus")
    @Log(operatorType = Log.OperateType.UPDATE, remark = "地区修改")
    public Result update(@RequestBody AreaInfo areaInfo) {
        areaInfo.setActiveStatus(null);
        areaInfoService.updateAreaInfo(areaInfo);
        return success();
    }

    @PostMapping("del")
    @UriPermissions
    @FormValid
    @Log(operatorType = Log.OperateType.DEL, remark = "地区删除")
    public Result del(@RequestBody AreaDelDto areaDelForm) {
        List<AreaInfo> areaInfos = areaInfoService.findAreaByParentAreaId(areaDelForm.getAreaId(), null);
        if (areaInfos.size() > 0) {
            return failure("还有子集不能删除");
        }
        areaInfoService.delArea(areaDelForm.getAreaId());
        return success();
    }

    @PostMapping("updateActiveStatus")
    @FormValid
    @UriPermissions
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改地区启用状态")
    public Result<Object> updateActiveStatus(@RequestBody AreaActiveStatusDto statusForm) {
        AreaInfo areaInfo = new AreaInfo();
        areaInfo.setAreaId(statusForm.getAreaId());
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        if (Enums.ActiveStatus.getActiveStatus(statusForm.getActiveStatus()) == null) {
            return failure("当前活动状态不存在！");
        }
        criteria.andEqual(AreaInfo::getParentAreaId, statusForm.getAreaId());
        long count = areaInfoService.selectCountByParams(where);
        if (count > 0) {
            return failure("还有子栏目不能修改！");
        }
        areaInfo.setActiveStatus(statusForm.getActiveStatus());
        areaInfoService.updateByEntity(areaInfo);
        return success();
    }
}

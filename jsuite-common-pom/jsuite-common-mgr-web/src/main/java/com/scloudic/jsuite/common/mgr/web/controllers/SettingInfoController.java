package com.scloudic.jsuite.common.mgr.web.controllers;

import com.scloudic.jsuite.common.entity.SettingInfo;
import com.scloudic.jsuite.common.mgr.web.model.SettingDelDto;
import com.scloudic.jsuite.common.service.SettingInfoService;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.security.authz.annotation.UriPermissions;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/jsuite/settingMgr/")
public class SettingInfoController extends AbstractRabbitController {
    @Autowired
    private SettingInfoService settingInfoService;

    @GetMapping("list")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "分页查询用户信息")
    public Result<PageBean<SettingInfo>> list(@RequestParam(value = "settingName", required = false) String settingName,
                                              @RequestParam(value = "settingCode", required = false) String settingCode,
                                              @RequestParam(value = "pageNum", required = false) Long pageNum,
                                              @RequestParam(value = "pageSize", required = false) Long pageSize) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        if (StringUtils.isNotBlank(settingName)) {
            criteria.andLike(SettingInfo::getSettingName, "%" + settingName + "%");
        }
        if (StringUtils.isNotBlank(settingCode)) {
            criteria.andEqual(SettingInfo::getSettingCode, "%" + settingCode + "%");
        }
        PageBean<SettingInfo> settingInfoPageBean = settingInfoService.findSettingInfoPage(where, pageNum, pageSize);
        return success(settingInfoPageBean);
    }

    @UriPermissions
    @PostMapping("add")
    @FormValid(fieldFilter = "settingId")
    @Log(operatorType = Log.OperateType.ADD, remark = "配置管理添加")
    public Result add(@RequestBody SettingInfo settingInfo) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        criteria.andEqual(SettingInfo::getSettingCode, settingInfo.getSettingCode());
        Long count = settingInfoService.selectCountByParams(where);
        if (count.longValue() > 0) {
            Result.failure("设置编码已存在不能添加!");
        }
        settingInfo.setCreateTime(new Date());
        settingInfoService.insertByEntity(settingInfo);
        return Result.success();
    }

    @PostMapping("update")
    @UriPermissions
    @FormValid
    @Log(operatorType = Log.OperateType.ADD, remark = "配置管理修改")
    public Result update(@RequestBody SettingInfo settingInfo) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        criteria.andEqual(SettingInfo::getSettingCode, settingInfo.getSettingCode());
        criteria.andNotEqual(SettingInfo::getSettingId, settingInfo.getSettingId());
        Long count = settingInfoService.selectCountByParams(where);
        if (count.longValue() > 0) {
            Result.failure("设置编码已存在不能修改!");
        }
        settingInfoService.updateByEntity(settingInfo);
        return Result.success();
    }

    @UriPermissions
    @PostMapping("del")
    @FormValid
    @Log(operatorType = Log.OperateType.ADD, remark = "配置管理删除")
    public Result del(@RequestBody SettingDelDto delForm) {
        settingInfoService.deleteById(delForm.getSettingId());
        return Result.success();
    }
}

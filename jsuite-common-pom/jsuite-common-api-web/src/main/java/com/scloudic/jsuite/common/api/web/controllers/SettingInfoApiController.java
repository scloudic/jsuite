package com.scloudic.jsuite.common.api.web.controllers;

import com.scloudic.jsuite.common.entity.SettingInfo;
import com.scloudic.jsuite.common.service.SettingInfoService;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/jsuite/api/setting")
public class SettingInfoApiController extends AbstractRabbitController {
    @Autowired
    private SettingInfoService settingInfoService;

    @GetMapping("getSettingInfoByCode")
    @FormValid
    public Result<SettingInfo> getSettingInfoByCode(@NotBlank
                                                    @RequestParam("settingCode") String settingCode) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        criteria.andEqual(SettingInfo::getSettingCode, settingCode);
        SettingInfo settingInfo = settingInfoService.selectOneByParams(where);
        return Result.success(settingInfo);
    }

    @GetMapping("getValueByCode")
    @FormValid
    public Result<String> getValueByCode(@NotBlank
                                         @RequestParam("settingCode") String settingCode) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        criteria.andEqual(SettingInfo::getSettingCode, settingCode);
        SettingInfo settingInfo = settingInfoService.selectOneByParams(where);
        if (settingInfo == null) {
            return Result.failure("访问内容不存在!");
        }
        return Result.success(settingInfo.getSettingValue());
    }
}

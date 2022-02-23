package com.scloudic.jsuite.common.api.web.controllers;

import com.scloudic.jsuite.common.entity.SettingInfo;
import com.scloudic.jsuite.common.service.SettingInfoService;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
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

@Component
@Singleton
@Path("/jsuite/api/setting")
public class SettingInfoApiController extends AbstractContextResource {
    @Autowired
    private SettingInfoService settingInfoService;

    @GET
    @Path("getSettingInfoByCode")
    @FormValid
    public Result<SettingInfo> getSettingInfoByCode(@NotBlank
                                                    @QueryParam("settingCode") String settingCode) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        criteria.andEqual(SettingInfo::getSettingCode, settingCode);
        SettingInfo settingInfo = settingInfoService.selectOneByParams(where);
        return Result.success(settingInfo);
    }

    @GET
    @Path("getValueByCode")
    @FormValid
    public Result<String> getValueByCode(@NotBlank
                                         @QueryParam("settingCode") String settingCode) {
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

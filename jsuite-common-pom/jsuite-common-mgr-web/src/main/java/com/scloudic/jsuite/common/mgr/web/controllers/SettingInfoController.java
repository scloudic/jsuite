package com.scloudic.jsuite.common.mgr.web.controllers;

import com.scloudic.jsuite.common.entity.SettingInfo;
import com.scloudic.jsuite.common.service.SettingInfoService;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.security.authz.annotation.UriPermissions;
import com.scloudic.rabbitframework.web.AbstractContextResource;
import com.scloudic.rabbitframework.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.Date;

@Component
@Singleton
@Path("/jsuite/settingMgr/")
public class SettingInfoController extends AbstractContextResource {
    @Autowired
    private SettingInfoService settingInfoService;

    @GET
    @Path("list")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "分页查询用户信息")
    public Result<PageBean<SettingInfo>> list(@Context HttpServletRequest request,
                                              @QueryParam("settingName") String settingName,
                                              @QueryParam("settingCode") String settingCode,
                                              @QueryParam("pageNum") Long pageNum,
                                              @QueryParam("pageSize") Long pageSize) {
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

    @POST
    @UriPermissions
    @Path("add")
    @Log(operatorType = Log.OperateType.ADD, remark = "配置管理添加")
    public Result add(@Context HttpServletRequest request,
                      @NotBlank @FormParam("settingName") String settingName,
                      @NotBlank @FormParam("settingValue") String settingValue,
                      @NotBlank @FormParam("settingCode") String settingCode,
                      @FormParam("remark") String remark) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        criteria.andEqual(SettingInfo::getSettingCode, settingValue);
        Long count = settingInfoService.selectCountByParams(where);
        if (count.longValue() > 0) {
            Result.failure("设置编码已存在不能添加!");
        }
        SettingInfo settingInfo = new SettingInfo();
        settingInfo.setSettingName(settingName);
        settingInfo.setSettingCode(settingCode);
        settingInfo.setSettingValue(settingValue);
        settingInfo.setRemark(remark);
        settingInfo.setCreateTime(new Date());
        settingInfoService.insertByEntity(settingInfo);
        return Result.success();
    }

    @POST
    @UriPermissions
    @Path("update")
    @Log(operatorType = Log.OperateType.ADD, remark = "配置管理修改")
    public Result update(@Context HttpServletRequest request,
                         @NotBlank @FormParam("settingId") Integer settingId,
                         @NotBlank @FormParam("settingName") String settingName,
                         @NotBlank @FormParam("settingValue") String settingValue,
                         @NotBlank @FormParam("settingCode") String settingCode,
                         @FormParam("remark") String remark) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        criteria.andEqual(SettingInfo::getSettingCode, settingValue);
        criteria.andNotEqual(SettingInfo::getSettingId, settingId);
        Long count = settingInfoService.selectCountByParams(where);
        if (count.longValue() > 0) {
            Result.failure("设置编码已存在不能修改!");
        }
        SettingInfo settingInfo = new SettingInfo();
        settingInfo.setSettingId(settingId);
        settingInfo.setSettingName(settingName);
        settingInfo.setSettingCode(settingCode);
        settingInfo.setSettingValue(settingValue);
        settingInfo.setRemark(remark);
        settingInfoService.updateByEntity(settingInfo);
        return Result.success();
    }

    @POST
    @UriPermissions
    @Path("del")
    @Log(operatorType = Log.OperateType.ADD, remark = "配置管理删除")
    public Result del(@Context HttpServletRequest request,
                      @NotBlank @FormParam("settingId") Integer settingId) {
        settingInfoService.deleteById(settingId);
        return Result.success();
    }
}

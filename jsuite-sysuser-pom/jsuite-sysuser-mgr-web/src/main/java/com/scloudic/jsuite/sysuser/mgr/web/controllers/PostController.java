package com.scloudic.jsuite.sysuser.mgr.web.controllers;

import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.jsuite.sysuser.mgr.entity.SysPost;
import com.scloudic.jsuite.sysuser.mgr.service.SysPostService;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.PageBean;
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
import java.util.Date;
import java.util.List;

/**
 * 岗位管理
 */
@Component
@Path("/jsuite/postMgr")
@Singleton
public class PostController extends AbstractContextResource {
    @Autowired
    private SysPostService sysPostService;

    @GET
    @Path("listPage")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "分页查询岗位记录")
    public Result<PageBean<SysPost>> listPage(@Context HttpServletRequest request, @QueryParam("postName") String postName,
                                              @QueryParam("pageNum") Long pageNum,
                                              @QueryParam("pageSize") Long pageSize) {
        PageBean<SysPost> sysPosts = sysPostService.listPage(postName, pageNum, pageSize);
        return success(sysPosts);
    }

    /**
     * 获取所有启用未删除状态的岗位记录
     *
     * @param request request
     * @return json
     */
    @GET
    @Path("apiList")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "获取所有岗位记录")
    public Result<List<SysPost>> list(@Context HttpServletRequest request) {
        Where where = new Where();
        where.createCriteria()
                .andEqual(SysPost::getDelStatus, Enums.DelStatus.NORMAL.getValue())
                .andEqual(SysPost::getActiveStatus, Enums.ActiveStatus.OPEN.getValue());
        where.setOrderBy(SysPost::getSortNum);
        List<SysPost> sysPosts = sysPostService.selectByParams(where);
        return success(sysPosts);
    }

    @POST
    @Path("add")
    @UriPermissions
    @FormValid
    @Log(operatorType = Log.OperateType.ADD, remark = "添加岗位信息")
    public Result add(@Context HttpServletRequest request, @NotBlank @FormParam("postName") String postName,
                      @NotBlank @FormParam("postCode") String postCode,
                      @NotBlank @DefaultValue("0") @FormParam("sortNum") Integer sortNum,
                      @FormParam("remark") String remark) {
        Where where = new Where();
        where.createCriteria()
                .andNotEqual(SysPost::getDelStatus, Enums.DelStatus.NORMAL.getValue())
                .andEqual(SysPost::getPostName, postName)
                .andEqual(SysPost::getPostCode, postCode);
        long totalCount = sysPostService.selectCountByParams(where);
        if (totalCount > 0) {
            throw new BizException("存在相同的岗位信息!");
        }
        SysPost sysPost = new SysPost();
        sysPost.setActiveStatus(Enums.ActiveStatus.OPEN.getValue());
        sysPost.setDelStatus(Enums.DelStatus.NORMAL.getValue());
        sysPost.setCreateTime(new Date());
        sysPost.setUpdateTime(new Date());
        sysPost.setPostCode(postCode);
        sysPost.setPostName(postName);
        sysPost.setRemark(remark);
        sysPost.setSortNum(sortNum);
        sysPostService.insertByEntity(sysPost);
        return success();
    }

    /**
     * 修改岗位启用状态
     *
     * @param request      request
     * @param postId       岗位主键
     * @param activeStatus 启用状态(1、正常,2、停用)
     * @return json
     */
    @POST
    @Path("updateActiveStatus")
    @FormValid
    @UriPermissions
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改岗位启用状态")
    public Result updateActiveStatus(@Context HttpServletRequest request,
                                     @NotBlank @FormParam("postId") Integer postId,
                                     @NotBlank @FormParam("activeStatus") Integer activeStatus) {
        SysPost sysPost = new SysPost();
        sysPost.setPostId(postId);
        if (Enums.ActiveStatus.getActiveStatus(activeStatus) == null) {
            throw new BizException("activeStatus.null");
        }
        sysPost.setActiveStatus(activeStatus);
        sysPost.setUpdateTime(new Date());
        sysPostService.updateByEntity(sysPost);
        return success();
    }

    @POST
    @Path("update")
    @UriPermissions
    @FormValid
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改岗位信息")
    public Result update(@Context HttpServletRequest request,
                         @NotBlank @FormParam("postId") Integer postId,
                         @NotBlank @FormParam("postName") String postName,
                         @NotBlank @FormParam("postCode") String postCode,
                         @NotBlank @FormParam("sortNum") Integer sortNum,
                         @NotBlank @FormParam("remark") String remark) {
        Where where = new Where();
        where.createCriteria()
                .andNotEqual(SysPost::getDelStatus, Enums.DelStatus.NORMAL.getValue())
                .andEqual(SysPost::getPostName, postName)
                .andEqual(SysPost::getPostCode, postCode)
                .andNotEqual(SysPost::getPostId, postId);
        long totalCount = sysPostService.selectCountByParams(where);
        if (totalCount > 0) {
            throw new BizException("存在相同的岗位信息!");
        }
        SysPost sysPost = new SysPost();
        sysPost.setUpdateTime(new Date());
        sysPost.setPostCode(postCode);
        sysPost.setPostId(postId);
        sysPost.setPostName(postName);
        sysPost.setRemark(remark);
        sysPost.setSortNum(sortNum);
        sysPostService.updateByEntity(sysPost);
        return success();
    }

    @POST
    @Path("del")
    @UriPermissions
    @FormValid
    @Log(operatorType = Log.OperateType.DEL, remark = "删除岗位信息")
    public Result del(@Context HttpServletRequest request, @NotBlank @FormParam("postId") Integer postId) {
        SysPost sysPost = new SysPost();
        sysPost.setPostId(postId);
        sysPost.setDelStatus(Enums.DelStatus.DEL.getValue());
        sysPostService.updateByEntity(sysPost);
        return success();
    }

}

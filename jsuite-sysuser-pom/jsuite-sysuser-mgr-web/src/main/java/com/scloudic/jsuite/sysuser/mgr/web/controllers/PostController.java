package com.scloudic.jsuite.sysuser.mgr.web.controllers;

import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.jsuite.sysuser.mgr.entity.SysPost;
import com.scloudic.jsuite.sysuser.mgr.service.SysPostService;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.PageBean;
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
 * 岗位管理
 */
@RestController
@RequestMapping("/jsuite/postMgr")
public class PostController extends AbstractRabbitController {
    @Autowired
    private SysPostService sysPostService;

    @GetMapping("listPage")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "分页查询岗位记录")
    public Result<PageBean<SysPost>> listPage(@RequestParam(value = "postName", required = false) String postName,
                                              @RequestParam(value = "pageNum", required = false) Long pageNum,
                                              @RequestParam(value = "pageSize", required = false) Long pageSize) {
        PageBean<SysPost> sysPosts = sysPostService.listPage(postName, pageNum, pageSize);
        return success(sysPosts);
    }

    /**
     * 获取所有启用未删除状态的岗位记录
     *
     * @return json
     */
    @GetMapping("apiList")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "获取所有岗位记录")
    public Result<List<SysPost>> list() {
        Where where = new Where();
        where.createCriteria()
                .andEqual(SysPost::getDelStatus, Enums.DelStatus.NORMAL.getValue())
                .andEqual(SysPost::getActiveStatus, Enums.ActiveStatus.OPEN.getValue());
        where.setOrderBy(SysPost::getSortNum);
        List<SysPost> sysPosts = sysPostService.selectByParams(where);
        return success(sysPosts);
    }

    @PostMapping("add")
    @UriPermissions
    @FormValid(fieldFilter = {"postId", "activeStatus"})
    @Log(operatorType = Log.OperateType.ADD, remark = "添加岗位信息")
    public Result<Integer> add(@RequestBody SysPost sysPost) {
        if (sysPost.getSortNum() == null) {
            sysPost.setSortNum(0);
        }
        String postName = sysPost.getPostName();
        String postCode = sysPost.getPostCode();
        Where where = new Where();
        where.createCriteria()
                .andNotEqual(SysPost::getDelStatus, Enums.DelStatus.NORMAL.getValue())
                .andEqual(SysPost::getPostName, postName)
                .andEqual(SysPost::getPostCode, postCode);
        long totalCount = sysPostService.selectCountByParams(where);
        if (totalCount > 0) {
            throw new BizException("存在相同的岗位信息!");
        }
        sysPost.setActiveStatus(Enums.ActiveStatus.OPEN.getValue());
        sysPost.setDelStatus(Enums.DelStatus.NORMAL.getValue());
        sysPost.setCreateTime(new Date());
        sysPost.setUpdateTime(new Date());
        sysPostService.insertByEntity(sysPost);
        return success(sysPost.getPostId());
    }

    /**
     * 修改岗位启用状态
     *
     * @param post
     * @return
     */
    @PostMapping("updateActiveStatus")
    @FormValid(fieldFilter = {"postName", "postCode"})
    @UriPermissions
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改岗位启用状态")
    public Result updateActiveStatus(@RequestBody SysPost post) {
        Integer postId = post.getPostId();
        Integer activeStatus = post.getActiveStatus();
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

    @PostMapping("update")
    @UriPermissions
    @FormValid(fieldFilter = "activeStatus")
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改岗位信息")
    public Result update(@RequestBody SysPost sysPost) {
        Where where = new Where();
        where.createCriteria()
                .andNotEqual(SysPost::getDelStatus, Enums.DelStatus.NORMAL.getValue())
                .andEqual(SysPost::getPostName, sysPost.getPostName())
                .andEqual(SysPost::getPostCode, sysPost.getPostCode())
                .andNotEqual(SysPost::getPostId, sysPost.getPostId());
        long totalCount = sysPostService.selectCountByParams(where);
        if (totalCount > 0) {
            throw new BizException("存在相同的岗位信息!");
        }
        sysPost.setActiveStatus(null);
        sysPost.setCreateTime(null);
        sysPost.setDelStatus(null);
        sysPost.setUpdateTime(new Date());
        sysPostService.updateByEntity(sysPost);
        return success(sysPost.getPostId());
    }

    @PostMapping("del")
    @UriPermissions
    @FormValid(fieldFilter = {"activeStatus", "postName", "postCode"})
    @Log(operatorType = Log.OperateType.DEL, remark = "删除岗位信息")
    public Result<Integer> del(@RequestBody SysPost post) {
        SysPost sysPost = new SysPost();
        sysPost.setPostId(post.getPostId());
        sysPost.setUpdateTime(new Date());
        sysPost.setDelStatus(Enums.DelStatus.DEL.getValue());
        sysPostService.updateByEntity(sysPost);
        return success(post.getPostId());
    }

}

package com.scloudic.jsuite.file.web.controllers;

import com.scloudic.jsuite.file.entity.FileCategory;
import com.scloudic.jsuite.file.service.FileCategoryService;
import com.scloudic.jsuite.file.service.FileInfoService;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.core.utils.UUIDUtils;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.security.SecurityUtils;
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
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

/**
 * 文件分类管理
 *
 * @since 1.0
 */
@Component
@Path("/jsuite/file/categoryMgr")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class FileCategoryController extends AbstractContextResource {
    @Autowired
    private FileCategoryService fileCategoryService;
    @Autowired
    private FileInfoService fileInfoService;

    /**
     * 　分页查询分类
     *
     * @param fileCategoryName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET
    @Path("listPage")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "分页查询分类")
    public Result<PageBean<FileCategory>> list(@Context HttpServletRequest request,
                                               @QueryParam("fileCategoryName") String fileCategoryName,
                                               @QueryParam("pageNum") Long pageNum,
                                               @QueryParam("pageSize") Long pageSize) {
        PageBean<FileCategory> categoryPageBean = fileCategoryService.findByParams(fileCategoryName, pageNum, pageSize);
        return success(categoryPageBean);
    }


    @POST
    @Path("add")
    @UriPermissions
    @FormValid
    @Log(operatorType = Log.OperateType.ADD, remark = "添加文件分类")
    public Result add(@Context HttpServletRequest request,
                      @NotBlank @FormParam("fileCategoryName") String fileCategoryName,
                      @NotBlank @FormParam("fileCategoryNameCode") String fileCategoryNameCode,
                      @DefaultValue("0") @FormParam("sortNum") Integer sortNum) {
        String userId = SecurityUtils.getUserId();
        FileCategory fileCategory = new FileCategory();
        fileCategory.setUserId(userId);
        fileCategory.setCreateTime(new Date());
        fileCategory.setSortNum(sortNum);
        fileCategory.setFileCategoryNameCode(fileCategoryNameCode);
        fileCategory.setFileCategoryName(fileCategoryName);
        fileCategory.setFileCategoryId(UUIDUtils.getTimeUUID32());
        fileCategoryService.insertByEntity(fileCategory);
        return success();
    }

    @POST
    @Path("update")
    @UriPermissions
    @FormValid
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改文件分类")
    public Result update(@Context HttpServletRequest request,
                         @NotBlank @FormParam("fileCategoryId") String fileCategoryId,
                         @NotBlank @FormParam("fileCategoryName") String fileCategoryName,
                         @NotBlank @FormParam("fileCategoryNameCode") String fileCategoryNameCode,
                         @FormParam("sortNum") Integer sortNum) {
        String userId = SecurityUtils.getUserId();
        FileCategory fileCategory = new FileCategory();
        fileCategory.setUserId(userId);
        fileCategory.setSortNum(sortNum);
        fileCategory.setFileCategoryNameCode(fileCategoryNameCode);
        fileCategory.setCreateTime(new Date());
        fileCategory.setFileCategoryId(fileCategoryId);
        fileCategory.setFileCategoryName(fileCategoryName);
        fileCategoryService.updateByEntity(fileCategory);
        return success();
    }

    @POST
    @Path("del")
    @UriPermissions
    @FormValid
    @Log(operatorType = Log.OperateType.DEL, remark = "删除文件分类")
    public Result del(@Context HttpServletRequest request,
                      @NotBlank @FormParam("fileCategoryId") String fileCategoryId) {
        if ("0".equals(fileCategoryId)) {
            throw new BizException("系统定义分类不允许删除");
        }
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        criteria.andEqual(FileCategory::getFileCategoryId, fileCategoryId);
        long totalCount = fileInfoService.selectCountByParams(where);
        if (totalCount > 0) {
            throw new BizException("该分下类还有其它文件不能删除");
        }
        fileCategoryService.deleteById(fileCategoryId);
        return success();
    }


    /**
     * 获取的有文件分类
     *
     * @return
     */
    @GET
    @Path("listAll")
    @UriPermissions
    public Result<List<FileCategory>> listAll(@Context HttpServletRequest request) {
        Where where = new Where();
        where.setOrderBy(FileCategory::getSortNum);
        List<FileCategory> fileCategories = fileCategoryService.selectByParams(where);
        return success(fileCategories);
    }
}

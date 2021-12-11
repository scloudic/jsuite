package com.scloudic.jsuite.article.mgr.web.controllers;

import com.scloudic.jsuite.article.entity.ArticleCategory;
import com.scloudic.jsuite.article.mgr.web.model.ArticleCategoryForm;
import com.scloudic.jsuite.article.service.ArticleCategoryService;
import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.rabbitframework.core.utils.BeanUtils;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.core.utils.StringUtils;
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
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.Date;

@Component
@Path("/jsuite/articleCategoryMgr")
@Singleton
public class ArticleCategoryController extends AbstractContextResource {
    @Autowired
    private ArticleCategoryService articleCategoryService;

    @GET
    @Path("list")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "查询文章分类")
    public Result<PageBean<ArticleCategory>> list(@Context HttpServletRequest request,
                                                  @QueryParam("articleCategoryName") String articleCategoryName,
                                                  @QueryParam("pageNum") Long pageNum,
                                                  @QueryParam("pageSize") Long pageSize) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        criteria.andEqual(ArticleCategory::getDelStatus, Enums.DelStatus.NORMAL.getValue());
        criteria.andLike(StringUtils.isNotBlank(articleCategoryName),
                ArticleCategory::getArticleCategoryName, "%" + articleCategoryName + "%");
        PageBean<ArticleCategory> pageBean = articleCategoryService.selectPageBeanByParams(where, pageNum, pageSize);
        return success(pageBean);
    }

    @POST
    @Path("add")
    @UriPermissions
    @Log(operatorType = Log.OperateType.ADD, remark = "添加文章分类")
    @FormValid(fieldFilter = {"articleCategoryId"})
    public Result<Long> add(@Context HttpServletRequest request,
                            @BeanParam ArticleCategoryForm articleCategoryForm) {
        Date currDate = new Date();
        ArticleCategory articleCategory = new ArticleCategory();
        BeanUtils.copyProperties(articleCategory, articleCategoryForm);
        articleCategory.setDelStatus(Enums.DelStatus.NORMAL.getValue());
        articleCategory.setUserId(SecurityUtils.getUserId());
        articleCategory.setCreateTime(currDate);
        articleCategory.setUpdateTime(currDate);
        articleCategoryService.insertByEntity(articleCategory);
        return success(articleCategory.getArticleCategoryId());
    }

    @POST
    @Path("update")
    @UriPermissions
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改文章分类")
    @FormValid
    public Result<Long> update(@Context HttpServletRequest request,
                               @BeanParam ArticleCategoryForm articleCategoryForm) {
        Date currDate = new Date();
        ArticleCategory articleCategory = new ArticleCategory();
        BeanUtils.copyProperties(articleCategory, articleCategoryForm);
        articleCategory.setUserId(SecurityUtils.getUserId());
        articleCategory.setUpdateTime(currDate);
        articleCategoryService.updateByEntity(articleCategory);
        return success(articleCategory.getArticleCategoryId());
    }

    @POST
    @Path("del")
    @UriPermissions
    @Log(operatorType = Log.OperateType.DEL, remark = "删除文章分类")
    @FormValid
    public Result<Long> del(@Context HttpServletRequest request,
                            @FormParam("articleCategoryId") Long articleCategoryId) {
        Date currDate = new Date();
        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setArticleCategoryId(articleCategoryId);
        articleCategory.setDelStatus(Enums.DelStatus.DEL.getValue());
        articleCategory.setUserId(SecurityUtils.getUserId());
        articleCategory.setUpdateTime(currDate);
        articleCategoryService.updateByEntity(articleCategory);
        return success(articleCategoryId);
    }
}

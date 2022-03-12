package com.scloudic.jsuite.article.mgr.web.controllers;

import com.scloudic.jsuite.article.entity.ArticleCategory;
import com.scloudic.jsuite.article.mgr.web.model.ArticleCategoryDelDto;
import com.scloudic.jsuite.article.mgr.web.model.ArticleCategoryDto;
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
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/jsuite/articleCategoryMgr")
public class ArticleCategoryController extends AbstractRabbitController {
    @Autowired
    private ArticleCategoryService articleCategoryService;

    @GetMapping("list")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "查询文章分类")
    public Result<PageBean<ArticleCategory>> list(@RequestParam("articleCategoryName") String articleCategoryName,
                                                  @RequestParam("pageNum") Long pageNum,
                                                  @RequestParam("pageSize") Long pageSize) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        criteria.andEqual(ArticleCategory::getDelStatus, Enums.DelStatus.NORMAL.getValue());
        criteria.andLike(StringUtils.isNotBlank(articleCategoryName),
                ArticleCategory::getArticleCategoryName, "%" + articleCategoryName + "%");
        PageBean<ArticleCategory> pageBean = articleCategoryService.selectPageBeanByParams(where, pageNum, pageSize);
        return success(pageBean);
    }

    @PostMapping("add")
    @UriPermissions
    @Log(operatorType = Log.OperateType.ADD, remark = "添加文章分类")
    @FormValid(fieldFilter = {"articleCategoryId"})
    public Result<Long> add(@RequestBody ArticleCategoryDto articleCategoryForm) {
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

    @PostMapping("update")
    @UriPermissions
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改文章分类")
    @FormValid
    public Result<Long> update(@RequestBody ArticleCategoryDto articleCategoryDto) {
        Date currDate = new Date();
        ArticleCategory articleCategory = new ArticleCategory();
        BeanUtils.copyProperties(articleCategory, articleCategoryDto);
        articleCategory.setUserId(SecurityUtils.getUserId());
        articleCategory.setUpdateTime(currDate);
        articleCategoryService.updateByEntity(articleCategory);
        return success(articleCategory.getArticleCategoryId());
    }

    @PostMapping("del")
    @UriPermissions
    @Log(operatorType = Log.OperateType.DEL, remark = "删除文章分类")
    @FormValid
    public Result<Long> del(@RequestBody ArticleCategoryDelDto delForm) {
        Date currDate = new Date();
        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setArticleCategoryId(delForm.getArticleCategoryId());
        articleCategory.setDelStatus(Enums.DelStatus.DEL.getValue());
        articleCategory.setUserId(SecurityUtils.getUserId());
        articleCategory.setUpdateTime(currDate);
        articleCategoryService.updateByEntity(articleCategory);
        return success(delForm.getArticleCategoryId());
    }
}

package com.scloudic.jsuite.article.mgr.web.controllers;

import com.scloudic.jsuite.article.ArticleEnums;
import com.scloudic.jsuite.article.entity.Article;
import com.scloudic.jsuite.article.entity.ArticleCategoryMapping;
import com.scloudic.jsuite.article.mgr.web.model.ArticleForm;
import com.scloudic.jsuite.article.service.ArticleService;
import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.rabbitframework.core.utils.BeanUtils;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.core.utils.StringUtils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Path("/jsuite/articleMgr")
@Singleton
public class ArticleController extends AbstractContextResource {
    @Autowired
    private ArticleService articleService;

    /**
     * 创建文章
     *
     * @param request
     * @param articleForm
     * @return
     */
    @POST
    @Path("add")
    @UriPermissions
    @FormValid(fieldFilter = {"articleId"})
    public Result<String> add(@Context HttpServletRequest request,
                              @BeanParam ArticleForm articleForm) {
        Article article = new Article();
        Date date = new Date();
        BeanUtils.copyProperties(article, articleForm);
        article.setDelStatus(Enums.DelStatus.NORMAL.getValue());
        article.setArticleStatus(ArticleEnums.ArticleStatus.DRAFT.getValue());
        article.setCreateTime(date);
        article.setUpdateTime(date);
        article.setArticleId(UUIDUtils.getTimeUUID32());
        article.setUserId(SecurityUtils.getUserId());
        article.setViewCount(0);
        article.setCommentCount(0);
        if (StringUtils.isBlank(articleForm.getUserName())) {
            article.setUserName(SecurityUtils.getSecurityUser().getNickName());
        }
        List<ArticleCategoryMapping> mappings = new ArrayList<>();
        List<Long> articleCategoryIds = articleForm.getArticleCategoryId();
        articleCategoryIds.forEach(articleCategoryId -> {
            ArticleCategoryMapping mapping = new ArticleCategoryMapping();
            mapping.setArticleId(article.getArticleId());
            mapping.setArticleCategoryId(articleCategoryId);
            mappings.add(mapping);
        });
        articleService.save(article, mappings);
        return success(article.getArticleId());
    }

    @POST
    @Path("update")
    @UriPermissions
    @FormValid
    public Result<String> update(@Context HttpServletRequest request,
                                 @BeanParam ArticleForm articleForm) {
        Article article = new Article();
        Date date = new Date();
        BeanUtils.copyProperties(article, articleForm);
        article.setDelStatus(Enums.DelStatus.NORMAL.getValue());
        article.setArticleStatus(ArticleEnums.ArticleStatus.DRAFT.getValue());
        article.setCreateTime(date);
        article.setUpdateTime(date);
        article.setUserId(SecurityUtils.getUserId());
        if (StringUtils.isBlank(articleForm.getUserName())) {
            article.setUserName(SecurityUtils.getSecurityUser().getNickName());
        }
        List<ArticleCategoryMapping> mappings = new ArrayList<>();
        List<Long> articleCategoryIds = articleForm.getArticleCategoryId();
        articleCategoryIds.forEach(articleCategoryId -> {
            ArticleCategoryMapping mapping = new ArticleCategoryMapping();
            mapping.setArticleId(article.getArticleId());
            mapping.setArticleCategoryId(articleCategoryId);
            mappings.add(mapping);
        });
        articleService.updateArticle(article, mappings);
        return success(article.getArticleId());
    }

    @GET
    @Path("articlePublish")
    @UriPermissions
    @FormValid
    public Result<String> articlePublish(@Context HttpServletRequest request,
                                         @NotBlank @QueryParam("articleId") String articleId) {
        Article article = articleService.selectById(articleId);
        if (article == null || article.getDelStatus().intValue() == Enums.DelStatus.DEL.getValue()) {
            return failure("文章不存在!");
        }
        Article update = new Article();
        update.setArticleId(articleId);
        update.setUpdateTime(new Date());
        update.setArticleStatus(ArticleEnums.ArticleStatus.PUBLISH.getValue());
        articleService.updateByEntity(update);
        return success(articleId);
    }

    @GET
    @Path("getArticleDetail")
    @UriPermissions
    @FormValid
    public Result<Article> getArticleDetail(@Context HttpServletRequest request,
                                            @NotBlank @QueryParam("articleId") String articleId) {
        Article article = articleService.selectById(articleId);
        if (article == null || article.getDelStatus().intValue() == Enums.DelStatus.DEL.getValue()) {
            return failure("文章不存在！");
        }
        return success(article);
    }

    @GET
    @Path("list")
    @UriPermissions
    public Result<PageBean<Article>> list(@Context HttpServletRequest request,
                                          @QueryParam("articleTitle") String articleTitle,
                                          @QueryParam("pageNum") Long pageNum,
                                          @QueryParam("pageSize") Long pageSize) {
        Where where = new Where(Article.NO_CONTENT_FIELDS);
        Criteria criteria = where.createCriteria();
        criteria.andLike(StringUtils.isNotBlank(articleTitle), Article::getArticleTitle, "%" + articleTitle + "%");
        PageBean<Article> articles = articleService.selectPageBeanByParams(where, pageNum, pageSize);
        return success(articles);
    }
}

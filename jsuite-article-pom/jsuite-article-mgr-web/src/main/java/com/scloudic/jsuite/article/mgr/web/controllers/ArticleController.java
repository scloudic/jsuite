package com.scloudic.jsuite.article.mgr.web.controllers;

import com.scloudic.jsuite.article.entity.Article;
import com.scloudic.jsuite.article.entity.ArticleCategoryMapping;
import com.scloudic.jsuite.article.mgr.web.model.ArticleDelForm;
import com.scloudic.jsuite.article.mgr.web.model.ArticleForm;
import com.scloudic.jsuite.article.service.ArticleService;
import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.rabbitframework.core.utils.BeanUtils;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.core.utils.UUIDUtils;
import com.scloudic.rabbitframework.jbatis.mapping.lambda.SFunctionUtils;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.security.SecurityUtils;
import com.scloudic.rabbitframework.security.authz.annotation.UriPermissions;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/jsuite/articleMgr")
public class ArticleController extends AbstractRabbitController {
    @Autowired
    private ArticleService articleService;

    /**
     * 创建文章
     *
     * @param articleForm
     * @return
     */
    @PostMapping("add")
    @UriPermissions
    @FormValid(fieldFilter = {"articleId"})
    public Result<String> add(@RequestBody ArticleForm articleForm) {
        Article article = new Article();
        Date date = new Date();
        BeanUtils.copyProperties(article, articleForm);
        article.setDelStatus(Enums.DelStatus.NORMAL.getValue());
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

    @PostMapping("update")
    @UriPermissions
    @FormValid
    public Result<String> update(@RequestBody ArticleForm articleForm) {
        Article article = new Article();
        Date date = new Date();
        BeanUtils.copyProperties(article, articleForm);
        article.setDelStatus(Enums.DelStatus.NORMAL.getValue());
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

    @GetMapping("getArticleDetail")
    @UriPermissions
    @FormValid
    public Result<Article> getArticleDetail(@NotBlank @RequestParam("articleId") String articleId) {
        Article article = articleService.selectById(articleId);
        if (article == null || article.getDelStatus().intValue() == Enums.DelStatus.DEL.getValue()) {
            return failure("文章不存在！");
        }
        return success(article);
    }

    @GetMapping("list")
    @UriPermissions
    public Result<PageBean<Article>> list(@RequestParam(value = "articleTitle", required = false) String articleTitle,
                                          @RequestParam(value = "startDate", required = false) String startDate,
                                          @RequestParam(value = "endDate", required = false) String endDate,
                                          @RequestParam(value = "pageNum", required = false) Long pageNum,
                                          @RequestParam(value = "pageSize", required = false) Long pageSize) {
        Where where = new Where(Article.NO_CONTENT_FIELDS);
        Criteria criteria = where.createCriteria();
        criteria.andLike(StringUtils.isNotBlank(articleTitle), Article::getArticleTitle, "%" + articleTitle + "%");
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            String createTimeField = SFunctionUtils.getFieldName(Article::getCreateTime);
            criteria.andBetween("DATE_FORMAT(" + createTimeField + ",'%Y-%m-%d')", startDate, endDate);
        }
        criteria.andEqual(Article::getDelStatus, Enums.DelStatus.NORMAL.getValue());
        PageBean<Article> articles = articleService.selectPageBeanByParams(where, pageNum, pageSize);
        return success(articles);
    }


    @PostMapping("articleDel")
    @UriPermissions
    @FormValid
    public Result<String> articleDel(@RequestBody ArticleDelForm delForm) {
        Article update = new Article();
        update.setArticleId(delForm.getArticleId());
        update.setUpdateTime(new Date());
        update.setDelStatus(Enums.DelStatus.DEL.getValue());
        articleService.updateByEntity(update);
        return success(delForm.getArticleId());
    }
}

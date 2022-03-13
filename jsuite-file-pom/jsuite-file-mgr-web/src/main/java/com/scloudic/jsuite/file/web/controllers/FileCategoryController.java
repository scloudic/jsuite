package com.scloudic.jsuite.file.web.controllers;

import com.scloudic.jsuite.file.entity.FileCategory;
import com.scloudic.jsuite.file.service.FileCategoryService;
import com.scloudic.jsuite.file.service.FileInfoService;
import com.scloudic.jsuite.file.web.model.FileCategoryDelDto;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.core.utils.UUIDUtils;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.security.SecurityUtils;
import com.scloudic.rabbitframework.security.authz.annotation.UriPermissions;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 文件分类管理
 *
 * @since 1.0
 */
@RestController
@RequestMapping("/jsuite/file/categoryMgr")
public class FileCategoryController extends AbstractRabbitController {
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
    @GetMapping("listPage")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "分页查询分类")
    public Result<PageBean<FileCategory>> list(@RequestParam(value = "fileCategoryName", required = false) String fileCategoryName,
                                               @RequestParam(value = "pageNum", required = false) Long pageNum,
                                               @RequestParam(value = "pageSize", required = false) Long pageSize) {
        PageBean<FileCategory> categoryPageBean = fileCategoryService.findByParams(fileCategoryName, pageNum, pageSize);
        return success(categoryPageBean);
    }


    @PostMapping("add")
    @UriPermissions
    @FormValid(fieldFilter = "fileCategoryId")
    @Log(operatorType = Log.OperateType.ADD, remark = "添加文件分类")
    public Result add(@RequestBody FileCategory fileCategory) {
        String userId = SecurityUtils.getUserId();
        fileCategory.setUserId(userId);
        fileCategory.setCreateTime(new Date());
        if (fileCategory.getSortNum() == null) {
            fileCategory.setSortNum(0);
        }
        fileCategory.setFileCategoryId(UUIDUtils.getTimeUUID32());
        fileCategoryService.insertByEntity(fileCategory);
        return success();
    }

    @PostMapping("update")
    @UriPermissions
    @FormValid
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改文件分类")
    public Result update(@RequestBody FileCategory fileCategory) {
        String userId = SecurityUtils.getUserId();
        fileCategory.setUserId(userId);
        fileCategoryService.updateByEntity(fileCategory);
        return success();
    }

    @PostMapping("del")
    @UriPermissions
    @FormValid
    @Log(operatorType = Log.OperateType.DEL, remark = "删除文件分类")
    public Result del(@RequestBody FileCategoryDelDto delForm) {
        if ("0".equals(delForm.getFileCategoryId())) {
            throw new BizException("系统定义分类不允许删除");
        }
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        criteria.andEqual(FileCategory::getFileCategoryId, delForm.getFileCategoryId());
        long totalCount = fileInfoService.selectCountByParams(where);
        if (totalCount > 0) {
            throw new BizException("该分下类还有其它文件不能删除");
        }
        fileCategoryService.deleteById(delForm.getFileCategoryId());
        return success();
    }


    /**
     * 获取的有文件分类
     *
     * @return
     */
    @GetMapping("listAll")
    @UriPermissions
    public Result<List<FileCategory>> listAll(HttpServletRequest request) {
        Where where = new Where();
        where.setOrderBy(FileCategory::getSortNum);
        List<FileCategory> fileCategories = fileCategoryService.selectByParams(where);
        return success(fileCategories);
    }
}

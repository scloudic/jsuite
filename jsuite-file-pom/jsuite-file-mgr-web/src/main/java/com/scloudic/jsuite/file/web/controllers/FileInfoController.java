package com.scloudic.jsuite.file.web.controllers;

import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.file.api.model.FileBaseInfo;
import com.scloudic.jsuite.file.api.service.FileService;
import com.scloudic.jsuite.file.api.utils.FileUtils;
import com.scloudic.jsuite.file.entity.FileCategory;
import com.scloudic.jsuite.file.entity.FileInfo;
import com.scloudic.jsuite.file.service.FileCategoryService;
import com.scloudic.jsuite.file.service.FileInfoService;
import com.scloudic.jsuite.file.web.model.FileInfoDto;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.DateFormatUtil;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.core.utils.UUIDUtils;
import com.scloudic.rabbitframework.security.SecurityUtils;
import com.scloudic.rabbitframework.security.authz.annotation.UriPermissions;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文件分类管理
 *
 * @since 1.0
 */
@RestController
@RequestMapping("/jsuite/fileMgr")
public class FileInfoController extends AbstractRabbitController {
    private static final Logger logger = LoggerFactory.getLogger(FileInfoController.class);
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileCategoryService fileCategoryService;

    /**
     * 　分页查询文件信息
     *
     * @param fileName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("list")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "分页查询文件信息")
    public Result<PageBean<FileInfo>> list(@RequestParam(value = "fileName", required = false) String fileName,
                                           @RequestParam(value = "fileCategoryId", required = false) String fileCategoryId,
                                           @RequestParam(value = "fileType", required = false) String fileType,
                                           @RequestParam(value = "pageNum", required = false) Long pageNum,
                                           @RequestParam(value = "pageSize", required = false) Long pageSize) {
        PageBean<FileInfo> fileInfoPageBean = fileInfoService.findFileInfo(fileName, fileCategoryId, fileType, pageNum, pageSize);
        return success(fileInfoPageBean);
    }

    @PostMapping("update")
    @UriPermissions
    @FormValid
    @Log(operatorType = Log.OperateType.UPDATE, remark = "文件修改")
    public Result<String> update(@RequestBody FileInfoDto form) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileId(form.getFileId());
        fileInfo.setFileName(form.getFileName());
        fileInfo.setFileCategoryId(form.getFileCategoryId());
        fileInfoService.updateByEntity(fileInfo);
        return success(form.getFileId());
    }

    @PostMapping("del")
    @UriPermissions
    @FormValid(fieldFilter = {"fileName", "fileCategoryId"})
    @Log(operatorType = Log.OperateType.UPDATE, remark = "文件删除")
    public Result del(@RequestBody FileInfoDto form) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileId(form.getFileId());
        fileInfo.setDelStatus(Enums.DelStatus.DEL.getValue());
        fileInfoService.updateByEntity(fileInfo);
        return success();
    }

    /**
     * 单文件上传,后台通过不同的文件类型存入相应的目录中。
     *
     * @param multipartFile
     * @param fileName
     * @param fileCategoryId
     * @return
     */
    @PostMapping("upload")
    @UriPermissions
    public Result<String> upload(@RequestPart("file") MultipartFile multipartFile,
                                 @RequestParam("fileName") String fileName,
                                 @RequestParam(value = "fileCategoryId", defaultValue = "1") String fileCategoryId) {
        InputStream is = null;
        try {
            FileCategory fileCategory = fileCategoryService.selectById(fileCategoryId);
            String fileCategoryName = fileCategory.getFileCategoryNameCode();
            String userId = SecurityUtils.getUserId();
            String srcFileName = multipartFile.getOriginalFilename(); // 文件名
            is = multipartFile.getInputStream();
            FileBaseInfo fileBaseInfo = fileService.fileUpload(fileCategoryName, srcFileName, is);
            if (StringUtils.isBlank(fileName)) {
                fileName = fileBaseInfo.getFileName();
            }
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileId(UUIDUtils.getTimeUUID32());
            fileInfo.setFileType(com.scloudic.jsuite.file.api.utils.FileUtils.getType(srcFileName).getValue());
            fileInfo.setFileName(fileName);
            fileInfo.setHttpUrl(fileBaseInfo.getHttpUrl());
            fileInfo.setCreateTime(new Date());
            fileInfo.setUserId(userId);
            fileInfo.setFilePath(fileBaseInfo.getFilePath());
            fileInfo.setFileCategoryId(fileCategoryId);
            fileInfo.setDelStatus(Enums.DelStatus.NORMAL.getValue());
            fileInfo.setFileStoreType(fileBaseInfo.getFileStore().getValue());
            fileInfo.setSrcFileName(srcFileName);
            fileInfoService.insertByEntity(fileInfo);
            return success(fileBaseInfo.getHttpUrl());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BizException("upload.file.error");
        } finally {
            if (is != null) {
                IOUtils.closeQuietly(is, null);
            }
        }
    }

    /**
     * 文件批量上传
     *
     * @param multipartFile
     * @param fileCategoryId
     * @return
     */
    @PostMapping("batchUpload")
    @UriPermissions
    public Result<List<String>> batchFileUpload(@RequestPart("files") MultipartFile[] multipartFile,
                                                @RequestParam(value = "fileCategoryId", defaultValue = "1") String fileCategoryId) {
        List<FileInfo> fileInfos = new ArrayList<FileInfo>();
        List<String> fileUrls = new ArrayList<String>();
        String userId = SecurityUtils.getUserId();
        String fileCategoryName = DateFormatUtil.dateToStr(new Date(), "yyyyMM");
        try {
            for (MultipartFile bodyPart : multipartFile) {
                InputStream is = bodyPart.getInputStream();
                try {
                    String fileName = bodyPart.getOriginalFilename(); // 文件名
                    FileBaseInfo fileBaseInfo = fileService.fileUpload(fileCategoryName, fileName, is);
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setFileId(UUIDUtils.getTimeUUID32());
                    fileInfo.setFileName(fileBaseInfo.getFileName());
                    fileInfo.setHttpUrl(fileBaseInfo.getHttpUrl());
                    fileInfo.setCreateTime(new Date());
                    fileInfo.setUserId(userId);
                    fileInfo.setFilePath(fileBaseInfo.getFilePath());
                    fileInfo.setFileType(FileUtils.getType(fileName).getValue());
                    fileInfo.setFileCategoryId(fileCategoryId);
                    fileInfo.setDelStatus(Enums.DelStatus.NORMAL.getValue());
                    fileInfo.setFileStoreType(fileBaseInfo.getFileStore().getValue());
                    fileInfo.setSrcFileName(fileName);
                    fileInfos.add(fileInfo);
                    fileUrls.add(fileBaseInfo.getHttpUrl());
                } finally {
                    IOUtils.closeQuietly(is, null);
                }
            }
            int result = fileInfoService.saveBatchInsert(fileInfos);
            if (result > 0) {
                return success(fileUrls);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BizException("upload file error:" + e.getMessage());
        }
        return failure();
    }
}

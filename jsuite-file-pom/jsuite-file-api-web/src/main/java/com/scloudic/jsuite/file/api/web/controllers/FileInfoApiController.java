package com.scloudic.jsuite.file.api.web.controllers;

import com.scloudic.jsuite.file.api.model.FileBaseInfo;
import com.scloudic.jsuite.file.api.service.FileService;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.DateUtils;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import com.scloudic.rabbitframework.web.Result;
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
 * 文件上传/下载管理
 */
@RestController
@RequestMapping("/jsuite/api/file")
public class FileInfoApiController extends AbstractRabbitController {
    private static final Logger logger = LoggerFactory.getLogger(FileInfoApiController.class);
    @Autowired
    private FileService fileService;

    /**
     * 单文件上传,后台通过不同的文件类型存入相应的目录中。
     *
     * @param multipartFile
     * @param fileCategoryName
     * @return
     */
    @PostMapping("upload")
    public Result<String> upload(@RequestPart("file") MultipartFile multipartFile,
                                 @RequestParam(value = "fileCategoryName",
                                         required = false, defaultValue = "") String fileCategoryName) {
        InputStream is = null;
        try {
            is = multipartFile.getInputStream();
            String srcFileName = multipartFile.getOriginalFilename(); // 文件名
            FileBaseInfo fileBaseInfo = fileService.fileUpload(fileCategoryName, srcFileName, is);
            return success(fileBaseInfo.getHttpUrl());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BizException("upload.file.error");
        } finally {
            if (is != null) {
                IOUtils.closeQuietly(is);
            }
        }
    }

    /**
     * 文件批量上传
     *
     * @return
     */
    @PostMapping("batchUpload")
    public Result<List<String>> batchFileUpload(@RequestPart("files") MultipartFile[] multipartFile) {
        List<String> fileUrls = new ArrayList<String>();
        String fileCategoryName = DateUtils.formatDate(new Date(), "yyyyMM");
        try {
            for (MultipartFile bodyPart : multipartFile) {
                InputStream is = null;
                try {
                    is = bodyPart.getInputStream();
                    String fileName = bodyPart.getOriginalFilename(); // 文件名
                    FileBaseInfo fileBaseInfo = fileService.fileUpload(fileCategoryName, fileName, is);
                    fileUrls.add(fileBaseInfo.getHttpUrl());
                } finally {
                    IOUtils.closeQuietly(is, null);
                }
            }
            if (fileUrls.size() > 0) {
                return success(fileUrls);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BizException("upload file error:" + e.getMessage());
        }
        return failure();
    }
}

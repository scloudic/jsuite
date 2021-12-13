package com.scloudic.jsuite.file.web.controllers;

import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.file.api.model.FileBaseInfo;
import com.scloudic.jsuite.file.api.service.FileService;
import com.scloudic.jsuite.file.api.utils.FileUtils;
import com.scloudic.jsuite.file.entity.FileCategory;
import com.scloudic.jsuite.file.entity.FileInfo;
import com.scloudic.jsuite.file.service.FileCategoryService;
import com.scloudic.jsuite.file.service.FileInfoService;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.DateFormatUtil;
import com.scloudic.rabbitframework.core.utils.PageBean;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.core.utils.UUIDUtils;
import com.scloudic.rabbitframework.security.SecurityUtils;
import com.scloudic.rabbitframework.security.authz.annotation.UriPermissions;
import com.scloudic.rabbitframework.web.AbstractContextResource;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文件分类管理
 *
 * @since 1.0
 */
@Component
@Path("/jsuite/fileMgr")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class FileInfoController extends AbstractContextResource {
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
    @GET
    @Path("list")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "分页查询文件信息")
    public Result<PageBean<FileInfo>> list(@Context HttpServletRequest request,
                                           @QueryParam("fileName") String fileName,
                                           @QueryParam("fileCategoryId") String fileCategoryId,
                                           @QueryParam("fileType") String fileType,
                                           @QueryParam("pageNum") Long pageNum,
                                           @QueryParam("pageSize") Long pageSize) {
        PageBean<FileInfo> fileInfoPageBean = fileInfoService.findFileInfo(fileName, fileCategoryId, fileType, pageNum, pageSize);
        return success(fileInfoPageBean);
    }

    @POST
    @Path("update")
    @UriPermissions
    @FormValid
    @Log(operatorType = Log.OperateType.UPDATE, remark = "文件修改")
    public Object update(@Context HttpServletRequest request,
                         @NotBlank @FormParam("fileId") String fileId,
                         @NotBlank @FormParam("fileName") String fileName,
                         @NotBlank @FormParam("fileCategoryId") String fileCategoryId) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileId(fileId);
        fileInfo.setFileName(fileName);
        fileInfo.setFileCategoryId(fileCategoryId);
        fileInfoService.updateByEntity(fileInfo);
        return getSimpleResponse(true);
    }

    @POST
    @Path("del")
    @UriPermissions
    @FormValid
    @Log(operatorType = Log.OperateType.UPDATE, remark = "文件删除")
    public Result del(@Context HttpServletRequest request,
                      @NotBlank @FormParam("fileId") String fileId) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileId(fileId);
        fileInfo.setDelStatus(Enums.DelStatus.DEL.getValue());
        fileInfoService.updateByEntity(fileInfo);
        return success();
    }

    /**
     * 单文件上传,后台通过不同的文件类型存入相应的目录中。
     *
     * @param form
     * @return
     */
    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @FormValid
    @UriPermissions
    public Result<String> upload(@Context HttpServletRequest request, FormDataMultiPart form,
                                 @FormDataParam("fileName") String fileName,
                                 @FormDataParam("fileCategoryId") @DefaultValue("1") String fileCategoryId) {
        InputStream is = null;
        try {
            FileCategory fileCategory = fileCategoryService.selectById(fileCategoryId);
            String fileCategoryName = fileCategory.getFileCategoryNameCode();
            String userId = SecurityUtils.getUserId();
            List<FormDataBodyPart> bodyParts = form.getFields("files");
            if (bodyParts == null || bodyParts.size() <= 0) {
                return failure("文件为空!");
            }

            FormDataBodyPart bodyPart = bodyParts.get(0);
            is = bodyPart.getValueAs(InputStream.class);
            FormDataContentDisposition formDataContentDisposition = bodyPart.getFormDataContentDisposition();
            String srcFileName = formDataContentDisposition.getFileName(); // 文件名
            MediaType mediaType = bodyPart.getMediaType();
            String fileMediaType = mediaType.getType() + "/" + mediaType.getSubtype();
            FileBaseInfo fileBaseInfo = fileService.fileUpload(fileCategoryName, srcFileName, is, fileMediaType);
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
                IOUtils.closeQuietly(is);
            }
        }
    }

    /**
     * 文件批量上传
     *
     * @param form
     * @param fileCategoryId
     * @return
     */
    @POST
    @Path("batchUpload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @FormValid
    @UriPermissions
    public Result<List<String>> batchFileUpload(@Context HttpServletRequest request,
                                                FormDataMultiPart form,
                                                @FormDataParam("fileCategoryId") @DefaultValue("1") String fileCategoryId) {
        InputStream is = null;
        List<FileInfo> fileInfos = new ArrayList<FileInfo>();
        List<String> fileUrls = new ArrayList<String>();
        String userId = SecurityUtils.getUserId();
        String fileCategoryName = DateFormatUtil.dateToStr(new Date(), "yyyyMM");
        try {
            List<FormDataBodyPart> bodyParts = form.getFields("files");
            if (bodyParts != null) {
                for (FormDataBodyPart bodyPart : bodyParts) {
                    is = bodyPart.getValueAs(InputStream.class);
                    FormDataContentDisposition formDataContentDisposition = bodyPart.getFormDataContentDisposition();
                    String fileName = formDataContentDisposition.getFileName(); // 文件名
                    MediaType mediaType = bodyPart.getMediaType();
                    String fileMediaType = mediaType.getType() + "/" + mediaType.getSubtype();
                    FileBaseInfo fileBaseInfo = fileService.fileUpload(fileCategoryName, fileName, is, fileMediaType);
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setFileId(UUIDUtils.getTimeUUID32());
                    fileInfo.setFileType(fileMediaType);
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
                }
                int result = fileInfoService.saveBatchInsert(fileInfos);
                if (result > 0) {
                    return success(fileUrls);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BizException("upload file error:" + e.getMessage());
        } finally {
            if (is != null) {
                IOUtils.closeQuietly(is);
            }
        }
        return failure();
    }
}

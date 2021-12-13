package com.scloudic.jsuite.file.api.web.controllers;

import com.scloudic.jsuite.file.api.model.FileBaseInfo;
import com.scloudic.jsuite.file.api.service.FileService;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.DateFormatUtil;
import com.scloudic.rabbitframework.core.utils.StatusCode;
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
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文件上传/下载管理
 */
@Component
@Singleton
@Path("/jsuite/api/file")
public class FileInfoApiController extends AbstractContextResource {
    private static final Logger logger = LoggerFactory.getLogger(FileInfoApiController.class);
    @Autowired
    private FileService fileService;

    /**
     * 单文件上传,后台通过不同的文件类型存入相应的目录中。
     *
     * @param form form
     * @return
     */
    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @FormValid
    public Result<String> upload(FormDataMultiPart form,
                                 @FormDataParam("fileCategoryName") String fileCategoryName) {
        InputStream is = null;
        try {
            List<FormDataBodyPart> bodyParts = form.getFields("files");
            if (bodyParts == null || bodyParts.size() <= 0) {
                return failure("文件为空");
            }
            FormDataBodyPart bodyPart = bodyParts.get(0);
            is = bodyPart.getValueAs(InputStream.class);
            FormDataContentDisposition formDataContentDisposition = bodyPart.getFormDataContentDisposition();
            String srcFileName = formDataContentDisposition.getFileName(); // 文件名
            MediaType mediaType = bodyPart.getMediaType();
            String fileMediaType = mediaType.getType() + "/" + mediaType.getSubtype();
            FileBaseInfo fileBaseInfo = fileService.fileUpload(fileCategoryName, srcFileName, is, fileMediaType);
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
     * @param form form
     * @return
     */
    @POST
    @Path("batchUpload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @FormValid
    public Result<List<String>> batchFileUpload(FormDataMultiPart form) {
        InputStream is = null;
        List<String> fileUrls = new ArrayList<String>();
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
                    fileUrls.add(fileBaseInfo.getHttpUrl());
                }
                if (fileUrls.size() > 0) {
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

package com.scloudic.jsuite.filealioss.provider.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.scloudic.jsuite.core.configure.JsuiteProperties;
import com.scloudic.jsuite.file.api.model.FileBaseInfo;
import com.scloudic.jsuite.file.api.service.FileService;
import com.scloudic.jsuite.file.api.utils.FileUtils;
import com.scloudic.jsuite.filealioss.provider.FileAliOssProperties;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.core.utils.UUIDUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 阿里云文件上传服务
 *
 * @author justin
 * @version 1.0
 * @since 1。8
 */
@Service
public class FileAliOssServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileAliOssServiceImpl.class);
    @Autowired
    private FileAliOssProperties fileAliOssProperties;
    @Autowired
    private JsuiteProperties jsuiteProperties;

    /**
     * 文件上传接口
     *
     * @param fileCategoryName 文件分类名称
     * @param srcFileName      原文件名
     * @param inputStream      文件流
     * @return {@link FileBaseInfo}
     */
    @Override
    public FileBaseInfo fileUpload(String fileCategoryName, String srcFileName, InputStream inputStream) {
        String extName = FilenameUtils.getExtension(srcFileName);
        String saveFileName = UUIDUtils.getRandomUUID32() + "." + extName;
        String fileFullPath = jsuiteProperties.getLocalFileStore();
        if (StringUtils.isNotBlank(fileCategoryName)) {
            fileFullPath = fileFullPath + "/" + fileCategoryName;
        }
        File file = new File(fileFullPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileUrl = jsuiteProperties.getFileUrl();
        if (StringUtils.isNotBlank(fileCategoryName)) {
            fileUrl = fileUrl + "/" + fileCategoryName;
        }
        fileUrl = fileUrl + "/" + saveFileName;
        uploadAliOss(fileCategoryName, saveFileName, inputStream);
        if (FileUtils.imageFileType.contains(extName.toLowerCase())) {
            imgUpload(fileCategoryName, saveFileName, inputStream, "." + extName);
        }

        FileBaseInfo fileBaseInfo = new FileBaseInfo();
        fileBaseInfo.setFileName(saveFileName);
        fileBaseInfo.setHttpUrl(fileUrl);
        fileBaseInfo.setFilePath(fileCategoryName);
        fileBaseInfo.setFileStore(FileUtils.FileStore.ALI);
        return fileBaseInfo;
    }

    /**
     * 图片上传地址
     *
     * @param fileCategoryName 文件分类
     * @param saveFileName     保存文件名
     * @param in               文件流
     * @param extName          扩展名
     */
    private void imgUpload(String fileCategoryName, String saveFileName, InputStream in,
                           String extName) {
        String thumbFileName = StringUtils.substring(saveFileName, 0, saveFileName.lastIndexOf("."));
        ByteArrayOutputStream sSmallOutput = null;
        ByteArrayInputStream sSmallInputStream = null;
        FileInputStream sSmallfileInputStream = null;
        ByteArrayOutputStream smallOutput = null;
        ByteArrayInputStream smallInputStream = null;
        FileInputStream smallfileInputStream = null;
        FileInputStream fileInputStream = null;
        File localFile = null;
        try {
            String fileFullPath = jsuiteProperties.getLocalFileStore();
            if (StringUtils.isNotBlank(fileCategoryName)) {
                fileFullPath = fileFullPath + "/" + fileCategoryName;
            }
            File file = new File(fileFullPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            localFile = new File(fileFullPath + "/" + saveFileName);
            FileUtils.copyInputStreamToFile(in, localFile);
            BufferedImage bufferedImage = ImageIO.read(localFile);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            fileInputStream = new FileInputStream(localFile);
            int ssmallWith = jsuiteProperties.getImgThumbSsmallWith();
            if (width > ssmallWith) {
                sSmallOutput = new ByteArrayOutputStream();
                Thumbnails.of(localFile).allowOverwrite(true).size(ssmallWith, height).toOutputStream(sSmallOutput);
                sSmallInputStream = new ByteArrayInputStream(sSmallOutput.toByteArray());
                uploadAliOss(fileCategoryName, thumbFileName + "_ssmall" + extName, sSmallInputStream);
            } else {
                sSmallfileInputStream = new FileInputStream(localFile);
                uploadAliOss(fileCategoryName, thumbFileName + "_ssmall" + extName, sSmallfileInputStream);
            }
            int smallWith = jsuiteProperties.getImgThumbSmallWith();
            if (width > smallWith) {
                smallOutput = new ByteArrayOutputStream();
                Thumbnails.of(localFile).allowOverwrite(true).size(smallWith, height).toOutputStream(smallOutput);
                smallInputStream = new ByteArrayInputStream(smallOutput.toByteArray());
                uploadAliOss(fileCategoryName, thumbFileName + "_small" + extName, smallInputStream);
            } else {
                smallfileInputStream = new FileInputStream(localFile);
                uploadAliOss(fileCategoryName, thumbFileName + "_small" + extName, smallfileInputStream);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BizException("upload.file.error");
        } finally {
            if (localFile != null) {
                localFile.delete();
            }
            IOUtils.closeQuietly(fileInputStream);
            IOUtils.closeQuietly(sSmallOutput);
            IOUtils.closeQuietly(sSmallInputStream);
            IOUtils.closeQuietly(sSmallfileInputStream);
            IOUtils.closeQuietly(smallOutput);
            IOUtils.closeQuietly(smallInputStream);
            IOUtils.closeQuietly(smallfileInputStream);
        }
    }

    /**
     * 阿里云上传
     *
     * @param filePath    文件路径
     * @param fileName    文件名
     * @param inputStream 上传文件流
     */
    private void uploadAliOss(String filePath, String fileName, InputStream inputStream) {
        OSSClient ossClient = null;
        String objectKey = "";
        try {
            String fileRootPath = fileAliOssProperties.getAliOssFirstPath();
            String endPoint = fileAliOssProperties.getAliOssEndPoint();
            String accessKey = fileAliOssProperties.getAliOssAccessKey();
            String accessKeySecret = fileAliOssProperties.getAliOssAccessKeySecret();
            String bucket = fileAliOssProperties.getAliOssBucket();
            ossClient = new OSSClient(endPoint, accessKey, accessKeySecret);
            if (StringUtils.isNotBlank(fileRootPath)) {
                objectKey = fileRootPath;
            }
            if (StringUtils.isNotBlank(filePath)) {
                if (StringUtils.isBlank(objectKey)) {
                    objectKey = filePath;
                } else {
                    objectKey = objectKey + "/" + filePath;
                }
            }
            objectKey = objectKey + "/" + fileName;

            ossClient.putObject(bucket, objectKey, inputStream);
            ossClient.setObjectAcl(bucket, objectKey, CannedAccessControlList.PublicRead);
        } finally {
            // 关闭client
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}

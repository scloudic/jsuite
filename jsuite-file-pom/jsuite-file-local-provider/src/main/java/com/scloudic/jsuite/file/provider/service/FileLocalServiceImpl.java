package com.scloudic.jsuite.file.provider.service;

import com.scloudic.jsuite.core.configure.JsuiteProperties;
import com.scloudic.jsuite.file.api.model.FileBaseInfo;
import com.scloudic.jsuite.file.api.service.FileService;
import com.scloudic.jsuite.file.api.utils.FileUtils;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.core.utils.UUIDUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 本地文件存储
 */
@Service
public class FileLocalServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileLocalServiceImpl.class);
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
        File localFile = new File(fileFullPath + "/" + saveFileName);
        String fileUrl = jsuiteProperties.getFileUrl();
        if (StringUtils.isNotBlank(fileCategoryName)) {
            fileUrl = fileUrl + "/" + fileCategoryName;
        }
        fileUrl = fileUrl + "/" + saveFileName;
        try {
            FileUtils.copyInputStreamToFile(inputStream, localFile);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BizException("upload.file.error");
        }
        if (FileUtils.imageFileType.contains(extName.toLowerCase())) {
            imgUpload(fileCategoryName, saveFileName, inputStream, extName);
        }
        FileBaseInfo fileBaseInfo = new FileBaseInfo();
        fileBaseInfo.setFileName(saveFileName);
        fileBaseInfo.setHttpUrl(fileUrl);
        fileBaseInfo.setFilePath(fileFullPath);
        fileBaseInfo.setFileStore(FileUtils.FileStore.LOCAL);
        return fileBaseInfo;
    }

    /**
     * 图片上传地址
     *
     * @param fileCategoryName
     * @param saveFileName
     * @param in
     * @param extName
     * @return
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
        String fileFullPath = jsuiteProperties.getLocalFileStore();
        if (StringUtils.isNotBlank(fileCategoryName)) {
            fileFullPath = fileFullPath + "/" + fileCategoryName;
        }
        File file = new File(fileFullPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            File localFile = new File(fileFullPath + "/" + saveFileName);
            File sSmallFile = new File(fileFullPath + "/" + thumbFileName + "_ssmall" + extName);
            File smallFile = new File(fileFullPath + "/" + thumbFileName + "_small" + extName);
            BufferedImage bufferedImage = ImageIO.read(localFile);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            int ssmallWith = jsuiteProperties.getImgThumbSsmallWith();
            if (width > ssmallWith) {
                sSmallOutput = new ByteArrayOutputStream();
                Thumbnails.of(localFile).allowOverwrite(true).size(ssmallWith, height).toOutputStream(sSmallOutput);
                sSmallInputStream = new ByteArrayInputStream(sSmallOutput.toByteArray());
                FileUtils.copyInputStreamToFile(sSmallInputStream, sSmallFile);
            } else {
                sSmallfileInputStream = new FileInputStream(localFile);
                FileUtils.copyInputStreamToFile(sSmallfileInputStream, sSmallFile);
            }

            int smallWith = jsuiteProperties.getImgThumbSmallWith();
            if (width > smallWith) {
                smallOutput = new ByteArrayOutputStream();
                Thumbnails.of(localFile).allowOverwrite(true).size(smallWith, height).toOutputStream(smallOutput);
                smallInputStream = new ByteArrayInputStream(smallOutput.toByteArray());
                FileUtils.copyInputStreamToFile(smallInputStream, smallFile);
            } else {
                smallfileInputStream = new FileInputStream(localFile);
                FileUtils.copyInputStreamToFile(smallInputStream, smallFile);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BizException("upload.file.error");
        } finally {
            IOUtils.closeQuietly(sSmallOutput);
            IOUtils.closeQuietly(sSmallInputStream);
            IOUtils.closeQuietly(sSmallfileInputStream);
            IOUtils.closeQuietly(smallOutput);
            IOUtils.closeQuietly(smallInputStream);
            IOUtils.closeQuietly(smallfileInputStream);
        }
    }
}

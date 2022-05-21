package com.scloudic.jsuite.file.api.utils;

import com.scloudic.rabbitframework.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FileUtils extends com.scloudic.rabbitframework.core.utils.FileUtils {
    public static final String PREFIX_IMAGE = "image/";
    public static List<String> videoFileType = new ArrayList<>();
    public static List<String> imageFileType = new ArrayList<>();

    static {
        videoFileType.add("video/mp4");
        videoFileType.add("video/3gpp");
        videoFileType.add("video/3gpp");
        videoFileType.add("video/3gpp2");
        videoFileType.add("video/x-ms-wmv");
        imageFileType.add("jpeg");
        imageFileType.add("jpg");
        imageFileType.add("png");
        imageFileType.add("bmp");
    }


    public enum FileStore {
        ALI("ali", "阿里云"),
        LOCAL("local", "本地"),
        ;
        String value;
        String name;

        FileStore(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    public static FileType getType(String fileName) {
        String extName = StringUtils.substring(fileName, fileName.lastIndexOf("."));
        if (FileUtils.imageFileType.contains(extName.toLowerCase())) {
            return FileType.IMG;
        }
        return FileType.DOCUMENT;
    }

    public enum FileType {
        IMG("img", "图片"),
        DOCUMENT("document", "文档"),
        VIDEO("document", "视频"),
        AUDIO("audio", "音频"),
        ;
        String value;
        String name;

        FileType(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

}

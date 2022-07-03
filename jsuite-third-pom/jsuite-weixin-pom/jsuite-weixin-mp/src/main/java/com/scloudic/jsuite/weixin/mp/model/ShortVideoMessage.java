package com.scloudic.jsuite.weixin.mp.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 小视频消息
 */
@Setter
@Getter
public class ShortVideoMessage extends Message {
    /**
     * 视频消息媒体id，可以调用获取临时素材接口拉取数据。
     */
    private String mediaId;

    /**
     * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    private String thumbMediaId;
}

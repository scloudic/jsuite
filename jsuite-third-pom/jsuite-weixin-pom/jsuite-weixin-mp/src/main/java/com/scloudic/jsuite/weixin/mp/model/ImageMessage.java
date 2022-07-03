package com.scloudic.jsuite.weixin.mp.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 图片消息
 */
@Getter
@Setter
public class ImageMessage extends Message {
    private String picUrl;
    /**
     * 图片消息媒体id，可以调用获取临时素材接口拉取数据。
     */
    private String mediaId;
}

package com.scloudic.jsuite.weixin.mp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationMessage extends Message {
    /**
     * 地理位置纬度
     */
    private String location_X;
    /**
     * 地理位置经度
     */
    private String location_Y;
    /**
     * 地图缩放大小
     */
    private String scale;
    /**
     * 地理位置信息
     */
    private String label;
}

package com.scloudic.jsuite.weixin.mp.model;

import com.scloudic.jsuite.weixin.mp.utils.MessageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message implements java.io.Serializable {
    private String toUserName;
    private String fromUserName;
    private Long createTime;
    private MessageType msgType;
    private Long msgId;
    private String appId;
    /**
     * 接收发送(1、接收,2、发送)
     */
    private Integer sendStatus;

}

package com.scloudic.jsuite.weixin.fficialaccount.utils;

public enum MessageType {
    TEXT("text", "文本消息"),
    IMAGE("image", "图片消息"),
    VOICE("voice", "语音消息"),
    VIDEO("video", "视频消息"),
    SHORTVIDEO("shortvideo", "小视频消息"),
    LOCATION("location", "地理位置消息"),
    LINK("link", "链接消息"),
    EVENT("event", "事件消息"),
    ;

    private String value;
    private String message;

    MessageType(String value, String message) {
        this.message = message;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}

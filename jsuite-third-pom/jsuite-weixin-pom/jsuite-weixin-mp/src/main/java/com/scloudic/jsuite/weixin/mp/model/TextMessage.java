package com.scloudic.jsuite.weixin.mp.model;

/**
 * 文本消息
 */
public class TextMessage extends Message {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

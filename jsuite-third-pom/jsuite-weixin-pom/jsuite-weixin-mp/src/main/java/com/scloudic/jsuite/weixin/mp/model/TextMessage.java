package com.scloudic.jsuite.weixin.mp.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 文本消息
 */
@Setter
@Getter
public class TextMessage extends Message {
    private String content;
}

package com.scloudic.jsuite.weixin.mp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkMessage extends Message {
    private String title;
    private String description;
    private String url;
}

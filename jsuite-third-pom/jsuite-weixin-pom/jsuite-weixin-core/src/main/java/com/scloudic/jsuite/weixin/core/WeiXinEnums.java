package com.scloudic.jsuite.weixin.core;

public class WeiXinEnums {
    public enum SendStatus {
        SEND(1, "发送消息"),
        RECEIVE(2, "接收消息"),
        ;
        int value;
        String message;

        SendStatus(int value, String message) {
            this.value = value;
            this.message = message;
        }

        public int getValue() {
            return value;
        }

        public String getMessage() {
            return message;
        }
    }
}

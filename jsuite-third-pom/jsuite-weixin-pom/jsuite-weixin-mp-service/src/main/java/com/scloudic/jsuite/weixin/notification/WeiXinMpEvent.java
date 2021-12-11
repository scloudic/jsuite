package com.scloudic.jsuite.weixin.notification;

import com.scloudic.rabbitframework.core.notification.NotificationEvent;

public class WeiXinMpEvent extends NotificationEvent {
    public WeiXinMpEvent(Object message, int action) {
        super(message, action);
    }

    @Override
    protected String getActionName(int action) {
        return null;
    }
}

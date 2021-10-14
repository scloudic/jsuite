package com.scloudic.jsuite.log.notification;

import com.scloudic.rabbitframework.core.notification.NotificationEvent;

public class OperateLogEvent extends NotificationEvent {
    public OperateLogEvent(Object message, int action) {
        super(message, action);
    }

    @Override
    protected String getActionName(int action) {
        return null;
    }
}

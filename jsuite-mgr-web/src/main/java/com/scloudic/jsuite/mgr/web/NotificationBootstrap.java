package com.scloudic.jsuite.mgr.web;

import com.scloudic.jsuite.log.notification.OperateLogEvent;
import com.scloudic.jsuite.log.notification.OperateLogListener;
import com.scloudic.rabbitframework.core.notification.NotificationServerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class NotificationBootstrap {
    private static final Logger logger = LoggerFactory.getLogger(NotificationBootstrap.class);
    @Autowired
    private NotificationServerManager notificationServerManager;
    @Autowired
    private OperateLogListener operateLogListener;

    @PostConstruct
    private void init() {
        logger.info("初始化NotificationBootstrap");
        installListeners();
    }

    private void installListeners() {
        notificationServerManager.registerEventType(OperateLogEvent.class, OperateLogListener.class);
        notificationServerManager.registerListener(operateLogListener);
        notificationServerManager.start();
    }
}

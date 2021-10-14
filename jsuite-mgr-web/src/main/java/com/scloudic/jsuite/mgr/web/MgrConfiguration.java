package com.scloudic.jsuite.mgr.web;

import com.scloudic.jsuite.log.aspects.OperateLogInterceptor;
import com.scloudic.rabbitframework.core.notification.NotificationServerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class MgrConfiguration {
    @Autowired
    private NotificationServerManager notificationServerManager;

    @Bean
    @DependsOn("notificationServerManager")
    public OperateLogInterceptor operateLogInterceptor() {
        OperateLogInterceptor operateInterceptor = new OperateLogInterceptor();
        operateInterceptor.setNotificationServerManager(notificationServerManager);
        return operateInterceptor;
    }

}

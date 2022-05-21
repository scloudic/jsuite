package com.scloudic.jsuite.mgr.web;

import com.scloudic.jsuite.log.aspects.OperateLogInterceptor;
import com.scloudic.rabbitframework.core.notification.NotificationServerManager;
import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
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

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
        return tomcatServletWebServerFactory -> tomcatServletWebServerFactory
                .addContextCustomizers((TomcatContextCustomizer) context -> {
            context.setCookieProcessor(new LegacyCookieProcessor());
        });
    }
}

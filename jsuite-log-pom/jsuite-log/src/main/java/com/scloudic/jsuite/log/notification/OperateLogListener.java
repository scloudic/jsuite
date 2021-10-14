package com.scloudic.jsuite.log.notification;

import com.scloudic.jsuite.log.model.LogBean;
import com.scloudic.jsuite.log.service.JsuiteLogService;
import com.scloudic.rabbitframework.core.notification.NotificationEvent;
import com.scloudic.rabbitframework.core.notification.NotificationServerListener;
import com.scloudic.rabbitframework.core.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 日志监听器
 */
@Component
public class OperateLogListener implements NotificationServerListener {
    private static final Logger logger = LoggerFactory.getLogger(OperateLogListener.class);
    @Autowired(required = false)
    private JsuiteLogService jsuiteLogService;

    /**
     * 发起通知事件
     *
     * @param notificationEvent
     */
    @Override
    public void onNotification(NotificationEvent notificationEvent) {
        LogBean operatorLog = (LogBean) notificationEvent.getSource();
        //如果日志服务为空，默认打印到控制台debug方式
        if (jsuiteLogService == null) {
            logger.debug("操作日志信息：" + JsonUtils.toJson(operatorLog));
            return;
        }
        jsuiteLogService.saveLog(operatorLog);
    }
}

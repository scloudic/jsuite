package com.scloudic.jsuite.weixin.notification;

import com.scloudic.jsuite.weixin.entity.WeiXinOfficialAccountMessage;
import com.scloudic.jsuite.weixin.mp.model.Message;
import com.scloudic.jsuite.weixin.service.WeiXinMpMessageService;
import com.scloudic.rabbitframework.core.notification.NotificationEvent;
import com.scloudic.rabbitframework.core.notification.NotificationServerListener;
import com.scloudic.rabbitframework.core.utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 公众号监听器
 */
@Component
public class WeiXinMpListener implements NotificationServerListener {
    private static final Logger logger = LoggerFactory.getLogger(WeiXinMpListener.class);
    @Autowired
    private WeiXinMpMessageService weiXinMpMessageService;

    /**
     * 发起通知事件
     *
     * @param notificationEvent
     */
    @Override
    public void onNotification(NotificationEvent notificationEvent) {
        Message message = (Message) notificationEvent.getSource();
        WeiXinOfficialAccountMessage weiXinOfficialAccountMessage = new WeiXinOfficialAccountMessage();
        weiXinOfficialAccountMessage.setMsgType(message.getMsgType().getValue());
        weiXinOfficialAccountMessage.setMessageId(UUIDUtils.getTimeUUID32());
        weiXinOfficialAccountMessage.setMsgId(message.getMsgId());
        weiXinOfficialAccountMessage.setAppId(message.getAppId());
        weiXinOfficialAccountMessage.setCreateTime(new Date());
        weiXinOfficialAccountMessage.setFromUserName(message.getFromUserName());
        weiXinOfficialAccountMessage.setToUserName(message.getToUserName());
        Date sendDate = new Date(message.getCreateTime());
        weiXinOfficialAccountMessage.setSendTime(sendDate);
        weiXinOfficialAccountMessage.setSendStatus(message.getSendStatus());
        weiXinMpMessageService.insertByEntity(weiXinOfficialAccountMessage);
    }
}

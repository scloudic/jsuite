package com.scloudic.jsuite.weixin.entity;
import com.scloudic.rabbitframework.jbatis.mapping.GenerationType;
import java.util.Date;
import com.scloudic.rabbitframework.jbatis.annontations.*;
import java.io.Serializable;

@Table
public class WeiXinOfficialAccountMessage implements Serializable {
	private static final long serialVersionUID = 1L;
    public static final String FIELDS = " message_id,from_user_name,send_time,create_time,msg_content,to_user_name,msg_type,send_status,msg_id,app_id ";

    /**
    *
    * 公众号消息主键
    *
    */
    @ID(keyType = GenerationType.MANUAL)
    private String messageId;

    /**
    *
    * 发送人
    *
    */
    @Column
    private String fromUserName;

    /**
    *
    * 发送时间
    *
    */
    @Column
    private Date sendTime;

    /**
    *
    * 创建时间
    *
    */
    @Column
    private Date createTime;

    /**
    *
    * 消息内容
    *
    */
    @Column
    private String msgContent;

    /**
    *
    * 接收人
    *
    */
    @Column
    private String toUserName;

    /**
    *
    * 消息类型
    *
    */
    @Column
    private String msgType;

    /**
    *
    * 接收/发送(1、接收,2、发送)
    *
    */
    @Column
    private Integer sendStatus;

    /**
    *
    * 消息id
    *
    */
    @Column
    private Long msgId;

    /**
    *
    * appId
    *
    */
    @Column
    private String appId;

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

}

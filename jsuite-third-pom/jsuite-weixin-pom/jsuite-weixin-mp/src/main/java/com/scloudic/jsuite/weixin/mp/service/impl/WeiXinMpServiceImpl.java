package com.scloudic.jsuite.weixin.mp.service.impl;

import com.scloudic.jsuite.weixin.core.WeiXinEnums;
import com.scloudic.jsuite.weixin.core.service.impl.WeiXinServiceImpl;
import com.scloudic.jsuite.weixin.mp.model.*;
import com.scloudic.jsuite.weixin.mp.service.WeiXinMpService;
import com.scloudic.jsuite.weixin.mp.crypt.SignUtil;
import com.scloudic.jsuite.weixin.mp.utils.MessageType;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.httpclient.HttpClient;
import com.scloudic.rabbitframework.core.httpclient.RequestParams;
import com.scloudic.rabbitframework.core.utils.JsonUtils;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.core.xmlparser.XNode;
import com.scloudic.rabbitframework.core.xmlparser.XPathParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 服务公众号服务接口
 */
@Service("officialAccountService")
public class WeiXinMpServiceImpl extends WeiXinServiceImpl
        implements WeiXinMpService {
    private static final Logger logger = LoggerFactory.getLogger(WeiXinMpServiceImpl.class);

    @Override
    public WeiXinMpUser getUsers(String accessToken, String nextOpenid) {
        String userUrl = url + "user/get";
        RequestParams requestParams = new RequestParams();
        requestParams.put("access_token", accessToken);
        if (StringUtils.isNotBlank(nextOpenid)) {
            requestParams.put("next_openid", nextOpenid);
        }
        String result = HttpClient.getInstance().get(userUrl, requestParams).string();
        Map<String, Object> map = JsonUtils.getObject(result, Map.class);
        Object obj = map.get("errcode");
        if (obj != null) {
            String msg = "获取用户信息失败,errorCode:" + obj.toString() + ",errmsg:" + map.get("errmsg");
            logger.error(msg);
            throw new BizException(msg);
        }
        WeiXinMpUser officialAccountUser = JsonUtils.getObject(result, WeiXinMpUser.class);
        return officialAccountUser;
    }

    @Override
    public WeiXinMpUserDetail getUserInfo(String accessToken, String openid, String lang) {
        String userUrl = url + "user/info";
        RequestParams requestParams = new RequestParams();
        requestParams.put("access_token", accessToken);
        requestParams.put("openid", openid);
        if (StringUtils.isNotBlank(lang)) {
            requestParams.put("lang", lang);
        }
        String result = HttpClient.getInstance().get(userUrl, requestParams).string();
        logger.debug(result);
        Map<String, Object> map = JsonUtils.getObject(result, Map.class);
        Object obj = map.get("errcode");
        if (obj != null) {
            String msg = "获取用户信息失败,errorCode:" + obj.toString() + ",errmsg:" + map.get("errmsg");
            logger.error(msg);
            throw new BizException(msg);
        }
        WeiXinMpUserDetail detail = JsonUtils.getObject(result, WeiXinMpUserDetail.class);
        return detail;
    }

    @Override
    public String notifyVerify(WeiXinMpProperties officialAccountProperties, String timestamp, String nonce) {
        return SignUtil.getSHA1(officialAccountProperties.getToken(), timestamp, nonce);
    }

    @Override
    public Message receiveMessage(WeiXinMpProperties officialAccountProperties, String msg) {
        XPathParser xPathParser = new XPathParser(msg, null);
        XNode xNode = xPathParser.evalNode("/xml");
        XNode encrypt = xNode.evalNode("Encrypt");
        String content = msg;
        String appId = "";
        if (encrypt != null) {
            String[] str = SignUtil.aesDecrypt(officialAccountProperties.getEncodingAESKey(), msg);
            appId = str[0];
            content = str[1];
        }
        xPathParser = new XPathParser(content, null);
        xNode = xPathParser.evalNode("/xml");
        String msgType = xNode.evalNode("MsgType").getStringBody();
        Message message = null;
        if (msgType.equals(MessageType.TEXT.getValue())) {
            message = new TextMessage();
            message.setMsgType(MessageType.TEXT);
            String msgContent = xNode.evalNode("Content").getStringBody();
            ((TextMessage) message).setContent(msgContent);
        } else if (msgType.equals(MessageType.IMAGE.getValue())) {
            message = new ImageMessage();
            message.setMsgType(MessageType.IMAGE);
            String picUrl = xNode.evalNode("PicUrl").getStringBody();
            String mediaId = xNode.evalNode("MediaId").getStringBody();
            ((ImageMessage) message).setPicUrl(picUrl);
            ((ImageMessage) message).setMediaId(mediaId);
        } else if (msgType.equals(MessageType.VOICE.getValue())) {
            message = new VoiceMessage();
            message.setMsgType(MessageType.VOICE);
            String format = xNode.evalNode("Format").getStringBody();
            String mediaId = xNode.evalNode("MediaId").getStringBody();
            String recognition = xNode.evalNode("Recognition").getStringBody();
            ((VoiceMessage) message).setFormat(format);
            ((VoiceMessage) message).setMediaId(mediaId);
            ((VoiceMessage) message).setRecognition(recognition);
        } else if (msgType.equals(MessageType.VIDEO.getValue())) {
            message = new VideoMessage();
            message.setMsgType(MessageType.VIDEO);
            String thumbMediaId = xNode.evalNode("ThumbMediaId").getStringBody();
            String mediaId = xNode.evalNode("MediaId").getStringBody();
            ((VideoMessage) message).setMediaId(mediaId);
            ((VideoMessage) message).setThumbMediaId(thumbMediaId);
        } else if (msgType.equals(MessageType.SHORTVIDEO.getValue())) {
            message = new ShortVideoMessage();
            message.setMsgType(MessageType.SHORTVIDEO);
            String thumbMediaId = xNode.evalNode("ThumbMediaId").getStringBody();
            String mediaId = xNode.evalNode("MediaId").getStringBody();
            ((ShortVideoMessage) message).setMediaId(mediaId);
            ((ShortVideoMessage) message).setThumbMediaId(thumbMediaId);
        } else if (msgType.equals(MessageType.LOCATION.getValue())) {
            message = new LocationMessage();
            message.setMsgType(MessageType.LOCATION);
            String location_X = xNode.evalNode("Location_X").getStringBody();
            String location_Y = xNode.evalNode("Location_Y").getStringBody();
            String scale = xNode.evalNode("Scale").getStringBody();
            String label = xNode.evalNode("Label").getStringBody();
            ((LocationMessage) message).setLocation_X(location_X);
            ((LocationMessage) message).setLocation_Y(location_Y);
            ((LocationMessage) message).setScale(scale);
            ((LocationMessage) message).setLabel(label);
        } else if (msgType.equals(MessageType.LINK.getValue())) {
            message = new LinkMessage();
            message.setMsgType(MessageType.LINK);
            String title = xNode.evalNode("Title").getStringBody();
            String description = xNode.evalNode("Description").getStringBody();
            String url = xNode.evalNode("Url").getStringBody();
            ((LinkMessage) message).setTitle(title);
            ((LinkMessage) message).setDescription(description);
            ((LinkMessage) message).setUrl(url);
        }
        if (message == null) {
            return null;
        }
        String toUserName = xNode.evalNode("ToUserName").getStringBody();
        String fromUserName = xNode.evalNode("FromUserName").getStringBody();
        Long createTime = xNode.evalNode("CreateTime").getLongBody();
        Long msgId = xNode.evalNode("MsgId").getLongBody();
        message.setAppId(appId);
        message.setToUserName(toUserName);
        message.setFromUserName(fromUserName);
        message.setCreateTime(createTime);
        message.setMsgId(msgId);
        message.setSendStatus(WeiXinEnums.SendStatus.SEND.getValue());
        return message;
    }
}

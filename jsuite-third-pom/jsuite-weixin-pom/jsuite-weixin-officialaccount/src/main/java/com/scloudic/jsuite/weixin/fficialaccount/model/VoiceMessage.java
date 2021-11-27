package com.scloudic.jsuite.weixin.fficialaccount.model;

/**
 * 语音消息
 */
public class VoiceMessage extends Message {
    /**
     * 语音消息媒体id，可以调用获取临时素材接口拉取数据。
     */
    private String mediaId;
    /**
     * 语音格式，如amr，speex等
     */
    private String format;

    /**
     * 语音识别结果，UTF8编码
     * 由于客户端缓存，开发者开启或者关闭语音识别功能，
     * 对新关注者立刻生效，对已关注用户需要24小时生效。开发者可以重新关注此帐号进行测试
     */
    private String recognition;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getRecognition() {
        return recognition;
    }

    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }


}

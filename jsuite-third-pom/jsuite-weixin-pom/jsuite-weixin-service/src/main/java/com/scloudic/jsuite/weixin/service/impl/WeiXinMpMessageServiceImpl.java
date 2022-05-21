package com.scloudic.jsuite.weixin.service.impl;
import com.scloudic.jsuite.weixin.entity.WeiXinMpMessage;
import com.scloudic.jsuite.weixin.mapper.WeiXinMpMessageMapper;
import com.scloudic.jsuite.weixin.service.WeiXinMpMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scloudic.rabbitframework.jbatis.service.IServiceImpl;
@Service
public class WeiXinMpMessageServiceImpl extends IServiceImpl<WeiXinMpMessageMapper, WeiXinMpMessage> implements WeiXinMpMessageService {
    @Autowired
    private WeiXinMpMessageMapper weiXinOfficialAccountMessageMapper;
    @Override
    public WeiXinMpMessageMapper getBaseMapper() {
        return weiXinOfficialAccountMessageMapper;
    }
}


package com.scloudic.jsuite.weixin.service.impl;
import com.scloudic.jsuite.weixin.entity.WeiXinOfficialAccountMessage;
import com.scloudic.jsuite.weixin.mapper.WeiXinOfficialAccountMessageMapper;
import com.scloudic.jsuite.weixin.service.WeiXinMpMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scloudic.rabbitframework.jbatis.service.IServiceImpl;
@Service
public class WeiXinMpMessageServiceImpl extends IServiceImpl<WeiXinOfficialAccountMessageMapper,WeiXinOfficialAccountMessage> implements WeiXinMpMessageService {
    @Autowired
    private WeiXinOfficialAccountMessageMapper weiXinOfficialAccountMessageMapper;
    @Override
    public WeiXinOfficialAccountMessageMapper getBaseMapper() {
        return weiXinOfficialAccountMessageMapper;
    }
}


package com.scloudic.jsuite.weixin.fficialaccount.service.impl;

import com.scloudic.jsuite.weixin.core.service.impl.WeiXinServiceImpl;
import com.scloudic.jsuite.weixin.fficialaccount.model.OfficialAccountProperties;
import com.scloudic.jsuite.weixin.fficialaccount.model.OfficialAccountUser;
import com.scloudic.jsuite.weixin.fficialaccount.model.OfficialAccountUserDetail;
import com.scloudic.jsuite.weixin.fficialaccount.service.OfficialAccountService;
import com.scloudic.jsuite.weixin.fficialaccount.utils.SignUtil;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.httpclient.HttpClient;
import com.scloudic.rabbitframework.core.httpclient.RequestParams;
import com.scloudic.rabbitframework.core.utils.JsonUtils;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 服务公众号服务接口
 */
public class OfficialAccountServiceImpl extends WeiXinServiceImpl
        implements OfficialAccountService {
    private static final Logger logger = LoggerFactory.getLogger(OfficialAccountServiceImpl.class);

    @Override
    public OfficialAccountUser getUsers(String accessToken, String nextOpenid) {
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
        OfficialAccountUser officialAccountUser = JsonUtils.getObject(result, OfficialAccountUser.class);
        return officialAccountUser;
    }

    @Override
    public OfficialAccountUserDetail getUserInfo(String accessToken, String openid, String lang) {
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
        OfficialAccountUserDetail detail = JsonUtils.getObject(result, OfficialAccountUserDetail.class);
        return detail;
    }

    @Override
    public String notifyVerify(OfficialAccountProperties officialAccountProperties, String timestamp, String nonce) {
        return SignUtil.getSHA1(officialAccountProperties.getToken(), timestamp, nonce);
    }
}

package com.scloudic.jsuite.weixin.pay.v3.service.impl;

import com.scloudic.jsuite.weixin.pay.utils.V3RequestUtils;
import com.scloudic.jsuite.weixin.pay.utils.WeiXinEnums;
import com.scloudic.jsuite.weixin.pay.v3.WeiXinCertificate;
import com.scloudic.jsuite.weixin.pay.v3.model.*;
import com.scloudic.jsuite.weixin.pay.v3.service.MiniPayV3Service;
import com.scloudic.rabbitframework.core.httpclient.HttpClientUtils;
import com.scloudic.rabbitframework.core.httpclient.ResponseBody;
import com.scloudic.rabbitframework.core.utils.JsonUtils;
import okhttp3.HttpUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

@Service
public class MiniPayV3ServiceImpl implements MiniPayV3Service {
    private static final Logger logger = LoggerFactory.getLogger(MiniPayV3ServiceImpl.class);
    @Autowired
    private WeiXinCertificate weiXinCertificate;

    @Override
    public V3PayResponse<Jsapi> jsapi(PayerParams payerParams, JsapiRequest jsapiRequest) {
        V3PayResponse<Jsapi> v3PayResponse = new V3PayResponse<>();
        String url = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";
        v3PayResponse.setRequestUrl(url);
        String resultBody = "";
        try {
            logger.debug("微信JSAPI下单");
            Map<String, Object> rootMap = new HashMap<>();
            rootMap.put("mchid", payerParams.getMerchantId());
            rootMap.put("out_trade_no", jsapiRequest.getOutTradeNo());
            rootMap.put("appid", payerParams.getAppId());
            rootMap.put("description", jsapiRequest.getSubject());
            Map<String, Object> amount = new HashMap<>();
            amount.put("total", jsapiRequest.getTotal());
            amount.put("currency", "CNY");
            rootMap.put("amount", amount);
            Map<String, Object> payer = new HashMap<>();
            payer.put("openid", jsapiRequest.getOpenid());
            rootMap.put("payer", payer);
            rootMap.put("notify_url", jsapiRequest.getNotifyUrl());
            Map<String, Object> sceneInfo = new HashMap<>();
            sceneInfo.put("payer_client_ip", jsapiRequest.getPayerClientIp());
            rootMap.put("scene_info", sceneInfo);
            String requestBody = JsonUtils.toJson(rootMap);
            String token = V3RequestUtils.getToken(payerParams, "POST", HttpUrl.parse(url), requestBody);
            logger.info("请求地址：" + url + ",token:" + token + ",请求参数：" + requestBody);
            ResponseBody responseBody = HttpClientUtils.post(url, requestBody, V3RequestUtils.getHeaders(token));
            int code = responseBody.code();
            v3PayResponse.setCode(code);
            v3PayResponse.setRequestBody(requestBody);
            if (!responseBody.isSuccessful()) {
                String msg = responseBody.string();
                logger.error("错误消息：" + msg + ",错误状态：" + code);
                v3PayResponse.setMsg(msg);
                v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.FAIL);
                return v3PayResponse;
            }
            v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.SUCCESS);
            V3Response v3Response = new V3Response(responseBody);
            String serial = v3Response.getSerial();
            X509Certificate x509Cert = weiXinCertificate.getCertificate(payerParams, serial);
            if (x509Cert == null) {
                v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.CERTIFICATE_ERROR);
                v3PayResponse.setMsg(WeiXinEnums.WeiXinPayStatus.CERTIFICATE_ERROR.getMessage());
                return v3PayResponse;
            }
            boolean verify = v3Response.verify(x509Cert);
            if (!verify) {
                v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.VERIFY_ERROR);
                v3PayResponse.setMsg(WeiXinEnums.WeiXinPayStatus.VERIFY_ERROR.getMessage());
                return v3PayResponse;
            }
            resultBody = v3Response.getBody();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.ERROR);
            v3PayResponse.setMsg(e.getMessage());
            return v3PayResponse;
        }
        Jsapi jsapi = JsonUtils.getObject(resultBody, Jsapi.class);
        v3PayResponse.setData(jsapi);
        return v3PayResponse;
    }

    public void setCertificate(WeiXinCertificate weiXinCertificate) {
        this.weiXinCertificate = weiXinCertificate;
    }
}

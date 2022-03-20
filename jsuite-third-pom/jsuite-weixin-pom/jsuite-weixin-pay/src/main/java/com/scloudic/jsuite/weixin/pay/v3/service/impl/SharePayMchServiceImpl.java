package com.scloudic.jsuite.weixin.pay.v3.service.impl;

import com.scloudic.jsuite.weixin.pay.utils.V3RequestUtils;
import com.scloudic.jsuite.weixin.pay.utils.WeiXinEnums;
import com.scloudic.jsuite.weixin.pay.v3.WeiXinCertificate;
import com.scloudic.jsuite.weixin.pay.v3.model.*;
import com.scloudic.jsuite.weixin.pay.v3.service.SharePayMchService;
import com.scloudic.rabbitframework.core.httpclient.HttpClientUtils;
import com.scloudic.rabbitframework.core.httpclient.ResponseBody;
import com.scloudic.rabbitframework.core.utils.JsonUtils;
import okhttp3.HttpUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.cert.X509Certificate;

public class SharePayMchServiceImpl implements SharePayMchService {
    @Autowired
    private WeiXinCertificate weiXinCertificate;

    private static final Logger logger = LoggerFactory.getLogger(SharePayMchServiceImpl.class);

    @Override
    public V3PayResponse<ShareOrderResult> orders(PayerParams payerParams,
                                                  ShareOrderRequest request) {
        String url = "https://api.mch.weixin.qq.com/v3/profitsharing/orders";
        V3PayResponse<ShareOrderResult> v3PayResponse = new V3PayResponse<ShareOrderResult>();
        v3PayResponse.setRequestUrl(url);
        try {
            logger.debug("微信分账申请");
            String requestBody = JsonUtils.toJson(request);
            v3PayResponse.setRequestBody(requestBody);
            String token = V3RequestUtils.getToken(payerParams, "POST", HttpUrl.parse(url), requestBody);
            logger.info("请求地址：" + url + ",token:" + token + ",请求参数：" + requestBody);
            ResponseBody responseBody = HttpClientUtils.post(url, requestBody, V3RequestUtils.getHeaders(token));
            V3ResponseBody v3Response = new V3ResponseBody(responseBody);
            int code = responseBody.code();
            String resultBody = v3Response.getBody();
            v3PayResponse.setCode(code);
            v3PayResponse.setResponseBody(resultBody);
            if (!responseBody.isSuccessful()) {
                String msg = resultBody;
                logger.error("错误消息：" + msg + ",错误状态：" + code);
                v3PayResponse.setMsg(msg);
                v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.FAIL);
                return v3PayResponse;
            }
            v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.SUCCESS);
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
            ShareOrderResult profitSharingOrderResult = JsonUtils.getObject(resultBody, ShareOrderResult.class);
            v3PayResponse.setData(profitSharingOrderResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.ERROR);
            v3PayResponse.setMsg(e.getMessage());
        }
        return v3PayResponse;
    }

    @Override
    public V3PayResponse<ShareOrderResult> searchOrders(PayerParams payerParams, ShareOrderSearchRequest request) {
        String url = "https://api.mch.weixin.qq.com/v3/profitsharing/orders/" + request.getOutOrderNo()
                + "?&transaction_id=" + request.getTransactionId();
        V3PayResponse<ShareOrderResult> v3PayResponse = new V3PayResponse<ShareOrderResult>();
        v3PayResponse.setRequestUrl(url);
        try {
            logger.debug("微信分账/解冻查询");
            String token = V3RequestUtils.getToken(payerParams, "GET", HttpUrl.parse(url));
            logger.info("请求地址：" + url + ",token:" + token);
            ResponseBody responseBody = HttpClientUtils.get(url, null, V3RequestUtils.getHeaders(token));
            V3ResponseBody v3Response = new V3ResponseBody(responseBody);
            int code = responseBody.code();
            String resultBody = v3Response.getBody();
            v3PayResponse.setCode(code);
            v3PayResponse.setResponseBody(resultBody);
            if (!responseBody.isSuccessful()) {
                String msg = resultBody;
                logger.error("错误消息：" + msg + ",错误状态：" + code);
                v3PayResponse.setMsg(msg);
                v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.FAIL);
                return v3PayResponse;
            }
            v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.SUCCESS);
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
            ShareOrderResult profitSharingOrderResult = JsonUtils.getObject(resultBody, ShareOrderResult.class);
            v3PayResponse.setData(profitSharingOrderResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.ERROR);
            v3PayResponse.setMsg(e.getMessage());
        }
        return v3PayResponse;
    }
}

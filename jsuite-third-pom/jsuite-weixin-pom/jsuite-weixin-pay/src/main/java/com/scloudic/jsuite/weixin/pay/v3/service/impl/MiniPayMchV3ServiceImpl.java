package com.scloudic.jsuite.weixin.pay.v3.service.impl;

import com.scloudic.jsuite.weixin.pay.utils.PayUtils;
import com.scloudic.jsuite.weixin.pay.utils.V3RequestUtils;
import com.scloudic.jsuite.weixin.pay.utils.WeiXinEnums;
import com.scloudic.jsuite.weixin.pay.v3.WeiXinCertificate;
import com.scloudic.jsuite.weixin.pay.v3.model.*;
import com.scloudic.jsuite.weixin.pay.v3.service.MiniPayMchV3Service;
import com.scloudic.rabbitframework.core.httpclient.HttpClientUtils;
import com.scloudic.rabbitframework.core.httpclient.ResponseBody;
import com.scloudic.rabbitframework.core.utils.JsonUtils;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.core.utils.UUIDUtils;
import okhttp3.HttpUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

@Service
public class MiniPayMchV3ServiceImpl implements MiniPayMchV3Service {
    private static final Logger logger = LoggerFactory.getLogger(MiniPayMchV3ServiceImpl.class);
    @Autowired
    private WeiXinCertificate weiXinCertificate;

    @Override
    public V3PayResponse<V3Pay> pay(PayerParams payerParams, JsapiRequest jsapiRequest) {
        V3PayResponse<V3Pay> v3PayResponse = new V3PayResponse<>();
        V3PayResponse<Jsapi> jsapi = jsapi(payerParams, jsapiRequest);
        v3PayResponse.setPayStatus(jsapi.getPayStatus());
        v3PayResponse.setMsg(jsapi.getMsg());
        v3PayResponse.setRequestBody(jsapi.getRequestBody());
        v3PayResponse.setResponseBody(jsapi.getResponseBody());
        v3PayResponse.setCode(jsapi.getCode());
        v3PayResponse.setRequestUrl(jsapi.getRequestUrl());
        if (jsapi.getPayStatus() != WeiXinEnums.WeiXinPayStatus.SUCCESS) {
            return v3PayResponse;
        }
        String timestamp = PayUtils.generateTimestamp() + "";
        String nonceStr = UUIDUtils.getTimeUUID32();
        String prepayId = "prepay_id=" + jsapi.getData().getPrepay_id();
        String signInfo =
                payerParams.getAppId() + "\n"
                        + timestamp + "\n"
                        + nonceStr + "\n"
                        + prepayId + "\n";
        String paySign = V3RequestUtils.sign(signInfo.getBytes(StandardCharsets.UTF_8), payerParams);
        V3Pay v3Pay = new V3Pay();
        v3Pay.setPaySign(paySign);
        v3Pay.setAppId(payerParams.getAppId());
        v3Pay.setNonceStr(nonceStr);
        v3Pay.setTimeStamp(timestamp);
        v3Pay.setPrepayIdPackage(prepayId);
        v3Pay.setSignType("RSA");
        v3PayResponse.setData(v3Pay);
        return v3PayResponse;
    }

    @Override
    public V3PayResponse<SearchPayResult> searchPayByTransactionId(PayerParams payerParams, String transactionId) {
        String url = " https://api.mch.weixin.qq.com/v3/pay/transactions/id/" + transactionId + "?mchid=" + payerParams.getMerchantId();
        return getSearchPayResult(payerParams, url);
    }

    @Override
    public V3PayResponse<SearchPayResult> searchPayByOutTradeNo(PayerParams payerParams, String outTradeNo) {
        String url = "https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/" + outTradeNo + "?mchid=" + payerParams.getMerchantId();
        return getSearchPayResult(payerParams, url);
    }

    @Override
    public V3PayResponse<RefundResult> refunds(PayerParams payerParams, RefundsRequest refundsRequest) {
        V3PayResponse<RefundResult> v3PayResponse = new V3PayResponse<>();
        String url = "https://api.mch.weixin.qq.com/v3/refund/domestic/refunds";
        v3PayResponse.setRequestUrl(url);
        try {
            logger.debug("微信小程序退款申请");
            Map<String, Object> rootMap = new HashMap<>();
            rootMap.put("transaction_id", refundsRequest.getTransactionId());
            rootMap.put("out_trade_no", refundsRequest.getOutTradeNo());
            rootMap.put("out_refund_no", refundsRequest.getOutRefundNo());
            if (StringUtils.isNotBlank(refundsRequest.getNotifyUrl())) {
                rootMap.put("notify_url", refundsRequest.getNotifyUrl());
            }
            Map<String, String> amount = new HashMap<>();
            amount.put("refund", refundsRequest.getRefundAmount());
            amount.put("total", refundsRequest.getSrcRefundAmount());
            amount.put("currency", refundsRequest.getCurrency());
            rootMap.put("amount", amount);
            String requestBody = JsonUtils.toJson(rootMap);
            v3PayResponse.setRequestBody(requestBody);
            String token = V3RequestUtils.getToken(payerParams, "POST", HttpUrl.parse(url), requestBody);
            logger.info("请求地址：" + url + ",token:" + token + ",请求参数：" + requestBody);
            ResponseBody responseBody = HttpClientUtils.post(url, requestBody, V3RequestUtils.getHeaders(token));
            int code = responseBody.code();
            V3ResponseBody v3Response = new V3ResponseBody(responseBody);
            String resultBody = v3Response.getBody();
            v3PayResponse.setResponseBody(resultBody);
            v3PayResponse.setCode(code);
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
            RefundResult refundResult = JsonUtils.getObject(resultBody, RefundResult.class);
            v3PayResponse.setData(refundResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.ERROR);
            v3PayResponse.setMsg(e.getMessage());
        }
        return v3PayResponse;
    }

    @Override
    public V3PayResponse<RefundResult> refundsSearch(PayerParams payerParams, String outRefundNo) {
        V3PayResponse<RefundResult> v3PayResponse = new V3PayResponse<>();
        String url = "https://api.mch.weixin.qq.com/v3/refund/domestic/refunds/" + outRefundNo;
        v3PayResponse.setRequestUrl(url);
        try {
            logger.debug("微信支付退款查询");
            String token = V3RequestUtils.getToken(payerParams, "GET", HttpUrl.parse(url));
            logger.info("请求地址：" + url + ",token:" + token);
            ResponseBody responseBody = HttpClientUtils.get(url, null, V3RequestUtils.getHeaders(token));
            int code = responseBody.code();
            V3ResponseBody v3Response = new V3ResponseBody(responseBody);
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
            RefundResult refundResult = JsonUtils.getObject(resultBody, RefundResult.class);
            v3PayResponse.setData(refundResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.ERROR);
            v3PayResponse.setMsg(e.getMessage());
        }
        return v3PayResponse;
    }

    private V3PayResponse<SearchPayResult> getSearchPayResult(PayerParams payerParams, String url) {
        V3PayResponse<SearchPayResult> v3PayResponse = new V3PayResponse<>();
        v3PayResponse.setRequestUrl(url);
        try {
            logger.debug("微信支付订单号查询");
            String token = V3RequestUtils.getToken(payerParams, "GET", HttpUrl.parse(url));
            logger.info("请求地址：" + url + ",token:" + token);
            ResponseBody responseBody = HttpClientUtils.get(url, null, V3RequestUtils.getHeaders(token));
            int code = responseBody.code();
            V3ResponseBody v3Response = new V3ResponseBody(responseBody);
            String resultBody = v3Response.getBody();
            v3PayResponse.setResponseBody(resultBody);
            v3PayResponse.setCode(code);
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
            SearchPayResult searchPayResult = JsonUtils.getObject(resultBody, SearchPayResult.class);
            v3PayResponse.setData(searchPayResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.ERROR);
            v3PayResponse.setMsg(e.getMessage());
        }
        return v3PayResponse;
    }
    
    private V3PayResponse<Jsapi> jsapi(PayerParams payerParams, JsapiRequest jsapiRequest) {
        V3PayResponse<Jsapi> v3PayResponse = new V3PayResponse<>();
        String url = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";
        v3PayResponse.setRequestUrl(url);
        try {
            logger.debug("微信小程序JSAPI下单");
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
            v3PayResponse.setRequestBody(requestBody);
            String token = V3RequestUtils.getToken(payerParams, "POST", HttpUrl.parse(url), requestBody);
            logger.info("请求地址：" + url + ",token:" + token + ",请求参数：" + requestBody);
            ResponseBody responseBody = HttpClientUtils.post(url, requestBody, V3RequestUtils.getHeaders(token));
            V3ResponseBody v3Response = new V3ResponseBody(responseBody);
            String resultBody = v3Response.getBody();
            int code = responseBody.code();
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
            Jsapi jsapi = JsonUtils.getObject(resultBody, Jsapi.class);
            v3PayResponse.setData(jsapi);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.ERROR);
            v3PayResponse.setMsg(e.getMessage());
        }
        return v3PayResponse;
    }

    public void setCertificate(WeiXinCertificate weiXinCertificate) {
        this.weiXinCertificate = weiXinCertificate;
    }
}

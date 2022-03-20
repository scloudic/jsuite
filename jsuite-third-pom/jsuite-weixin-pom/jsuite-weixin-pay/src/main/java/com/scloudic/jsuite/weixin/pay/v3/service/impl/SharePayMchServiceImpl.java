package com.scloudic.jsuite.weixin.pay.v3.service.impl;

import com.scloudic.jsuite.weixin.pay.utils.CertificateUtils;
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
import java.util.HashMap;
import java.util.Map;

public class SharePayMchServiceImpl implements SharePayMchService {
    @Autowired
    private WeiXinCertificate weiXinCertificate;

    private static final Logger logger = LoggerFactory.getLogger(SharePayMchServiceImpl.class);

    @Override
    public V3PayResponse<ShareResult> orders(PayerParams payerParams,
                                             ShareRequest request) {
        String url = "https://api.mch.weixin.qq.com/v3/profitsharing/orders";
        V3PayResponse<ShareResult> v3PayResponse = new V3PayResponse<ShareResult>();
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
            ShareResult profitSharingOrderResult = JsonUtils.getObject(resultBody, ShareResult.class);
            v3PayResponse.setData(profitSharingOrderResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.ERROR);
            v3PayResponse.setMsg(e.getMessage());
        }
        return v3PayResponse;
    }

    @Override
    public V3PayResponse<ShareResult> searchOrders(PayerParams payerParams, ShareSearchRequest request) {
        String url = "https://api.mch.weixin.qq.com/v3/profitsharing/orders/" + request.getOutOrderNo()
                + "?&transaction_id=" + request.getTransactionId();
        V3PayResponse<ShareResult> v3PayResponse = new V3PayResponse<ShareResult>();
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
            ShareResult profitSharingOrderResult = JsonUtils.getObject(resultBody, ShareResult.class);
            v3PayResponse.setData(profitSharingOrderResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.ERROR);
            v3PayResponse.setMsg(e.getMessage());
        }
        return v3PayResponse;
    }

    @Override
    public V3PayResponse<ShareRefundResult> returnOrders(PayerParams payerParams, ShareRefundRequest shareRefundRequest) {
        String url = "https://api.mch.weixin.qq.com/v3/profitsharing/return-orders";
        V3PayResponse<ShareRefundResult> v3PayResponse = new V3PayResponse<ShareRefundResult>();
        v3PayResponse.setRequestUrl(url);
        try {
            logger.debug("微信分账回退申请");
            String requestBody = JsonUtils.toJson(shareRefundRequest);
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
            ShareRefundResult refundResponse = JsonUtils.getObject(resultBody, ShareRefundResult.class);
            v3PayResponse.setData(refundResponse);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.ERROR);
            v3PayResponse.setMsg(e.getMessage());
        }
        return v3PayResponse;
    }

    @Override
    public V3PayResponse<ShareRefundResult> searchReturnOrders(PayerParams payerParams,
                                                               ShareSearchRefundRequest shareRefundRequest) {
        String url = "https://api.mch.weixin.qq.com/v3/profitsharing/return-orders/" + shareRefundRequest.getOutReturnNo() +
                "?&out_order_no=" + shareRefundRequest.getOutOrderNo();
        V3PayResponse<ShareRefundResult> v3PayResponse = new V3PayResponse<ShareRefundResult>();
        v3PayResponse.setRequestUrl(url);
        try {
            logger.debug("微信分账回退查询");
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
            ShareRefundResult profitSharingOrderResult = JsonUtils.getObject(resultBody, ShareRefundResult.class);
            v3PayResponse.setData(profitSharingOrderResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.ERROR);
            v3PayResponse.setMsg(e.getMessage());
        }
        return v3PayResponse;
    }

    @Override
    public V3PayResponse<ShareResult> unfreeze(PayerParams payerParams,
                                               ShareUnfreezeRequest shareUnfreezeRequest) {
        String url = "https://api.mch.weixin.qq.com/v3/profitsharing/orders/unfreeze";
        V3PayResponse<ShareResult> v3PayResponse = new V3PayResponse<ShareResult>();
        v3PayResponse.setRequestUrl(url);
        try {
            logger.debug("微信解冻剩余资金申请");
            String requestBody = JsonUtils.toJson(shareUnfreezeRequest);
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
            ShareResult shareResult = JsonUtils.getObject(resultBody, ShareResult.class);
            v3PayResponse.setData(shareResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.ERROR);
            v3PayResponse.setMsg(e.getMessage());
        }
        return v3PayResponse;
    }

    @Override
    public V3PayResponse<ShareRemainResult> shareRemain(PayerParams payerParams,
                                                        String transactionId) {
        String url = "https://api.mch.weixin.qq.com/v3/profitsharing/transactions/" + transactionId + "/amounts";
        V3PayResponse<ShareRemainResult> v3PayResponse = new V3PayResponse<ShareRemainResult>();
        v3PayResponse.setRequestUrl(url);
        try {
            logger.debug("微信查询剩余待分金额查询");
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
            ShareRemainResult shareRemainResult = JsonUtils.getObject(resultBody, ShareRemainResult.class);
            v3PayResponse.setData(shareRemainResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.ERROR);
            v3PayResponse.setMsg(e.getMessage());
        }
        return v3PayResponse;
    }

    @Override
    public V3PayResponse<ShareAddReceiversResult> addReceivers(PayerParams payerParams,
                                                               ShareAddReceiversRequest request) {
        String url = "https://api.mch.weixin.qq.com/v3/profitsharing/receivers/add";
        V3PayResponse<ShareAddReceiversResult> v3PayResponse = new V3PayResponse<ShareAddReceiversResult>();
        v3PayResponse.setRequestUrl(url);
        try {
            logger.debug("微信添加分账接收方申请");
            Map<String, String> params = new HashMap<>();
            params.put("appid", request.getAppid());
            params.put("type", request.getType());
            params.put("account", request.getAccount());
            String weixinNo = "";
            if ("MERCHANT_ID".equals(request.getType())) {
                Map<String, X509Certificate> x509Certificate = weiXinCertificate.getCertificate(payerParams);
                weixinNo = x509Certificate.keySet().iterator().next();
                X509Certificate cate = x509Certificate.values().iterator().next();
                String msg = CertificateUtils.rsaEncryptOAEP(request.getName(), cate);
                params.put("name", msg);
            }
            params.put("relation_type", request.getRelationType().getValue());
            if (request.getRelationType() == WeiXinEnums.RelationType.CUSTOM) {
                params.put("custom_relation", request.getCustomRelation());
            }
            String requestBody = JsonUtils.toJson(params);
            v3PayResponse.setRequestBody(requestBody);
            String token = V3RequestUtils.getToken(payerParams, "POST", HttpUrl.parse(url), requestBody);
            logger.info("请求地址：" + url + ",token:" + token + ",请求参数：" + requestBody);
            Map<String, String> headers = V3RequestUtils.getHeaders(token);
            headers.put(V3ResponseBody.WECHAT_PAY_SERIAL, weixinNo);
            ResponseBody responseBody = HttpClientUtils.post(url, requestBody, headers);
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
            ShareAddReceiversResult receiversResult = JsonUtils.getObject(resultBody, ShareAddReceiversResult.class);
            v3PayResponse.setData(receiversResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.ERROR);
            v3PayResponse.setMsg(e.getMessage());
        }
        return v3PayResponse;
    }

    @Override
    public V3PayResponse<ShareDelReceiversResult> delReceivers(PayerParams payerParams,
                                                               ShareDelReceiversRequest request) {
        String url = "https://api.mch.weixin.qq.com/v3/profitsharing/receivers/delete";
        V3PayResponse<ShareDelReceiversResult> v3PayResponse = new V3PayResponse<ShareDelReceiversResult>();
        v3PayResponse.setRequestUrl(url);
        try {
            logger.debug("微信删除分账接收方申请");
            String requestBody = JsonUtils.toJson(request);
            v3PayResponse.setRequestBody(requestBody);
            String token = V3RequestUtils.getToken(payerParams, "POST", HttpUrl.parse(url), requestBody);
            logger.info("请求地址：" + url + ",token:" + token + ",请求参数：" + requestBody);
            Map<String, String> headers = V3RequestUtils.getHeaders(token);
            ResponseBody responseBody = HttpClientUtils.post(url, requestBody, headers);
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
            ShareDelReceiversResult receiversResult = JsonUtils.getObject(resultBody, ShareDelReceiversResult.class);
            v3PayResponse.setData(receiversResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            v3PayResponse.setPayStatus(WeiXinEnums.WeiXinPayStatus.ERROR);
            v3PayResponse.setMsg(e.getMessage());
        }
        return v3PayResponse;
    }
}

package com.scloudic.jsuite.weixin.pay.utils;

import com.scloudic.jsuite.weixin.core.exception.WeiXinException;
import com.scloudic.jsuite.weixin.pay.v3.model.PayerParams;
import okhttp3.HttpUrl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class V3RequestUtils {

    public static Map<String, String> getHeaders(String token) {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", PayUtils.V3SCHEMA + " " + token);
        header.put("User-Agent", "jsuite-v1.0");
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json; charset=utf-8");
        return header;
    }

    public static String getToken(PayerParams payerParams,
                                  String method, HttpUrl url) {
        return getToken(payerParams, method, url, "");
    }

    public static String getToken(PayerParams payerParams,
                                  String method, HttpUrl url, String body) {
        String nonceStr = PayUtils.generateNonceStr();
        long timestamp = PayUtils.generateTimestamp();
        String message = buildMessage(method, url, timestamp, nonceStr, body);
        String signature = null;
        try {
            signature = sign(message.getBytes("utf-8"), payerParams);
        } catch (UnsupportedEncodingException e) {
            throw new WeiXinException("字符转换异常！");
        }
        return "mchid=\"" + payerParams.getMerchantId() + "\","
                + "nonce_str=\"" + nonceStr + "\","
                + "timestamp=\"" + timestamp + "\","
                + "serial_no=\"" + payerParams.getCertificateSerialNumber() + "\","
                + "signature=\"" + signature + "\"";
    }


    private static String buildMessage(String method, HttpUrl url,
                                       long timestamp, String nonceStr, String body) {
        String canonicalUrl = url.encodedPath();
        if (url.encodedQuery() != null) {
            canonicalUrl += "?" + url.encodedQuery();
        }
        return method + "\n"
                + canonicalUrl + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + body + "\n";
    }

    public static String sign(byte[] message, PayerParams payerParams) {
        Signature sign = null;
        try {
            sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(CertificateUtils.loadPrivateKey(payerParams.getPrivateKey()));
            sign.update(message);
            return Base64.getEncoder().encodeToString(sign.sign());
        } catch (NoSuchAlgorithmException e) {
            throw new WeiXinException("当前Java环境不支持SHA256withRSA", e);
        } catch (SignatureException e) {
            throw new WeiXinException("签名计算失败", e);
        } catch (InvalidKeyException e) {
            throw new WeiXinException("无效的私钥", e);
        }
    }
}

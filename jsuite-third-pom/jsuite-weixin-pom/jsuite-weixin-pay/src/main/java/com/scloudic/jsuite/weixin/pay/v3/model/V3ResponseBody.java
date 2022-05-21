package com.scloudic.jsuite.weixin.pay.v3.model;

import com.scloudic.rabbitframework.core.httpclient.ResponseBody;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class V3ResponseBody {
    public static final String REQUEST_ID = "Request-ID";
    public static final String WECHAT_PAY_SERIAL = "Wechatpay-Serial";
    public static final String WECHAT_PAY_SIGNATURE = "Wechatpay-Signature";
    public static final String WECHAT_PAY_TIMESTAMP = "Wechatpay-Timestamp";
    public static final String WECHAT_PAY_NONCE = "Wechatpay-Nonce";
    private String body;
    private String serial;
    private String requestId;
    private String signature;
    private String timestamp;
    private String nonce;

    public V3ResponseBody(ResponseBody responseBody) {
        body = responseBody.string();
        serial = responseBody.header(V3ResponseBody.WECHAT_PAY_SERIAL);
        requestId = responseBody.header(V3ResponseBody.REQUEST_ID);
        signature = responseBody.header(V3ResponseBody.WECHAT_PAY_SIGNATURE);
        timestamp = responseBody.header(V3ResponseBody.WECHAT_PAY_TIMESTAMP);
        nonce = responseBody.header(V3ResponseBody.WECHAT_PAY_NONCE);
    }
    
    public boolean verify(X509Certificate certificate) {
        try {
            String message = timestamp + "\n"
                    + nonce + "\n"
                    + body + "\n";
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initVerify(certificate);
            sign.update(message.getBytes(StandardCharsets.UTF_8));
            return sign.verify(Base64.getDecoder().decode(signature));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持SHA256withRSA", e);
        } catch (SignatureException e) {
            throw new RuntimeException("签名验证过程发生了错误", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("无效的证书", e);
        }
    }

    public String getBody() {
        return body;
    }

    public String getSerial() {
        return serial;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getSignature() {
        return signature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getNonce() {
        return nonce;
    }
}

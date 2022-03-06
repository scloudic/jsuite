package com.scloudic.jsuite.weixin.pay.v3;

import com.alibaba.fastjson.JSONObject;
import com.scloudic.jsuite.weixin.pay.utils.AesUtils;
import com.scloudic.jsuite.weixin.pay.utils.CertificateUtils;
import com.scloudic.jsuite.weixin.pay.utils.V3RequestUtils;
import com.scloudic.jsuite.weixin.pay.v3.model.DownloadCertificatesResponse;
import com.scloudic.jsuite.weixin.pay.v3.model.EncryptCertificate;
import com.scloudic.jsuite.weixin.pay.v3.model.PayerParams;
import com.scloudic.jsuite.weixin.pay.v3.model.V3ResponseBody;
import com.scloudic.rabbitframework.core.httpclient.HttpClientUtils;
import com.scloudic.rabbitframework.core.httpclient.ResponseBody;
import com.scloudic.rabbitframework.core.utils.JsonUtils;
import okhttp3.HttpUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信平台证书,此抽象类需要业务实现
 */
public abstract class WeiXinCertificate {
    private static final Logger logger = LoggerFactory.getLogger(WeiXinCertificate.class);

    /**
     * 下载平台证书
     *
     * @param payerParams
     * @return
     */
    public Map<String, String> downLoadCertificate(PayerParams payerParams) {
        String url = "https://api.mch.weixin.qq.com/v3/certificates";
        String token = V3RequestUtils.getToken(payerParams, "GET", HttpUrl.parse(url));
        ResponseBody responseBody = HttpClientUtils.get(url, null, V3RequestUtils.getHeaders(token));
        V3ResponseBody v3Response = new V3ResponseBody(responseBody);
        JSONObject jsonObject = JSONObject.parseObject(v3Response.getBody());
        String data = jsonObject.getString("data");
        logger.debug("请求地址：" + url + ",token:" + token);
        List<DownloadCertificatesResponse> responseList = JsonUtils.getListObject(data, DownloadCertificatesResponse.class);
        Map<String, String> certificateStrMap = new HashMap<>();
        X509Certificate x509Cert = null;
        for (DownloadCertificatesResponse response : responseList) {
            EncryptCertificate encryptCertificate = response.getEncrypt_certificate();
            String result = AesUtils.v3DecryptToStr(
                    payerParams.getApiV3Key(),
                    encryptCertificate.getAssociated_data(),
                    encryptCertificate.getNonce(),
                    encryptCertificate.getCiphertext());
            logger.info("商户标识:" + payerParams.getMerchantId() + ",平台证书：" + response.getSerial_no());
            certificateStrMap.put(response.getSerial_no(), result);
            if (v3Response.getSerial().equals(response.getSerial_no())) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
                x509Cert = CertificateUtils.loadCertificate(byteArrayInputStream);
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {

                }
            }
        }
        // BigInteger val = new BigInteger(v3Response.getSerial(), 16);
        if (x509Cert == null) {
            logger.error("证书下载生成失败");
            return new HashMap<>();
        }
        boolean verify = v3Response.verify(x509Cert);
        if (!verify) {
            logger.error("平台证书下载验证错误");
            return new HashMap<>();
        }
        return certificateStrMap;
    }

    /**
     * 获取平台证书
     *
     * @param serialNumber
     * @return
     */
    public abstract X509Certificate getCertificate(PayerParams payerParams, String serialNumber);

    /**
     * 设置平台证书
     *
     * @param serialNumber
     * @param merchantId
     * @param certificateStr
     * @return
     */
    public abstract void setCertificate(String serialNumber, String merchantId, String certificateStr);
}

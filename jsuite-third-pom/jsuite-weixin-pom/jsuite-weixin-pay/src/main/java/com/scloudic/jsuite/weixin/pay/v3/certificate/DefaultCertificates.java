package com.scloudic.jsuite.weixin.pay.v3.certificate;

import com.scloudic.jsuite.weixin.pay.utils.CertificateUtils;
import com.scloudic.jsuite.weixin.pay.v3.model.PayerParams;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认实现支付证书
 */
public class DefaultCertificates extends PayCertificate {
    private static final Logger logger = LoggerFactory.getLogger(DefaultCertificates.class);
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, X509Certificate>>
            certificates = new ConcurrentHashMap();

    @Override
    public X509Certificate getCertificate(PayerParams payerParams, String serialNumber) {
        return loadCertificate(payerParams, serialNumber, 1);

    }

    private X509Certificate loadCertificate(PayerParams payerParams,
                                            String serialNumber, int count) {
        checkAndDownload(payerParams);
        X509Certificate x509Certificate = getLastX509Certificate(payerParams, serialNumber);
        if (x509Certificate == null && count < 4) {
            count = count + 1;
            loadCertificate(payerParams, serialNumber, count);
        }
        return x509Certificate;
    }

    @Override
    public Map<String, X509Certificate> getCertificate(PayerParams payerParams) {
        checkAndDownload(payerParams);
        ConcurrentHashMap<String, X509Certificate> x509Map = certificates.get(payerParams.getMerchantId());
        return x509Map;
    }


    private void checkAndDownload(PayerParams payerParams) {
        String merchantId = payerParams.getMerchantId();
        ConcurrentHashMap<String, X509Certificate> x509Map = certificates.get(merchantId);
        if (x509Map == null) {
            x509Map = new ConcurrentHashMap<String, X509Certificate>();
            ConcurrentHashMap<String, String> downLoadMap = downLoadCertificate(payerParams);
            if (downLoadMap == null) {
                return;
            }
            for (Map.Entry<String, String> entry : downLoadMap.entrySet()) {
                String x509Str = entry.getValue();
                String number = entry.getKey();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(x509Str.getBytes(StandardCharsets.UTF_8));
                X509Certificate x509Cert = CertificateUtils.loadCertificate(byteArrayInputStream);
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                }
                x509Map.put(number, x509Cert);
            }
            certificates.put(merchantId, x509Map);
        }
    }

    private X509Certificate getLastX509Certificate(PayerParams payerParams, String serialNumber) {
        String merchantId = payerParams.getMerchantId();
        ConcurrentHashMap<String, X509Certificate> x509Map = certificates.get(merchantId);
        X509Certificate latestCert = null;
        if (StringUtils.isNotBlank(serialNumber)) {
            latestCert = x509Map.get(serialNumber);
        }
        try {
            if (latestCert != null) {
                latestCert.checkValidity();
            }
        } catch (CertificateNotYetValidException | CertificateExpiredException var6) {
            logger.error("平台证书未生效或已过期，merchantId:{}", merchantId);
        }
        return latestCert;
    }


}

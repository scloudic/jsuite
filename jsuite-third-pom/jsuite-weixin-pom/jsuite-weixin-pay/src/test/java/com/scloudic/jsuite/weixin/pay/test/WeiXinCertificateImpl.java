package com.scloudic.jsuite.weixin.pay.test;

import com.scloudic.jsuite.weixin.pay.utils.CertificateUtils;
import com.scloudic.jsuite.weixin.pay.v3.WeiXinCertificate;
import com.scloudic.jsuite.weixin.pay.v3.model.PayerParams;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.Map;

public class WeiXinCertificateImpl extends WeiXinCertificate {
    private static final Logger logger = LoggerFactory.getLogger(WeiXinCertificateImpl.class);

    @Override
    public X509Certificate getCertificate(PayerParams payerParams, String serialNumber) {
        Map<String, String> map = downLoadCertificate(payerParams);
        if (map.size() == 0) {
            logger.error("证书不存在！");
            return null;
        }
        String certificate = map.get(serialNumber);
        if (StringUtils.isBlank(certificate)) {
            logger.error("证书不存在！");
            return null;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(certificate.getBytes(StandardCharsets.UTF_8));
        X509Certificate x509Cert = CertificateUtils.loadCertificate(byteArrayInputStream);
        IOUtils.closeQuietly(byteArrayInputStream, null);
        return x509Cert;
    }

    @Override
    public Map<String, X509Certificate> getCertificate(PayerParams payerParams) {
        return null;
    }

    @Override
    public void setCertificate(String serialNumber, String merchantId,
                               String certificateStr) {

    }
}

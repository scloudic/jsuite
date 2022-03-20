package com.scloudic.jsuite.weixin.pay.test;

import com.scloudic.jsuite.weixin.pay.utils.CertificateUtils;
import com.scloudic.jsuite.weixin.pay.v3.model.JsapiRequest;
import com.scloudic.jsuite.weixin.pay.v3.model.PayerParams;
import com.scloudic.jsuite.weixin.pay.v3.model.V3Pay;
import com.scloudic.jsuite.weixin.pay.v3.model.V3PayResponse;
import com.scloudic.jsuite.weixin.pay.v3.service.MiniPayMchV3Service;
import com.scloudic.jsuite.weixin.pay.v3.service.impl.MiniPayMchV3ServiceImpl;
import com.scloudic.rabbitframework.core.utils.JsonUtils;
import com.scloudic.rabbitframework.core.utils.UUIDUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;

public class MiniPayV3ServiceTest {
    private static Logger logger = LoggerFactory.getLogger(MiniPayV3ServiceTest.class);

    @Test
    public void jsapiTest() throws Exception {
        PayerParams payerParams = new PayerParams();
        payerParams.setApiV3Key("");
        payerParams.setCertificateSerialNumber("");
        payerParams.setMerchantId("");
        String privateKeyPath = "/1622153031_20220301_cert/apiclient_key.pem";
        String privateKey = CertificateUtils.loadPrivateKeyToString(new FileInputStream(privateKeyPath));
        payerParams.setPrivateKey(privateKey);
        payerParams.setAppId("");
        JsapiRequest jsapiRequest = new JsapiRequest();
        jsapiRequest.setOpenid("");
        jsapiRequest.setNotifyUrl("https://weixin.qq.com/");
        jsapiRequest.setOutTradeNo(UUIDUtils.getTimeUUID32());
        jsapiRequest.setSubject("购买商品");
        jsapiRequest.setTotal(100);
        jsapiRequest.setPayerClientIp("127.0.0.1");
        MiniPayMchV3Service payV3Service = new MiniPayMchV3ServiceImpl();
        payV3Service.setCertificate(new WeiXinCertificateImpl());
        V3PayResponse<V3Pay> v3PayResponse = payV3Service.pay(payerParams, jsapiRequest);
        logger.debug("返回结果：" + JsonUtils.toJson(v3PayResponse));
    }
}

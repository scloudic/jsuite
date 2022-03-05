package com.scloudic.jsuite.weixin.pay.v3.service;

import com.scloudic.jsuite.weixin.pay.v3.WeiXinCertificate;
import com.scloudic.jsuite.weixin.pay.v3.model.*;

/**
 * 商户平台支付
 */
public interface MiniPayV3Service {
    /**
     * 发起支付请求
     *
     * @param payerParams
     * @param jsapiRequest
     * @return
     */
    public V3PayResponse<V3Pay> pay(PayerParams payerParams, JsapiRequest jsapiRequest);





    public void setCertificate(WeiXinCertificate weiXinCertificate);
}

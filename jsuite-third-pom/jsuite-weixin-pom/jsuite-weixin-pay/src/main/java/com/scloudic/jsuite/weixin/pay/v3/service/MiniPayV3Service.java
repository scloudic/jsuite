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

    /**
     * 微信支付订单号查询
     *
     * @param payerParams
     * @param transactionId
     * @return
     */
    public V3PayResponse<SearchPayResult> searchPayByTransactionId(PayerParams payerParams, String transactionId);

    /**
     * 商户订单号查询
     *
     * @param payerParams
     * @param outTradeNo
     * @return
     */
    public V3PayResponse<SearchPayResult> searchPayByOutTradeNo(PayerParams payerParams, String outTradeNo);


    public void setCertificate(WeiXinCertificate weiXinCertificate);
}

package com.scloudic.jsuite.weixin.pay.v3.service;

import com.scloudic.jsuite.weixin.pay.v3.WeiXinCertificate;
import com.scloudic.jsuite.weixin.pay.v3.model.Jsapi;
import com.scloudic.jsuite.weixin.pay.v3.model.JsapiRequest;
import com.scloudic.jsuite.weixin.pay.v3.model.PayerParams;
import com.scloudic.jsuite.weixin.pay.v3.model.V3PayResponse;

/**
 * 商户平台支付
 */
public interface MiniPayV3Service {

    /**
     * JSAPI下单
     * 示例：
     * {
     * "mchid": "1900006XXX",
     * "out_trade_no": "1217752501201407033233368318",
     * "appid": "wxdace645e0bc2cXXX",
     * "description": "Image形象店-深圳腾大-QQ公仔",
     * "notify_url": "https://weixin.qq.com/",
     * "amount": {
     * "total": 1,
     * "currency": "CNY"
     * },
     * "payer": {
     * "openid": "o4GgauInH_RCEdvrrNGrntXDuXXX"
     * }
     * }
     *
     * @param payerParams
     * @param jsapiRequest
     * @return
     */
    public V3PayResponse<Jsapi> jsapi(PayerParams payerParams, JsapiRequest jsapiRequest);

    public void setCertificate(WeiXinCertificate weiXinCertificate);
}

package com.scloudic.jsuite.weixin.pay.v3.service;

import com.scloudic.jsuite.weixin.pay.v3.model.*;

/**
 * 商户平台分账，只有V3版才能支持分账
 */
public interface SharePayMchService {
    /**
     * 对同一笔订单最多能发起50次分账请求，每次请求最多分给50个接收方
     * <p>
     * 此接口采用异步处理模式，即在接收到商户请求后，优先受理请求再异步处理，
     * 最终的分账结果可以通过查询分账接口获取
     *
     * @param request
     * @param payerParams
     */
    public V3PayResponse<ShareOrderResult> orders(PayerParams payerParams,
                                                  ShareOrderRequest request);

    /**
     * 分冻/解冻查询
     *
     * @param payerParams
     * @param request
     * @return
     */
    public V3PayResponse<ShareOrderResult> searchOrders(PayerParams payerParams,
                                                        ShareOrderSearchRequest request);

}

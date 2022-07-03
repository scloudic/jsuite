package com.scloudic.jsuite.weixin.pay.v3.service;

import com.scloudic.jsuite.weixin.pay.v3.certificate.PayCertificate;
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
    public V3PayResponse<ShareResult> orders(PayerParams payerParams,
                                             ShareRequest request);

    /**
     * 分冻/解冻查询
     *
     * @param payerParams
     * @param request
     * @return
     */
    public V3PayResponse<ShareResult> searchOrders(PayerParams payerParams,
                                                   ShareSearchRequest request);

    /**
     * 分账回退请求
     * 此接口采用同步处理模式，即在接收到商户请求后，会实时返回处理结果。
     *
     * @param payerParams
     * @param shareRefundRequest
     * @return
     */
    public V3PayResponse<ShareRefundResult> returnOrders(PayerParams payerParams,
                                                         ShareRefundRequest shareRefundRequest);

    /**
     * 查询分账回退结果
     *
     * @param payerParams
     * @param shareRefundRequest
     * @return
     */
    public V3PayResponse<ShareRefundResult> searchReturnOrders(PayerParams payerParams,
                                                               ShareSearchRefundRequest shareRefundRequest);

    /**
     * 解冻剩余资金
     *
     * @param payerParams
     * @param shareUnfreezeRequest
     * @return
     */
    public V3PayResponse<ShareResult> unfreeze(PayerParams payerParams,
                                               ShareUnfreezeRequest shareUnfreezeRequest);

    /**
     * 查询剩余金额
     *
     * @param payerParams
     * @param transactionId
     * @return
     */
    public V3PayResponse<ShareRemainResult> shareRemain(PayerParams payerParams,
                                                        String transactionId);

    /**
     * 添加分账用户
     *
     * @param payerParams
     * @param request
     * @return
     */
    public V3PayResponse<ShareAddReceiversResult> addReceivers(PayerParams payerParams,
                                                               ShareAddReceiversRequest request);

    /**
     * 删除接收方
     *
     * @param payerParams
     * @param request
     * @return
     */
    public V3PayResponse<ShareDelReceiversResult> delReceivers(PayerParams payerParams,
                                                               ShareDelReceiversRequest request);



    public void setPayCertificate(PayCertificate payCertificate);
}

package com.scloudic.jsuite.weixin.pay.v3.model;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
public class ShareResult {
    private String transaction_id;
    private String out_order_no;
    /**
     * 微信分账单号，微信支付系统返回的唯一标识
     */
    private String order_id;
    /**
     * 分账单状态（每个接收方的分账结果请查看receivers中的result字段），枚举值：
     * 1、PROCESSING：处理中
     * 2、FINISHED：分账完成
     */
    private String state;
    /**
     * 分账接收方列表
     */
    private List<ShareRevResult> receivers;
}

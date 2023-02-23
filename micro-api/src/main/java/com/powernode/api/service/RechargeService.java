package com.powernode.api.service;

import com.powernode.api.model.RechargeRecord;

/**
 * 充值记录相关服务
 */
public interface RechargeService {
    int addRecharge(RechargeRecord rechargeRecord);

    /**
     * 处理支付结果
     * @param orderId   充值记录id
     * @param payResult 支付结果
     * @param payAmount 实际支付金额
     */
    int dealWithNotify(String orderId, String payResult, String payAmount);
}

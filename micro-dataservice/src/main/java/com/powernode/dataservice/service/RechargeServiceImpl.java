package com.powernode.dataservice.service;

import com.powernode.api.model.RechargeRecord;
import com.powernode.api.service.RechargeService;
import com.powernode.constants.YLBConstants;
import com.powernode.dataservice.mapper.FinanceAccountMapper;
import com.powernode.dataservice.mapper.RechargeRecordMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@DubboService(interfaceClass = RechargeService.class,version = "1.0.0")
public class RechargeServiceImpl implements RechargeService {

    @Autowired
    private RechargeRecordMapper rechargeRecordMapper;

    @Autowired
    private FinanceAccountMapper financeAccountMapper;

    @Override
    public int addRecharge(RechargeRecord rechargeRecord) {

        return rechargeRecordMapper.insertSelective(rechargeRecord);
    }

    /**
     * 处理支付结果 根据支付结果，修改充值记录状态，修改用户的账户余额
     *
     * @param orderId   充值记录id
     * @param payResult 支付结果
     * @param payAmount 实际支付金额
     */
    @Override
    public int dealWithNotify(String orderId, String payResult, String payAmount) {
        /*声明 变量表示 执行的结果*/
        int result = 0; //默认值
        /*获取充值记录*/
        RechargeRecord rechargeRecord = rechargeRecordMapper.selectByRechargeNo(orderId);
        /*判断充值记录是否存在*/
        if(rechargeRecord==null){
            result = 1; //订单不存在
            return result;
        }
        /*快钱 可能重复发送结果，要判断该订单是否已被处理 */
        if(rechargeRecord.getRechargeStatus()!= YLBConstants.RECHARGE_ING){
            result = 2;//订单已被处理 无需再次处理
            return result;
        }
        /* 判断支付结果 和 订单金额 */
        /* 统一 金额的单位 分 */
        String fen = rechargeRecord.getRechargeMoney().multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString();
        if(!fen.equals(payAmount)){
            return result = 3;  //金额不一致
        }
        /*支付结果不同 修改不同的订单状态 和 账户余额 */
        /*充值失败*/
        if(!"10".equals(payResult)){
            System.out.println("充值失败");
            result = 4; //支付失败
            /*修改记录状态为 2*/
            int rows = rechargeRecordMapper.updateStatus(orderId,YLBConstants.RECHARGE_FAILURE);
            if (rows < 1) {
                throw new RuntimeException("修改充值记录失败");
            }
            return result;
        }
        /* 充值成功 修改记录状态为 1 修改用户的账户 */
        System.out.println("充值成功");
        int rows = rechargeRecordMapper.updateStatus(orderId,YLBConstants.RECHARGE_SUCCESS);
        if (rows < 1) {
            throw new RuntimeException("修改充值记录失败");
        }
        rows = financeAccountMapper.updateAvailableMoneyByRecharge(rechargeRecord.getUid(),rechargeRecord.getRechargeMoney());

        if (rows < 1) {
            throw new RuntimeException("修改用户账户余额失败");
        }

        result = 5; //充值成功
        return result;

    }
}

package com.powernode.dataservice.mapper;

import com.powernode.api.model.RechargeRecord;

public interface RechargeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RechargeRecord record);

    int insertSelective(RechargeRecord record);

    RechargeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RechargeRecord record);

    int updateByPrimaryKey(RechargeRecord record);

    /**
     * 通过充值记录编号 获取充值记录信息
     * @param orderId
     * @return
     */
    RechargeRecord selectByRechargeNo(String orderId);

    /**
     * 修改指定编号的 充值记录状态
     * @param orderId
     * @param status
     * @return
     */
    int updateStatus(String orderId, Integer status);
}
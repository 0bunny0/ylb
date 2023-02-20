package com.powernode.dataservice.mapper;

import com.powernode.api.model.FinanceAccount;

import java.math.BigDecimal;

public interface FinanceAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FinanceAccount record);

    int insertSelective(FinanceAccount record);

    FinanceAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FinanceAccount record);

    int updateByPrimaryKey(FinanceAccount record);

    /**
     * 通过用户id 查询 账户表信息
     * @param uid
     * @return
     */
    FinanceAccount selectByUid(Integer uid);

    /**
     * 使用排它锁 通过用户id获取账户信息
     * @param uid
     * @return
     */
    FinanceAccount selectByUidForUpdate(Integer uid);

    /**
     * 扣除某用户的 余额
     * @param uid
     * @param bidMoney
     * @return
     */
    int reduceAvailableMoney(Integer uid, BigDecimal bidMoney);
}
package com.powernode.dataservice.mapper;

import com.powernode.api.model.FinanceAccount;

import java.math.BigDecimal;
import java.util.Map;

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

    /**
     * 通过收益表的 uid bid_money income_money 去修改 某用户的 账户 余额
     * @param params
     * @return
     */
    int updateFinanceAccountIncome(Map<String, Object> params);

    /**
     * 给指定用户增加余额
     * @param uid
     * @param rechargeMoney
     * @return
     */
    int updateAvailableMoneyByRecharge(Integer uid, BigDecimal rechargeMoney);
}
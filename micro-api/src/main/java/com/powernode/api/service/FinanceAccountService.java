package com.powernode.api.service;

import com.powernode.api.model.FinanceAccount;

/*
    账户相关服务
 */
public interface FinanceAccountService {
    /**
     * 通过 用户id 获取账户信息
     * @param uid   用户的id
     * @return
     */
    FinanceAccount queryByUid(Integer uid);

}

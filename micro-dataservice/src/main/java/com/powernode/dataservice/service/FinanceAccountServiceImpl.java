package com.powernode.dataservice.service;

import com.powernode.api.model.FinanceAccount;
import com.powernode.api.service.FinanceAccountService;
import com.powernode.dataservice.mapper.FinanceAccountMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService(interfaceClass = FinanceAccountService.class,version = "1.0.0")
public class FinanceAccountServiceImpl implements FinanceAccountService {

    @Autowired
    private FinanceAccountMapper financeAccountMapper;


    /**
     * 通过 用户id 获取账户信息
     * @param uid 用户的id
     * @return
     */
    @Override
    public FinanceAccount queryByUid(Integer uid) {
        return financeAccountMapper.selectByUid(uid);
    }
}

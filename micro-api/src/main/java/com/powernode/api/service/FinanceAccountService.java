package com.powernode.api.service;

import com.powernode.api.model.FinanceAccount;

public interface FinanceAccountService {
    FinanceAccount queryByUid(Integer uid);

}

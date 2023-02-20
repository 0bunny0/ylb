package com.powernode.vo;

import java.math.BigDecimal;

/**
 * 投资界面显示的用户信息 和 账户信息
 */
public class UserInfo {
    private String name;
    private BigDecimal accountMoney;

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", accountMoney=" + accountMoney +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(BigDecimal accountMoney) {
        this.accountMoney = accountMoney;
    }
}

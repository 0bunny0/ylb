package com.powernode.api.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 首页三个数据
 */
public class BaseInfo implements Serializable {
    /*平均利率*/
    private BigDecimal avgRate;
    /*总用户数据*/
    private Integer registerUsers;
    /*累计交易金额*/
    private BigDecimal sumBidMoney;

    @Override
    public String toString() {
        return "BaseInfo{" +
                "avgRate=" + avgRate +
                ", registerUsers=" + registerUsers +
                ", sumBidMoney=" + sumBidMoney +
                '}';
    }

    public BigDecimal getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(BigDecimal avgRate) {
        this.avgRate = avgRate;
    }

    public Integer getRegisterUsers() {
        return registerUsers;
    }

    public void setRegisterUsers(Integer registerUsers) {
        this.registerUsers = registerUsers;
    }

    public BigDecimal getSumBidMoney() {
        return sumBidMoney;
    }

    public void setSumBidMoney(BigDecimal sumBidMoney) {
        this.sumBidMoney = sumBidMoney;
    }
}

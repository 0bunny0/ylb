package com.powernode.vo;


import java.math.BigDecimal;

/**
 * 投资请求数据模型
 */
public class BidInfo {
    private Integer uid;
    private Integer productId;
    private BigDecimal bidMoney;

    @Override
    public String toString() {
        return "BidInfo{" +
                "uid=" + uid +
                ", productId=" + productId +
                ", bidMoney=" + bidMoney +
                '}';
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getBidMoney() {
        return bidMoney;
    }

    public void setBidMoney(BigDecimal bidMoney) {
        this.bidMoney = bidMoney;
    }
}

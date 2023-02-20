package com.powernode.api.service;

import java.math.BigDecimal;

/**
 * 投资api
 */

public interface InvestService {

    /**
     * 用户投资了某个产品一定金额
     * @param uid
     * @param productId
     * @param bidMoney
     * @return
     */
    boolean investProduct(Integer uid, Integer productId, BigDecimal bidMoney);
}

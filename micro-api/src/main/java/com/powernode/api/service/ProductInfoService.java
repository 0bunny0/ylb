package com.powernode.api.service;

import com.powernode.api.model.ProductInfo;
import com.powernode.api.pojo.ProductData;

/**
 * 产品相关服务
 */
public interface ProductInfoService {
    ProductData queryIndexProductList();

    ProductInfo queryById(Integer pid);
}

package com.powernode.api.service;

import com.powernode.api.model.ProductInfo;
import com.powernode.api.pojo.ProductData;

import java.util.List;

/**
 * 产品相关服务
 */
public interface ProductInfoService {
    ProductData queryIndexProductList();

    ProductInfo queryById(Integer pid);

    /**
     * 计算总条数
     * @param ltype
     * @return
     */
    int selectTotalRowsByType(Integer ltype);


    /**
     * 根据type 页码 页数 分页
     * @param ltype
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<ProductInfo> queryAllByType(Integer ltype, Integer pageNo, Integer pageSize);
}

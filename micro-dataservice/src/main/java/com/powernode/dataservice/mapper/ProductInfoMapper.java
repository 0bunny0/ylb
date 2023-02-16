package com.powernode.dataservice.mapper;

import com.powernode.api.model.ProductInfo;

import java.math.BigDecimal;
import java.util.List;

public interface ProductInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    ProductInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);

    BigDecimal selectAvgRate();


    /**
     * 通过类型分页查询 商品信息
     * @param type  类型
     * @param index 索引下标
     * @param rows  查询行数
     * @return
     */
    List<ProductInfo> selectPageByType(int type, int index, int rows);
}
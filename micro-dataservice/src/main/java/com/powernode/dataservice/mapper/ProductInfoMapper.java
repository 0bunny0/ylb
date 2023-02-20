package com.powernode.dataservice.mapper;

import com.powernode.api.model.ProductInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ProductInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    ProductInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);

    /**
     * 获取 产品平均利率
     * @return
     */
    BigDecimal selectAvgRate();


    /**
     * 通过类型分页查询 商品信息
     * @param type  类型
     * @param index 索引下标
     * @param rows  查询行数
     * @return
     */
    List<ProductInfo> selectPageByType(int type, int index, int rows);

    /**
     * 使用排它锁通过产品id获取产品信息
     * @param productId
     * @return
     */
    ProductInfo selectByIdForUpdate(Integer productId);

    /**
     * 扣除某产品的剩余可投资金额
     * @param productId
     * @param bidMoney
     * @return
     */
    int reduceProductLeftMoney(Integer productId, BigDecimal bidMoney);

    /**
     * 修改某产品的状态为满标，和满标时间
     * @param productId
     * @param date
     * @return
     */
    int updateFullTimeStatus(Integer productId, Date date);
}
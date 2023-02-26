package com.powernode.dataservice.mapper;

import com.powernode.api.model.BidInfo;
import com.powernode.api.pojo.PhoneBidInfo;

import java.math.BigDecimal;
import java.util.List;

public interface BidInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BidInfo record);

    int insertSelective(BidInfo record);

    BidInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BidInfo record);

    int updateByPrimaryKey(BidInfo record);

    /**
     * 获取 总交易金额
     * @return
     */
    BigDecimal selectSumBid();

    List<PhoneBidInfo> selectBidByProductId(Integer pid, int index, int pageSize);

    /**
     * 根据产品id 获取所有投资记录
     * @param pid
     * @return
     */
    List<BidInfo> selectByProductId(Integer pid);

    /**
     * 查找top3的数据
     * @return
     */
    List<PhoneBidInfo> SelectInvestRankTop3();
}
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

    BigDecimal selectSumBid();

    List<PhoneBidInfo> selectBidByProductId(Integer pid, int index, int pageSize);
}
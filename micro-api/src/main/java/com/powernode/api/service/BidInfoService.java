package com.powernode.api.service;


import com.powernode.api.pojo.PhoneBidInfo;

import java.util.List;

/**
 * 投资记录相关服务
 */
public interface BidInfoService {
    List<PhoneBidInfo> queryBidByProductId(Integer pid, int pageNo, int pageSize);
}

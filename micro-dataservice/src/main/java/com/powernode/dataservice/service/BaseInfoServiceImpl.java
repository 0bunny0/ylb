package com.powernode.dataservice.service;

import com.powernode.api.pojo.BaseInfo;
import com.powernode.api.service.BaseInfoService;
import com.powernode.dataservice.mapper.BidInfoMapper;
import com.powernode.dataservice.mapper.ProductInfoMapper;
import com.powernode.dataservice.mapper.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

//向外界暴露 提供服务
@DubboService(interfaceClass = BaseInfoService.class,version = "1.0.0")
public class BaseInfoServiceImpl implements BaseInfoService {
    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BidInfoMapper bidInfoMapper;

    @Override
    public BaseInfo queryBaseInfo() {
        BaseInfo baseInfo = new BaseInfo();
        baseInfo.setAvgRate(productInfoMapper.selectAvgRate());
        baseInfo.setRegisterUsers(userMapper.selectCountUsers());
        baseInfo.setSumBidMoney(bidInfoMapper.selectSumBid());

        return baseInfo;
    }
}

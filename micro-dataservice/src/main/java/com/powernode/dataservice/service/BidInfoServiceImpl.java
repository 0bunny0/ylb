package com.powernode.dataservice.service;

import com.powernode.api.pojo.PhoneBidInfo;
import com.powernode.api.service.BidInfoService;
import com.powernode.dataservice.mapper.BidInfoMapper;
import com.powernode.util.CommonUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@DubboService(interfaceClass = BidInfoService.class,version = "1.0.0")
public class BidInfoServiceImpl implements BidInfoService {

    @Autowired
    private BidInfoMapper bidInfoMapper;

    @Override
    public List<PhoneBidInfo> queryBidByProductId(Integer pid, int pageNo, int pageSize) {
        List<PhoneBidInfo> phoneBidInfos = new ArrayList<>();
        if(pid==null||pid<1){
            return phoneBidInfos;
        }
        /*校验 页面 和 显示行数*/
        pageNo = CommonUtil.pageNo(pageNo);
        pageSize = CommonUtil.pageSize(pageSize);

        /*分页查询 limit index, size */
        int index = (pageNo - 1)*pageSize;
        phoneBidInfos = bidInfoMapper.selectBidByProductId(pid,index,pageSize);

        return phoneBidInfos;
    }

    @Override
    public List<PhoneBidInfo> InvestRankTop3() {

        List<PhoneBidInfo> phoneBidInfos= bidInfoMapper.SelectInvestRankTop3();;
        /*查找top3的数据*/

        return phoneBidInfos;
    }
}

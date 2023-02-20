package com.powernode.controller;

import com.powernode.api.service.InvestService;
import com.powernode.common.Code;
import com.powernode.resp.Result;
import com.powernode.vo.BidInfo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 投资api
 */
@RestController
public class InvestController {

    @DubboReference(interfaceClass = InvestService.class,version = "1.0.0")
    private InvestService investService;

    @PostMapping("/v1/product/invest")
    public Result investProduct(@RequestBody BidInfo bidInfo){
        Result result = Result.FAIL();

//        数据校验
        if(bidInfo==null
                ||bidInfo.getUid()<1
                ||bidInfo.getProductId()==null
                ||bidInfo.getProductId()<1
                ||bidInfo.getBidMoney().intValue()%100!=0
        ){
            result.setCodeEnum(Code.QUERY_PARAM_ERROR);
            return result;
        }

        /*调用远程投资服务*/
        boolean investResult = investService.investProduct(bidInfo.getUid(),bidInfo.getProductId(),bidInfo.getBidMoney());
        if(!investResult){
            result.setCodeEnum(Code.PRODUCT_INVEST_ERROR);
            return result;
        }


        result = Result.SUCCESS();

        return result;
    }
}

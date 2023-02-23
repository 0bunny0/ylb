package com.powernode.controller;

import com.powernode.api.model.BidInfo;
import com.powernode.api.model.ProductInfo;
import com.powernode.api.pojo.PhoneBidInfo;
import com.powernode.api.service.BidInfoService;
import com.powernode.api.service.ProductInfoService;
import com.powernode.common.Code;
import com.powernode.resp.Result;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
    产品api接口
 */
@RestController
public class ProductController {

    @DubboReference(interfaceClass = ProductInfoService.class,version = "1.0.0")
    private ProductInfoService productInfoService;

    @DubboReference(interfaceClass = BidInfoService.class,version = "1.0.0")
    private BidInfoService bidInfoService;

    /**
     * 通过产品id 获取产品详情(包含投资记录)
     * @param pid
     * @return
     */
    @GetMapping("/v1/product/detail")
    public Result queryByProductId(Integer pid){
        Result result = Result.FAIL();
        /*验证参数*/
        if (pid==null||pid<1){
            result.setCodeEnum(Code.QUERY_PARAM_ERROR);
            return result;
        }
        /*可以访问一次远程服务，也可以 分多次访问远程服务 获取不同的数据*/
        /*获取产品详情*/
        ProductInfo productInfo = productInfoService.queryById(pid);
        /*获取产品的投资记录  分页  按照最新时间排序*/
        List<PhoneBidInfo> phoneBidInfos = bidInfoService.queryBidByProductId(pid,1,5);
        /*封装数据 */
        result = Result.SUCCESS();
        result.setObject(productInfo);
        result.setList(phoneBidInfos);
        return result;
    }


}

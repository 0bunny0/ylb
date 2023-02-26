package com.powernode.controller;

import com.powernode.api.model.ProductInfo;
import com.powernode.api.pojo.PageInfo;
import com.powernode.api.pojo.PhoneBidInfo;
import com.powernode.api.pojo.ProductData;
import com.powernode.api.service.BidInfoService;
import com.powernode.api.service.ProductInfoService;
import com.powernode.resp.Result;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分页界面
 */
@RestController
public class PaginationController {

    @DubboReference(interfaceClass = BidInfoService.class,version = "1.0.0")
    private BidInfoService bidInfoService;

    @DubboReference(interfaceClass = ProductInfoService.class,version = "1.0.0")
    private ProductInfoService productInfoService;

    /**
     * 投资排行榜Top 3
     * @return
     */
    @GetMapping("/v1/product/ranklist")
    public Result getRankList(){
        Result result = Result.FAIL();

        List<PhoneBidInfo> phoneBidInfo = bidInfoService.InvestRankTop3();

        result = Result.SUCCESS();
        result.setList(phoneBidInfo);
        return result;
    }

    @GetMapping("/v1/product/type")
    public Result Pagination(Integer ltype, Integer pageSize,Integer pageNo){
        Result result = Result.FAIL();


        PageInfo pageInfo = new PageInfo();
        int tr = productInfoService.selectTotalRowsByType(ltype);
        if(tr % pageSize == 0){
            pageInfo.setTotalPages(tr / pageSize);
        }else {
            pageInfo.setTotalPages(tr / pageSize + 1);
        }
        pageInfo.setPageNo(pageNo);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotalRows(tr);


        List<ProductInfo> productInfos = productInfoService.queryAllByType(ltype,pageInfo.getPageNo(),pageInfo.getPageSize());


        result = Result.SUCCESS();
        result.setList(productInfos);
        result.setObject(pageInfo);
        return result;
    }

}

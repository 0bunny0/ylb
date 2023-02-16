package com.powernode.controller;

import com.powernode.api.pojo.BaseInfo;
import com.powernode.api.pojo.ProductData;
import com.powernode.api.service.BaseInfoService;
import com.powernode.api.service.ProductInfoService;
import com.powernode.resp.Result;
import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页api
 */
@RestController
@Api(tags = "首页基本信息")
public class IndexController {

    /*引入远程服务*/
    @DubboReference(interfaceClass = BaseInfoService.class,version = "1.0.0")
    private BaseInfoService baseInfoService;

    @DubboReference(interfaceClass = ProductInfoService.class,version = "1.0.0")
    private ProductInfoService productInfoService;

    /*首页三个数据*/
    @GetMapping("v1/base/info")
    @ApiOperation(value = "首页三个数据",notes = "平均利率，总人数，总交易金额")
    @ApiResponses({
            @ApiResponse(code = 200,message = "ok!请求响应成功"),
            @ApiResponse(code = 500,message = "无服务异常"),
            @ApiResponse(code = 403,message = "拒绝访问"),
            @ApiResponse(code = 400,message = "参数错误"),
            @ApiResponse(code = 405,message = "请求方式错误"),
            @ApiResponse(code = 415,message = "类型转换错误"),
            @ApiResponse(code = 302,message = "重定向"),
    })
    public Result getBaseInfo(){
        Result result = Result.SUCCESS();

        /*调用远程服务的方法*/
        BaseInfo baseInfo = baseInfoService.queryBaseInfo();
        result.setObject(baseInfo);

        return result;
    }

    /*首页三个集合*/
    @GetMapping("v1/base/products")
    public Result getIndexProducts(){
        Result result = Result.SUCCESS();

        /*调用远程服务获取 数据*/
        ProductData productData = productInfoService.queryIndexProductList();
        result.setObject(productData);

        return result;
    }
}

package com.powernode.dataservice.service;

import com.alibaba.fastjson.JSONObject;
import com.powernode.api.pojo.ProductData;
import com.powernode.api.service.ProductInfoService;
import com.powernode.constants.RedisKey;
import com.powernode.dataservice.mapper.ProductInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@DubboService(interfaceClass = ProductInfoService.class,version = "1.0.0")
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ProductData queryIndexProductList() {
        ProductData productData = new ProductData();

        /*是否要查询数据库*/
        boolean isQuerySql = false;

        /*优先从redis缓存中获取*/

        /*1 判断redis中是否存在缓存数据*/
        if (stringRedisTemplate.hasKey(RedisKey.YLB_INDEX_PRODUCTS)) {
            System.out.println("从redis中获取数据");
            /*从redis中获取*/
            String json = stringRedisTemplate.boundValueOps(RedisKey.YLB_INDEX_PRODUCTS).get();
            /*使用fastjson 将json字符串 反序列化 为java对象*/
            try {
                productData = JSONObject.parseObject(json,ProductData.class);
                return productData;
            } catch (Exception e) {
                //json数据异常 需要查询数据库
                isQuerySql = true;
                System.out.println("json解析失败");
            }
        }
        isQuerySql = true;

        /*2 如果存在直接获取*/

        /*3 如果不存在 查询数据库 并且 缓存*/
        if(isQuerySql){
            /* 从数据库中获取 */
            productData.setXinShouBao(productInfoMapper.selectPageByType(0,0,1));
            productData.setYouXuan(productInfoMapper.selectPageByType(1,0,3));
            productData.setSanBiao(productInfoMapper.selectPageByType(2,0,3));

            /*json序列化*/
            String json = JSONObject.toJSONString(productData);
            /*缓存*/
            stringRedisTemplate.boundValueOps(RedisKey.YLB_INDEX_PRODUCTS).set(json,2,TimeUnit.HOURS);
        }


        return productData;
    }
}

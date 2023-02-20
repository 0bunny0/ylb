package com.powernode.dataservice.service;

import com.alibaba.fastjson.JSONObject;
import com.powernode.api.model.ProductInfo;
import com.powernode.api.pojo.ProductData;
import com.powernode.api.service.ProductInfoService;
import com.powernode.constants.RedisKey;
import com.powernode.dataservice.mapper.ProductInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Random;
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

                System.out.println("json解析失败");
            }
        }else {
            synchronized (this){
                if (stringRedisTemplate.hasKey(RedisKey.YLB_INDEX_PRODUCTS)){
                    System.out.println("上一个线程已经缓存了，从redis中获取");
                    String json = stringRedisTemplate.boundValueOps(RedisKey.YLB_INDEX_PRODUCTS).get();
                    /*使用fastjson 将json字符串 反序列化 为java对象*/
                    try {
                        productData = JSONObject.parseObject(json,ProductData.class);
                        return productData;
                    } catch (Exception e) {
                        //json数据异常 需要查询数据库
                        //isQuerySql = true;
                        System.out.println("json解析失败");
                    }
                }

                /*3 如果不存在 查询数据库 并且 缓存*/
                /* 从数据库中获取 */
                productData.setXinShouBao(productInfoMapper.selectPageByType(0,0,1));
                productData.setYouXuan(productInfoMapper.selectPageByType(1,0,3));
                productData.setSanBiao(productInfoMapper.selectPageByType(2,0,3));

                /* 解决缓存雪崩,使用不同的过期时间 */
                /* 过期时间 */
                Random random = new Random();
                int rnum = random.nextInt(5);

                /*json序列化*/
                String json = JSONObject.toJSONString(productData);
                /*缓存*/
                stringRedisTemplate.boundValueOps(RedisKey.YLB_INDEX_PRODUCTS).set(json,5+rnum,TimeUnit.HOURS);

                //如果 数据中没有数据 也要缓存 但是 缓存的时间 短一些
                if(productData == null){
                    stringRedisTemplate.expire(RedisKey.YLB_INDEX_PRODUCTS,30,TimeUnit.SECONDS);
                }

            }
        }

        return productData;
    }

    @Override
    public ProductInfo queryById(Integer pid) {
        ProductInfo productInfo = null;

        if(pid == null || pid < 1){
            return productInfo;
        }

        productInfo = productInfoMapper.selectByPrimaryKey(pid);

        return productInfo;
    }
}

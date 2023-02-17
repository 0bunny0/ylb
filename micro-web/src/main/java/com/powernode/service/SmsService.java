package com.powernode.service;

import com.alibaba.fastjson.JSONObject;
import com.powernode.config.JDWXSmsConfig;
import com.powernode.constants.RedisKey;
import com.powernode.util.CommonUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class SmsService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JDWXSmsConfig jdwxSmsConfig;

    /**
     * 判断 某手机对应的验证码 是否可继续使用
     *
     * @param phone
     * @param cmd
     * @return
     */
    public boolean checkCodeIsUse(String phone, String cmd) {
        /*省略 参数校验*/
        if(!CommonUtil.checkPhone(phone)){
            return false;
        }
        /*根据不同的操作 判断 不用的验证码 是否存在redis*/

        String key = "";
        if ("reg".equals(cmd)) {
            key = RedisKey.SMS_REG_CODE + phone;
        } else if ("login".equals(cmd)) {
            key = RedisKey.SMS_LOGIN_CODE + phone;
        } else if ("realname".equals(cmd)) {
            key = RedisKey.SMS_REALNAME_CODE + phone;
        }
        return stringRedisTemplate.hasKey(key);

    }

    public boolean sendSmsCode(String phone, String cmd) {
        /*生成验证码*/
        String code = RandomStringUtils.randomNumeric(6);

        /*访问第三方接口发送验证码*/
        boolean sendResult = handleSendCode(phone, code);
        if (!sendResult) {
            return false;
        }

        /*redis中 缓存 验证码 3分钟有效*/
        String key = "";
        if ("reg".equals(cmd)) {
            key = RedisKey.SMS_REG_CODE + phone;
        } else if ("login".equals(cmd)) {
            key = RedisKey.SMS_LOGIN_CODE + phone;
        } else if ("realname".equals(cmd)) {
            key = RedisKey.SMS_REALNAME_CODE + phone;
        }
        /*使用键绑定器*/
        BoundValueOperations<String, String> boundValueOps = stringRedisTemplate.boundValueOps(key);
        /*存值*/
        boundValueOps.set(code, 3, TimeUnit.MINUTES);
        return true;
    }

    /* 访问第三方接口发送短信验证码 */
    private boolean handleSendCode(String phone,String code){
        String url =jdwxSmsConfig.getUrl();
        String content = String.format(jdwxSmsConfig.getContent(),code);//字符串替代 %s
        String appkey=jdwxSmsConfig.getAppkey();
        String completedURL = url+"?"+"mobile="+phone+"&"+"content="+content+"&"+"appkey="+appkey;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(completedURL);
        try {
            HttpResponse response = httpClient.execute(get);
            String json = EntityUtils.toString(response.getEntity());
            System.out.println("原json："+json);
            /*写死json：要收费*/
            json="{\n" +
                    "    \"code\": \"10000\",\n" +
                    "    \"charge\": false,\n" +
                    "    \"remain\": 1305,\n" +
                    "    \"msg\": \"查询成功\",\n" +
                    "    \"result\": {\n" +
                    "        \"ReturnStatus\": \"Success\",\n" +
                    "        \"Message\": \"ok\",\n" +
                    "        \"RemainPoint\": 420842,\n" +
                    "        \"TaskID\": 18424321,\n" +
                    "        \"SuccessCounts\": 1\n" +
                    "    }\n" +
                    "}\n";
            /*解析json*/
            JSONObject jsonObject = JSONObject.parseObject(json);
            if (!"10000".equals(jsonObject.getString("code"))){
                /*访问接口失败=----*/
                return false;
            }
            String ReturnStatus = jsonObject.getJSONObject("result").getString("ReturnStatus");
            if (!"Success".equals(ReturnStatus)){
                return false;
            }
            System.out.println("您的验证码是："+code);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 验证验证码输入是否正确
     * @param phone 手机号码
     * @param code  用户输入验证码
     * @param cmd   操作
     * @return
     */
    public boolean checkSmsCode(String phone,String code,String cmd){
        /*根据操作 和手机号码,获取key, 通过key 去与验证码的值和用户输入的验证码进行比较*/
        String key = "";
        if ("reg".equals(cmd)) {
            key = RedisKey.SMS_REG_CODE + phone;
        } else if ("login".equals(cmd)) {
            key = RedisKey.SMS_LOGIN_CODE + phone;
        } else if ("realname".equals(cmd)) {
            key = RedisKey.SMS_REALNAME_CODE + phone;
        }

        /*从redis中去获取vaules*/
        String redisCode = stringRedisTemplate.boundValueOps(key).get();
        if(!code.equals(redisCode)){
            return false;
        }

        return true;
    }
}

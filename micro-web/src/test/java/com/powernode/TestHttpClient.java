package com.powernode;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class TestHttpClient {
    @Test
    public void testGet(){
        /*第三方接口访问地址*/
        String url="https://restapi.amap.com/v3/ip?key=0113a13c88697dcea6a445584d535837&ip=60.25.188.64";

        /*1 创建 客户端对象*/
        HttpClient client = HttpClients.createDefault();
        /*2 创建 请求对象 配置访问地址*/
        HttpGet get = new HttpGet(url);
        try{
            /*3 使用客户端对象 发送请求 获取响应对象*/
            HttpResponse response = client.execute(get);
            /*4 从响应对象中 获取 响应信息,解析 响应数据对象 获取响应的结果*/
            String json = EntityUtils.toString(response.getEntity());
            /*解析 json数据*/
            System.out.println(json);

            /*使用 FastJson */
            JSONObject object = JSONObject.parseObject(json);
            String city = object.getString("city");
            System.out.println(city);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*post*/
    @Test
    public void testPost(){
        //测试使用HttpClient的post请求
        String url = "https://restapi.amap.com/v3/ip";

        /*1 创建 客户端对象*/
        HttpClient client = HttpClients.createDefault();
        /*2 创建 请求对象 配置访问地址*/
        HttpPost post = new HttpPost(url);

        /*准备请求参数的 list集合*/
        List<NameValuePair> params = new ArrayList<>();
        params.add( new BasicNameValuePair("key","0113a13c88697dcea6a445584d535837"));
        params.add(new BasicNameValuePair("ip","60.25.188.64"));

        try {
            /*创建 参数对象*/
            HttpEntity entity = new UrlEncodedFormEntity(params);
            /*请求携带 参数*/
            post.setEntity(entity);
            //执行请求
            HttpResponse response = client.execute(post);
            if( response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String json = EntityUtils.toString(response.getEntity());
                System.out.println(json);
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*短信接口*/
    @Test
    public void testCode(){
        String url ="https://way.jd.com/chuangxin/VerCodesms?mobile=15360581202&content=【创信】你的验证码是：5873，3分钟内有效！&appkey=2f00342dfba6d00290d1c65c047d6778";
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            String json = EntityUtils.toString(entity);
            /*解析 json数据*/
            System.out.println(json);
            /*写死json数据*/
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
            /*使用 FastJson*/
            JSONObject jsonObject = JSONObject.parseObject(json);
            /*获取 key-value*/
            String code = jsonObject.getString("code");
            if (!code.equals("10000")){
                System.out.println("发送失败");
            }
            /*获取 key-obj*/
            JSONObject result = jsonObject.getJSONObject("result");
            String returnStatus = result.getString("ReturnStatus");
            if (!returnStatus.equals("Success")){
                System.out.println("用户没有收到信息");
            }
            System.out.println("发送成功 用户收到了验证码");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*实名认证接口*/
    @Test
    public void testRealName(){
        /*第三方接口访问地址*/
        String url="https://way.jd.com/hangzhoushumaikeji/id_card_check?idcard=372523197108020513&name=任学昌&appkey=2f00342dfba6d00290d1c65c047d6778";
        /*1 创建 客户端对象*/
        HttpClient client = HttpClients.createDefault();
        /*2 创建 请求对象  配置访问地址*/
        HttpGet get = new HttpGet(url);
        try {
            /*3 使用客户端对象 发送请求  获取响应对象*/
            HttpResponse response = client.execute(get);
            /*4 从响应对象中 获取 响应信息*/
            HttpEntity entity = response.getEntity();
            /*解析 响应数据对象  获取响应的结果*/
            String json = EntityUtils.toString(entity);
            /*解析 json数据*/
            System.out.println(json);
            /*使用 FastJson*/
            json="{\n" +
                    "    \"code\": \"10000\",\n" +
                    "    \"charge\": false,\n" +
                    "    \"remain\": 1305,\n" +
                    "    \"msg\": \"查询成功\",\n" +
                    "    \"result\": {\n" +
                    "        \"msg\": \"\",\n" +
                    "        \"success\": true,\n" +
                    "        \"code\": 200,\n" +
                    "        \"data\": {\n" +
                    "            \"result\": 1,\n" +
                    "            \"order_no\": \"626072002058391552\",\n" +
                    "            \"desc\": \"不一致\",\n" +
                    "            \"sex\": \"男\",\n" +
                    "            \"birthday\": \"19940320\",\n" +
                    "            \"address\": \"江西省南昌市东湖区\"\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";

            /*业务逻辑*/
            /*判断 接口访问是否成功*/
            JSONObject jsonObject = JSONObject.parseObject(json);
            boolean code = jsonObject.getString("code").equals("10000");
            if (!code){
                System.out.println("接口访问失败");
            }
            /*查询结果*/
            JSONObject result = jsonObject.getJSONObject("result");
            Integer resultInteger = result.getInteger("code");
            Boolean success = result.getBoolean("success");
            if (!success||resultInteger!=200){
                System.out.println("实名认证失败 响应提示信息");
            }
            /*修改当前用户的  name  id_card 字段*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

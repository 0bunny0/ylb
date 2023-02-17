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

    }

    /*实名验证接口*/
}

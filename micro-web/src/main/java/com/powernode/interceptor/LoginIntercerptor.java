package com.powernode.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.powernode.common.Code;
import com.powernode.constants.RedisKey;
import com.powernode.resp.Result;
import com.powernode.util.TokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截，拦截请求 验证token是否合法
 */
@Component
public class LoginIntercerptor implements HandlerInterceptor {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 在controller方法执行前 执行
     * @param request
     * @param response
     * @param handler
     * @return  true 放行 false 拦截
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        Result result = Result.FAIL();
        result.setCodeEnum(Code.TOKEN_INVALIDATE);
        String json = JSONObject.toJSONString(result);

        /*1 获取请求头中的token 验证是否包含token*/
        String authorization = request.getHeader("Authorization");
        if(authorization==null || !authorization.contains("Bearer ")){
            /*没有token*/
            System.out.println(authorization);
            System.out.println("没有token");
            response.getWriter().write(json);
            response.getWriter().flush();
            return false;
        }

        /*2 验证token是否合法*/
        String token = authorization.substring(7);
        Claims claims = tokenUtil.readJwt(token);
        /*判断 claim 是否为null 如果为 null 则非法*/
        if(claims==null){
            System.out.println("token非法");
            response.getWriter().write(json);
            response.getWriter().flush();
            return false;
        }

        /*redis中是否存在token对应的key 用户是否登出*/
        Boolean hasKey = stringRedisTemplate.hasKey(RedisKey.TOKEN_KEY+token);
        if(!hasKey){
            System.out.println("用户非法");
            response.getWriter().write(json);
            response.getWriter().flush();
            return false;
        }



        /*3 从请求头中获取uid 从token中获取uid 比较是否是同一个用户*/
        String headerUid = request.getHeader("uid");
        String tokenUid = claims.get("uid").toString();
        if(headerUid==null||tokenUid==null||!headerUid.equals(tokenUid)){
            System.out.println("不是同一个用户");
            response.getWriter().write(json);
            response.getWriter().flush();
            return false;
        }

        /*拦截器 传递给 目标资源 数据*/
        request.setAttribute("uid",tokenUid);
        return true;
    }
}

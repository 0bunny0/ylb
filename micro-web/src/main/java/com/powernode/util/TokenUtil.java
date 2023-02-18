package com.powernode.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * token相关工具类
 */
@Component
public class TokenUtil {
    /*密钥*/
    @Value("${token.secretKey}")
    private String key;

    /**
     * 生成token
     * @param payLoad    负载数据
     * @param minute    过期时间 单位分钟
     * @return
     */
    public String createJwt(Map<String,Object> payLoad, int minute){
        /*创建密钥对象*/
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        /*通过密钥生成token*/
        return Jwts.builder().signWith(secretKey, SignatureAlgorithm.HS256)    //加密算法HS256
                .setExpiration(DateUtils.addMinutes(new Date(),minute)) //到期时间
                .setIssuedAt(new Date())
                .setId(UUID.randomUUID().toString().replace("-",""))
                .addClaims(payLoad) //添加自定义负载数据
                .compact();
    }

    /*验证token*/
    public Claims readJwt(String jwt){
        Claims claims = null;
        try{
            /*创建密钥对象*/
            SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
            claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
            //获取数据
//        claims.get("key");
        }catch (Exception e){
            e.printStackTrace();
        }

        return claims;
    }
}

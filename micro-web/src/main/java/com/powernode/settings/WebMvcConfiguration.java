package com.powernode.settings;

import com.powernode.config.LoginIntercerptorPathConfig;
import com.powernode.interceptor.LoginIntercerptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web相关配置: 拦截器的拦截路径、过滤路径
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private LoginIntercerptor loginIntercerptor;

    @Autowired
    private LoginIntercerptorPathConfig config;

    /*写死的*/
   /* private String[] addPath={
        "/v1/user/info",
        "/v1/user/**"
    };
    private String[] excludePath={
        "/v1/user/test"
    };*/


    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loginIntercerptor)
                .addPathPatterns(config.getAddPath())   //拦截路径
                .excludePathPatterns(config.getExcludePath()) //过滤路径
        ;
    }

}

package com.powernode.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 从配置文件中获取 登录拦截器的 拦截路径 和 过滤路径
 */
@Component
@ConfigurationProperties(prefix = "interceptor.login")
public class LoginInterceptorPathConfig {
    private String[] addPath;
    private String[] excludePath;

    @Override
    public String toString() {
        return "LoginIntercerptorPathConfig{" +
                "addPath=" + Arrays.toString(addPath) +
                ", excludePath=" + Arrays.toString(excludePath) +
                '}';
    }

    public String[] getAddPath() {
        return addPath;
    }

    public void setAddPath(String[] addPath) {
        this.addPath = addPath;
    }

    public String[] getExcludePath() {
        return excludePath;
    }

    public void setExcludePath(String[] excludePath) {
        this.excludePath = excludePath;
    }
}

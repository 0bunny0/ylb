package com.powernode;

import com.powernode.config.LoginInterceptorPathConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MicroWebApplicationTests {


    @Autowired
    private LoginInterceptorPathConfig loginInterceptorPathConfig;

    @Test
    void contextLoads() {
        for (int i = 0; i < loginInterceptorPathConfig.getAddPath().length; i++) {
            System.out.println(loginInterceptorPathConfig.getAddPath()[i]);
        }

    }
}

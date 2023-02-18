package com.powernode;

import com.powernode.config.LoginIntercerptorPathConfig;
import com.powernode.interceptor.LoginIntercerptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MicroWebApplicationTests {

    @Autowired
    private LoginIntercerptorPathConfig loginIntercerptorPathConfig;

    @Test
    void contextLoads() {
    }

}

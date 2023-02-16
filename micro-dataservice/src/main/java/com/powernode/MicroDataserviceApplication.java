package com.powernode;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.powernode.dataservice.mapper")
public class MicroDataserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroDataserviceApplication.class, args);
    }

}

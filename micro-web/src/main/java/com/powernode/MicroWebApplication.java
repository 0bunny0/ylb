package com.powernode;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDubbo
@EnableSwagger2 //开启swagger2
public class MicroWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroWebApplication.class, args);
    }

}

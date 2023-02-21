package com.powernode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling   //开启定时任务
public class MicroTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroTaskApplication.class, args);
    }

}

package com.powernode;

import com.powernode.task.TaskManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling   //开启定时任务
public class MicroTaskApplication {

    public static void main(String[] args) {
        /*测试 定时服务*/
        /*springboot 启动服务的同时 spring容器也启动了*/
        ApplicationContext context = SpringApplication.run(MicroTaskApplication.class, args);
        /*从容器中获取bean*/
        TaskManager manager = context.getBean(TaskManager.class);
        manager.generatorIncomePlan();
    }

}

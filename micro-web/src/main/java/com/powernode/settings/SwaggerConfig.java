package com.powernode.settings;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swagger2 的配置
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        /*使用2.0版本的文档类型*/
        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("powernode盈利宝")
                .version("1.0.0")
                .description("分布式前后端分离微服务项目")
                .contact(new Contact("powernode","http://www.powernode.com","powernode@qq.com")).build();

        docket.apiInfo(apiInfo);

        /*指定注解*/
        // 指定注解
        docket = docket.select()
                .apis(RequestHandlerSelectors.basePackage("com.powernode.controller")).build();
        return docket;


    }
}

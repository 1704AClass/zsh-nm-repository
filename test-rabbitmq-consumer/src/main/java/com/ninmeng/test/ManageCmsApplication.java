package com.ninmeng.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by 赵晟晖 on 2020/2/12.
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.ningmeng.framework"})
@ComponentScan(basePackages={"com.ningmeng.test.config"})
public class ManageCmsApplication {
    public static void main(String[] args){
        SpringApplication.run(ManageCmsApplication.class,args);
    }
}

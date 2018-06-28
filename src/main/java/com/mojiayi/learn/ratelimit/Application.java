package com.mojiayi.learn.ratelimit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 访问限制示例程序入口
 *
 * @author mojiayi
 */
@SpringBootApplication
@ComponentScan("com.mojiayi.learn.ratelimit")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

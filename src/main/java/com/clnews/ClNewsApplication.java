package com.clnews;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 李学亮
 */
@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = {"com.clnews.dao"})
public class ClNewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClNewsApplication.class, args);
    }

}

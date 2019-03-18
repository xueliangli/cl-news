package com.clnews.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: cl-news
 * @description:
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-15 11:30
 **/
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String index() {
        return "Hello Spring Boot 2.0!";
    }

    @RequestMapping("docker")
    public String index1() {
        return "Hello Docker!";
    }


    @Value("${com.clnews.s1}")
    private  String name;
    @Value("${com.clnews.s2}")
    private  String want;

    @RequestMapping("/")
    public String hexo(){
        return name+","+want;
    }
}

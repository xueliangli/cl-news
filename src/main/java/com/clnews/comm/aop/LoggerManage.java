package com.clnews.comm.aop;

import java.lang.annotation.*;

/**
 * @program: demo
 * @description:
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-04-03 19:14
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoggerManage {
    String description();
}

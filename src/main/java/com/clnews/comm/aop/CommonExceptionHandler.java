package com.clnews.comm.aop;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: cl-news
 * @description: 统一异常处理类
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-04-05 20:15
 **/
@ControllerAdvice
public class CommonExceptionHandler {

    /**
     *  拦截Exception类的异常
     * @param e
     * @return
     *
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String,Object> exceptionHandler(Exception e){
        Map<String,Object> result = new HashMap<>();
        result.put("respCode", "9999");
        result.put("respMsg", e.getMessage());
        //正常开发中，可创建一个统一响应实体，如CommonResp
        return result;
    }
}

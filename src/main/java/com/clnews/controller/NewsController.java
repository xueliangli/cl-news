package com.clnews.controller;

import com.clnews.service.NewsService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: cl-news
 * @description:
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-19 14:13
 **/
@RestController
@RequestMapping("/news")
public class NewsController {

    /**
     * @description: TODO：logger 有什么作用
     */
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;

    @RequestMapping("/toutiao")
    @ResponseStatus(HttpStatus.OK)
    public Object toutiaoNews() {
        Map ret = Maps.newHashMap();
        newsService.pullNews();
        ret.put("news", "toutiao");
        return ret;
    }

}

package com.clnews.controller;

import com.clnews.processor.ToutiaoNewsPuller;
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
 * Created with cl-news
 * Created By lxc
 * Date: 2019-03-18
 *
 * @author lxc
 */
@RestController
@RequestMapping("/news")
public class NewsController {

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

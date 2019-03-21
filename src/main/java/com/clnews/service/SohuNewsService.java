package com.clnews.service;

import com.clnews.domain.News;

import java.util.List;

/**
 * @program: cl-news
 * @description: 搜狐爬虫的服务
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-21 18:59
 **/
public interface SohuNewsService {
    /**
     * @description: 返回新闻条数的方法
     */
    int insertSelective(News record);

    /**
     * @description: 显示所有新闻列表
     */
    List<News> listNewsAll();

    /**
     * @description: 拉取新闻
     */
    void pullNews();
}

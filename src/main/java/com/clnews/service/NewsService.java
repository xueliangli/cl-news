package com.clnews.service;

import com.clnews.domain.News;

import java.util.List;

/**
 * @program: cl-news
 * @description: 新闻爬虫服务接口
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-19 14:18
 **/
public interface NewsService {
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
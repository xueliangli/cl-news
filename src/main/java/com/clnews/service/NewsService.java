package com.clnews.service;

import com.clnews.domain.News;

import java.util.List;

/**
 * Created with cl-news
 * Created By lxc
 * Date: 2019-03-18
 */
public interface NewsService {

    int insertSelective(News record);

    List<News> listNewsAll();

    void pullNews();
}

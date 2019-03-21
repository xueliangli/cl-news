package com.clnews.service.impl;

import com.clnews.constant.Constant;
import com.clnews.dao.NewsMapper;
import com.clnews.domain.News;
import com.clnews.processor.SohuNewsPuller;
import com.clnews.service.SohuNewsService;
import com.google.common.collect.Lists;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: cl-news
 * @description:
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-21 19:01
 **/
@Service
public class SohuNewsServiceImpl implements SohuNewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private SohuNewsPuller sohuNewsPuller;



    @Override
    public int insertSelective(News record) {
        return newsMapper.insertSelective(record);
    }

    @Override
    public List<News> listNewsAll() {
        List<News> newsList = newsMapper.listNewsAll(Constant.START, Constant.END);
        if (null == newsList || newsList.isEmpty()) {
            return Lists.newArrayList();
        }
        return newsList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void pullNews() {
        List<News> newsList = sohuNewsPuller.pullNews();
        if (null == newsList || newsList.isEmpty()) {
            logger.info("【今日头条】拉取内容为空不需要插入!");
            return;
        }
        for (News news : newsList) {
            newsMapper.insertSelective(news);
        }
    }

}


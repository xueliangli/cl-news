package com.clnews.service.impl;

import com.clnews.constant.Constant;
import com.clnews.dao.NewsMapper;
import com.clnews.domain.News;
import com.clnews.enums.SourceEnum;
import com.clnews.processor.SohuNewsPuller;
import com.clnews.processor.ToutiaoNewsPuller;
import com.clnews.service.NewsService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用该注解 spring 可以将此类扫描为服务
 *
 * @author 李学亮
 */
@Service
public class NewsServiceImpl implements NewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

    @Autowired
    private NewsMapper newsMapper;

    /**
     * @description: 注入头条和搜狐的拉取器
     */
    @Autowired
    private ToutiaoNewsPuller toutiaoNewsPuller;

    @Autowired
    private SohuNewsPuller sohuNewsPuller;

    //    /**
//     * @description: 线程工厂，需要对线程做一些修饰的时候使用，比如修改线程名称
//     */
//    static class TestThreadFactory implements ThreadFactory {
//        private AtomicInteger threadNum = new AtomicInteger(0);
//
//        @Override
//        public Thread newThread(Runnable r) {
//            Thread t = new Thread(r);
//            t.setName("----- 拉取新闻的线程 ------ " + threadNum.incrementAndGet());
//            return t;
//        }
//    }
//
//    private TestThreadFactory testThreadFactory = new TestThreadFactory();
//    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3,
//            10,
//            5,
//            TimeUnit.SECONDS,
//            new ArrayBlockingQueue<>(
//                    1),
//            testThreadFactory);
    private static final ExecutorService executorService = Executors.newFixedThreadPool(5);

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


        for (final SourceEnum sourceEnum : SourceEnum.values()) {
            if (sourceEnum.key.equalsIgnoreCase(SourceEnum.TOU_TIAO.key)) {
                executorService.submit(new TtPullTask(sourceEnum));
            }
            if (sourceEnum.key.equalsIgnoreCase(SourceEnum.SO_HU.key)) {
                executorService.submit(new ShPullTask(sourceEnum));
            }
        }
    }

    public class TtPullTask implements Runnable {

        private SourceEnum sourceEnum;

        TtPullTask(SourceEnum sourceEnum) {
            this.sourceEnum = sourceEnum;
        }

        @Override
        public void run() {
            List<News> newsList = toutiaoNewsPuller.pullNews();
            if (null == newsList || newsList.isEmpty()) {
                logger.info("【{}】拉取内容为空不需要插入!", sourceEnum.name);
                return;
            }
            for (News news : newsList) {
                newsMapper.insertSelective(news);
            }
        }
    }

    public class ShPullTask implements Runnable {

        private SourceEnum sourceEnum;

        ShPullTask(SourceEnum sourceEnum) {
            this.sourceEnum = sourceEnum;
        }

        @Override
        public void run() {
            // TODO: 2019-03-22 添加你写的搜狐的拉取器
            List<News> newsList = sohuNewsPuller.pullNews();
            if (null == newsList || newsList.isEmpty()) {
                logger.info("【{}】拉取内容为空不需要插入!", sourceEnum.name);
                return;
            }
            for (News news : newsList) {
                newsMapper.insertSelective(news);
            }
        }
    }
}
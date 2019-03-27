package com.clnews.processor;

import com.clnews.domain.News;
import com.clnews.enums.SourceEnum;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static com.clnews.constant.Constant.*;

/**
 * @program: cl-news
 * @description: 凤凰网的新闻爬虫
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-27 16:21
 **/
@Component("ifengNewsPuller")
public class IfengNewsPuller extends NewsPuller {

    private static final Logger logger = LoggerFactory.getLogger(IfengNewsPuller.class);
    @Value("${news.ifeng.url}")
    private String url;

    @Override
    public List<News> pullNews() {
        logger.info("【凤凰网】开始拉取新闻！");
        // 1.获取首页
        Document document;
        try {
            document = getHtmlFromUrl(url, true);
        } catch (Exception e) {
            logger.error("==============获取凤凰首页失败: {} =============", url);
            e.printStackTrace();
            return null;
        }
        // 2.jsoup获取新闻<a>标签
        Elements elements = document.select("a[url~=/group/.*]:not(.comment)");

        // 3.从<a>标签中抽取基本信息，封装成news
        Date now = new Date();
        HashSet<News> newsSet = new HashSet<>();
        for (Element element : elements) {
            String href = element.attr("url");
            String title = element.attr("title");
            String image = StringUtils.isNotBlank(element.select(HTML_IMG).attr(HTML_SRC))
                    ? HTTPS_PREFIX + element.select(HTML_IMG).attr(HTML_SRC) : element.select(HTML_IMG).attr(HTML_SRC);
            News n = new News();
            n.setSource(SourceEnum.FENG_HUANG.key);
            n.setSourceName(SourceEnum.FENG_HUANG.name);
            n.setTitle(title);
            n.setContentUrl(href);
            n.setImageUrl(image);
            n.setCreateDate(now);
            n.setUpdateDate(now);
            newsSet.add(n);
        }
        // 4.根据新闻url访问新闻，获取新闻内容
        newsSet.parallelStream().forEach(news -> {
            logger.info("开始抽取凤凰新闻《{}》内容：{}", news.getTitle());
            Document newsHtml;
            try {
                newsHtml = getHtmlFromUrl(news.getContentUrl(), false);
                Elements newsContent = newsHtml.select("div#main_content");
                if (newsContent.isEmpty()) {
                    newsContent = newsHtml.select("div#yc_con_txt");
                }
                if (newsContent.isEmpty()) {
                    return;
                }
                String content = newsContent.toString();
                String title = newsContent.select("div.text-title").select("h1").text();
                news.setContent(content);
                logger.info("抽取凤凰新闻《{}》成功！", news.getTitle());
            } catch (Exception e) {
                logger.error("凤凰新闻抽取失败:{}", news.getContentUrl());
                e.printStackTrace();
            }
        });
        logger.info("凤凰新闻抽取完成！");

        List<News> newsList = Lists.newArrayList();
        newsList.addAll(newsSet);
        return newsList;
    }
}

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
 * @description: 爬取搜狐网的爬虫
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-20 22:24
 **/
@Component("sohuNewsPuller")
public class SohuNewsPuller extends NewsPuller {

    private static final Logger logger = LoggerFactory.getLogger(SohuNewsPuller.class);
    @Value("${news.sohu.url}")
    private String url;

    @Override
    public List<News> pullNews() {
        logger.info("【搜狐网】开始拉取搜狐新闻！");
        // 1.获取首页
        Document html;
        try {
            html = getHtmlFromUrl(url, false);
        } catch (Exception e) {
            logger.error("==============获取搜狐首页失败: {}=============", url);
            e.printStackTrace();
            return null;
        }
        // 2.jsoup获取新闻<a>标签
        Elements newsATags = html.select("div.focus-news")
                .select("div.list16")
                .select("li")
                .select("a");

        // 3.从<a>标签中抽取基本信息，封装成news
        Date now = new Date();
        HashSet<News> newsSet = new HashSet<>();
        for (Element element : newsATags) {
            String href = element.attr("href");
            String title = element.attr("title");
            String image = StringUtils.isNotBlank(element.select(HTML_IMG).attr(HTML_SRC))
                    ? HTTPS_PREFIX + element.select(HTML_IMG).attr(HTML_SRC) : element.select(HTML_IMG).attr(HTML_SRC);
            News n = new News();
            n.setSource(SourceEnum.SO_HU.key);
            n.setSourceName(SourceEnum.SO_HU.name);
            n.setTitle(title);
            n.setContentUrl(href);
            n.setImageUrl(image);
            n.setCreateDate(now);
            n.setUpdateDate(now);
            newsSet.add(n);
        }
        // 4.根据新闻url访问新闻，获取新闻内容
        newsSet.forEach(news -> {
            logger.info("【搜狐网】开始抽取搜狐新闻内容：{}", news.getTitle());
            Document newsHtml = null;
            try {
                newsHtml = getHtmlFromUrl(news.getContentUrl(), true);
                Element newsContent = newsHtml.select("div#article-container")
                        .select("div.main")
                        .select("div.text")
                        .first();
                String title = newsContent.select("div.text-title").select("h1").text();
                String content = newsContent.select("article.article").first().toString();

                news.setTitle(title);
                news.setContent(content);
                logger.info("【搜狐网】抽取搜狐新闻《{}》成功！", news.getTitle());
            } catch (Exception e) {
                logger.error("【搜狐网】新闻抽取失败:{}", news.getTitle());
                e.printStackTrace();
            }
        });

        List<News> newsList = Lists.newArrayList();
        newsList.addAll(newsSet);
        return newsList;

    }
}

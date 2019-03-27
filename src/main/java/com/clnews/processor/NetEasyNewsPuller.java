package com.clnews.processor;

import com.clnews.domain.News;
import com.clnews.enums.SourceEnum;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
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
 * @description: 网易新闻的爬虫主体
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-27 16:50
 **/
@Component("netEasyNewsPuller")
public class NetEasyNewsPuller extends NewsPuller {

    private static final Logger logger = LoggerFactory.getLogger(NetEasyNewsPuller.class);
    @Value("${news.neteasy.url}")
    private String url;
    @Override
    public List<News> pullNews() {
        logger.info("开始拉取网易热门新闻！");
        // 1.获取首页
        Document html;
        try {
            html = getHtmlFromUrl(url, false);
        } catch (Exception e) {
            logger.error("==============获取网易新闻首页失败: {}=============", url);
            e.printStackTrace();
            return null;
        }
        // 2.jsoup获取指定标签
        Elements newsA = html.select("div.mod_focus")
                .select("div.f_body")
                .select("ul")
                .select("a");

        // 3.从标签中抽取信息，封装成news
        Date now = new Date();
        HashSet<News> newsSet = new HashSet<>();
        newsA.forEach(element -> {
            String href = element.attr("href");
            String title = element.attr("title");
            String image = StringUtils.isNotBlank(element.select(HTML_IMG).attr(HTML_SRC))
                    ? HTTPS_PREFIX + element.select(HTML_IMG).attr(HTML_SRC) : element.select(HTML_IMG).attr(HTML_SRC);
            News n = new News();
            n.setSource(SourceEnum.WANG_YI.key);
            n.setSourceName(SourceEnum.WANG_YI.name);
            n.setTitle(title);
            n.setContentUrl(href);
            n.setImageUrl(image);
            n.setCreateDate(now);
            n.setUpdateDate(now);
            newsSet.add(n);
        });

        // 4.根据url访问新闻，获取新闻内容
        newsSet.forEach(news -> {
            logger.info("开始抽取新闻内容：{}", news.getContentUrl());
            Document newsHtml;
            try {
                newsHtml = getHtmlFromUrl(news.getContentUrl(), false);
                Elements newsContent = newsHtml.select("div#endText");
                Elements titleP = newsContent.select("p.otitle");
                String title = titleP.text();
                title = title.substring(5, title.length() - 1);

                news.setTitle(title);
                news.setContent(newsContent.toString());
                logger.info("抽取网易新闻《{}》成功！", news.getTitle());
            } catch (Exception e) {
                logger.error("新闻抽取失败:{}", news.getContentUrl());
                e.printStackTrace();
            }
        });
        logger.info("网易新闻拉取完成！");

        List<News> newsList = Lists.newArrayList();
        newsList.addAll(newsSet);
        return newsList;
    }
}

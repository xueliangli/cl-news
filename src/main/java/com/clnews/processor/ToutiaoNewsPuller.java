package com.clnews.processor;

import com.clnews.domain.News;
import com.clnews.enums.SourceEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.clnews.constant.Constant.*;

/**
 * Created with cl-news
 * Created By lxc
 * Date: 2019-03-18
 *
 * @author lxc
 */
@Component("toutiaoNewsPuller")
public class ToutiaoNewsPuller extends NewsPuller {

    private static final Logger logger = LoggerFactory.getLogger(ToutiaoNewsPuller.class);

    @Value("${news.toutiao.main_url}")
    private String mainUrl;

    @Value("${news.toutiao.news_hot_url}")
    private String newsHotUrl;

    @Override
    public List<News> pullNews() {
        logger.info("【今日头条】开始拉取今日头条热门新闻！");
        Document document;
        try {
            document = getHtmlFromUrl(newsHotUrl, true);
        } catch (Exception e) {
            logger.error("【今日头条】获取今日头条热门新闻主页失败！");
            e.printStackTrace();
            return null;
        }
        if (document == null) {
            logger.info("【今日头条】获取今日头条热门新闻主页内容为空！");
            return null;
        }
        Map<String, News> newsMap = Maps.newHashMap();
        Date now = new Date();
        Elements elements = document.select("a[href~=/group/.*]:not(.comment)");
        for (Element element : elements) {
            logger.info("【今日头条】标签:{}", element);
            String href = mainUrl + element.attr(HTML_HREF);
            String title = StringUtils.isNotBlank(element.select(HTML_P).text()) ? element.select(HTML_P).text()
                    : element.text();
            String image = StringUtils.isNotBlank(element.select(HTML_IMG).attr(HTML_SRC))
                    ? HTTPS_PREFIX + element.select(HTML_IMG).attr(HTML_SRC) : element.select(HTML_IMG).attr(HTML_SRC);

            News news = newsMap.get(href);
            if (news == null) {
                News n = new News();
                n.setSource(SourceEnum.TOU_TIAO.key);
                n.setSourceName(SourceEnum.TOU_TIAO.name);
                n.setTitle(title);
                n.setContentUrl(href);
                n.setImageUrl(image);
                n.setCreateDate(now);
                n.setUpdateDate(now);
                newsMap.put(href, n);
            } else {
                if (element.hasClass(HTML_IMG_WRAP)) {
                    news.setImageUrl(image);
                } else if (element.hasClass(HTML_TITLE)) {
                    news.setTitle(title);
                }
            }
        }
        logger.info("【今日头条】今日头条热门新闻标题拉取完成!");

        logger.info("【今日头条】开始拉今日头条取热门新闻内容...");
        newsMap.values().parallelStream().forEach(news -> {
            logger.info("【今日头条】===================={}====================", news.getTitle());
            Document contentHtml;
            try {
                contentHtml = getHtmlFromUrl(news.getContentUrl(), true);
            } catch (Exception e) {
                logger.error("【今日头条】获取新闻《{}》内容失败！", news.getTitle());
                return;
            }
            Elements scripts = contentHtml.getElementsByTag(ELEMENTS_SCRIPT);
            scripts.forEach(script -> {
                String regex = "articleInfo: \\{\\s*[\\n\\r]*\\s*title: '.*',\\s*[\\n\\r]*\\s*content: '(.*)',";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(script.toString());
                if (matcher.find()) {
                    String content = matcher.group(1)
                            .replace("<", "<")
                            .replace(">", ">")
                            .replace("\"", "\"")
                            .replace("=", "=");
                    logger.info("【今日头条】 内容: {}", content);
                    news.setContent(content);
                }
            });
        });
        logger.info("【今日头条】今日头条热门新闻内容拉取完成!");


        if (newsMap == null) {
            logger.info("【今日头条】今日头条拉取内容为空!");
            return null;
        }

        List<News> newsList = Lists.newArrayList();
        newsMap.forEach((k, v) -> {
            newsList.add(v);
        });
        return newsList;
    }
}

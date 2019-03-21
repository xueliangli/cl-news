package com.clnews.processor;

import com.clnews.domain.News;
import com.clnews.enums.SourceEnum;
import com.clnews.utils.SslUtils;
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
 * @program: cl-news
 * @description: 拉取头条新闻的实现代码
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-19 14:31
 **/
@Component("toutiaoNewsPuller")
public class ToutiaoNewsPuller extends NewsPuller {

    private static final Logger logger = LoggerFactory.getLogger(ToutiaoNewsPuller.class);

    @Value("${news.toutiao.main_url}")
    private String mainUrl;

    @Value("${news.toutiao.news_hot_url}")
    private String newsHotUrl;

    @Override
    public List<News> pullNews() {
        //打印日志信息
        logger.info("【今日头条】开始拉取今日头条热门新闻！");
        Document document;
        try {
            //1、通过 url 获取 html
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

        //2、解析 html 页面并将各部分存放到 news 实体相应的字段内
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
        //3、将信息存放在 map 中
        newsMap.values().parallelStream().forEach(news -> {
            logger.info("【今日头条】===================={}====================", news.getTitle());
            Document contentHtml;
            try {
                //解决站点不信任
                SslUtils.ignoreSsl();
                contentHtml = getHtmlFromUrl(news.getContentUrl(), true);
            } catch (Exception e) {
                logger.error("【今日头条】获取新闻《{}》内容失败！", news.getTitle());
                return;
            }
            //todo:空指针异常;缺少安全证书时出现的异常后自动消失
            //4、通过正则表达式对 script 标签进行解析
            Elements scripts = contentHtml.getElementsByTag(ELEMENTS_SCRIPT);
            scripts.forEach(script -> {
                //正则表达式匹配页面标签
                String regex1 = "articleInfo: \\{\\s*[\\n\\r]*\\s*title: '.*',\\s*[\\n\\r]*\\s*content: '(.*)',";
                Pattern pattern = Pattern.compile(regex1);
                Matcher matcher = pattern.matcher(script.toString());
                if (matcher.find()) {
                    String content = matcher.group(1)
                            .replace("<", "<")
                            .replace(">", ">")
                            .replace("\"", "\"")
                            .replace("=", "=");
                    //去除非中文字符
//                    String regex2 = "[^\\u4e00-\\u9fa5]";
//                    content = content.replaceAll(regex2  , "");
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

        //5、将获得数据以 list 集合形式返回
        List<News> newsList = Lists.newArrayList();
        newsMap.forEach((k, v) -> {
            newsList.add(v);
        });
        return newsList;
    }
}

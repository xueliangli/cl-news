package com.clnews.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @program: cl-news
 * @description:
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-17 19:38
 **/
public class CSDNTry implements PageProcessor {

    // 设置csdn用户名
    private static String username = "sinat_37740277";
    // 共抓取到的文章数量
    private static int size = 0;

    // 抓取网站的相关配置，包括：编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public Site getSite() {
        return site;
    }
    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        // 列表页
        if (!page.getUrl().regex("http://blog\\.csdn\\.net/" + username + "/article/details/\\d+").match()) {
            // 添加所有文章页
            page.addTargetRequests(page.getHtml()
                    // 限定文章列表获取区域
                    .xpath("//div[@id='article_list']").links()
                    .regex("/" + username + "/article/details/\\d+")
                    // 巧用替换给把相对url转换成绝对url
                    .replace("/" + username + "/", "http://blog.csdn.net/" + username + "/")
                    .all());
            // 添加其他列表页
            page.addTargetRequests(page.getHtml()
                    // 限定其他列表页获取区域
                    .xpath("//div[@id='papelist']").links()
                    .regex("/" + username + "/article/list/\\d+")
                    // 巧用替换给把相对url转换成绝对url
                    .replace("/" + username + "/", "http://blog.csdn.net/" + username + "/")
                    .all());
            // 文章页
        } else {
            size++;// 文章数量加1
            // 用CsdnBlog类来存抓取到的数据，方便存入数据库
            CsdnBlog csdnBlog = new CsdnBlog();
            // 设置编号
            csdnBlog.setId(Integer.parseInt(
                    page.getUrl().regex("http://blog\\.csdn\\.net/" + username + "/article/details/(\\d+)").get()));
            // 设置标题，！ 第二个span
            csdnBlog.setTitle(
                    page.getHtml().xpath("//div[@class='article_title']//span[@class='link_title']/a/text()").get());
            // 设置日期
            csdnBlog.setDate(
                    page.getHtml().xpath("//div[@class='article_r']/span[@class='link_postdate']/text()").get());
            // 设置标签（可以有多个，需要分割）
            csdnBlog.setTags(listToString(page.getHtml()
                    .xpath("//div[@class='article_l']/span[@class='link_categories']/a/allText()").all()));
            // 设置类别（可以有多个，需要分割）
            csdnBlog.setCategory(
                    listToString(page.getHtml().xpath("//div[@class='category_r']/label/span/text()").all()));
            // 设置阅读人数
            csdnBlog.setView(Integer.parseInt(page.getHtml().xpath("//div[@class='article_r']/span[@class='link_view']")
                    .regex("(\\d+)人阅读").get()));
            // 设置评论人数
            csdnBlog.setComments(Integer.parseInt(page.getHtml()
                    .xpath("//div[@class='article_r']/span[@class='link_comments']").regex("\\((\\d+)\\)").get()));
            // 设置是否原创
            csdnBlog.setCopyright(page.getHtml().regex("bog_copyright").match() ? 1 : 0);
            // 把对象存入数据库
            new CSDNDao().add(csdnBlog);
            // 把对象输出控制台
            System.out.println(csdnBlog);
        }

    }

    // 把list转换为string，用,分割
    private static String listToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    public static void main(String[] args) {
        long startTime, endTime;
        System.out.println("【爬虫开始】请耐心等待一大波数据到你碗里来...");
        startTime = System.currentTimeMillis();
        // 从用户博客首页开始抓，开启5个线程，启动爬虫
        Spider.create(new CSDNTry()).addUrl("http://blog.csdn.net/" + username).thread(5).run();
        endTime = System.currentTimeMillis();
        System.out.println("【爬虫结束】共抓取" + size + "篇文章，耗时约" + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查收！");
    }
}


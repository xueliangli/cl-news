package com.clnews.processor;

import com.clnews.domain.News;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created with cl-news
 * Created By lxc
 * Date: 2019-03-18
 *
 * @author lxc
 */
public abstract class NewsPuller {

    private static final int TIME_OUT = 10000;

    /**
     * 统一抽象拉取新闻点的接口
     */
    abstract List<News> pullNews();

    /**
     * 获取页面内容
     *
     * @param url
     * @param useHtmlUnit
     * @return
     * @throws Exception
     */
    public Document getHtmlFromUrl(String url, boolean useHtmlUnit) throws Exception {
        //模拟火狐浏览器
        if (!useHtmlUnit) {
            return Jsoup.connect(url).userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)").get();
        } else {
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setActiveXNative(false);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setTimeout(TIME_OUT);
            try {
                HtmlPage htmlPage = webClient.getPage(url);
                webClient.waitForBackgroundJavaScript(TIME_OUT);
                String htmlString = htmlPage.asXml();
                return Jsoup.parse(htmlString);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                webClient.close();
            }
        }
    }

}
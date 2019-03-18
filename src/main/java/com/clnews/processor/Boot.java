package com.clnews.processor;

import cn.wanghaomiao.seimi.annotation.EnableSeimiCrawler;
import cn.wanghaomiao.seimi.core.Seimi;

/**
 * @program: cl-news
 * @description:
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-17 20:57
 **/
@EnableSeimiCrawler
public class Boot {
    public static void main(String[] args){
        Seimi s = new Seimi();
        s.goRun("basic");
    }
}

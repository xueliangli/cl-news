package com.clnews.enums;

/**
 * @program: cl-news
 * @description:
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-19 14:36
 **/
public enum SourceEnum {
    /**
     * @description: 头条作为源
     */
    TOU_TIAO("TOU_TIAO", "今日头条"), SOU_HU("SOU_HU", "搜狐");

    public final String key;

    public final String name;
    SourceEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }

}

package com.clnews.enums;

/**
 * Created with cl-news
 * Created By lxc
 * Date: 2019-03-18
 *
 * @author lxc
 */
public enum SourceEnum {

    TOU_TIAO("TOU_TIAO", "今日头条");

    public final String key;

    public final String name;

    SourceEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }
}

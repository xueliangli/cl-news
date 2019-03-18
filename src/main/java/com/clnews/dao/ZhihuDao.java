package com.clnews.dao;

import com.clnews.domain.ZhihuUser;

/**
 * @program: cl-news
 * @description:
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-17 21:36
 **/
public interface ZhihuDao {
    /**
     * 保存用户信息
     * @param user
     * @return
     */
    public int saveUser(ZhihuUser user);
}

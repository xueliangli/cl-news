package com.clnews.dao;

import com.clnews.domain.News;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: cl-news
 * @description:
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-16 21:33
 **/
@Repository
public interface NewsMapper {

    int insertSelective(News record);

    List<News> listNewsAll(@Param("start") Integer start, @Param("end") Integer end);
}

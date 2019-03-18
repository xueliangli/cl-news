package com.clnews.dao;

import com.clnews.domain.AppMessage;
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
public interface AppMessageMapper {


    int deleteByPrimaryKey(String id);


    int insert(AppMessage record);

    int insertSelective(AppMessage record);

    AppMessage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AppMessage record);

    int updateByPrimaryKey(AppMessage record);

    List<AppMessage> selectAll();

    List<AppMessage> getMessById(String id);
}

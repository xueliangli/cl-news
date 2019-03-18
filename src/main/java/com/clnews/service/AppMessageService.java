package com.clnews.service;
import java.util.ArrayList;
import java.util.List;

import com.clnews.domain.AppMessage;
import com.clnews.dao.AppMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @program: cl-news
 * @description:
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-16 21:42
 **/

@Service
public class AppMessageService {

    private final AppMessageMapper appMessageMapper;

    @Autowired
    public AppMessageService(AppMessageMapper appMessageMapper) {
        this.appMessageMapper = appMessageMapper;
    }

    public List<AppMessage> getMessage(){
        List<AppMessage> list = new ArrayList<>();
        list.add(appMessageMapper.selectByPrimaryKey("xtt"));
        return list;
    }

    public List<AppMessage> getAllMessage(){
        List<AppMessage> list = appMessageMapper.selectAll();
        return list;
    }

    public int addMessage(AppMessage appMessage) {
        return appMessageMapper.insert(appMessage);
    }

    public List<AppMessage> getMessageById(String id) {
        return appMessageMapper.getMessById(id);
    }

    public int delMessage(String id) {
        return appMessageMapper.deleteByPrimaryKey(id);
    }

}

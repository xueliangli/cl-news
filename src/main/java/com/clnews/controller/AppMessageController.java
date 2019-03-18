package com.clnews.controller;

import java.util.List;

import com.clnews.domain.AppMessage;
import com.clnews.service.AppMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: cl-news
 * @description:
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-16 21:43
 **/

@RestController
@RequestMapping("/appmessage")
public class AppMessageController {

    private final AppMessageService service;

    @Autowired
    public AppMessageController(AppMessageService service) {
        this.service = service;
    }

    @RequestMapping("/getThree")
    public List<AppMessage> getThreeForMessage() {

        List<AppMessage> list = service.getMessage();
        return list;
    }

    @RequestMapping("/getAll")
    public List<AppMessage> getAllMessage() {

        List<AppMessage> list = service.getAllMessage();
        int num = list.size();
        if (null != list && num > 3) {
            for (int i = 0; i < num - 3; i++) {
                list.remove(0);
            }
        }
        return list;
    }

    @RequestMapping("/getByID")
    public List<AppMessage> getMessageById(@RequestParam("id") String id) {
        List<AppMessage> list = service.getMessageById(id);
        int num = list.size();
        if (null != list && num > 5) {
            for (int i = 0; i < num - 5; i++) {
                list.remove(0);
            }
        }
        return list;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public int addMessage(@RequestBody AppMessage appMessage) {
        return service.addMessage(appMessage);
    }

    @RequestMapping(value = "/delMessageById", method = RequestMethod.POST)
    public int delMessageById(@RequestParam("id") String id) {
        return service.delMessage(id);
    }
}

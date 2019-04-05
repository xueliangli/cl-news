package com.clnews.controller;

import com.clnews.comm.aop.LoggerManage;
import com.clnews.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program: demo
 * @description:
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-04-03 16:47
 **/
@Controller
@RequestMapping("/")
public class IndexController extends BaseController {
    /**
     * 首页
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @LoggerManage(description = "首页")
    public String index(Model model) {
        model.addAttribute("collector", "");
        User user = super.getUser();
        if (null != user) {
            model.addAttribute("user", user);
        }
        return "index";
    }

    /**
     * 跳转到个人信息的页面
     */
    @RequestMapping(value = "/introduce", method = RequestMethod.GET)
    @LoggerManage(description = "信息")
    public String introduce(Model model) {
        return "introduce";
    }

    /**
     * 跳转到时间线的页面
     */
    @RequestMapping(value = "/timeline", method = RequestMethod.GET)
    @LoggerManage(description = "信息")
    public String timeline(Model model) {
        return "timeline";
    }
}

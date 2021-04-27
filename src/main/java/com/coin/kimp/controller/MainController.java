package com.coin.kimp.controller;

import javax.annotation.Resource;
import com.coin.kimp.service.ChatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @Resource(name = "chatService")
    ChatService testService;

    @RequestMapping("/")
    public String index() {

        return "kimp";
    }
}
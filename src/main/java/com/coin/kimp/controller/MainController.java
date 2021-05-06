package com.coin.kimp.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.coin.kimp.service.ChatService;
import com.coin.kimp.service.UpbitApiService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @Resource(name = "chatService")
    ChatService testService;
    @Resource(name = "upbitApiService")
    UpbitApiService upbitApiService;

    @RequestMapping("/")
    public String index(HttpServletRequest request) throws Exception {

        request.getSession(true);
        return "kimp";
    }

    @RequestMapping("/showRaceCoin")
    @CrossOrigin(origins = { "http://localhost:8080", "https://localhost:8443", "https://dongjun.iptime.org:8443",
            "http://dongjun.iptime.org:8080" })
    public @ResponseBody Map<String, List<Map<String, String>>> showRaceCoin() throws Exception {
        return upbitApiService.showRaceCoin();
    }
}
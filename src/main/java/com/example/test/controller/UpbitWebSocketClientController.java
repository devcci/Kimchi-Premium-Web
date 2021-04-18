package com.example.test.controller;

import javax.annotation.Resource;

import com.example.test.service.TestService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UpbitWebSocketClientController {

    @Resource(name = "testService")
    TestService testService;

    @RequestMapping("/")
    public String index() {
        return "kimp";
    }
}
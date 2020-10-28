package com.example.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.example.test.service.TestService;
import com.example.test.vo.TestVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HelloController {

    @Resource(name="testService")
    TestService testService;

    // @ModelAttribute("testa")
    // public Map<String, String> comm(){
    //     Map<String, String> testa = new HashMap<String,String>();
    //     testa.put("ha", "aa");
    //     return testa;
    // }

    @ModelAttribute("testa")
    public List<TestVo> comm(){

        List<TestVo> testa = testService.selectListMember();
        return testa;
    }

    @RequestMapping("member")
    public String showMember(ModelMap model) throws Exception {
        List<TestVo> lst = testService.selectListMember();

        Map<String, String> map = new HashMap<String,String>();
        map.put("name", "1");
        map.put("age", "22");

        
        
        
        model.addAttribute("map",map);
        model.addAttribute("memberList",lst);
        return "home";
    }
}
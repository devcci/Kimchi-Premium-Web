package com.example.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.example.test.service.TestService;
import com.example.test.vo.TestVo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

// @component 사용 가능하지만 명시적으로 표시
@Controller
public class HelloController {

    // @Autowired 사용 가능하지만 명시적으로 자바 Class(Bean)을 주입
    @Resource(name = "testService")
    TestService testService;

    @RequestMapping("/")
    public String index(){
        return "kimp";
    }

    // map 이용한 임시 VO 활용한 ModelAttribute 활용
    @ModelAttribute("testa")
    public List<Map<String, String>> comm() {
        Map<String, String> testmap = new HashMap<String, String>();
        testmap.put("ha", "cc");

        List<Map<String, String>> testa = new ArrayList<Map<String, String>>();
        testa.add(testmap);
        return testa;
    }

    // VO 활용한 ModelAttribute 활용 예시
    // RequestMapping 어노테이션 메소드보다 먼저 실행되어
    // 코드에서 공통적으로 필요한 VO를 항시 사용 가능
    // @ModelAttribute("testa")
    // public List<TestVo> comm(){

    // List<TestVo> testa = testService.selectListMember();
    // return testa;
    // }

    @RequestMapping("member")
    public String showMember(ModelMap model) throws Exception {
        // DB로부터 데이터 가져와서 VO 자동 매칭
        List<TestVo> lst = testService.selectListMember();
        // map으로 임시 VO 생성
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "1");
        map.put("age", "22");

        model.addAttribute("memberList", lst);
        model.addAttribute("map", map);
        return "home";
    }

}
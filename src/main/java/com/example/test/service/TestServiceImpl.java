package com.example.test.service;

import java.util.List;

import javax.annotation.Resource;

import com.example.test.dao.TestMapper;
import com.example.test.vo.TestVo;

import org.springframework.stereotype.Service;

@Service("testService")
public class TestServiceImpl implements TestService {

    @Resource(name="testMapper")
    TestMapper testMapper;

    @Override
    public List<TestVo> selectListMember() {
        return testMapper.selectListMember();
    }

}
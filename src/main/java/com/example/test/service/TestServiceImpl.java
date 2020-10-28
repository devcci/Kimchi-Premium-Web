package com.example.test.service;

import java.util.List;

import com.example.test.dao.TestDao;
import com.example.test.vo.TestVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestDao testDao;

    @Override
    public List<TestVo> selectListMember() {
        return testDao.selectListMember();
    }

}
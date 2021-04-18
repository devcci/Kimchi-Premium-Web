package com.example.test.service;

import javax.annotation.Resource;

import com.example.test.mapper.TestMapper;

import org.springframework.stereotype.Service;

@Service("testService")
public class TestServiceImpl implements TestService {

	@Resource(name = "testMapper")
	TestMapper testMapper;
}
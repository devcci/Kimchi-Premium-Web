package com.coin.kimp.service.impl;

import javax.annotation.Resource;

import com.coin.kimp.mapper.ChatMapper;
import com.coin.kimp.service.ChatService;

import org.springframework.stereotype.Service;

@Service("chatService")
public class ChatServiceImpl implements ChatService {

	@Resource(name = "chatMapper")
	ChatMapper chatMapper;
}
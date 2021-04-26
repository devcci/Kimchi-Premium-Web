package com.coin.kimp.mapper;

import java.util.List;

import com.coin.kimp.vo.ChatVo;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMapper {

    public List<ChatVo> selectListMember();

}

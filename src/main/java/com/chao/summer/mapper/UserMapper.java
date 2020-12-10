package com.chao.summer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chao.summer.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

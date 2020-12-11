package com.chao.summer.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chao.summer.entity.User;
import com.chao.summer.mapper.UserMapper;
import com.chao.summer.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Chao
 * @since 2020-12-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public List<User> getAllYoungPeople() {
        return new LambdaQueryChainWrapper<>(baseMapper)
                .lt(User::getAge, 40)
                .list();
    }

    @Override
    public IPage<User> selectPage(Page<?> page, Integer age) {
        // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
        // page.setOptimizeCountSql(false);
        // 当 total 为小于 0 或者设置 setSearchCount(false) 分页插件不会进行 count 查询
//        page.setSearchCount(false);
        // 要点!! 分页返回的对象与传入的对象是同一个
        return baseMapper.selectPage(page, age);
    }
}

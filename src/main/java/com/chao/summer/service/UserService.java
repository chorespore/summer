package com.chao.summer.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chao.summer.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Chao
 * @since 2020-12-11
 */
public interface UserService extends IService<User> {
    List<User> getAllYoungPeople();

    IPage<User> selectPage(Page<?> page, Integer age);

}

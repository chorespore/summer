package com.chao.summer.controller;


import com.alibaba.druid.support.json.JSONUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chao.summer.domain.entity.User;
import com.chao.summer.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Chao
 * @since 2020-12-11
 */
@RestController
@RequestMapping("/summer/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public Map<String, Object> findById(Long id) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("id", id);
        Map<String, Object> user = userService.getMap(query);
        return user;
    }

    @GetMapping("list")
    public List<User> list() {
        List<User> users = userService.list();
        userService.list().forEach(System.out::println);
        return users;
    }

    @GetMapping("page")
    public IPage<User> pageList(Integer p, Integer size) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.lt(User::getAge, 40).orderByDesc(User::getAge, User::getId);
        IPage<User> page = userService.page(new Page<>(p, size), queryWrapper);
        IPage<Map<String, Object>> pageMaps = userService.pageMaps(new Page<>(p, size), queryWrapper);
        System.out.println(JSONUtils.toJSONString(pageMaps.getRecords()));
        return page;
    }

    @GetMapping("select")
    public IPage<User> select(Integer age, Integer p, Integer size) {
        IPage<User> page = userService.selectPage(new Page<>(p, size), age);
        return page;
    }

    @GetMapping("name")
    public List<User> findByName(String name) {
        List<User> users = userService.lambdaQuery()
                .like(User::getName, name)
                .list();
        return users;
    }

    @GetMapping("young")
    public List<User> findYoung() {
        return userService.getAllYoungPeople();
    }

    @GetMapping("age")
    public List<User> findByAge(Integer age) {
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query.ge(User::getAge, age - 5)
                .lt(User::getAge, age + 5);

        List<Integer> userId = userService.listObjs(query, o -> Integer.parseInt(o.toString()));
        System.out.println(userId);
        List<User> users = userService.list(query);
        return users;
    }

    @PostMapping("")
    public boolean create(@RequestBody User user) {
        User activeUser = new User();
        BeanUtils.copyProperties(user, activeUser);
        activeUser.setId(activeUser.getId() + 1);
        activeUser.setName(activeUser.getName() + " Active");
        activeUser.setAge(user.getAge() - 1);
        activeUser.insertOrUpdate();

        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getAge, user.getAge())
                .set(User::getAge, user.getAge() + 1)
                .setSql("email='update@chao.com'");

        User userToUpdate = new User();
        userToUpdate.setName(UUID.randomUUID().toString().substring(1, 6));
        userToUpdate.update(updateWrapper);

        return userService.saveOrUpdate(user);
    }


    @PostMapping("update")
    public boolean update(@RequestBody User user) {
        new LambdaUpdateChainWrapper<>(userService.getBaseMapper())
                .like(User::getName, "ha")
                .lt(User::getAge, 40)
                .set(User::getEmail, "8888")
                .set(User::getAge, 88)
                .update();

        User userInfo = new User();
        userInfo.setEmail("8888");
        boolean success = new LambdaUpdateChainWrapper<>(userService.getBaseMapper())
                .like(User::getName, "ha Active")
                .lt(User::getAge, 40)
                .set(User::getAge, 88)
                .update(userInfo);
        return success;
    }


    /**
     * Check whether the given {@code CharSequence} contains actual <em>text</em>.
     * <p>More specifically, this method returns {@code true} if the
     * {@code CharSequence} is not {@code null}, its length is greater than
     * 0, and it contains at least one non-whitespace character.
     * </p>
     * <pre class="code">
     * StringUtils.hasText(null) = false
     * StringUtils.hasText("") = false
     * StringUtils.hasText(" ") = false
     * StringUtils.hasText("12345") = true
     * StringUtils.hasText(" 12345 ") = true
     * </pre>
     *
     * @param ids the {@code CharSequence} to check (may be {@code null})
     * @return {@code true} if the {@code CharSequence} is not {@code null},
     * its length is greater than 0, {@link java.lang.String#charAt(int)} and it does not contain whitespace only
     * @see Character#isWhitespace
     * @deprecated As of JDK 1.1, replaced by {@link #findByAge(Integer age)}
     */
    @DeleteMapping("")
    public boolean delete(@RequestBody String[] ids) {
        return userService.removeByIds(Arrays.asList(ids));
    }

}


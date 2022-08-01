package io.github.maodua.user.api.controller;

import io.github.maodua.order.client.OrderClient;
import io.github.maodua.user.api.entity.User;
import io.github.maodua.user.api.service.IUserService;
import io.github.maodua.user.util.SecurityContextHolder;
import io.github.maodua.wrench.common.bean.Id;
import io.github.maodua.wrench.common.vo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private OrderClient orderClient;

    @RequestMapping("info")
    public Result<?> get() {
//        long id = Thread.currentThread().getId();
//        String name = Thread.currentThread().getName();
//        log.info("当前线程id：{}， 线程名称：{}", id, name);
//        Object name1 = SecurityContextHolder.get("name");
//        log.info("取：线程 {} 的值 {}", name, name1);
//        double random = Math.random();
//        SecurityContextHolder.set(name,  random);
//        log.info("存：线程 {} 的值 {}", name, random);
//        log.info("---------------------");

        String userId = SecurityContextHolder.getUserId();
        boolean login = SecurityContextHolder.isLogin();

        Id info = orderClient.info();

        log.info("是否登录 {}, 当前用户：{}", login, userId);
        User user = userService.getById(userId);
        user.setPassword(StringUtils.EMPTY);


        return Result.success(user);
    }
}

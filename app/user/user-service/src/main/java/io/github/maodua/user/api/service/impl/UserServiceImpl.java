package io.github.maodua.user.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.maodua.user.api.entity.User;
import io.github.maodua.user.api.mapper.UserMapper;
import io.github.maodua.user.api.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService{

}

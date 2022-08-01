package io.github.maodua.user.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.maodua.user.api.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

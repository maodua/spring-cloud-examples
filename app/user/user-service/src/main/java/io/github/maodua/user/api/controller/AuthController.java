package io.github.maodua.user.api.controller;

import io.github.maodua.user.api.entity.User;
import io.github.maodua.user.api.service.IUserService;
import io.github.maodua.user.api.vo.OAuth2.OAuth2Model;
import io.github.maodua.user.api.vo.OAuth2.OAuthVO;
import io.github.maodua.user.util.TokenUtil;
import io.github.maodua.user.api.vo.LoginVO;
import io.github.maodua.wrench.common.exception.MessageException;
import io.github.maodua.wrench.common.vo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @PostMapping("login")
    public Result<?> login(@RequestBody @Valid LoginVO login) {

        User user = userService.lambdaQuery().eq(User::getUsername, login.getUsername()).one();
        if (!StringUtils.equals(user.getPassword(), login.getPassword())){
            return Result.fail("账号或密码错误");
        }


        String token = TokenUtil.create(true, user.getId().toString());

        Map<String, ? extends Serializable> map = Map.of("id", user.getId(), "username", user.getUsername(), "token", token);

        return Result.success(map);
    }



    @PostMapping("register")
    public Result<?> register(@RequestBody @Valid LoginVO login) {
        if (userService.lambdaQuery().eq(User::getUsername, login.getUsername()).count() > 0) {
            return Result.fail("用户已经存在");
        }
        User user = new User();
        user.setUsername(login.getUsername());
        user.setPassword(login.getPassword());
        boolean save = userService.save(user);
        return Result.auto(save, "添加失败");
    }


    @GetMapping("token")
    public Result<?> token(OAuthVO oAuth){
        OAuth2Model oAuth2Model = OAuthVO.get(oAuth);

        switch (oAuth2Model){
            case PASSWORD:
                return Result.success(password(oAuth.getUsername(), oAuth.getPassword()));
            case REFRESH_TOKEN:
                return Result.success(refreshToken(oAuth.getRefresh_token()));
            default:
                return Result.fail();
        }
    }

    public Map<String, Object> password(String username, String password){
        User user = userService.lambdaQuery().eq(User::getUsername, username).one();
        if (!StringUtils.equals(user.getPassword(), password)){
            throw new MessageException("账号或密码错误");
        }
        String access_token = TokenUtil.create(true, user.getId().toString());
        String longToken = TokenUtil.create(false, user.getId().toString());

        return Map.of(
                "access_token", access_token,
                "refresh_token", longToken,
                "expires_in", TokenUtil.SHORT_DURATION,
                "refresh_expires_in", TokenUtil.LONG_DURATION
        );
    }

    public Map<String, Object> refreshToken(String refreshToken){
        Date expiresAt = TokenUtil.getJWT(refreshToken).getExpiresAt();
        LocalDateTime stopTime = LocalDateTime.ofInstant(expiresAt.toInstant(), ZoneId.systemDefault());

        long seconds = Duration.between(LocalDateTime.now(), stopTime).toSeconds();
        if (seconds <= 0){
            throw new MessageException("refresh_token 已经过期");
        }

        String userId = TokenUtil.getUserId(refreshToken);
        String access_token = TokenUtil.create(true, userId);


        return Map.of(
                "access_token", access_token,
                "refresh_token", refreshToken,
                "expires_in", TokenUtil.SHORT_DURATION,
                "refresh_expires_in", seconds
        );
    }

}

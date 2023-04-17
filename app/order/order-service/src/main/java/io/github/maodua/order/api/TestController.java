package io.github.maodua.order.api;


import io.github.maodua.common.security.util.ContextHolder;
import io.github.maodua.wrench.common.bean.Id;
import io.github.maodua.wrench.common.vo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("info")
    public Result<Id> info(){
        String userId = ContextHolder.getUserId();
        boolean login = ContextHolder.isLogin();


        log.info("order  - 是否登录 {}, 当前用户：{}", login, userId);

        var id = new Id();
        id.setId("1111");
        return Result.success(id);
    }


}

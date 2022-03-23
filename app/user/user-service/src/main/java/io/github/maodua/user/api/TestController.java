package io.github.maodua.user.api;


import io.github.maodua.order.client.OrderClient;
import io.github.maodua.wrench.common.bean.Id;
import io.github.maodua.wrench.common.vo.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@RefreshScope
public class TestController {

    @Value("${userxxx:默认的}")
    private String userxxx;

    @RequestMapping("get")
    public String get() {
        System.out.println(userxxx);
        return userxxx;
    }

    @Autowired
    private OrderClient orderClient;

    @GetMapping("info")
    public Result<Id> list(){
        Id info = orderClient.info();
        System.out.println(info);
        return Result.success(info);
    }



}

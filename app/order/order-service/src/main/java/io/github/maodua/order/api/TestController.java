package io.github.maodua.order.api;


import io.github.maodua.wrench.common.bean.Id;
import io.github.maodua.wrench.common.vo.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("info")
    public Result<Id> info(){
        System.out.println("--调用的我---");
        var id = new Id();
        id.setId("1111");
        return Result.success(id);
    }


}

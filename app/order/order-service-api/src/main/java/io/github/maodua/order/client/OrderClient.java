package io.github.maodua.order.client;

import io.github.maodua.wrench.common.bean.Id;
import io.github.maodua.wrench.common.vo.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "order")
public interface OrderClient {
    @GetMapping("/test/info")
    Result<Id> info_remote();

    default Id info(){
        return this.info_remote().getData();
    }
}

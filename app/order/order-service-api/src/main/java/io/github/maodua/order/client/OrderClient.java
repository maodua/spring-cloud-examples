package io.github.maodua.order.client;

import io.github.maodua.wrench.common.bean.Id;
import io.github.maodua.wrench.common.exception.MessageException;
import io.github.maodua.wrench.common.vo.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "order")
public interface OrderClient {
    @GetMapping("/test/info")
    Result<Id> info_remote();

    default Id info(){
        Result<Id> result = this.info_remote();
        if (!result.isSuccess()){
            throw new  MessageException("调用order服务出错");
        }
        return result.getData();
    }
}

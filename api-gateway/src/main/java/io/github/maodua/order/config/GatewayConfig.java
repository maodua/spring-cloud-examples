package io.github.maodua.order.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import io.github.maodua.wrench.common.vo.result.Result;
import io.github.maodua.wrench.common.vo.result.ResultCode;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.annotation.PostConstruct;

@Configuration
public class GatewayConfig {
    /**
     * 限流返回错误信息
     */
    @PostConstruct
    public void initBlockHandlers() {
        BlockRequestHandler blockHandler = (serverWebExchange, throwable) -> {
            Result<String> fail = Result.fail("访问人数过多，请稍后再试！", ResultCode.LIMITING.getCode());
            return ServerResponse
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromValue(fail));
        };
        GatewayCallbackManager.setBlockHandler(blockHandler);
    }
}
package io.github.maodua.order.handler;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maodua.order.exception.GatewayException;
import io.github.maodua.wrench.common.vo.result.Result;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局异常处理器
 */
@Slf4j
@Order(-1)
@Configuration
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }

        Result<String> result = Result.fail("Gateway 内部服务器错误");

        if (ex instanceof NotFoundException) {
            result = Result.fail("相关服务未找到");
        } else if (ex instanceof SignatureVerificationException){
            result = Result.fail("TOKEN签名验证异常");
        }
        else if (ex instanceof GatewayException){
            result = Result.fail(ex.getMessage());
        } else if (ex instanceof ResponseStatusException) { //  && log.isDebugEnabled()
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            result = Result.fail(responseStatusException.getMessage());
        }

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);
        DataBuffer dataBuffer = response.bufferFactory().wrap(objectMapper.writeValueAsBytes(result));
        return response.writeWith(Mono.just(dataBuffer));
    }


}
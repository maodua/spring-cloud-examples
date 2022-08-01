package io.github.maodua.order.filter;

import com.google.common.base.Strings;
import io.github.maodua.order.config.ConfigProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Consumer;

@Component
public class AddRequestParameterFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String userId = Objects.toString(exchange.getAttributes().get(ConfigProperties.USER_ID), "");

        // 如果userId 不等于空就添加到请求中
        if (StringUtils.isNotBlank(userId)){
            Consumer<HttpHeaders> headers = httpHeader -> {
                httpHeader.set(ConfigProperties.USER_ID, userId);
            };
            // 构建请求
            ServerHttpRequest request = exchange.getRequest().mutate().headers(headers).build();
            // 替换请求
            exchange.mutate().request(request).build();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}

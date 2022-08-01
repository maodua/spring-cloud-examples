package io.github.maodua.order.filter;

import io.github.maodua.order.config.ConfigProperties;
import io.github.maodua.order.exception.GatewayException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 开放 Url 过滤器 order(-10)
 */
@Component
public class OpenUrlFilter implements GlobalFilter , Ordered{

    @Autowired
    private ConfigProperties configProperties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public int getOrder() {
        // 在CacheBodyFilter后面执行
        return -10;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 请求路径
        String path = request.getURI().getPath();
        // 请求方法
        String method = request.getMethodValue();

        // 1. 不需要的直接放行
        boolean isOpen = verifyOpenUrl(method, path);
        if (isOpen){
            // 跳过认证
            exchange.getAttributes().put(ConfigProperties.SKIP_AUTH, true);
            return chain.filter(exchange);
        }

        String authorization = request.getHeaders().getFirst("Authorization");
        // 2 判断是否有 token
        if (StringUtils.isBlank(authorization)){
            throw new GatewayException("Gateway 请求参数异常");
        }

        // 3 传递 token
        exchange.getAttributes().put(ConfigProperties.REQUEST_TOKEN, authorization);

        // 4 放行
        return chain.filter(exchange);
    }

    /**
     * 验证url是否开放
     * @param method 请求方式
     * @param url 请求url
     * @return 是否开放
     */
    public boolean verifyOpenUrl(String method, String url){
        // 当前请求所有开放的url
        List<String> openUrls = configProperties.getOpenUrl().get(method);
        long count = openUrls.stream().filter(u -> antPathMatcher.match(u, url)).count();
        return count > 0;
    }



}

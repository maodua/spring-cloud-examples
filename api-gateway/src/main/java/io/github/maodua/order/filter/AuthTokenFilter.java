package io.github.maodua.order.filter;

import io.github.maodua.order.config.ConfigProperties;
import io.github.maodua.order.exception.GatewayException;
import io.github.maodua.order.util.TokenUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class AuthTokenFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Boolean skip = (Boolean) exchange.getAttributes().get(ConfigProperties.SKIP_AUTH);
        if (BooleanUtils.isTrue(skip)){
            return chain.filter(exchange);
        }
        String token = exchange.getAttributes().get(ConfigProperties.REQUEST_TOKEN).toString();
        // 排除自定前缀
        token = token.replace("Bearer ", "").trim();
        // 获取用户id
        String userId = TokenUtil.getUserId(token);

        if (StringUtils.isBlank(userId)){
            throw new GatewayException("请登录用户访问");
        }

        exchange.getAttributes().put(ConfigProperties.USER_ID, userId);

        return chain.filter(exchange);
    }


    /**
     * 验证 Token
     * @param token token
     */
    public boolean verifyToken(String token){
        return false;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}

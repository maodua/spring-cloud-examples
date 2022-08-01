package io.github.maodua.user.autoconfigure;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.github.maodua.user.util.RequestVariable;
import io.github.maodua.user.util.SecurityContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {

        String userId = SecurityContextHolder.getUserId();
        if (StringUtils.isNotBlank(userId)) {
            template.header(RequestVariable.USER_ID, SecurityContextHolder.getUserId());
            template.header(RequestVariable.LOGIN, String.valueOf(SecurityContextHolder.isLogin()));
        }

    }
}

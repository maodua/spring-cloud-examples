package io.github.maodua.user.interceptor;


import io.github.maodua.user.util.RequestVariable;
import io.github.maodua.user.util.SecurityContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HeaderInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String user_id = request.getHeader(RequestVariable.USER_ID);
        if (StringUtils.isNotBlank(user_id)) {
            SecurityContextHolder.set(SecurityContextHolder.USER_ID, user_id);
            SecurityContextHolder.set(SecurityContextHolder.LOGIN, true);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityContextHolder.remove();
    }
}

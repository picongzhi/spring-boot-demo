package com.pcz.session.interceptor;

import com.pcz.session.constants.Consts;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author picongzhi
 */
@Component
public class SessionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession httpSession = request.getSession();
        if (httpSession.getAttribute(Consts.SESSION_KEY) != null) {
            return true;
        }

        String url = "/page/login?redirect=true";
        response.sendRedirect(request.getContextPath() + url);

        return false;
    }
}

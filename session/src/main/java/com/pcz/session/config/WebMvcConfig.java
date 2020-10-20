package com.pcz.session.config;

import com.pcz.session.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author picongzhi
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(sessionInterceptor);
        interceptorRegistration.excludePathPatterns("/page/login");
        interceptorRegistration.excludePathPatterns("/page/doLogin");
        interceptorRegistration.excludePathPatterns("/error");

        interceptorRegistration.addPathPatterns("/**");
    }
}

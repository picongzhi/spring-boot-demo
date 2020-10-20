package com.pcz.template.enjoy.config;

import com.jfinal.template.ext.spring.JFinalViewResolver;
import com.jfinal.template.source.ClassPathSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author picongzhi
 */
@Configuration
public class EnjoyConfig {
    @Bean(name = "jfinalViewResolver")
    public JFinalViewResolver getJFinalViewResolver() {
        JFinalViewResolver jFinalViewResolver = new JFinalViewResolver();
        // setDevMode放在最前面
        jFinalViewResolver.setDevMode(true);
        // 使用ClassPathSourceFactory从class path与jar包中加载模板文件
        jFinalViewResolver.setSourceFactory(new ClassPathSourceFactory());
        // 在使用ClassPathSourceFactory要使用setBaseTemplatePath代替jFinalViewResolver.setPrefix("/view/")
        JFinalViewResolver.engine.setBaseTemplatePath("/templates/");

        jFinalViewResolver.setSessionInView(true);
        jFinalViewResolver.setSuffix(".html");
        jFinalViewResolver.setContentType("text/html;charset=UTF-8");
        jFinalViewResolver.setOrder(0);

        return jFinalViewResolver;
    }
}

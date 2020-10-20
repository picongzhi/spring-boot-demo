package com.pcz.email.service;

import cn.hutool.core.io.resource.ResourceUtil;
import com.pcz.email.SpringBootEmailApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import javax.mail.MessagingException;
import java.net.URL;

public class MailServiceTest extends SpringBootEmailApplicationTest {
    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void sendSimpleMail() {
        mailService.sendSimpleMail("picongzhi@gmail.com", "simple mail", "simple mail");
    }

    @Test
    public void sendHtmlMail() throws MessagingException {
        Context context = new Context();
        context.setVariable("name", "PiCongzhi");
        String emailTemplate = templateEngine.process("welcome", context);
        mailService.sendHtmlMail("picongzhi@gmail.com", "html mail", emailTemplate);
    }

    @Test
    public void sendHtmlMail2() throws MessagingException {
        SpringResourceTemplateResolver springResourceTemplateResolver = new SpringResourceTemplateResolver();
        springResourceTemplateResolver.setApplicationContext(applicationContext);
        springResourceTemplateResolver.setCacheable(false);
        springResourceTemplateResolver.setPrefix("classpath:/email/");
        springResourceTemplateResolver.setSuffix(".html");

        templateEngine.setTemplateResolver(springResourceTemplateResolver);

        Context context = new Context();
        context.setVariable("name", "PiCongzhi");

        String emailTemplate = templateEngine.process("test", context);
        mailService.sendHtmlMail("picongzhi@gmail.com", "html mail 2", emailTemplate);
    }

    @Test
    public void sendAttachmentsMail() throws MessagingException {
        URL url = ResourceUtil.getResource("static/duobao.JPG");
        mailService.sendAttachmentsMail("picongzhi@gmail.com", "mail with attachment", "附件", url.getPath());
    }

    @Test
    public void sendResourceMail() throws MessagingException {
        String rscId = "duobao";
        String content = "<html><body>静态资源<br/><img src=\'cid:" + rscId + "\'></body></html>";
        URL url = ResourceUtil.getResource("static/duobao.JPG");
        mailService.sendResourceMail("picongzhi@gmail.com", "静态资源", content, url.getPath(), rscId);
    }
}

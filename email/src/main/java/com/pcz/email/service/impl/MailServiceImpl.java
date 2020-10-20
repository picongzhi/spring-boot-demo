package com.pcz.email.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.pcz.email.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author picongzhi
 */
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendSimpleMail(String to, String subject, String content, String... cc) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        if (ArrayUtil.isNotEmpty(cc)) {
            simpleMailMessage.setCc(cc);
        }

        mailSender.send(simpleMailMessage);
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content, String... cc) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content, true);
        if (ArrayUtil.isNotEmpty(cc)) {
            mimeMessageHelper.setCc(cc);
        }

        mailSender.send(mimeMessage);
    }

    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content, true);
        if (ArrayUtil.isNotEmpty(cc)) {
            mimeMessageHelper.setCc(cc);
        }

        FileSystemResource fileSystemResource = new FileSystemResource(new File(filePath));
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        mimeMessageHelper.addAttachment(fileName, fileSystemResource);

        mailSender.send(mimeMessage);
    }

    @Override
    public void sendResourceMail(String to, String subject, String content, String rscPath, String srcId, String... cc) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content, true);
        if (ArrayUtil.isNotEmpty(cc)) {
            mimeMessageHelper.setCc(cc);
        }

        FileSystemResource fileSystemResource = new FileSystemResource(new File(rscPath));
        mimeMessageHelper.addInline(srcId, fileSystemResource);

        mailSender.send(mimeMessage);
    }
}

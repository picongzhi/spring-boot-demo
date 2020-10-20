package com.pcz.email.service;

import javax.mail.MessagingException;

/**
 * @author picongzhi
 */
public interface MailService {
    /**
     * 发送文本邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     * @param cc      抄送地址
     */
    void sendSimpleMail(String to, String subject, String content, String... cc);

    /**
     * 发送html邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     * @param cc      抄送地址
     * @throws MessagingException 邮件发送异常s
     */
    void sendHtmlMail(String to, String subject, String content, String... cc)
            throws MessagingException;

    /**
     * 发送带附件的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param content  内容
     * @param filePath 附件地址
     * @param cc       抄送地址
     * @throws MessagingException 邮件发送异常
     */
    void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc)
            throws MessagingException;

    /**
     * 发送顾正文中有静态资源的邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     * @param rscPath 静态资源地址
     * @param srcId   静态资源id
     * @param cc      抄送地址
     * @throws MessagingException 邮件发送异常
     */
    void sendResourceMail(String to, String subject, String content, String rscPath, String srcId, String... cc)
            throws MessagingException;
}

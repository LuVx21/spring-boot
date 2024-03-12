package org.luvx.mail.spring;

import lombok.extern.slf4j.Slf4j;
import org.luvx.mail.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.time.LocalDateTime;

/**
 * @author: Ren, Xie
 * @desc:
 */
@Slf4j
@Component
public class Send {
    @Autowired
    private JavaMailSenderImpl mailSender;
    @Autowired
    private TemplateEngine     templateEngine;
    @Value("${spring.mail.username}")
    private String             from;

    /**
     * 发送包含简单文本的邮件
     */
    public void sendSimpleMail() {
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom(MailUtils.sender);
        mail.setTo(MailUtils.sender, MailUtils.receiver);
        mail.setCc(MailUtils.sender, MailUtils.receiver);
        mail.setBcc(MailUtils.sender, MailUtils.receiver);
        mail.setSubject("邮件测试【文本】");
        mail.setText("这里是一段简单文本。" + LocalDateTime.now());

        mailSender.send(mail);
    }

    /**
     * 发送包含HTML文本的邮件
     *
     * @throws Exception
     */
    public void sendHtmlMail() throws Exception {
        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);

        helper.setFrom(MailUtils.sender);
        helper.setTo(new String[]{MailUtils.sender, MailUtils.receiver});
        helper.setSubject("Spring Boot Mail 邮件测试【HTML】");
        Context c = new Context();
        c.setVariable("id", "006");
        String content = new StringBuilder()
                .append("<html><head></head>")
                .append("<body><h1>spring 邮件测试</h1><p>hello!this is spring mail test。" + LocalDateTime.now())
                .append("</p></body></html>")
                .toString();
        content = templateEngine.process("emailTemplate", c);
        helper.setText(content, true);

        mailSender.send(mail);
    }

    /**
     * 发送正文中有静态资源（图片）的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    public void sendInlineResourceMail(String[] to, String subject, String content, String rscPath, String rscId) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);

            mailSender.send(message);
            log.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送嵌入静态资源的邮件时发生异常！", e);
        }
    }

    public void sendAttachmentsMail(String[] to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);

            mailSender.send(message);
            log.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送带附件的邮件时发生异常！", e);
        }
    }
}



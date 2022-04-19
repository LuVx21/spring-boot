package org.luvx.message.mail;

import java.io.File;
import java.util.Optional;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.alibaba.nacos.api.config.annotation.NacosValue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailService {
    @Value("${spring.mail.username}")
    private String userMail;
    @NacosValue(value = "${toMail:${spring.mail.username}}", autoRefreshed = true)
    private String[] toMail;

    @Resource
    private JavaMailSender mailSender;

    public void sendSimpleMail(SimpleMailMessage bean) {
        bean.setFrom(userMail);
        try {
            mailSender.send(bean);
            log.info("简单邮件已经发送。");
        } catch (Exception e) {
            log.error("发送简单邮件时发生异常！", e);
        }
    }

    /**
     * 发送html邮件
     */
    public void sendHtmlMail(SimpleMailMessage bean) {
        bean.setFrom(userMail);
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(userMail);
            helper.setTo(toMail);
            helper.setSubject(bean.getSubject());
            helper.setText(bean.getText(), true);

            mailSender.send(message);
            log.info("html邮件发送成功");
        } catch (MessagingException e) {
            log.error("发送html邮件时发生异常！", e);
        }
    }

    /**
     * 正文中有静态资源（图片）的邮件
     */
    public void sendInlineResourceMail(SimpleMailMessage bean, String rscPath, String rscId) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(bean.getFrom());
            helper.setTo(bean.getTo());
            helper.setSubject(bean.getSubject());
            helper.setText(bean.getText(), true);

            FileSystemResource res = new FileSystemResource(rscPath);
            helper.addInline(rscId, res);

            mailSender.send(message);
            log.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送嵌入静态资源的邮件时发生异常！", e);
        }
    }

    /**
     * 带附件的邮件
     */
    public void sendAttachMail(SimpleMailMessage bean, File file) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(bean.getFrom());

            helper.setTo(Optional.ofNullable(bean.getTo()).get());
            helper.setCc(Optional.ofNullable(bean.getCc()).orElse(new String[0]));
            helper.setBcc(Optional.ofNullable(bean.getBcc()).orElse(new String[0]));

            helper.setSubject(bean.getSubject());
            helper.setText(bean.getText(), false);

            FileSystemResource csvFile = new FileSystemResource(file);
            helper.addAttachment(file.getName(), csvFile);

            mailSender.send(message);
            log.info("发送邮件 to:{} 附件:{}", bean.getTo(), file.getAbsolutePath());
        } catch (MessagingException e) {
            log.error("发送带附件的邮件异常！", e);
        }
    }
}

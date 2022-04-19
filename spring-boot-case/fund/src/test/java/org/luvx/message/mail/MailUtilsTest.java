package org.luvx.message.mail;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
class MailUtilsTest {
    @Value("${spring.mail.username}")
    private String userMail;
    @Value("${spring.mail.password}")
    private String password;

    @SneakyThrows
    public void a() {
        Session session = Session.getDefaultInstance(MailUtils.smtp_config(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userMail, password);
            }
        });
        Message mail = new MimeMessage(session);
        mail.addHeader("X-Mailer", "Microsoft Outlook Express 6.00.2900.2869");
        mail.setFrom(new InternetAddress(userMail));
        mail.addRecipient(Message.RecipientType.TO, new InternetAddress(userMail));
        mail.setSubject("title");
        mail.setText("test test");
        try {
            Transport.send(mail);
            log.info("简单邮件已经发送。");
        } catch (Exception e) {
            log.error("发送简单邮件时发生异常！", e);
        }
    }
}
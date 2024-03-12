package org.luvx.mail.jdk;

import org.luvx.mail.MailEntity;
import org.luvx.mail.utils.MailUtils;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author: Ren, Xie
 * @desc: 微软邮箱:
 * SMTP: smtp.office365.com 587 STARTTLS
 */
public class Send1 {


    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session     和服务器交互的会话
     * @param sendMail    发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    public static void createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
        MimeMessage mail = new MimeMessage(session);
        mail.setFrom(new InternetAddress(sendMail, "某宝网", "UTF-8"));
        mail.setRecipient(MimeMessage.RecipientType.TO,
                new InternetAddress(receiveMail, "XX用户", StandardCharsets.UTF_8.name()));
        mail.setSubject("有效期确认1", StandardCharsets.UTF_8.name());
        mail.setContent("账户有效期到了 是不是就自动销户了", "text/html;charset=UTF-8");
        mail.setSentDate(new Date());
        mail.saveChanges();

        try (Transport transport = session.getTransport()) {
            transport.connect(MailUtils.sender, MailUtils.password);
            transport.sendMessage(mail, mail.getAllRecipients());
        }
    }

    /**
     * 创建邮件
     *
     * @param session
     * @param entity
     * @return
     * @throws Exception
     */
    public static void createMimeMessage(Session session, MailEntity entity) throws Exception {
        MimeMessage mail = new MimeMessage(session);
        mail.setFrom(new InternetAddress(entity.getSender().getMail(), entity.getSender().getName(), "UTF-8"));

        for (MailEntity.MailName m : entity.getToList()) {
            mail.setRecipient(MimeMessage.RecipientType.TO,
                    new InternetAddress(m.getMail(), m.getName(), StandardCharsets.UTF_8.name()));
        }
        for (MailEntity.MailName m : entity.getCcList()) {
            mail.setRecipient(MimeMessage.RecipientType.CC,
                    new InternetAddress(m.getMail(), m.getName(), StandardCharsets.UTF_8.name()));
        }
        for (MailEntity.MailName m : entity.getBccList()) {
            mail.setRecipient(MimeMessage.RecipientType.BCC,
                    new InternetAddress(m.getMail(), m.getName(), StandardCharsets.UTF_8.name()));
        }

        mail.setSubject(entity.getTitle(), StandardCharsets.UTF_8.name());
        mail.setContent(entity.getContent(), "text/html;charset=UTF-8");
        mail.setSentDate(new Date());
        mail.saveChanges();
        try (Transport transport = session.getTransport()) {
            transport.connect(MailUtils.sender, MailUtils.password);
            transport.sendMessage(mail, mail.getAllRecipients());
        }
        // Transport.send(mail);
    }

    public static void main(String[] args) throws Exception {
        // Authenticator authenticator = new Authenticator() {
        //     @Override
        //     public PasswordAuthentication getPasswordAuthentication() {
        //         return new PasswordAuthentication(sender, password);
        //     }
        // };
        // Session.getDefaultInstance(config(), authenticator);

        Session session = Session.getInstance(MailUtils.smtp_config());
        createMimeMessage(session, MailUtils.sender, MailUtils.receiver);
    }
}

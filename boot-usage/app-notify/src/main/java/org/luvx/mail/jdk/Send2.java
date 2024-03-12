package org.luvx.mail.jdk;

import org.luvx.mail.utils.MailUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.util.Date;

/**
 * @author: Ren, Xie
 * @desc: 微软邮箱:
 * SMTP: smtp.office365.com 587 STARTTLS
 * 复杂邮件, 如文字+图片+附件
 */
public class Send2 {

    public static void main(String[] args) throws Exception {
        Session session = Session.getInstance(MailUtils.smtp_config());
        MimeMessage mail = createMimeMessage(session, MailUtils.sender, MailUtils.receiver);
        try (Transport transport = session.getTransport()) {
            transport.connect(MailUtils.sender, MailUtils.password);
            transport.sendMessage(mail, mail.getAllRecipients());
        }
    }

    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
        MimeMessage mail = new MimeMessage(session);
        mail.setFrom(new InternetAddress(sendMail, "这里是发件人昵称", "UTF-8"));
        mail.addRecipient(MimeMessage.RecipientType.TO,
                new InternetAddress(receiveMail, "这里是收件人昵称", "UTF-8")
        );
        mail.setSubject("TEST邮件主题（文本+图片+附件）", "UTF-8");

        MimeBodyPart text_image = text_image();
        MimeBodyPart attachment = attachment("D:\\code\\OneDrive\\00.temp\\main_visual_a.jpg");
        MimeBodyPart attachment1 = attachment("D:\\code\\OneDrive\\00.temp\\等一分钟(徐誉滕).txt");

        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text_image);
        mm.addBodyPart(attachment);
        mm.addBodyPart(attachment1);
        mm.setSubType("mixed");

        mail.setContent(mm);
        mail.setSentDate(new Date());
        mail.saveChanges();
        return mail;
    }

    /**
     * 图片+文字
     *
     * @return
     * @throws Exception
     */
    public static MimeBodyPart text_image() throws Exception {
        DataHandler dh = new DataHandler(new FileDataSource("D:\\code\\OneDrive\\00.temp\\1049316.jpg"));
        MimeBodyPart image = new MimeBodyPart();
        image.setDataHandler(dh);
        image.setContentID("image_fairy_tail");

        MimeBodyPart text = new MimeBodyPart();
        text.setContent("这是一张图片<br/><img src='cid:" + image.getContentID() + "'/><br/>哈哈哈哈", "text/html;charset=UTF-8");

        MimeMultipart mm_text_image = new MimeMultipart();
        mm_text_image.addBodyPart(text);
        mm_text_image.addBodyPart(image);
        mm_text_image.setSubType("related");

        MimeBodyPart text_image = new MimeBodyPart();
        text_image.setContent(mm_text_image);
        return text_image;
    }

    /**
     * 附件
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static MimeBodyPart attachment(String path) throws Exception {
        DataHandler dh2 = new DataHandler(new FileDataSource(path));
        MimeBodyPart attachment = new MimeBodyPart();
        attachment.setDataHandler(dh2);
        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));
        return attachment;
    }
}

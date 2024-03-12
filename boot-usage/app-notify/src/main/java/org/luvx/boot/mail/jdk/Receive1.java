package org.luvx.boot.mail.jdk;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.angus.mail.util.BASE64DecoderStream;
import org.luvx.boot.mail.utils.MailUtils;

import jakarta.mail.*;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;
import java.io.File;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * 微软邮箱:
 * SMTP: smtp.office365.com 587 STARTTLS
 * 读取邮件
 */
@Slf4j
public class Receive1 {

    public static void main(String[] args) throws Exception {
        Session session = Session.getDefaultInstance(MailUtils.imap_config(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MailUtils.sender, MailUtils.password);
            }
        });

        /// Session session = Session.getInstance(MailUtils.pop3_config());

        try (Store store = session.getStore()) {
            store.connect(MailUtils.POP3_SERVER, MailUtils.sender, MailUtils.password);
            try (Folder folder = store.getFolder("INBOX")) {
                folder.open(Folder.READ_ONLY);
                // Message[] messages = folder.getMessages();
                int cnt = folder.getMessageCount();
                Message[] messages = folder.getMessages(cnt - 9, cnt);
                print(messages);
            }
        }
    }

    private static void print(Message[] messages) throws Exception {
        System.out.println("The count of the Email is :" + messages.length);
        for (int i = messages.length - 1; i >= 0; i--) {
            Message message = messages[i];
            System.out.println(
                    "--------------------" + (i + 1) + "-------------------"
                            + "\nSubject: " + message.getSubject()
                            + "\nFrom: " + message.getFrom()[0]
                            + "\nText: " + message.getContent().toString()
                            + "\n发送日期: " + message.getSentDate().toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDateTime()
            );
        }
    }

    /**
     * 获取邮件的内容(各个组成部分)
     */
    private static void print1(Message[] messages) throws Exception {
        System.out.println("The count of the Email is :" + messages.length);
        for (int i = messages.length - 1; i >= 0; i--) {
            Message message = messages[i];
            String title = message.getSubject();
            if (!"TITLE".equals(title)) {
                continue;
            }
            Date date = message.getSentDate();
            LocalDate day = date.toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDate();
            if (!day.equals(LocalDate.now())) {
                continue;
            }

            MimeMultipart content = (MimeMultipart) message.getContent();
            int count = content.getCount();
            System.out.println("++++++++++++" + count + "++++++++++");
            for (int j = 0; j < count; j++) {
                MimeBodyPart bodyPart = (MimeBodyPart) content.getBodyPart(j);
                log.info("type:{}", bodyPart.getContentType());

                if (j == 0) {
                    MimeMultipart content1 = (MimeMultipart) bodyPart.getContent();
                    MimeBodyPart bodyPart1 = (MimeBodyPart) content1.getBodyPart("pic_0");
                    BASE64DecoderStream content2 = (BASE64DecoderStream) bodyPart1.getContent();
                    Files.write(ByteStreams.toByteArray(content2), new File("D:\\work\\open\\email\\data_delivery\\" + i + ".png"));
                    // int count1 = content1.getCount();
                    // for (int k = 0; k < count1; k++) {
                    //     MimeBodyPart bodyPart1 = (MimeBodyPart) content1.getBodyPart(k);
                    //     log.info("type:{}", bodyPart1.getContentType());
                    //     if (k == 0) {
                    //         String content2 = (String) bodyPart1.getContent();
                    //         log.info(content2);
                    //     }
                    //     if (k == 1) {
                    //         BASE64DecoderStream content2 = (BASE64DecoderStream) bodyPart1.getContent();
                    //         Files.write(ByteStreams.toByteArray(content2), new File("D:\\work\\open\\email\\data_delivery\\" + i + ".png"));
                    //     }
                    // }
                }
                if (j == 1) {
                    BASE64DecoderStream content1 = (BASE64DecoderStream) bodyPart.getContent();
                    Files.write(ByteStreams.toByteArray(content1), new File("D:\\work\\open\\email\\data_delivery\\" + i + ".pdf"));
                }
            }
            System.out.println("++++++++++++++++++++++");
        }
    }


}

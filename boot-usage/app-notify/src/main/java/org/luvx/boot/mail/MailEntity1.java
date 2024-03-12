package org.luvx.boot.mail;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
public class MailEntity1 {
    private MimeMessage  mimeMessage = null;
    private StringBuffer bodyText    = new StringBuffer();
    private String       dateFormat  = "yy-MM-dd HH:mm";
    private String       attachDir   = "";

    public String getSubject() {
        String subject = "";
        try {
            System.out.println("转换前的subject：" + mimeMessage.getSubject());
            subject = MimeUtility.decodeText(mimeMessage.getSubject());
            System.out.println("转换后的subject: " + mimeMessage.getSubject());
            if (subject == null) {
                subject = "";
            }
        } catch (Exception exce) {
            exce.printStackTrace();
        }
        return subject;
    }

    public String getFrom() throws Exception {
        InternetAddress[] addressArray = (InternetAddress[]) mimeMessage.getFrom();
        String from = addressArray[0].getAddress();
        if (from == null) {
            from = "";
            System.out.println("无法知道发送者.");
        }
        String personal = addressArray[0].getPersonal();

        if (personal == null) {
            personal = "";
            System.out.println("无法知道发送者的姓名.");
        }

        String fromAddr = null;
        if (personal != null || from != null) {
            fromAddr = personal + "<" + from + ">";
            System.out.println("发送者是：" + fromAddr);
        } else {
            System.out.println("无法获得发送者信息.");
        }
        return fromAddr;
    }

    public String getTo(String type) throws Exception {
        String mailAddr = "";
        String addType = type.toUpperCase();

        InternetAddress[] address = null;
        if (addType.equals("TO") || addType.equals("CC")
                || addType.equals("BCC")) {

            if (addType.equals("TO")) {
                address = (InternetAddress[]) mimeMessage
                        .getRecipients(Message.RecipientType.TO);
            } else if (addType.equals("CC")) {
                address = (InternetAddress[]) mimeMessage
                        .getRecipients(Message.RecipientType.CC);
            } else {
                address = (InternetAddress[]) mimeMessage
                        .getRecipients(Message.RecipientType.BCC);
            }

            if (address != null) {
                for (int i = 0; i < address.length; i++) {
                    String emailAddr = address[i].getAddress();
                    if (emailAddr == null) {
                        emailAddr = "";
                    } else {
                        System.out.println("转换之前的emailAddr: " + emailAddr);
                        emailAddr = MimeUtility.decodeText(emailAddr);
                        System.out.println("转换之后的emailAddr: " + emailAddr);
                    }
                    String personal = address[i].getPersonal();
                    if (personal == null) {
                        personal = "";
                    } else {
                        System.out.println("转换之前的personal: " + personal);
                        personal = MimeUtility.decodeText(personal);
                        System.out.println("转换之后的personal: " + personal);
                    }
                    String compositeto = personal + "<" + emailAddr + ">";
                    System.out.println("完整的邮件地址：" + compositeto);
                    mailAddr += "," + compositeto;
                }
                mailAddr = mailAddr.substring(1);
            }
        } else {
            throw new Exception("错误的电子邮件类型!");
        }
        return mailAddr;
    }

    public String getSentDate() throws Exception {
        Date sentDate = mimeMessage.getSentDate();
        System.out.println("发送日期 原始类型: " + dateFormat);
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        String strSentDate = format.format(sentDate);
        System.out.println("发送日期 可读类型: " + strSentDate);
        return strSentDate;
    }

    public void getMailContent(Part part) throws Exception {
        String contentType = part.getContentType();
        // 获得邮件的MimeType类型
        System.out.println("邮件的MimeType类型: " + contentType);

        int nameIndex = contentType.indexOf("name");

        boolean conName = false;

        if (nameIndex != -1) {
            conName = true;
        }

        System.out.println("邮件内容的类型: " + contentType);

        if (part.isMimeType("text/plain") && conName == false) {
            // text/plain 类型
            bodyText.append((String) part.getContent());
        } else if (part.isMimeType("text/html") && conName == false) {
            // text/html 类型
            bodyText.append((String) part.getContent());
        } else if (part.isMimeType("multipart/*")) {
            // multipart/*
            Multipart multipart = (Multipart) part.getContent();
            int counts = multipart.getCount();
            for (int i = 0; i < counts; i++) {
                getMailContent(multipart.getBodyPart(i));
            }
        } else if (part.isMimeType("message/rfc822")) {
            // message/rfc822
            getMailContent((Part) part.getContent());
        } else {
        }
    }

    public boolean getReplySign() throws MessagingException {
        boolean replySign = false;
        String needReply[] = mimeMessage
                .getHeader("Disposition-Notification-To");
        if (needReply != null) {
            replySign = true;
        }
        if (replySign) {
            System.out.println("该邮件需要回复");
        } else {
            System.out.println("该邮件不需要回复");
        }
        return replySign;
    }

    public String getMessageId() throws MessagingException {
        String messageID = mimeMessage.getMessageID();
        System.out.println("邮件ID: " + messageID);
        return messageID;
    }

    public boolean isReaded() throws MessagingException {
        boolean isNew = false;
        Flags flags = ((Message) mimeMessage).getFlags();
        Flags.Flag[] flag = flags.getSystemFlags();
        System.out.println("flags的长度: " + flag.length);
        for (int i = 0; i < flag.length; i++) {
            if (flag[i] == Flags.Flag.SEEN) {
                isNew = true;
                System.out.println("seen email...");
                // break;
            }
        }
        return isNew;
    }

    public boolean isContainAttach(Part part) throws Exception {
        boolean attachFlag = false;
        // String contentType = part.getContentType();
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart mPart = mp.getBodyPart(i);
                String disposition = mPart.getDisposition();
                if ((disposition != null)
                        && ((disposition.equals(Part.ATTACHMENT)) || (disposition
                        .equals(Part.INLINE))))
                    attachFlag = true;
                else if (mPart.isMimeType("multipart/*")) {
                    attachFlag = isContainAttach((Part) mPart);
                } else {
                    String conType = mPart.getContentType();
                    if (conType.toLowerCase().indexOf("application") != -1)
                        attachFlag = true;
                    if (conType.toLowerCase().indexOf("name") != -1)
                        attachFlag = true;
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            attachFlag = isContainAttach((Part) part.getContent());
        }
        return attachFlag;
    }

    public void saveAttachMent(Part part) throws Exception {
        String fileName = "";
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart mPart = mp.getBodyPart(i);
                String disposition = mPart.getDisposition();
                if ((disposition != null)
                        && ((disposition.equals(Part.ATTACHMENT)) || (disposition
                        .equals(Part.INLINE)))) {
                    fileName = mPart.getFileName();
                    if (fileName.toLowerCase().indexOf("gb2312") != -1) {
                        fileName = MimeUtility.decodeText(fileName);
                    }
                    saveFile(fileName, mPart.getInputStream());
                } else if (mPart.isMimeType("multipart/*")) {
                    saveAttachMent(mPart);
                } else {
                    fileName = mPart.getFileName();
                    if ((fileName != null)
                            && (fileName.toLowerCase().indexOf("GB2312") != -1)) {
                        fileName = MimeUtility.decodeText(fileName);
                        saveFile(fileName, mPart.getInputStream());
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            saveAttachMent((Part) part.getContent());
        }
    }

    private void saveFile(String fileName, InputStream in) throws Exception {
        String osName = System.getProperty("os.name");
        String storeDir = attachDir;

        String separator = "";
        if (osName == null) {
            osName = "";
        }
        if (osName.toLowerCase().indexOf("win") != -1) {
            separator = "\\";
            if (storeDir == null || storeDir.equals(""))
                storeDir = "c:\\tmp";
        } else {
            separator = "/";
            storeDir = "/tmp";
        }
        File storeFile = new File(storeDir + separator + fileName);
        System.out.println("附件的保存地址: " + storeFile.toString());
        // for(int i=0;storefile.exists();i++){
        // storefile = new File(storedir+separator+fileName+i);
        // }
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(storeFile));
            bis = new BufferedInputStream(in);
            int c;
            while ((c = bis.read()) != -1) {
                bos.write(c);
                bos.flush();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new Exception("文件保存失败!");
        } finally {
            bos.close();
            bis.close();
        }
    }
}

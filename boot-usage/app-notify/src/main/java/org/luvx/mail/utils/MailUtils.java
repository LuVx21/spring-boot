package org.luvx.mail.utils;

import java.util.Properties;

/**
 * @author: Ren, Xie
 * @desc:
 */
public class MailUtils {
    /**
     * 发件人的 邮箱 和 密码
     * 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码(如163邮箱称为“授权码”),
     * 对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码(授权码)。
     */
    public static final String sender   = "foobar@hotmail.com";
    public static final String password = "foobar";
    public static final String receiver = "foobar@gmail.com";

    public static final String SMTP_SERVER = "smtp.office365.com";
    public static final String SMTP_PORT   = "587";

    public static final String POP3_SERVER = "outlook.office365.com";
    public static final String POP3_PORT   = "995";

    public static final String IMAP_SERVER = "outlook.office365.com";
    public static final String IMAP_PORT   = "993";

    /**
     * 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
     * 如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
     * <pre>
     *     props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
     *     props.setProperty("mail.smtp.socketFactory.fallback", "false");
     *     props.setProperty("mail.smtp.socketFactory.port", port);
     * </pre>
     *
     * @return
     */
    public static Properties smtp_config() {
        Properties props = new Properties();
        props.setProperty("mail.debug", "true");
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", SMTP_SERVER);
        props.setProperty("mail.smtp.port", SMTP_PORT);
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        return props;
    }

    public static Properties pop3_config() {
        Properties props = new Properties();
        // props.setProperty("mail.debug", "true");
        props.setProperty("mail.transport.protocol", "pop3");
        props.setProperty("mail.store.protocol", "pop3s");
        props.setProperty("mail.pop3.host", POP3_SERVER);
        props.setProperty("mail.pop3.port", POP3_PORT);
        props.setProperty("mail.pop3.auth", "true");
        props.setProperty("mail.pop3.tls.enable", "true");
        return props;
    }

    public static Properties imap_config() {
        Properties props = new Properties();
        // props.setProperty("mail.debug", "true");
        props.setProperty("mail.transport.protocol", "imap");
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imap.host", IMAP_SERVER);
        props.setProperty("mail.imap.port", IMAP_PORT);
        props.setProperty("mail.imap.auth", "true");
        props.setProperty("mail.imap.tls.enable", "true");
        return props;
    }
}

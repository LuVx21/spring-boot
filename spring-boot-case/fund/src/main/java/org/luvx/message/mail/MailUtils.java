package org.luvx.message.mail;

import java.util.Properties;

public class MailUtils {
    public static final String POP3_SERVER = "outlook.office365.com";
    public static final String POP3_PORT = "995";
    public static final String SMTP_SERVER = "smtp.office365.com";
    public static final String SMTP_PORT = "587";

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

    public static Properties smtp_config() {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", SMTP_SERVER);
        props.setProperty("mail.smtp.port", SMTP_PORT);
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        return props;
    }
}

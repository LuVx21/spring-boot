package org.luvx.mail.spring;

import org.junit.jupiter.api.Test;
import org.luvx.ApplicationTest;
import org.luvx.boot.mail.spring.Send;
import org.springframework.beans.factory.annotation.Autowired;

public class SendTest extends ApplicationTest {
    @Autowired
    Send send;

    @Test
    public void sendSimpleMail() {
        send.sendSimpleMail();
    }

    @Test
    public void sendHtmlMailTest() throws Exception {
        send.sendHtmlMail();
    }
}
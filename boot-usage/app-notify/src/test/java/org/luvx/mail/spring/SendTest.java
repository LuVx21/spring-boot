package org.luvx.mail.spring;

import org.junit.Test;
import org.luvx.ApplicationTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Ren, Xie
 * @desc:
 */
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
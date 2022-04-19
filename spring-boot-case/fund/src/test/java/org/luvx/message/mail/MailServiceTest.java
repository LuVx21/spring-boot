package org.luvx.message.mail;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luvx.fund.util.MarkdownUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.google.common.io.Files;

import lombok.SneakyThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MailServiceTest {

    @Resource
    private MailService mailService;

    @SneakyThrows
    @Test
    void sendSimpleMail() {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject("title");
        mail.setText("test test");
        // mailService.sendSimpleMail(mail);

        String path = "";
        List<String> lines = Files.readLines(new File(path), StandardCharsets.UTF_8);
        String md = lines.stream().collect(Collectors.joining("\n"));
        String html = MarkdownUtils.markdown2Html1(md);
        mail.setText(html);
        mailService.sendHtmlMail(mail);
    }

    @Test
    void sendHtmlMail() {
    }
}
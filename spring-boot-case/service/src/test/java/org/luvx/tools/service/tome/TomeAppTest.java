package org.luvx.tools.service.tome;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luvx.tools.service.tome.pojo.Content;
import org.luvx.tools.service.tome.pojo.MarkdownType;
import org.luvx.tools.service.tome.pojo.TextType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TomeAppTest {

    @Autowired
    WeChatService          weChatService;

    @Test
    void m1() throws IOException, InterruptedException {
        TextType textType = new TextType(new Content("你的快递已到，请携带工卡前往邮件中心领取"));
        weChatService.send(textType);

        MarkdownType markdownType = new MarkdownType(new Content("你的**快递**已到，请携带工卡前往`邮件`中心领取"));
        weChatService.send(markdownType);
        // String token = scheduler.getToken();
        // API.println(token);
    }
}
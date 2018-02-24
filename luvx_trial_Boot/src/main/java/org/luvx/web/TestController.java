package org.luvx.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
public class TestController {
    @RequestMapping("/test")
    public String index() {
        return "Hello Spring-Boot";
    }

    /**
     * session 共享测试用
     * redis-cli下:keys '*sessions*'
     *
     * @param session
     * @return SessionId
     */
    @RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
}
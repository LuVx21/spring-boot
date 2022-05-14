package org.luvx.oauth2.client.controller;

import org.luvx.oauth2.client.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @Autowired
    private HelloService service;

    @GetMapping("/login")
    public String login(String code, Model model) {
        service.getData(code, model);
        return "index";
    }

    @GetMapping("/logout")
    public String logout(String code, Model model) {
        service.logout();
        return "index";
    }
}

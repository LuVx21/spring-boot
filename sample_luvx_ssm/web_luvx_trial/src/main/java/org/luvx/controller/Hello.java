package org.luvx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Hello {

    /**
     * RequestMapping注解来映射请求URL
     *
     * @return
     */
    @RequestMapping("/hello")
    public String hello() {
        System.out.println("Hello World!");
        return "success";
    }

    @RequestMapping("/test")
    public String hello(Model model, @RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        model.addAttribute("name", name);
        return "hello";
    }
}

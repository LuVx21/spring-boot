package org.luvx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}

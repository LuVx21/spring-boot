package org.luvx.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/")
    public String index(ModelMap map) {
        map.put("name", "JSP Test!");
        return "index";
    }

    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name", "LuVx!");
        return "index";
    }
}
package org.luvx.module.controller;

import org.luvx.module.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

  @Autowired
  HelloService helloService;

  public HelloController() {
  }

  @RequestMapping("/")
  @ResponseBody
  public String hello() {
    return helloService.hello();
  }


}

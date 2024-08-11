package org.luvx.boot.sentinel.controller;

import org.luvx.boot.sentinel.demo.Main;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import jakarta.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class SentinelController {
    @Resource
    private Main main;

    @GetMapping("/hello")
    public String apiHello() {
        doBusiness();
        main.helloWorld();
        return "Hello!";
    }

    @GetMapping("/err")
    public String apiError() {
        doBusiness();
        return "Oops...";
    }

    @GetMapping("/foo/{id}")
    public String apiFoo(@PathVariable("id") Long id) {
        doBusiness();
        return "Hello " + id;
    }

    @GetMapping("/exclude/{id}")
    public String apiExclude(@PathVariable("id") Long id) {
        doBusiness();
        return "Exclude " + id;
    }

    @GetMapping("/forward")
    public ModelAndView apiForward() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("hello");
        return mav;
    }

    private void doBusiness() {
        Random random = new Random(1);
        try {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
        } catch (InterruptedException _) {
        }
    }
}

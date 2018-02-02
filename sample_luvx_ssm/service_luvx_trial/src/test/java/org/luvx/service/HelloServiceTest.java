package org.luvx.service;

import org.junit.Test;
import org.luvx.BaseTest;
import org.luvx.entity.Hello;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HelloServiceTest extends BaseTest{
    @Autowired
    private HelloService helloService;

    @Test
    public void findAllTest() {
        List<Hello> list = helloService.findAll();
        System.out.println(list.size());
    }
}

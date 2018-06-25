package org.luvx.consumer;

import org.luvx.service.AppService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        AppService service = (AppService) context.getBean("appService");
        System.out.println(service.listNames(1L));
    }
}

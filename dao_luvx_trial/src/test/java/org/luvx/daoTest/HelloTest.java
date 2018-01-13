package org.luvx.daoTest;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.luvx.dao.HelloDao;
import org.luvx.entity.Hello;

public class HelloTest {

    @Test
    public void testFindAll() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext-dao.xml");
        HelloDao helloDao = (HelloDao) ac.getBean("helloDao");

        List<Hello> list = helloDao.findAll();
        System.out.println(list.size());
    }
}

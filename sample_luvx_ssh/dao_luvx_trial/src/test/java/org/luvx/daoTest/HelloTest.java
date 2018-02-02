package org.luvx.daoTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.luvx.dao.HelloDao;
import org.luvx.entity.Hello;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-dao.xml")
public class HelloTest {


    @Autowired
    private HelloDao helloDao;

    @Test
    public void testFindAll() {
        List<Hello> list = helloDao.findAll();
        System.out.println(list.size());
    }

    // @Test
    // public void testFindAll() {
    //     ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext-dao.xml");
    //     HelloDao helloDao = (HelloDao) ac.getBean("helloDao");
    //
    //     List<Hello> list = helloDao.findAll();
    //     System.out.println(list.size());
    // }
}

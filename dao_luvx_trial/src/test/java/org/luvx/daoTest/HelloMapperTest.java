package org.luvx.daoTest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.dao.HelloDao;
import org.luvx.entity.Hello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * mybatis测试用
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-dao_MyBatis.xml")
public class HelloMapperTest {

    @Autowired
    private HelloDao helloDao;

    @Test
    public void findAllTest() {
        List<Hello> list = helloDao.findAll();
        System.out.println(list.size());
    }
}

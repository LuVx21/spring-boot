package org.luvx.daoTest;


import org.junit.Test;
import org.luvx.BaseTest;
import org.luvx.dao.HelloDao;
import org.luvx.entity.Hello;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * mybatis测试用
 */
public class HelloMapperTest extends BaseTest {

    @Autowired
    private HelloDao helloDao;

    @Test
    public void findAllTest() {
        List<Hello> list = helloDao.findAll();
        System.out.println(list.size());
    }
}

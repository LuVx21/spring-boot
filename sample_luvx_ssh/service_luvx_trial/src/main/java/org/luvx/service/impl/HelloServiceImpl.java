package org.luvx.service.impl;

import java.util.List;

import org.luvx.dao.HelloDao;
import org.luvx.entity.Hello;
import org.luvx.service.HelloService;

public class HelloServiceImpl implements HelloService {
    private HelloDao helloDao;

    public void setHelloDao(HelloDao helloDao) {
        this.helloDao = helloDao;
    }

    public List<Hello> findAll() {
        return helloDao.findAll();
    }

}

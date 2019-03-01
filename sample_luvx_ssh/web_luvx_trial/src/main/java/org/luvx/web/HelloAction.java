package org.luvx.web;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import org.luvx.entity.Hello;
import org.luvx.service.HelloService;

public class HelloAction extends ActionSupport {
    private HelloService helloService;

    public void setHelloService(HelloService helloService) {
        this.helloService = helloService;
    }

    /**
     * 查询所有数据
     */
    public String execute() throws Exception {
        List<Hello> list = helloService.findAll();
        System.out.println(list.size());

        ActionContext.getContext().put("list", list);
        return SUCCESS;
    }
}

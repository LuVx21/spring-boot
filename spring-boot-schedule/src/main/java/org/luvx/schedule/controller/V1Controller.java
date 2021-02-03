package org.luvx.schedule.controller;

import lombok.extern.slf4j.Slf4j;
import org.luvx.schedule.dynamic.pojo.TaskModel;
import org.luvx.schedule.serivce.Service1;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Ren, Xie
 */
@Slf4j
@RestController
@RequestMapping("/v1")
public class V1Controller {

    @Resource
    Service1 service1;

    @GetMapping(value = {"/create"})
    public void select() {
        TaskModel model = new TaskModel();
        model.setId("_1");
        model.setCron("0/5 * * * * ?");
        model.setSql("");
        service1.addTriggerTask(model);
    }
}

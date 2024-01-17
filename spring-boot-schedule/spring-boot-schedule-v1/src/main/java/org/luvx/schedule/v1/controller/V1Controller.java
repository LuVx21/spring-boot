package org.luvx.schedule.v1.controller;

import lombok.extern.slf4j.Slf4j;
import org.luvx.schedule.v1.dynamic.DynamicTaskScheduler;
import org.luvx.schedule.v1.entity.TaskEntity;
import org.luvx.schedule.v1.serivce.Service1;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.Set;

/**
 * @author Ren, Xie
 */
@Slf4j
@RestController
@RequestMapping("/v1")
public class V1Controller {
    @Resource
    Service1             service1;
    @Resource
    DynamicTaskScheduler dynamicTaskScheduler;

    @PostMapping(value = {"/createOrUpdate"})
    public void createOrUpdate(@RequestBody TaskEntity entity) {
        String id = entity.getId();
        if (dynamicTaskScheduler.hasTask(id)) {
            dynamicTaskScheduler.cancelTriggerTask(id);
        }
        service1.addTriggerTask(entity);
    }

    @DeleteMapping(value = {"/delete/{taskId}"})
    public void delete(@PathVariable String taskId) {
        dynamicTaskScheduler.cancelTriggerTask(taskId);
    }

    @GetMapping(value = {"/select"})
    public Set<String> select() {
        return dynamicTaskScheduler.taskIds();
    }
}

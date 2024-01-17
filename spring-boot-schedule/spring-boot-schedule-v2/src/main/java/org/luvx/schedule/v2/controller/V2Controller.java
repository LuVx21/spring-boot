package org.luvx.schedule.v2.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.luvx.schedule.v2.entity.TaskEntity;
import org.luvx.schedule.v2.mapper.TaskMapper;
import org.luvx.schedule.v2.service.TaskServiceImpl;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author Ren, Xie
 */
@Slf4j
@RestController
@RequestMapping("/v2")
public class V2Controller {

    @Resource
    TaskServiceImpl taskService;
    @Resource
    TaskMapper      taskMapper;

    @PostMapping(value = {"/createOrUpdate"})
    public void createOrUpdate(@RequestBody TaskEntity entity) {
        boolean b = taskService.addConfigTask(entity);
        log.info("新增任务配置成功?->{}", b);
    }

    @DeleteMapping(value = {"/delete/{taskId}"})
    public void delete(@PathVariable String taskId) {
        taskService.delete(taskId);
    }

    @GetMapping(value = {"/select"})
    public Set<String> select() {
        List<TaskEntity> tasks = taskMapper.selectList(new QueryWrapper<>());
        System.out.println(tasks);
        return null;
    }
}

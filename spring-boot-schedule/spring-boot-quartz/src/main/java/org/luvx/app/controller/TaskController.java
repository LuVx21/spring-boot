package org.luvx.app.controller;

import org.luvx.app.entity.TaskEntity;
import org.luvx.app.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: org.luvx.controller
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/7 13:45
 */
@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskRepository repository;

    @PostMapping(value = "/save")
    public ResponseEntity save(TaskEntity task) {
        repository.save(task);
        return ResponseEntity.ok("新增任务成功");
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity delete(Long taskId) {
        repository.deleteById(taskId);
        return ResponseEntity.ok("删除任务成功");
    }

    @PutMapping(value = "/update")
    public ResponseEntity update(TaskEntity task) {
        Assert.notNull(task.getId(), "指定任务id");
        repository.saveAndFlush(task);
        return ResponseEntity.ok("更新任务成功");
    }

    @GetMapping(value = "/select")
    public ResponseEntity select() {
        List<TaskEntity> list = repository.findAll();
        if (list.size() == 0) {
            repository.save(
                    TaskEntity.builder()
                            .cron("0/5 * * * * ?")
                            .jobKey("foo").jobGroup("bar")
                            .clazz("org.quartz.Job").method("execute")
                            .isAuto(true).isDeleted(false)
                            .build()
            );
        }
        return ResponseEntity.ok(list);
    }
}

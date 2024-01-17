package org.luvx.app.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.luvx.app.entity.TaskEntity;
import org.luvx.app.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Ren, Xie
 * @desc:
 */
@Slf4j
@Service
public class TaskServiceImpl {
    @Autowired
    private TaskRepository repository;

    public List<TaskEntity> getAllTask() {
        TaskEntity task = new TaskEntity();
        task.setIsDeleted(false);
        List<TaskEntity> list = repository.findAll(
                Example.of(task)
        );
        return list;
    }

    public List<TaskEntity> getAllAutoTask() {
        TaskEntity task = new TaskEntity();
        task.setIsDeleted(false);
        task.setIsAuto(true);
        List<TaskEntity> list = repository.findAll(
                Example.of(task)
        );
        return list;
    }
}

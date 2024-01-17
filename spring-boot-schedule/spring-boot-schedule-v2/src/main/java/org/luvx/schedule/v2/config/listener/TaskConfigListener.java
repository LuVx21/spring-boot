package org.luvx.schedule.v2.config.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.luvx.schedule.v2.entity.TaskEntity;
import org.luvx.schedule.v2.mapper.TaskMapper;
import org.luvx.schedule.v2.service.TaskServiceImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * @author Ren, Xie
 */
@Slf4j
@Component
public class TaskConfigListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    TaskServiceImpl taskService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        TaskMapper taskMapper = taskService.getBaseMapper();
        List<TaskEntity> tasks = taskMapper.selectList(new QueryWrapper<>());
        for (TaskEntity task : tasks) {
            log.info("读取任务配置创建定时任务:{}", task.getId());
            taskService.addRunTask(task);
        }
    }
}

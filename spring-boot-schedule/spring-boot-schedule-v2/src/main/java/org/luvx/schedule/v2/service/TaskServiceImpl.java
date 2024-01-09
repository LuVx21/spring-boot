package org.luvx.schedule.v2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.luvx.schedule.v2.dynamic.DynamicTaskScheduler;
import org.luvx.schedule.v2.entity.TaskEntity;
import org.luvx.schedule.v2.mapper.TaskMapper;
import org.luvx.schedule.v2.pojo.TaskRunnable;
import org.luvx.schedule.v2.pojo.WaitScheduledTask;
import org.luvx.schedule.v2.utils.CornUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;

/**
 * @author Ren, Xie
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, TaskEntity> implements IService<TaskEntity> {

    @Resource
    DynamicTaskScheduler dynamicTaskScheduler;

    public boolean addConfigTask(TaskEntity entity) {
        int insert = baseMapper.insert(entity);
        if (insert != 0) {
            addRunTask(entity);
        }
        return insert != 0;
    }

    public void addRunTask(TaskEntity entity) {
        dynamicTaskScheduler.remove(entity.getId());
        String cron = entity.getCron();
        Instant instant = CornUtils.cronNextTime(cron);
        TaskRunnable runnable = new TaskRunnable(entity);
        WaitScheduledTask waitScheduledTask = new WaitScheduledTask(instant, entity, runnable);
        dynamicTaskScheduler.schedule(waitScheduledTask);
    }

    public void delete(String taskId) {
        dynamicTaskScheduler.cancelTask(taskId);
        baseMapper.deleteById(taskId);
    }
}

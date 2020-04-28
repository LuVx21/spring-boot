package org.luvx.app.controller;

import org.luvx.app.entity.TaskEntity;
import org.luvx.app.service.impl.TaskServiceImpl;
import org.luvx.job.ScheduledJob;
import org.luvx.utils.SchedulerUtils;
import org.quartz.Job;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName: org.luvx.controller
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/7 13:45
 */
@RestController
@RequestMapping("/schedule")
public class TaskScheduleController {
    @Autowired
    private TaskServiceImpl service;

    /**
     * 一键启动
     *
     * @return
     * @throws SchedulerException
     * @throws ClassNotFoundException
     */
    @GetMapping(value = "/auto")
    public Object auto() throws SchedulerException, ClassNotFoundException {
        List<TaskEntity> allAutoTask = service.getAllAutoTask();
        for (TaskEntity task : allAutoTask) {
            SchedulerUtils.startJob(task.getCron(),
                    task.getJobKey(),
                    task.getJobGroup(),
                    (Class<? extends Job>) Class.forName(task.getClazz())
            );
        }

        return new HashMap<String, String>(1) {
            {
                put("msg", "启动定时任务成功");
            }
        };
    }

    /**
     * 直接实现接口
     *
     * @return
     */
    @GetMapping(value = "/job2")
    public ResponseEntity scheduleJob2() {
        try {
            SchedulerUtils.startJob("0/5 * * * * ?", "job2", "group2", ScheduledJob.class);
            return ResponseEntity.ok("启动定时任务成功");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("启动定时任务失败");
    }

    @GetMapping(value = "/del_job2")
    public ResponseEntity deleteScheduleJob2() {
        try {
            SchedulerUtils.deleteJob("job2", "group2");
            return ResponseEntity.ok("删除定时任务成功");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("删除定时任务失败");
    }

    // @GetMapping(value = "/job3")
    // public ResponseEntity scheduleJob3() throws SchedulerException {
    //     MethodInvokingJobDetailFactoryBean fa =
    //             SchedulerBeanUtils.createJobDetailFactory(new ScheduledJob1());
    //     JobDetail jobDetail = SchedulerBeanUtils.createJobDetail(fa);
    //     CronTriggerFactoryBean fa1 = SchedulerBeanUtils.createCronTriggerFactory(jobDetail);
    //     CronTrigger cronTrigger = SchedulerBeanUtils.createCronTrigger(fa1);
    //     SchedulerFactoryBean fa3 = SchedulerBeanUtils.createSchedulerFactory(cronTrigger);
    //     Scheduler scheduler1 = SchedulerBeanUtils.createScheduler(fa3);
    //
    //     scheduler1.getListenerManager()
    //             .addJobListener(
    //                     new SchedulerListener(),
    //                     EverythingMatcher.allJobs()
    //                     // KeyMatcher.keyEquals(JobKey.jobKey("job-group-foo", "group-foo"))
    //             );
    //
    //     scheduler1.scheduleJob(jobDetail, cronTrigger);
    //
    //     return ResponseEntity.ok("启动定时任务成功");
    // }
}

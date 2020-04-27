package org.luvx.app.controller;

import org.luvx.job.ScheduledJob;
import org.luvx.utils.SchedulerUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: org.luvx.controller
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/7 13:45
 */
@RestController
@RequestMapping("/quartz")
public class TaskController {
    @Autowired
    public SchedulerUtils schedulerUtils;

    @GetMapping(value = "/job2")
    public ResponseEntity scheduleJob2() {
        try {
            schedulerUtils.startJob("0/5 * * * * ?", "job2", "group2", ScheduledJob.class);
            return ResponseEntity.ok("启动定时器成功");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("启动定时器失败");
    }

    @GetMapping(value = "/del_job2")
    public ResponseEntity deleteScheduleJob2() {
        try {
            schedulerUtils.deleteJob("job2", "group2");
            return ResponseEntity.ok("删除定时器成功");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("删除定时器失败");
    }
}

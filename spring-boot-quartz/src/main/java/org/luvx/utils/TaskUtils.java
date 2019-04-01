package org.luvx.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

/**
 * @ClassName: org.luvx.utils
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/7 10:25
 */
public class TaskUtils {
    private static final Logger logger = LoggerFactory.getLogger(TaskUtils.class);

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");


    private void before(String taskName, long start) {
        String now = formatter.format(start);
        logger.info("--------- 执行定时任务: {} START 开始时间: {} ----------", taskName, now);
    }

    private void after(String taskName, long start, long end) {
        String now = formatter.format(end);
        logger.info("--------- 执行定时任务: {}  END  结束时间: {} ----------", taskName, now);
        logger.info("--------- 执行定时任务: {} 共耗时{}s ----------", taskName, (end - start) / 1000);
    }
}

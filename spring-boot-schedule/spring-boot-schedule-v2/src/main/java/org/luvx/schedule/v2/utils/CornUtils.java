package org.luvx.schedule.v2.utils;

import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;

import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * @author Ren, Xie
 */
public class CornUtils {
    /**
     * CRON 表达式下次执行时间
     *
     * @param expression CRON 表达式
     * @return
     */
    public static Instant cronNextTime(final String expression) {
        CronParser cronParser = ApplicationContextUtils.getBean(CronParser.class);
        ExecutionTime executionTime = ExecutionTime.forCron(cronParser.parse(expression));
        return executionTime.nextExecution(ZonedDateTime.now()).get().toInstant();
    }
}
